package top.kxyu.modules.video.entity;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * @INFO:
 * @author: KXY
 * @Date: Created in 2020/3/21 18:14
 */

public class Video extends File {

	public Video(String pathname) {
		super(pathname);
		this.pathname = pathname;
		int index = pathname.lastIndexOf(".");
		if (index == -1) {
			this.ext = "";
		} else {
			this.ext = pathname.substring(index + 1);
		}
	}

	private String id;
	private int len;
	private int width;
	private int height;
	private String chapter;
	private double frame;
	private String vStream;
	private List<ASStream> asStreams;
	private int numStream = 0;
	private String pName;
	private String pathname;
	private String ext;
	private String name;
	private String indexId;
	private long size;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Video{" +
				"id='" + id + '\'' +
				", len=" + len +
				", width=" + width +
				", height=" + height +
				", chapter=" + chapter +
				", frame=" + frame +
				", vStream='" + vStream + '\'' +
				", asStreams=" + asStreams +
				", numStream=" + numStream +
				", pName='" + pName + '\'' +
				", pathname='" + pathname + '\'' +
				", ext='" + ext + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public double getFrame() {
		return frame;
	}

	public void setFrame(double frame) {
		this.frame = frame;
	}

	public String getvStream() {
		return vStream;
	}

	public void setvStream(String vStream) {
		this.vStream = vStream;
	}

	public List<ASStream> getAsStreams() {
		return asStreams;
	}

	public void setAsStreams(List<ASStream> asStreams) {
		this.asStreams = asStreams;
	}

	public int getNumStream() {
		return numStream;
	}

	public void setNumStream(int numStream) {
		this.numStream = numStream;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}
