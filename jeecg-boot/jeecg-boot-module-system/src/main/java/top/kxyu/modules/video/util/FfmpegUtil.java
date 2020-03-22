package top.kxyu.modules.video.util;


import top.kxyu.modules.video.entity.ASStream;
import top.kxyu.modules.video.entity.Video;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @INFO:
 * @author: KXY
 * @Date: Created in 2020/3/21 18:36
 */
public class FfmpegUtil {
	private static final String FFMPEGPATH = "\"D:\\Program Files\\Tools_Media\\ffmpeg.exe\" ";

	public static String signExec(String cmd) {
		//执行命令生成cube
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		AtomicReference<String> info = new AtomicReference<>("");
		try {
			System.out.println("\n\nCMD:" + cmd + "\n\n");
			p = rt.exec(cmd);
			//获取进程的标准输入流
			final InputStream is1 = p.getInputStream();
			//获取进城的错误流
			final InputStream is2 = p.getErrorStream();

			//启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
			new Thread(() -> {
				BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
				try {
					String line1 = null;
					while ((line1 = br1.readLine()) != null) {
						System.out.println(line1);

					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is1.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			new Thread(() -> {
				BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
				try {
					String line2 = null;
					while ((line2 = br2.readLine()) != null) {
						if (line2.indexOf("Invalid UTF-8 in decoded subtitles text") > 0) {
							info.set("code ERROR");
						}
						System.out.println(line2);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			p.waitFor();
			p.destroy();
			System.out.println("DONE...");
		} catch (Exception e) {
			try {
				assert p != null;
				p.getErrorStream().close();
				p.getInputStream().close();
				p.getOutputStream().close();
			} catch (Exception ignored) {
			}
		}
		return info.get();
	}

	public static Video getVideo(String path) throws IOException {
		String cmd = FFMPEGPATH;
		cmd += " -i \"" + path + "\"";
		Runtime runtime = Runtime.getRuntime();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runtime.exec(cmd).getErrorStream()));
		String line;
		StringBuffer stringBuffer = new StringBuffer();
		Video v = new Video(path);
		List<ASStream> asStreams = new ArrayList<>();
		int no;
		int numStream = 0;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("    Stream ")) {
				numStream += 1;
				String[] streamInfo = line.split(":");
				no = Integer.valueOf(Integer.valueOf(streamInfo[1].substring(0, 1)));
				if (no > 0) {
					ASStream asStream = new ASStream();
					if (streamInfo[1].indexOf("(") > 0) {
						asStream.setLang(streamInfo[1].substring(2, streamInfo[1].length() - 1));
					}
					asStream.setNo(no);
					asStream.setType(streamInfo[2].trim());
					asStream.setInfo(streamInfo[3].trim());
					if (streamInfo[3].indexOf("default") > 0) {
						asStream.setDef(true);
					}
					asStreams.add(asStream);
				} else {
					if (!"Subtitle".equals(streamInfo[2].trim())) {
						String pattern="(\\d{3,4})x(\\d{3,4})";
						Pattern r =Pattern.compile(pattern);
						Matcher m = r.matcher(line);
						if(m.find()){
							v.setWidth(Integer.valueOf(m.group(1)));
							v.setHeight(Integer.valueOf(m.group(2)));
						}
					}
				}
			}
		}
		v.setAsStreams(asStreams);
		v.setNumStream(numStream);
		return v;
//		System.out.println(stringBuffer.toString());
	}

	public static void covMkv(Video video, String encoder, String outPutPath) {
		outPutPath = chkOutPutPath(video, outPutPath);
		StringBuffer cmd = new StringBuffer();
		int rate = (int) (40000 * (double) video.getHeight() * (double) video.getHeight() / 18662.4);
		cmd.append(FFMPEGPATH).append(" -y -i ").append("\"").append(video.getAbsolutePath()).append("\"");
		video.getNumStream();
		StringBuffer audioStreamCmd = new StringBuffer(" ");
		StringBuffer mapCmd = new StringBuffer(" -map 0:0 ");
		StringBuffer videoStreamCmd = new StringBuffer(" ");
		StringBuffer otherStreamCmd = new StringBuffer(" ");
		for (int i = 1; i < video.getNumStream(); i++) {
			mapCmd.append(" -map 0:" + i);
			if ("Audio".equals(video.getAsStreams().get(i - 1).getType())) {
				audioStreamCmd
						.append(" -c:a:").append(i).append(" acc ")
						.append(" -b:a:").append(i).append(" 256k ")
						.append(" -ar:").append(i).append(" 48000 ");
			} else if ("Subtitle".equals(video.getAsStreams().get(i - 1).getType())) {
				otherStreamCmd.append(" -c:s:").append(i).append(" copy ");
			}
		}
		videoStreamCmd.append(" -b:v:0 ").append(rate).append(" -c:v:0 ").append("".equals(encoder) ? "hevc_nvenc" : encoder);
		cmd.append(mapCmd).append(audioStreamCmd).append(videoStreamCmd).append(otherStreamCmd).append("\"").append(outPutPath).append(video.getName().replace("." + video.getExt(), "")).append("mkv\"");
		FfmpegUtil.signExec(cmd.toString());
	}

	public static void covVtt(Video video, String outPutPath) {
		outPutPath = chkOutPutPath(video, outPutPath);
		StringBuffer cmd = new StringBuffer();
		cmd.append(FFMPEGPATH).append(" -y -i ").append("\"").append(video.getAbsolutePath()).append("\"");
		video.getNumStream();
		StringBuffer mapCmd = new StringBuffer(" -map 0:0 ");
		StringBuffer otherStreamCmd = new StringBuffer(" ");

		otherStreamCmd.append(" -c:s:").append(0).append(" webvtt ");

		cmd.append(mapCmd).append(otherStreamCmd).append("\"").append(outPutPath).append(video.getName().replace("." + video.getExt(), "")).append(".vtt\"");
//		System.out.println(cmd.toString());
		if ("code ERROR".equals(FfmpegUtil.signExec(cmd.toString()))) {
			if ("code ERROR".equals(FfmpegUtil.signExec(cmd.toString().replace(" -y -i ", " -y -sub_charenc GBK -i ")))) {
				if ("code ERROR".equals(FfmpegUtil.signExec(cmd.toString().replace("GBK", "BIG5")))) {
					return;
				}
			}
		}
	}

	private static String chkOutPutPath(Video video, String outPutPath) {
		if ("".equals(outPutPath)) {
			outPutPath = video.getPath().replace(video.getName(), "") + "\\output\\";
			File f = new File(outPutPath);
			if (!f.isDirectory()) {
				f.mkdir();
			}
			return outPutPath;
		} else {
			return outPutPath;
		}
	}

	public static void covMp4(Video video, String encoder, String outPutPath) {
		outPutPath = chkOutPutPath(video, outPutPath);
		StringBuffer cmd = new StringBuffer();
		int rate = (int) (40000 * (double) video.getHeight() * (double) video.getHeight() / 18662.4);
		cmd.append(FFMPEGPATH).append(" -y -i ").append("\"").append(video.getAbsolutePath()).append("\"");
		video.getNumStream();
		StringBuffer audioStreamCmd = new StringBuffer(" ");
		StringBuffer mapCmd = new StringBuffer(" -map 0:0 ");
		StringBuffer videoStreamCmd = new StringBuffer(" ");
		StringBuffer otherStreamCmd;
		for (int i = 1; i < video.getNumStream(); i++) {
			mapCmd.append(" -map 0:" + i);
			if ("Audio".equals(video.getAsStreams().get(i - 1).getType()) && video.getAsStreams().get(i - 1).isDef()) {
				audioStreamCmd
						.append(" -c:a:").append(i).append(" acc ")
						.append(" -b:a:").append(i).append(" 256k ")
						.append(" -ar:").append(i).append(" 48000 ");
				break;
			}
		}
		videoStreamCmd.append(" -b:v:0 ").append(rate).append(" -c:v:0 ").append("".equals(encoder) ? " hevc_nvenc " : encoder);
		cmd.append(mapCmd).append(audioStreamCmd).append(videoStreamCmd).append(" \"").append(outPutPath).append(video.getName().replace("." + video.getExt(), "")).append(".mp4\"");

		FfmpegUtil.signExec(cmd.toString());
		for (int i = 1; i < video.getNumStream(); i++) {
			cmd = new StringBuffer().append(FFMPEGPATH).append(" -y -i ").append("\"").append(video.getAbsolutePath()).append("\"");
			otherStreamCmd = new StringBuffer(" ");
			mapCmd = new StringBuffer(" ");
			if ("Subtitle".equals(video.getAsStreams().get(i - 1).getType())) {
				mapCmd.append(" -map 0:" + i);
				otherStreamCmd
						.append(" -c:s:").append(i).append(" ssa ");
				cmd.append(mapCmd).append(otherStreamCmd).append("\"").append(outPutPath).append(video.getName().replace("." + video.getExt(), "")).append("[" + video.getAsStreams().get(i - 1).getLang() + "]").append(".vtt\"");
				FfmpegUtil.signExec(cmd.toString());
			}
		}

	}

	public static void main(String[] args) throws IOException {
		Video v = FfmpegUtil.getVideo("F:\\Videos\\动画\\宫崎骏动画电影合集国粤日三语720P\\[借东西的小人阿莉埃蒂].The.Borrower.Arrietty.REPACK.2010.Bluray...AC3.3Audios].mkv");
		covMp4(v, "h264_nvenc", "");
//		covVtt(v, "");
	}
}
