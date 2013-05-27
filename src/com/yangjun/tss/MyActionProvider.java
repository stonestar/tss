package com.yangjun.tss;

import java.util.ArrayList;

import com.yangjun.baidu.utils.BaiduFileService;
import com.yangjun.tss.TSSCoursesListActivity.CoursesFragment;
import com.yangjun.tss.TSSCoursesListActivity.MyCourseListAdapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class MyActionProvider extends ActionProvider {
    /** Context wrapper. */
    private ContextWrapper mContextWrapper;

    PopupMenu mPopupMenu;
    private BaiduFileService bfs;
    private TextView term;
    public MyActionProvider(Context context) {
        super(context);
        mContextWrapper = (ContextWrapper)context;
       
    }
    private String[] termString;
  
    private Handler handler=new Handler(){
       	public void handleMessage(Message msg)
       	 {
       		 {
       		makeTerms();
       	       
       		 }
       	 }
        };
    public View onCreateActionView() {
        // Inflate the action view to be shown on the action bar.
        LayoutInflater layoutInflater = LayoutInflater.from(mContextWrapper);
        View view = layoutInflater.inflate(R.layout.baiduterm, null);
        Button popupView = (Button)view.findViewById(R.id.buttonterm);
        term=(TextView) view.findViewById(R.id.textView1);
        SharedPreferences sharedPreferences = mContextWrapper.getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
	    String name = sharedPreferences.getString("T", "2013 Spring");
	    term.setText(name);
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        new Thread()
 		{
 			public void run()
 			{
 			  
 				 BaiduFileService bfs=new BaiduFileService();
 				  String ters=bfs.getTermArray();
 				 SharedPreferences sharedPreferences =mContextWrapper.getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
				  Editor   editor=sharedPreferences.edit();
                    editor.putString("term",ters);
                  
                    editor.commit();
 				handler.sendEmptyMessage(0);
 				
 			}
 		}.start();
     

        return view;
    }
    public void makeTerms()
    {
    	SharedPreferences sharedPreferences =  mContextWrapper.getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
    	String temp = sharedPreferences.getString("term", "");
    	if(temp.equals(""))
    	{
    		termString=new String[]{"AllTerm"};
    	}
    	else
    	{
    		termString=temp.split("_");
    	}
    }
    /**
     * show the popup menu.
     *
     * @param v
     */
    private void showPopup(View v) {
        mPopupMenu = new PopupMenu(mContextWrapper, v);
        mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // do someting
            	Intent mIntent = new Intent("ACTION_TERM"); 
                mIntent.putExtra("termValue", item.getTitle()); 
                 
                //·¢ËÍ¹ã²¥ 
                mContextWrapper.sendBroadcast(mIntent);
                term.setText(item.getTitle());
                SharedPreferences sharedPreferences = mContextWrapper.getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
				  Editor   editor=sharedPreferences.edit();
                  editor.putString("T",item.getTitle().toString());
                 
                  editor.commit();
                return false;
            }

        });
       // MenuInflater inflater = mPopupMenu.getMenuInflater();
       // inflater.inflate(R.menu.popmenu, mPopupMenu.getMenu());
        if(termString!=null)
        {
        	for(int i=0;i<termString.length;i++)
        	{
        		mPopupMenu.getMenu().add(1, i+1, i+1, termString[i]);
        		
        	}
        }
       // mPopupMenu.getMenu().a
        mPopupMenu.show();
    }
}