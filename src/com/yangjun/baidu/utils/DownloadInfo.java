package com.yangjun.baidu.utils;
public class DownloadInfo {
	public DownloadInfo(String fileName,String path,long size,boolean isEnd) {
		this.fileName = fileName;
		this.size=size;
		this.path=path;
		this.isEnd=isEnd;

	}
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		DownloadInfo d=(DownloadInfo)o;
		
		return this.path.equals(d.getPath());
	}
    
	private String fileName,path;
	private long size;
	private int progress = 0;
	private boolean isStart=false;
	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	private boolean isEnd=false;
	
}