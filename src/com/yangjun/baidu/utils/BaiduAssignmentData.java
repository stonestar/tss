package com.yangjun.baidu.utils;

public class BaiduAssignmentData {
private String nums,duetime,des;



public String getNums() {
	return nums;
}

public void setNums(String nums) {
	this.nums = nums;
}

public String getDuetime() {
	return duetime;
}

public void setDuetime(String duetime) {
	this.duetime = duetime;
}

public String getDes() {
	return des;
}

public void setDes(String des) {
	this.des = des;
}
public BaiduAssignmentData(String num,String t,String des)
{
	this.nums=num;
	this.duetime=t;
	this.des=des;
}
}
