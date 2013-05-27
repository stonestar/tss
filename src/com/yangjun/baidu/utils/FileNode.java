package com.yangjun.baidu.utils;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FileNode {
private HashSet<FileNode> childSet;
public HashSet<FileNode> getChildSet() {
	return childSet;
}
public FileNode getParents() {
	return parents;
}
public String getName() {
	return name;
}
public boolean isDir() {
	return isDir;
}
public long getSize() {
	return size;
}
private FileNode parents;
private String name;
private boolean isDir;
private long size;
private String path;
public String getPath() {
	return path;
}
public FileNode(FileNode parents,String name,long size,String path,boolean isDir)
{
	this.childSet=new HashSet<FileNode>();
	this.parents=parents;
	this.name=name;
	this.size=size;
	this.path=path;
	this.isDir=isDir;
}
public FileNode(FileNode parents,String name,String path)
{
	this.childSet=new HashSet<FileNode>();
	this.parents=parents;
	this.name=name;
	this.path=path;
	this.size=-1;
	this.isDir=true;
}
public FileNode(FileNode parents,String name,long size,String path)
{
	this.childSet=null;
	this.parents=parents;
	this.name=name;
	this.path=path;
	this.size=size;
	this.isDir=false;
}
public static ArrayList<String> getAllChildFiles(FileNode root)
{
	if(root==null)
		return null;
	ArrayList<String> list=new ArrayList<String>();
	if(!root.isDir())
	{
	
		list.add(root.path);
		return list;
	}
	else
	{
		
		HashSet<FileNode> childs=root.getChildSet();
		Iterator<FileNode> ite=childs.iterator();
		while(ite.hasNext())
		{
			FileNode temp=ite.next();
			ArrayList<String> templist=getAllChildFiles(temp);
			if(templist!=null)
			for(int i=0;i<templist.size();i++)
			{
				list.add(templist.get(i));
			}
		}
		return list;
	}
}
}
