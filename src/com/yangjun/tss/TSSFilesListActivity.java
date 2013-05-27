package com.yangjun.tss;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.yangjun.baidu.utils.BaiduCourseData;
import com.yangjun.baidu.utils.BaiduFileService;
import com.yangjun.baidu.utils.BaiduRefData;
import com.yangjun.baidu.utils.DownloadInfo;
import com.yangjun.baidu.utils.MyUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.TextView;


public class TSSFilesListActivity {
    @SuppressLint("ShowToast")
	public static class FilesFragment extends ListFragment {
    	  private Stack<String> stack=new Stack<String>();
  		private String path=new String("");
  		private String prepath=new String("");
  		private String curpath=new String("");
		   private MyFilesListAdapter mAdapter;
		   private BaiduFileService bfs=new BaiduFileService();
		   public  List<BaiduRefData> rfs=new ArrayList<BaiduRefData>();



		   public void setPath(String path) {
			if(path!=null&&path!="")
		   {
			//为了区别两门不同的课
			
			
			
			   stack=new Stack<String>();
			 //为了区别两门不同的课
			    this.prepath=this.curpath;
				this.curpath=path;
			    
			  this.path = path;
		      stack.add(path);
		    }
		   }
		   public void addPath(String path)
		   {
			   this.path = path;
			      stack.add(path);
		   }

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// TODO Auto-generated method stub
			
			inflater.inflate(R.menu.filesmenu, menu);
			
	       
		}

      private ArrayList<DownloadInfo> getFileDir(String path,ArrayList<DownloadInfo> list)
      {
    	  if(list==null)
    	  {
    		  list=new ArrayList<DownloadInfo>();
    	  }
    	  List<BaiduRefData> brf=bfs.getFileOfDir(path);
    	  for(int i=0;i<brf.size();i++)
    	  {
    		  BaiduRefData b=brf.get(i);
    		  if(b.isDir())
    		  {
    			  getFileDir(b.getPath(),list);
    		  }
    		  else
    		  {
    			  DownloadInfo d=new DownloadInfo(b.getName(),b.getPath(),b.getSize(),false) ;
    			  list.add(d);
    		  }
    	  }
    	  return list;
      }
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		
			int index=item.getOrder();
	
