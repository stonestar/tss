package com.yangjun.baidu.utils;

public class BaiduRefData implements Comparable<BaiduRefData>{
private String name,path;
private long size;
private boolean isDir;
public String getName()
{
	return this.name;
}
public String getPath()
{
	return this.path;
	
}
public  boolean isDir()
{
	return this.isDir;
	
}
public long getSize()
{
	return this.size;
}
public BaiduRefData(String name,String path,long size,boolean isDir)
{
	this.name=name;
	this.path=path;
	this.size=size;
	this.isDir=isDir;
}
@Override
public int compareTo(BaiduRefData arg0) {
	// TODO Auto-generated method stub
	if(this.isDir)
		return 1;
	else
	{
		if(arg0.isDir)
			return -1;
		else
			return 0;
	}
	
}
}
