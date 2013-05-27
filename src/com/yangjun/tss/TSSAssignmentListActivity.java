package com.yangjun.tss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yangjun.baidu.utils.BaiduAssignmentData;
import com.yangjun.baidu.utils.BaiduCourseData;
import com.yangjun.baidu.utils.BaiduFileService;
import com.yangjun.baidu.utils.MyUtils;
import com.yangjun.tss.TSSFilesListActivity.FilesFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class TSSAssignmentListActivity {

	
	
	
	public static class AssignmentsFragment extends ListFragment 
	{
		
		   private MyAssignListAdapter mAdapter;
		   private  List<BaiduAssignmentData> assignlist;
		   private String[] mycoursenums;
		   private String curnum;


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
		}


	


		


		public void onActivityCreated(Bundle savedInstanceState) {
	             super.onActivityCreated(savedInstanceState);
	             Log.i("Course", "ActivityCreated");
	             setEmptyText("º”‘ÿ÷–£°  ");
	           
	             new Thread()
	     		{
	     			public void run()
	     			{
	     			  
	     				
	     				 assignlist=MyUtils.getAssignment("c0795");
	     				 handler.sendEmptyMessage(0);
	     				
	     			}
	     		}.start();
                

                 // Start out with a progress indicator.
                 setListShown(true);

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
	           			 mAdapter = new MyAssignListAdapter(getActivity());
	           		 if(assignlist!=null)
	           		 {
	           		  mAdapter.setData(assignlist);
	           	     
	           		 }
	           		 else
	           		 {
	           			AssignmentsFragment.this.setEmptyText("No Assignments!!") ;
	           		 }
	           		 setListAdapter(mAdapter);
	           		 }
	           	 }
	            };

	}
	 public static  class MyAssignListAdapter extends ArrayAdapter<BaiduAssignmentData>{
		     private final LayoutInflater mInflater;
	    	 //List<BaiduCourseData> listPerson;
	    	HashMap<Integer,View> map = new HashMap<Integer,View>(); 
	    	
	    	public MyAssignListAdapter(Context context){
	    		
	    		  super(context, R.layout.courseitem);
	    		  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		
	    		  //  this.listPerson=data;
	    	}
             public void setData(List<BaiduAssignmentData> data)
             {
            	 this.addAll(data);
             }
			

			@SuppressLint("ResourceAsColor")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view;
				ViewHolder holder = null;
				
				if (map.get(position) == null) {
					
				
					view = mInflater.inflate(R.layout.assignmentsitem, null);
					holder = new ViewHolder();
				    holder.duetime= (TextView)view.findViewById(R.id.TimetextView);
					holder.des = (EditText) view.findViewById(R.id.DeseditText);
					map.put(position, view);
				    view.setTag(holder);
				}else{
					view = map.get(position);
					holder = (ViewHolder)view.getTag();
				
				}
				BaiduAssignmentData b=this.getItem(position);
				holder.duetime.setText(b.getDuetime());
				holder.des.setText(b.getDes());
			 
				return view;
			}
	    	
	    }
	    
	  public  static class ViewHolder{
	    	
	    	TextView duetime;
	    	EditText des;
	    }
	  
	    public static class CoursesListLoader extends AsyncTaskLoader<List<BaiduCourseData>> {
	      
          private List<BaiduCourseData> mCourseList;
          private BaiduFileService bfs;

	        public CoursesListLoader(Context context) {
	            super(context);
	          
	      
	        }

	        /**
	         * This is where the bulk of our work is done.  This function is
	         * called in a background thread and should generate a new set of
	         * data to be published by the loader.
	         */
	        @Override public List<BaiduCourseData> loadInBackground() {
	            // Retrieve all known applications.
	           

	            final Context context = getContext();
	            bfs=new BaiduFileService();
	            mCourseList=bfs.listCourse("2013 Spring/");
	            // Create corresponding array of entries and load their labels.
	            List<BaiduCourseData> entries = new ArrayList<BaiduCourseData>();
	          
	           /* for (int i=0; i<apps.size(); i++) {
	                AppEntry entry = new AppEntry(this, apps.get(i));
	                entry.loadLabel(context);
	                entries.add(entry);
	            }
*/
	         

	            // Done!
	            return entries;
	        }

	        /**
	         * Called when there is new data to deliver to the client.  The
	         * super class will take care of delivering it; the implementation
	         * here just adds a little more logic.
	         */
	        @Override public void deliverResult(List<BaiduCourseData> apps) {
	            if (isReset()) {
	                // An async query came in while the loader is stopped.  We
	                // don't need the result.
	                if (apps != null) {
	                    onReleaseResources(apps);
	                }
	            }
	            List<BaiduCourseData> oldApps = apps;
	            mCourseList = apps;

	            if (isStarted()) {
	                // If the Loader is currently started, we can immediately
	                // deliver its results.
	                super.deliverResult(apps);
	            }

	            // At this point we can release the resources associated with
	            // 'oldApps' if needed; now that the new result is delivered we
	            // know that it is no longer in use.
	            if (oldApps != null) {
	                onReleaseResources(oldApps);
	            }
	        }

	        /**
	         * Handles a request to start the Loader.
	         */
	        @Override protected void onStartLoading() {
	            if (mCourseList != null) {
	                // If we currently have a result available, deliver it
	                // immediately.
	                deliverResult(mCourseList);
	            }


	            if (takeContentChanged() || mCourseList == null) {
	                // If the data has changed since the last time it was loaded
	                // or is not currently available, start a load.
	                forceLoad();
	            }
	        }

	        /**
	         * Handles a request to stop the Loader.
	         */
	        @Override protected void onStopLoading() {
	            // Attempt to cancel the current load task if possible.
	            cancelLoad();
	        }

	        /**
	         * Handles a request to cancel a load.
	         */
	        @Override public void onCanceled(List<BaiduCourseData> apps) {
	            super.onCanceled(apps);

	            // At this point we can release the resources associated with 'apps'
	            // if needed.
	            onReleaseResources(apps);
	        }

	        /**
	         * Handles a request to completely reset the Loader.
	         */
	        @Override protected void onReset() {
	            super.onReset();

	            // Ensure the loader is stopped
	            onStopLoading();

	            // At this point we can release the resources associated with 'apps'
	            // if needed.
	            if (mCourseList != null) {
	                onReleaseResources(mCourseList);
	                mCourseList = null;
	            }

	            // Stop monitoring for changes.
	           
	        }

	        /**
	         * Helper function to take care of releasing resources associated
	         * with an actively loaded data set.
	         */
	        protected void onReleaseResources(List<BaiduCourseData> apps) {
	            // For a simple List<> there is nothing to do.  For something
	            // like a Cursor, we would close it here.
	        }
	    }
}
