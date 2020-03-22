package top.kxyu.modules.video.controller;

import top.kxyu.modules.video.entity.Video;
import top.kxyu.modules.video.util.FfmpegUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @INFO:
 * @author: KXY
 * @Date: Created in 2020/3/21 19:49
 */
public class VideoController {
	public static void getAllVideos(String root, List<Video> allVideos) {
		Video f = new Video(root);
		if ("mkv,mp4,srt,ssa,ass,avi,rmvb,vtt,sub".indexOf(f.getExt()) > 0) {
			allVideos.add(f);
		}
		if (f.isDirectory() && f.list().length > 0) {
			for (String file : f.list()) {
				VideoController.getAllVideos(f.getAbsolutePath() + "\\" + file, allVideos);
			}
		}
	}

	public static void main(String[] args) {
		List<Video> allVideos = new ArrayList<Video>();
		VideoController.getAllVideos("F:\\Videos\\动画", allVideos);
		Iterator iterator = allVideos.iterator();
		while (iterator.hasNext()) {
			Video v = (Video) iterator.next();
			try {

				System.out.println(FfmpegUtil.getVideo(v.getPathname()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		System.out.println(allVideos.size());
	}

}