			if(index==101&&mAdapter!=null)
			{
			   Toast.makeText(getActivity(), "下载资料分析中！", Toast.LENGTH_SHORT).show();
				new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						for (int i = 0; i < mAdapter.getCount(); i++) {
							boolean tem=mAdapter.mChecked.get(i);
							if(tem)
							{
								if(!rfs.get(i).isDir())
								    {
								DownloadInfo d=new DownloadInfo(rfs.get(i).getName(),rfs.get(i).getPath(),rfs.get(i).getSize(),false);
								if(!((TSSMainActivity)FilesFragment.this.getActivity()).isHasDownFile(d))
								{
								 ((TSSMainActivity)FilesFragment.this.getActivity()).getDowns().add(d);
								}
								     }
								else
								{
									ArrayList<DownloadInfo> list=FilesFragment.this.getFileDir(rfs.get(i).getPath(), null);
									for(int j=0;j<list.size();j++)
									{
										if(!((TSSMainActivity)FilesFragment.this.getActivity()).isHasDownFile(list.get(j)))
										{
										 ((TSSMainActivity)FilesFragment.this.getActivity()).getDowns().add(list.get(j));
										}
									}
								}
							}
						}
						handler.sendEmptyMessage(1);
					
					}
			    	
			    }.start();
				
			}
			else if(index==100&&mAdapter!=null)
			{
				if(stack.size()>1)
				{
					stack.pop();
	            // this.setListAdapter(null);
	            // setEmptyText("加载中！");
	             new Thread(new FilesThread(this,bfs,stack.peek(),handler)).start();
	             }
			}
			
			return super.onOptionsItemSelected(item);
		    }
		
     
		   @Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			BaiduRefData brf=rfs.get(position);
			System.out.println("Clieck");
		    if(brf.isDir())
		    {
		    this.addPath(brf.getPath());
		    
		    //((TSSMainActivity)this.getActivity()).setPath(brf.getPath());
			//this.setListAdapter(null);
            new Thread(new FilesThread(this,bfs,path,handler)).start();
		    }
		    else
		    {
		 
		    }
		}


		public void onActivityCreated(Bundle savedInstanceState) {
	             super.onActivityCreated(savedInstanceState);
	             Log.i("Files", "ActivityCreated");
	            
                
	             if(savedInstanceState!=null)
	             {
	            	
	            	 this.path=savedInstanceState.getString("path");
	            	 this.prepath=savedInstanceState.getString("prepath");
	            	 stack=new Stack<String>();
	            	 System.out.println("PathFS:"+this.path);
	            	 String [] str=savedInstanceState.getStringArray("stack");
	            	 if(str!=null)
	            	 {
	            		
	            		System.out.println("InStack");
	            		 for(int i=str.length-1;i>=0;i--)
	            		 {
	            			 this.stack.add(str[i]);
	            		 }
	            	 }
	            	System.out.println("ACStackSIze:"+stack.size());
	            	 if(stack.size()>0)
		             {
		             this.setListAdapter(null);
		           //  setEmptyText("加载中！");
		             System.out.println("Path2"+this.path);
		             new Thread(new FilesThread(this,bfs,this.path,handler)).start();
		             }
	             }
	             else
	             {
	            	
	            	 if(mAdapter==null)
	            	 {
	            		 this.setPath(((TSSMainActivity)this.getActivity()).getPath());
	            		 System.out.println("PATHF:"+this.path);
	            		if(this.path.equals(""))
	    	            {
	    	             setEmptyText("Choose one course！！");
	    	            }
	    	            else
	    	            {
	    	        
	    	             setHasOptionsMenu(true);
	    	             setEmptyText("加载中！！ ");
	   	    	         new Thread(new FilesThread(this,bfs,this.path,handler)).start();
	    	            }
	    	         
	            	 }
	            	 else
	            	 {
	            		System.out.println("CurPath:"+this.curpath);
	            		 if(!this.curpath.equals(((TSSMainActivity)this.getActivity()).getPath()))
	            		 {
	            			 this.setPath(((TSSMainActivity)this.getActivity()).getPath());
	            			 setHasOptionsMenu(true);
	            			 this.setListAdapter(null);
		    	             setEmptyText("加载中！！ ");
		   	    	         new Thread(new FilesThread(this,bfs,this.path,handler)).start();
	            		 }
	            		 
	            	 }
	            	
	            	
	             }
	             
	            
	             setListShown(true);
               
	        }

	       
	        @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
	        	Log.i("Files", "CreateView");
			return super.onCreateView(inflater, container, savedInstanceState);
		}


		@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			
			super.onDestroyView();
			Log.i("Files", "DestoryView");
		}


			@Override
		public void onSaveInstanceState(Bundle outState) {
			// TODO Auto-generated method stub
			super.onSaveInstanceState(outState);
			Log.i("Files", "Save");
			outState.putString("path", path);
			outState.putString("prepath", prepath);
			 System.out.println("SPath:"+this.path);
			if(stack.size()>0)
			{
				 String[] str=new String[stack.size()];
				 String s="";
				 for(int i=0;i<stack.size();i++)
				 {
					 str[i]=stack.pop();
					 s+=str[i]+"  ";
				 }
				 outState.putStringArray("stack", str);
				 System.out.println("StackContent:"+s);
			}
			//
		}

	        @Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			Log.i("Files", "Attach");
		}


		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			Log.i("Files", "Create");
		}


		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.i("Files", "Destory");
		}


		@Override
		public void onDetach() {
			// TODO Auto-generated method stub
			super.onDetach();
			Log.i("Files", "Datach");
		}


		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			Log.i("Files", "Pause");
		}


		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			Log.i("Files", "Resume");
		}


	

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			Log.i("Files", "start");
		}


		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			Log.i("Files", "Stop");
		}
		


			private Handler handler=new Handler(){
	           	public void handleMessage(Message msg)
	           	 {
	           		 if(msg.what==0)
	           		{
	           		  mAdapter = new MyFilesListAdapter(getActivity());
	           		  mAdapter.setData(rfs);
	           	      setListAdapter(mAdapter);
	           	       
	           		 }
	           		 else if(msg.what==1)
	           		 {
	           			((TSSMainActivity)FilesFragment.this.getActivity()).getActionBar().selectTab(((TSSMainActivity)FilesFragment.this.getActivity()).getActionBar().getTabAt(2));
	           		 }
	           	 }
	            };

	}
	public static class FilesThread implements Runnable
	{
       private BaiduFileService bfs;
       private String path;
       private Handler handle;
       private FilesFragment ff;
		public FilesThread(FilesFragment ff,BaiduFileService bfs,String path,Handler handle)
		{
		      this.path=path;
			 this.bfs=bfs;
			 this.handle=handle;
		     this.ff=ff;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  if(path!=null&&path!="")
			  {
			  ff.rfs=bfs.getFileOfDir(path);
			  System.out.println("InIn");
			  }
			  handle.sendEmptyMessage(0);
		}
		
	}
	 public static  class MyFilesListAdapter extends ArrayAdapter<BaiduRefData>{
		     private final LayoutInflater mInflater;
	    	 //List<BaiduCourseData> listPerson;
	    	HashMap<Integer,View> map = new HashMap<Integer,View>(); 
	    	public List<Boolean> mChecked;
	    	public MyFilesListAdapter(Context context){
	    		
	    		  super(context, R.layout.courseitem);
	    		  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		  mChecked = new ArrayList<Boolean>();
	       		
	    		  //  this.listPerson=data;
	    	}
             public void setData(List<BaiduRefData> data)
             {
            	 this.addAll(data);
            	 for(int i=0;i<data.size();i++){
 	       			mChecked.add(false);
 	       		}
             }
			

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view;
				ViewHolder holder = null;
				
				if (map.get(position) == null) {
					
				
					view = mInflater.inflate(R.layout.baidufileitem, null);
					holder = new ViewHolder();
					holder.selected = (CheckBox)view.findViewById(R.id.checkBox1);
				    holder.filename= (TextView)view.findViewById(R.id.textView1);
				    holder.image=(ImageView) view.findViewById(R.id.imageView1);
					holder.size = (TextView)view.findViewById(R.id.textView2);
					CheckBox ck=(CheckBox) view.findViewById(R.id.checkBox1);
					ck.setFocusableInTouchMode(false);   
					ck.setFocusable(false);   
					map.put(position, view);
					final int p = position;
					holder.selected.setOnClickListener(new View.OnClickListener() {
	 					
	 					@Override
	 					public void onClick(View v) {
	 						CheckBox cb = (CheckBox)v;
	 						mChecked.set(p, cb.isChecked());
	 					}
	 				});
				    view.setTag(holder);
				}else{
					view = map.get(position);
					holder = (ViewHolder)view.getTag();
				}
				
		
				holder.filename.setText(getItem(position).getName());
				DecimalFormat nf1 = new DecimalFormat("#0.000");
				if(!getItem(position).isDir())
				
				holder.size.setText(nf1.format(getItem(position).getSize()/1024.0/1024)+"MB");
				else
				{
					//holder.selected.setEnabled(false);
				}
				holder.image.setImageResource(MyUtils.getImageId(getItem(position).getName()));
				return view;
			}
	    	
	    }
	    
	  public  static class ViewHolder{
			CheckBox selected;
	    	ImageView image;
		    TextView filename;
	    	TextView size;
	    	
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
