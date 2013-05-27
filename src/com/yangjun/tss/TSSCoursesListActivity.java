package com.yangjun.tss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Test;

import com.yangjun.baidu.utils.BaiduCourseData;
import com.yangjun.baidu.utils.BaiduFileService;
import com.yangjun.baidu.utils.DownloadInfo;
import com.yangjun.tss.TSSFilesListActivity.FilesFragment;
import com.yangjun.tss.TSSFilesListActivity.FilesThread;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class TSSCoursesListActivity {

	
	
	
	public static class CoursesFragment extends ListFragment 
	{
		
		   private MyCourseListAdapter mAdapter;
		   private  List<BaiduCourseData> cffs;
		   private String term="2013 Spring/";
		   private String nums="";
		  
		    
		   public String getNums() {
			return nums;
		}

		 

	

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// TODO Auto-generated method stub
			inflater.inflate(R.menu.coursemenu, menu);
		}


		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			int index=item.getOrder();
			if(index==100)
			{
			for (int i = 0; i < mAdapter.getCount(); i++) {
				
				
			}
			}
			return super.onOptionsItemSelected(item);
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			Log.i("Course", "CreateView");
			return super.onCreateView(inflater, container, savedInstanceState);
		}


		@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			Log.i("Course", "DetoryView");
			 this.getActivity().unregisterReceiver(mBroadcastReceiver);
		}


		public void setNums(String nums) {
			this.nums = nums;
		}


		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			System.out.println("ClickPath:"+cffs.get(position).getCoursePath());
			((TSSMainActivity)this.getActivity()).setPath(cffs.get(position).getCoursePath());
			((TSSMainActivity)this.getActivity()).getActionBar().selectTab(((TSSMainActivity)this.getActivity()).getActionBar().getTabAt(1));			
		}


		public void onActivityCreated(Bundle savedInstanceState) {
	             super.onActivityCreated(savedInstanceState);
	             Log.i("Course", "ActivityCreated");
	            
	             setHasOptionsMenu(true);
	             setEmptyText("加载中！  ");
	             this.setNums(((TSSMainActivity)this.getActivity()).getNums());
	           //  setHasOptionsMenu(true);
	             SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
	     	    term= sharedPreferences.getString("T", "2013 Spring");
	     	    term+="/";
	             new Thread()
	     		{
	     			public void run()
	     			{
	     			  
	     				 BaiduFileService bfs=new BaiduFileService();
	     				 bfs.setNums(getNums());
	     				 cffs=bfs.listCourse(term);
	     				 handler.sendEmptyMessage(0);
	     				
	     			}
	     		}.start();
                
	     		
                 // Start out with a progress indicator.
                 setListShown(true);
                  this.registerBoradcastReceiver();
                 // Prepare the loader.  Either re-connect with an existing one,
                 // or start a new one.
               //  getLoaderManager().initLoader(0, null, this);
	            // Prepare the loader.  Either re-connect with an existing one,
	            // or start a new one.
	             }
	        

	       
	        @Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			Log.i("Course", "Attach");
		}


		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			Log.i("Course", "Create");
		}


		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.i("Course", "Destory");
		}


		@Override
		public void onDetach() {
			// TODO Auto-generated method stub
			super.onDetach();
			Log.i("Course", "Datach");
		}


		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			Log.i("Course", "Pause");
		}


		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			Log.i("Course", "Resume");
		}


		@Override
		public void onSaveInstanceState(Bundle outState) {
			// TODO Auto-generated method stub
			super.onSaveInstanceState(outState);
			Log.i("Course", "Save");
		}


		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			Log.i("Course", "start");
		}


		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			Log.i("Course", "Stop");
		}


			private Handler handler=new Handler(){
	           	public void handleMessage(Message msg)
	           	 {
	           		 {
	           		 // System.out.println("CF:"+nums);
	           		mAdapter = new MyCourseListAdapter(getActivity(),nums);
	           		  mAdapter.setData(cffs);
	           	      setListAdapter(mAdapter);
	           	       
	           		 }
	           	 }
	            };
	            
	            
	        private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){ 
	                @Override 
	                public void onReceive(Context context, Intent intent) { 
	                    String action = intent.getAction(); 
	                    if(action.equals("ACTION_TERM")){ 
	                    	 Toast.makeText(CoursesFragment.this.getActivity(), "课程加载中！", Toast.LENGTH_SHORT).show();                                
	                    	final String str=intent.getStringExtra("termValue");
	                    	term=str+"/";
	                    	  new Thread()
	          	     		{
	          	     			public void run()
	          	     			{
	          	     			  
	          	     			    setEmptyText("加载中！  ");
	          	     				BaiduFileService bfs=new BaiduFileService();
	          	     				bfs.setNums(getNums());
	          	     				 cffs=bfs.listCourse(str+"/");
	          	     				 handler.sendEmptyMessage(2);
	          	     				
	          	     			}
	          	     		}.start();
	                    } 
	                } 
	                 
	            }; 
	            
	            public void registerBoradcastReceiver(){ 
	                IntentFilter myIntentFilter = new IntentFilter(); 
	                myIntentFilter.addAction("ACTION_TERM"); 
	                //注册广播       
	                this.getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter); 
	            }      

	}
	 public static  class MyCourseListAdapter extends ArrayAdapter<BaiduCourseData>{
		     private final LayoutInflater mInflater;
	    	 //List<BaiduCourseData> listPerson;
	    	HashMap<Integer,View> map = new HashMap<Integer,View>(); 
	    	private String num="";
	    	public MyCourseListAdapter(Context context,String nums){
	    		
	    		  super(context, R.layout.courseitem);
	    		  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		  num=nums;
	    		  //  this.listPerson=data;
	    	}
             public void setData(List<BaiduCourseData> data)
             {
            	 this.addAll(data);
             }
			

			@SuppressLint("ResourceAsColor")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view;
				ViewHolder holder = null;
				
				if (map.get(position) == null) {
					
				
					view = mInflater.inflate(R.layout.courseitem, null);
					holder = new ViewHolder();
				    holder.coursename= (TextView)view.findViewById(R.id.textCourseName);
					holder.teachername = (TextView)view.findViewById(R.id.textCourseTeacher);
					holder.term=(TextView)view.findViewById(R.id.textTerm);
					map.put(position, view);
				    view.setTag(holder);
				}else{
					view = map.get(position);
					holder = (ViewHolder)view.getTag();
				}
				
			   String[] str=this.getItem(position).getCoursename().split("_");
				holder.coursename.setText(str[0]);
				holder.teachername.setText(str[1]);
				holder.term.setText(this.getItem(position).getCourseTime());
				if(num.contains(str[2]))
				{
					view.setBackgroundColor(R.color.brown);
				}
				return view;
			}
	    	
	    }
	    
	  public  static class ViewHolder{
	    	
	    	TextView coursename;
	    	TextView teachername;
	    	TextView term;
	    }
	  
	    
}
