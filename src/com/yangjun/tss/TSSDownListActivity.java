package com.yangjun.tss;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yangjun.baidu.utils.BaiduFileService;
import com.yangjun.baidu.utils.DownloadInfo;
import com.yangjun.baidu.utils.MyUtils;


public class TSSDownListActivity {
	public static class TSSDownListFragment extends ListFragment {
		private static final int UPDATE_PROGRESS = 100025;
		private static final int DOWNLOAD_COMPLETE = 100026;
		private static final int UPDATE_GAP = 1000;// 1 second
        private Activity mActivity;
	    private DownloadAdapter mAdapter;
		private BaiduFileService bfs;
		private String[] strName;
		private String[] strSize;
		private String[] strPath;
		long lastUpdatedTime = 0;
		boolean buttonInListClicked = false;
		private int maxNum=5;
		private int currentIndex=0;
		private int hasDownNum=0;
		private long avail=0;
		private boolean iscard=false;
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			DownloadInfo d= downloadInfos.get(position);
			if(d.isEnd())
			{
		
			String path;
			if(iscard)
			{
				path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/TSSDownsFiles";
				
			}
			else
			{
				path="/data/data/com.yangjun.tss/TSSDownsFiles";
				
			}
			File file=new File(path+"/"+downloadInfos.get(position).getFileName());
			this.openFile(file);
			}
			else
			{
				Toast.makeText(getActivity(), "没有下载完成！！", Toast.LENGTH_SHORT).show();
			}
			
		}
	
