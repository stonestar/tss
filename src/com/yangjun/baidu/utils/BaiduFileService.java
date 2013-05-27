package com.yangjun.baidu.utils;





import java.io.File;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
public class BaiduFileService {
	private final Log log = LogFactory.getLog(BaiduFileService.class);
	// ----------------------------------------
	private  String host ;
	private  String accessKey ;
	private  String secretKey;
	private  String bucket;
	// ----------------------------------------
	//private static String object = "/2013summer/first.txt";
	//private static File destFile = new File("test.pdf");
	private  BCSCredentials credentials;
	private  BaiduBCS baiduBCS;
	private  ListObjectRequest listObjectRequest;
	private  BaiduBCSResponse<ObjectListing> response ;
	private  FileNode root;
	private String nums="";
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	/**
	 * @param args
	 */
	public BaiduFileService()
	{
		init();
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public  void init()
	{
		root=new FileNode(null,"/","/");
		host = "bcs.duapp.com";
		accessKey = "BC3b144d1d406bcf5aba7f0b694a96c4";
		secretKey = "F22f100d34f35f55cef25757e8d678ab";
		bucket = "yangjuntss";
		credentials = new BCSCredentials(accessKey, secretKey);
		baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8");
	    listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			// listObjectRequest.setPrefix("/1/");
			// listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
	   
		
	}
	public  FileNode buildTreeForFile(FileNode root)
	{
		if(root==null||(!root.isDir()))
		{
			if(root!=null);
				//System.out.println(root.getPath());
			return root;
		}
			
		else
		{
			 String path=root.getPath();
			 int index=path.length();
			 listObjectRequest.setPrefix(path);
			 listObjectRequest.setListModel(2);
			 response = baiduBCS.listObject(listObjectRequest);
			for (ObjectSummary os : response.getResult().getObjectSummaries()) {
				String str=os.getName();
				boolean isDir=str.endsWith("/");
			    String name=str.substring(index);
			    long size=os.getSize();
				FileNode temp=new FileNode(root,name,size,str,isDir);
			    temp=buildTreeForFile(temp);
				root.getChildSet().add(temp);
				
			}
			return root;
		}
	}
    
	public  List<BaiduCourseData> listCourse(String time) {
		
	
		if(!time.equals("AllTerm/"))
		{
		 List<BaiduCourseData> list=new ArrayList<BaiduCourseData>();
		 listObjectRequest.setPrefix("/"+time);
		 listObjectRequest.setListModel(2);
		 response = baiduBCS.listObject(listObjectRequest);
		
	
		
		 for (ObjectSummary os : response.getResult().getObjectSummaries()) {
				
			 int index=time.length()+1;
			    String str=os.getName();
				boolean isDir=str.endsWith("/");
				
			    String name=str.substring(index);
			    long size=os.getSize();
			    
			    BaiduCourseData temp=new BaiduCourseData(name,"","",str,time.substring(0, time.length()-1),this.nums.contains(name.split("_")[2]));
			 
			 list.add(temp);
				
			}
		 Collections.sort(list, new Comparator<BaiduCourseData>() {
			 
	            @Override
	            public int compare(BaiduCourseData o1, BaiduCourseData o2) {
	               
	                    return o2.compareTo(o1);
	            }
	        });
		 return list;
		}
		else
		{
			List<BaiduCourseData> list=new ArrayList<BaiduCourseData>();
			 listObjectRequest.setPrefix("/");
			 listObjectRequest.setListModel(2);
			 response = baiduBCS.listObject(listObjectRequest);
			
		
			for(ObjectSummary os1 : response.getResult().getObjectSummaries())
			{
			
				     String str1=os1.getName();
				     listObjectRequest.setPrefix(str1);
					 listObjectRequest.setListModel(2);
					 BaiduBCSResponse<ObjectListing> temp1= baiduBCS.listObject(listObjectRequest);
				for (ObjectSummary os : temp1.getResult().getObjectSummaries()) {
					
				 int index=str1.length();
				    String str=os.getName();
					boolean isDir=str.endsWith("/");
					
				    String name=str.substring(index);
				    long size=os.getSize();
				    
				    BaiduCourseData temp=new BaiduCourseData(name,"","",str,str1.substring(1, str1.length()-1),this.nums.contains(name.split("_")[2]));
				 
				 list.add(temp);
					
				}
			}
			 Collections.sort(list, new Comparator<BaiduCourseData>() {
				 
		            @Override
		            public int compare(BaiduCourseData o1, BaiduCourseData o2) {
		               
		                    return o2.compareTo(o1);
		            }
		        });
			 return list;	
			}
		
		
	}
	/**
	 * 获取有几个季度
	 * @return
	 */
	public String getTermArray()
	{
		 listObjectRequest.setPrefix("/");
		 listObjectRequest.setListModel(2);
		 response = baiduBCS.listObject(listObjectRequest);
		  String res="AllTerm";
		  for (ObjectSummary os : response.getResult().getObjectSummaries()) {
				String str=os.getName();
				str=str.substring(1, str.length()-1);
				res+="_"+str;
			}
		  return res;
	}
	/**
	 *获取文件夹下的文件
	 * @param path
	 * @return
	 */
	public List<BaiduRefData> getFileOfDir(String path)
	{
		 
		 listObjectRequest.setPrefix(path);
		 listObjectRequest.setListModel(2);
		 response = baiduBCS.listObject(listObjectRequest);
		 List<BaiduRefData> list=new ArrayList<BaiduRefData>();
	
		 int index=path.length();
		 for (ObjectSummary os : response.getResult().getObjectSummaries()) {
				String str=os.getName();
				boolean isDir=str.endsWith("/");
			    String name=str.substring(index);
			    long size=os.getSize();
				BaiduRefData temp=new BaiduRefData(name,str,size,isDir);
			 
			list.add(temp);
				
			}
		 Collections.sort(list, new Comparator<BaiduRefData>() {
			 
	            @Override
	            public int compare(BaiduRefData o1, BaiduRefData o2) {
	               
	                    return o2.compareTo(o1);
	            }
	        });
		 return list;
		}
		
	
	
	public  String generateUrl(String object) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
		return baiduBCS.generateUrl(generateUrlRequest);
	}
	public  void getObjectWithDestFile(String path,String name) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, path);
		File file=new File(name);
		
		baiduBCS.getObject(getObjectRequest, file);
	}
}
