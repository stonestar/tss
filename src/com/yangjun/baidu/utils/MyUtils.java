package com.yangjun.baidu.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yangjun.tss.R;



public class MyUtils {
public static int getImageId(String path)
{   
	if(path.endsWith("/"))
    return R.drawable.folder;
	else
	{
		int index=path.lastIndexOf(".");
		if(index!=-1)
		{
			String str=path.substring(index+1).toLowerCase();
			if(str!=null)
			{
				if(str.equals("txt"))
					return R.drawable.type_txt;
				if(str.equals("doc")||str.equals("docx"))
					return R.drawable.type_docx;
				if(str.equals("xls")||str.equals("xlsx")||str.equals("xsl"))
					return R.drawable.type_xlsx;
				if(str.equals("ppt")||str.equals("pptx"))
					return R.drawable.type_pptx;
				if(str.equals("avi"))
					return R.drawable.type_avi;
				if(str.equals("wmv"))
					return R.drawable.type_wmv;
				if(str.equals("bmp"))
					return R.drawable.type_bmp;
				if(str.equals("jpeg")||str.equals("jpg"))
					return R.drawable.type_jpeg;
				if(str.equals("pdf"))
					return R.drawable.type_pdf;
				if(str.equals("rar"))
					return R.drawable.type_rar;
				if(str.equals("zip"))
					return R.drawable.type_zip;
				if(str.equals("xml"))
					return R.drawable.type_xml;
				if(str.equals("c"))
					return R.drawable.type_c;
				if(str.equals("java"))
					return R.drawable.type_java;
				if(str.equals("cpp"))
					return R.drawable.type_cpp;
				if(str.equals("js"))
					return R.drawable.type_js;
				if(str.equals("php"))
					return R.drawable.type_php;
				if(str.equals("jar"))
					return R.drawable.type_jar;
			}
		}
	}
	return R.drawable.type_undefined;
	
}
public static ArrayList<BaiduAssignmentData> getAssignment(String nums)
{
	   String url="http://218.94.159.102/tss/en/"+nums+"/assignment/index.html";
	   ArrayList<BaiduAssignmentData> res=new ArrayList<BaiduAssignmentData>();
	   Document doc;
  	
	  
		try {
		doc = (Document) Jsoup.connect(url).get();
		Elements ets= doc.select("center table.forum_text tbody");
		if(ets==null||ets.size()==0)
		return null;
		for(int i=0;i<ets.size();i++) {
			Element temp=ets.get(i);
			Elements child=temp.children();
		    String dueTime=child.get(1).children().get(1).text();
		    String dep=child.get(3).children().get(1).text();
		    BaiduAssignmentData b=new BaiduAssignmentData(nums,dueTime,dep);
		    res.add(b);
		}
		return res;
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null;
}
}