		private void openFile(File file){ 
		     
		    Intent intent = new Intent(); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    //设置intent的Action属性 
		    intent.setAction(Intent.ACTION_VIEW); 
		    //获取文件file的MIME类型 
		    String type = getMIMEType(file); 
		    //设置intent的data和Type属性。 
		    intent.setDataAndType(Uri.fromFile(file), type); 
		    //跳转 
		    try{
		    startActivity(intent);     //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
		    }
		    catch(ActivityNotFoundException e)
		    {
		    	Toast.makeText(getActivity(), "没有可用的程序可以打开！", Toast.LENGTH_SHORT).show();
		    }
		} 
		private final String[][] MIME_MapTable={ 
	            //{后缀名，MIME类型} 
	            {".3gp",    "video/3gpp"}, 
	            {".apk",    "application/vnd.android.package-archive"}, 
	            {".asf",    "video/x-ms-asf"}, 
	            {".avi",    "video/x-msvideo"}, 
	            {".bin",    "application/octet-stream"}, 
	            {".bmp",    "image/bmp"}, 
	            {".c",  "text/plain"}, 
	            {".class",  "application/octet-stream"}, 
	            {".conf",   "text/plain"}, 
	            {".cpp",    "text/plain"}, 
	            {".doc",    "application/msword"}, 
	            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
	            {".xls",    "application/vnd.ms-excel"},  
	            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
	            {".exe",    "application/octet-stream"}, 
	            {".gif",    "image/gif"}, 
	            {".gtar",   "application/x-gtar"}, 
	            {".gz", "application/x-gzip"}, 
	            {".h",  "text/plain"}, 
	            {".htm",    "text/html"}, 
	            {".html",   "text/html"}, 
	            {".jar",    "application/java-archive"}, 
	            {".java",   "text/plain"}, 
	            {".jpeg",   "image/jpeg"}, 
	            {".jpg",    "image/jpeg"}, 
	            {".js", "application/x-javascript"}, 
	            {".log",    "text/plain"}, 
	            {".m3u",    "audio/x-mpegurl"}, 
	            {".m4a",    "audio/mp4a-latm"}, 
	            {".m4b",    "audio/mp4a-latm"}, 
	            {".m4p",    "audio/mp4a-latm"}, 
	            {".m4u",    "video/vnd.mpegurl"}, 
	            {".m4v",    "video/x-m4v"},  
	            {".mov",    "video/quicktime"}, 
	            {".mp2",    "audio/x-mpeg"}, 
	            {".mp3",    "audio/x-mpeg"}, 
	            {".mp4",    "video/mp4"}, 
	            {".mpc",    "application/vnd.mpohun.certificate"},        
	            {".mpe",    "video/mpeg"},   
	            {".mpeg",   "video/mpeg"},   
	            {".mpg",    "video/mpeg"},   
	            {".mpg4",   "video/mp4"},    
	            {".mpga",   "audio/mpeg"}, 
	            {".msg",    "application/vnd.ms-outlook"}, 
	            {".ogg",    "audio/ogg"}, 
	            {".pdf",    "application/pdf"}, 
	            {".png",    "image/png"}, 
	            {".pps",    "application/vnd.ms-powerpoint"}, 
	            {".ppt",    "application/vnd.ms-powerpoint"}, 
	            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
	            {".prop",   "text/plain"}, 
	            {".rc", "text/plain"}, 
	            {".rmvb",   "audio/x-pn-realaudio"}, 
	            {".rtf",    "application/rtf"}, 
	            {".sh", "text/plain"}, 
	            {".tar",    "application/x-tar"},    
	            {".tgz",    "application/x-compressed"},  
	            {".txt",    "text/plain"}, 
	            {".wav",    "audio/x-wav"}, 
	            {".wma",    "audio/x-ms-wma"}, 
	            {".wmv",    "audio/x-ms-wmv"}, 
	            {".wps",    "application/vnd.ms-works"}, 
	            {".xml",    "text/plain"}, 
	            {".z",  "application/x-compress"}, 
	            {".zip",    "application/x-zip-compressed"}, 
	            {"",        "*/*"}   
	        }; 
		private String getMIMEType( File file) { 
		     
		    String type="*/*"; 
		    String fName = file.getName(); 
		    //获取后缀名前的分隔符"."在fName中的位置。 
		    int dotIndex = fName.lastIndexOf("."); 
		    if(dotIndex < 0){ 
		        return type; 
		    } 
		    /* 获取文件的后缀名*/ 
		    String end=fName.substring(dotIndex,fName.length()).toLowerCase(); 
		    if(end=="")return type; 
		    //在MIME和文件类型的匹配表中找到对应的MIME类型。 
		    for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？ 
		        if(end.equals(MIME_MapTable[i][0])) 
		            type = MIME_MapTable[i][1]; 
		    }        
		    return type; 
		} 
		private   Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case UPDATE_PROGRESS:
					
					if (System.currentTimeMillis() - lastUpdatedTime > UPDATE_GAP
							&& buttonInListClicked == false) {
						mAdapter.notifyDataSetChanged();
						lastUpdatedTime = System.currentTimeMillis();
					}
					break;
				case DOWNLOAD_COMPLETE:
					Toast.makeText(mActivity,
							downloadInfos.get(msg.arg1).getFileName() + " 下载完成",
							Toast.LENGTH_SHORT).show();
					  downloadInfos.get(msg.arg1).setEnd(true);
					  hasDownNum--;
					  if(currentIndex<downloadInfos.size()-1&&hasDownNum<maxNum)
						  
					  {
						  new DownloadThread(currentIndex+1).start();
							downloadInfos.get(currentIndex+1).setStart(true);
							  hasDownNum++;
						     currentIndex++;
					  }
					  break;
					  default:
						  Toast.makeText(mActivity,
									"下载"+downloadInfos.get(msg.what-1).getFileName()+":内存不足！",
									Toast.LENGTH_SHORT).show();
					break;
				}
			}

		};
		public long getSDAvailaleSize(){

			File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径

			StatFs stat = new StatFs(path.getPath()); 

			 

			long blockSize = stat.getBlockSize(); 

			 

			long availableBlocks = stat.getAvailableBlocks();

			 

			return availableBlocks * blockSize/1024; 

			//(availableBlocks * blockSize)/1024      KIB 单位

			//(availableBlocks * blockSize)/1024 /1024  MIB单位

			 

			}
		public long readSystemAvailaleSize() {
			 File root = Environment.getRootDirectory();
			 StatFs sf = new StatFs(root.getPath());
			 long blockSize = sf.getBlockSize();
			 long blockCount = sf.getBlockCount();
			 long availCount = sf.getAvailableBlocks();
			 return availCount*blockSize/1024;
			}

		ArrayList<DownloadInfo> downloadInfos ;

		@Override
		public void  onActivityCreated(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mActivity=this.getActivity();
			downloadInfos=((TSSMainActivity)this.getActivity()).getDowns();
			mAdapter = new DownloadAdapter(this.getActivity());
			setListAdapter(mAdapter);
		     iscard=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		  
			for (int i = 0; i < downloadInfos.size()&&this.hasDownNum<this.maxNum; i++) {
				if(!downloadInfos.get(i).isStart())
				{
				new DownloadThread(i).start();
				downloadInfos.get(i).setStart(true);
				this.hasDownNum++;
				this.currentIndex=i;
				}
			
			}
			mAdapter.notifyDataSetChanged();
		}

		
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			((TSSMainActivity)this.getActivity()).setDowns(downloadInfos);
		}


		public  class ViewHolder {
			TextView fileNameText;
			TextView sizeText;
			TextView hasSizeText;
			TextView perText;
			ImageView image;
			ProgressBar progress;
			
		}

		public  class DownloadAdapter extends BaseAdapter {

			  private final LayoutInflater mInflater;
			  public DownloadAdapter(Context context){
		    		
	    		 mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		
	    	}
			@Override
			public int getCount() {
				return downloadInfos.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO DO observe this result in console ,and see if it works.
			
					System.out.println("refreshing" + position);
				ViewHolder viewHolder;
				if (convertView == null) {
					
					convertView = mInflater.inflate(R.layout.downfile_item, null);
					viewHolder = new ViewHolder();
					viewHolder.fileNameText = (TextView) convertView
							.findViewById(R.id.textView1);
					viewHolder.progress = (ProgressBar) convertView
							.findViewById(R.id.progressBar1);
					viewHolder.perText=	(TextView) convertView
							.findViewById(R.id.textView2);
					viewHolder.hasSizeText=(TextView) convertView
							.findViewById(R.id.textView4);
					viewHolder.sizeText=(TextView) convertView
							.findViewById(R.id.textView3);
					viewHolder.image=(ImageView) convertView.findViewById(R.id.imageView1);
					convertView.setTag(viewHolder);
				} else {
					viewHolder = (ViewHolder) convertView.getTag();
				}
				viewHolder.fileNameText
						.setText(downloadInfos.get(position).getFileName());
				viewHolder.progress
						.setProgress(downloadInfos.get(position).getProgress());
				viewHolder.perText.setText(downloadInfos.get(position).getProgress()+"%");
				DecimalFormat nf1 = new DecimalFormat("#0.00");
				
				viewHolder.sizeText.setText(nf1.format((long)(downloadInfos.get(position).getSize()*(downloadInfos.get(position).getProgress()/100.0))/1024.0/1024)+"MB"+" / ");
				viewHolder.hasSizeText.setText(nf1.format(((long)downloadInfos.get(position).getSize())/1024.0/1024)+"MB"+"");
				viewHolder.image.setImageResource(MyUtils.getImageId(downloadInfos.get(position).getFileName()));
				return convertView;
			}
		}

		// a thread class to simulate downloading  files from the Internet
		public   class DownloadThread extends Thread {
			int index;

			DownloadThread(int index) {
				this.index = index;
			}

			@Override
			public void run() {
				if(bfs==null)
				bfs=new BaiduFileService();
				
				String path="";
				long avail;
				if(iscard)
				{
					path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/TSSDownsFiles";
					File dest=new File(path);
					avail=getSDAvailaleSize();
					if(!dest.exists())
					{
						dest.mkdirs();
					}
				}
				else
				{
					path="/data/data/com.yangjun.tss/TSSDownsFiles";
					File dest=new File(path);
					avail=readSystemAvailaleSize();
					if(!dest.exists())
					{
						dest.mkdirs();
					}
				}
					
						
			    URL url;
			    if(avail>downloadInfos.get(index).getSize()/1024*1.2+1024*10)
			    {
			           try {
							
			    	 
			    		  
			    	   url = new URL(bfs.generateUrl(downloadInfos.get(index).getPath()));
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						       
					          DataInputStream in = new DataInputStream(connection.getInputStream());
					          DataOutputStream out = new DataOutputStream(new FileOutputStream((path+"/"+downloadInfos.get(index).getFileName())));
					          byte[] buffer = new byte[4096];
					           int count = 0;
					           int allsize=0;
					           while ((count = in.read(buffer)) > 0) {
					              out.write(buffer, 0, count);
					              allsize+=count;
					              downloadInfos.get(index).setProgress((int) (allsize*100/ downloadInfos.get(index).getSize()));
					              Message msg = mHandler.obtainMessage();
									msg.what = UPDATE_PROGRESS;
									msg.arg1 = this.index;
									msg.sendToTarget();  
					          }
					          out.close();
					          in.close();
					            Message msg = mHandler.obtainMessage();
								msg.what = DOWNLOAD_COMPLETE;
								msg.arg1 = this.index;
								msg.sendToTarget();
					            Message finalUpdate = mHandler.obtainMessage();
								finalUpdate.what = UPDATE_PROGRESS;
								mHandler.sendMessageDelayed(finalUpdate, UPDATE_GAP + 100);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					       
			    }
			    else
			    {
			    	
			    	mHandler.sendEmptyMessage(index+1);
			    	((TSSMainActivity)TSSDownListFragment.this.getActivity()).setNomem(true);
			    	//downloadInfos.get(index).setStart(true);
			    	//downloadInfos.get(index).setEnd(true);
			    }
				         
					
				
				
			}
		}
	}

}
