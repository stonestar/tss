package com.yangjun.tss;
import java.util.ArrayList;

import com.yangjun.baidu.utils.DownloadInfo;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class TSSMainActivity extends Activity  {
 /*
  * 保存我的课程coursesnum
  */
	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	private String nums="";   
	    @Override
	   
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.i("MainTSS", "Create");
	        final ActionBar bar = getActionBar();
	        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
            
	        bar.addTab(bar.newTab()
	                .setText("Courses")
	                .setTabListener(new TabListener<TSSCoursesListActivity.CoursesFragment>(
	                        this, "courses", TSSCoursesListActivity.CoursesFragment.class)));
	        bar.addTab(bar.newTab()
	                .setText("Files")
	                .setTabListener(new TabListener<TSSFilesListActivity.FilesFragment>(
	                        this, "files", TSSFilesListActivity.FilesFragment.class)));
	        bar.addTab(bar.newTab()
	                .setText("Download")
	                .setTabListener(new TabListener<TSSDownListActivity.TSSDownListFragment>(
	                        this, "download", TSSDownListActivity.TSSDownListFragment.class)));
	      // bar.addTab(bar.newTab()
	             //   .setText("Assigenments")
	             //  .setTabListener(new TabListener<TSSAssignmentListActivity.AssignmentsFragment>(
	                  //   this, "Assigenments", TSSAssignmentListActivity.AssignmentsFragment.class)));
	       
	        if (savedInstanceState != null) {
	            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	            nums=savedInstanceState.getString("nums");
	            path=savedInstanceState.getString("path");
	            System.out.println("PATHMS:"+path);
	            
	        }
	        else
	        {
	        Bundle bundle=this.getIntent().getExtras();
	        nums=bundle.getString("nums");
	        System.out.println("PATHMN:"+nums);
	        }
	       // System.out.println("TSSMainNum:"+nums);
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        Log.i("MainTSS", "Save");
	        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	        outState.putString("path", path);
	        outState.putString("nums", nums);
	    }

	  @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			 Log.i("MainTSS", "Back");
		}

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			 Log.i("MainTSS", "Destory");
		}

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			 Log.i("MainTSS", "Pause");
		}

		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			super.onRestart();
			 Log.i("MainTSS", "Restart");
		}

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			 Log.i("MainTSS", "Resume");
		}

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			 Log.i("MainTSS", "Start");
		}

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			 Log.i("MainTSS", "Stop");
		}

	public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		private String course="",path="";
		private boolean nomem=false;
		public boolean isNomem() {
			return nomem;
		}

		public void setNomem(boolean nomem) {
			this.nomem = nomem;
		}

		private ArrayList<DownloadInfo> downs=new ArrayList<DownloadInfo>();
		public boolean isHasDownFile(DownloadInfo b)
		{
			return downs.contains(b);
		}
	    public ArrayList<DownloadInfo> getDowns() {
			return downs;
		}

		public void setDowns(ArrayList<DownloadInfo> downs) {
			this.downs = downs;
		}
		private long exitTime = 0;

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
		        if((System.currentTimeMillis()-exitTime) > 2000){  
		        	if(this.nomem||this.downs.size()==0||this.downs.get(this.downs.size()-1).isEnd())
		        	{
		            Toast.makeText(getApplicationContext(), "再按一次退出程序！", Toast.LENGTH_SHORT).show();                                
		            exitTime = System.currentTimeMillis();   
		        	}else
		        	{
		        		 Toast.makeText(getApplicationContext(), "还有资料下载中！", Toast.LENGTH_SHORT).show();                
		        	}
		        	
		        } else {
		        	Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					android.os.Process.killProcess(android.os.Process.myPid());
		        }
		        return true;   
		    }
		    return super.onKeyDown(keyCode, event);
		}
		public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	        private  TSSMainActivity mActivity;
	        private final String mTag;
	        private final Class<T> mClass;
	        private final Bundle mArgs;
	        private Fragment mFragment;

	        public TabListener(TSSMainActivity activity, String tag, Class<T> clz) {
	            this(activity, tag, clz, null);
	        }

	        public TabListener(TSSMainActivity activity, String tag, Class<T> clz, Bundle args) {
	            mActivity = activity;
	            mTag = tag;
	            mClass = clz;
	            mArgs = args;

	            // Check to see if we already have a fragment for this tab, probably
	            // from a previously saved state.  If so, deactivate it, because our
	            // initial state is that a tab isn't shown.
	            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
	          
	            if (mFragment != null && !mFragment.isDetached()) {
	                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
	                ft.detach(mFragment);
	                ft.commit();
	            }
	        }

	        public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        	System.out.println(mTag);
	        	// mFragment = null;
	            if (mFragment == null) {
	                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
	                
	               
	                ft.add(android.R.id.content, mFragment, mTag);
	            } else {
	            	
	                ft.attach(mFragment);
	            }
	        }

	        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	            if (mFragment != null) {
	                ft.detach(mFragment);
	            }
	        }

	        public void onTabReselected(Tab tab, FragmentTransaction ft) {
	         //   Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	        }
	    }

}
