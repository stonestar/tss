package com.yangjun.baidu.utils;

public class BaiduCourseData implements Comparable<BaiduCourseData>{
private String coursename,courseNum,courseTeachers,coursePath;
private FileNode file;
public FileNode getFile()
{
	return this.file;
}
public String getCoursename() {
	return coursename;
}
public String getCourseNum() {
	return courseNum;
}
public String getCourseTeachers() {
	return courseTeachers;
}
public String getCoursePath() {
	return coursePath;
}
public String getCourseTime() {
	return courseTime;
}
private String courseTime;
private boolean ismycourse;
public boolean isIsmycourse() {
	return ismycourse;
}
public void setIsmycourse(boolean ismycourse) {
	this.ismycourse = ismycourse;
}
public BaiduCourseData(String name,String num,String teacher,String path,String time,boolean ismycourse)
{
	this.coursename=name;
	this.courseNum=num;
	this.courseTeachers=teacher;
	this.coursePath=path;
	this.courseTime=time;
	this.ismycourse=ismycourse;
	
}
public BaiduCourseData()
{
	
}
@Override
public int compareTo(BaiduCourseData arg0) {
	// TODO Auto-generated method stub
	if(this.ismycourse)
		return 1;
	else
	{
		if(arg0.isIsmycourse())
			return -1;
		else
			return 0;
	}
	
}
}
