package com.yangjun.tss;



import com.yangjun.baidu.utils.LoginServer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends FragmentActivity{
	 private LoginServer ls;
 	 private EditText username;
 	 private EditText password;
 	 private CheckBox isRem;
 	 private Button button;
 	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Main", "Destory");
		this.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("Main", "Pause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("Main", "Resume");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i("Main", "Save");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("Main", "Start");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Main", "Stop");
	}

	private String nums;
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Main", "OnCreate");
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.username=(EditText) this.findViewById(R.id.editText1);
		this.password=(EditText) this.findViewById(R.id.editText2);
		SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
	    String name = sharedPreferences.getString("name", "");
	    String password = sharedPreferences.getString("password", "");
	    this.username.setText(name);
	    this.password.setText(password);
		this.isRem=(CheckBox) this.findViewById(R.id.checkBox1);
		if(name!="")
		{
			this.isRem.setChecked(true);
		}
		this.ls=new LoginServer();
	    button=(Button) this.findViewById(R.id.button3);
		
		button.setOnClickListener(new Button.OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		        Toast.makeText(MainActivity.this, "ÕýÔÚµÇÂ¼ÖÐ£¡£¡", Toast.LENGTH_LONG).show();
			      button.setEnabled(false);
			      isRem.setEnabled(false);
		        new Thread()
				{
					public void run()
					{
						final String name=MainActivity.this.username.getText().toString();
						final String password=MainActivity.this.password.getText().toString();
						boolean b=ls.login(name,password);
						Message msg=new Message();
						msg.getData().putBoolean("islogin", b);
						if(b)
						{
						   nums=ls.getMyCoursesNumber();
							if(isRem.isChecked())
						  {
							  SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
							  Editor   editor=sharedPreferences.edit();
	                            editor.putString("name",name);
	                            editor.putString("password", password);
	                            editor.commit();
						  }
						  else
						  {
							  SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
							    Editor   editor=sharedPreferences.edit();
	                            editor.remove("name");
	                            editor.remove("password");
	                            editor.commit(); 
						  }
						}
						handler.sendMessage(msg);
						
					}
				}.start();
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	  
     private Handler handler=new Handler()
     {
    	 @SuppressLint("ShowToast")
		public void handleMessage(Message msg)
    	 {
    		 {
    			  
    			   boolean b=msg.getData().getBoolean("islogin");	 
    			   System.out.println(b);
				    if(b)
					{
					Bundle bundle = new Bundle();
					
					bundle.putString("JSESSIONID", ls.getJSESSIONID());	
					bundle.putString("Name", ls.getName());
				
					bundle.putString("nums", nums);
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, TSSMainActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					}
					else
					{	
						    FragmentManager fm = MainActivity.this.getSupportFragmentManager();
				            if (fm.findFragmentByTag("dialog") == null) {
				                ConfirmClearDialogFragment frag = new ConfirmClearDialogFragment();
				                frag.show(fm, "dialog");
				            }
					}	
				    button.setEnabled(true);
				    isRem.setEnabled(true);
    	 }
     }
     };
}
