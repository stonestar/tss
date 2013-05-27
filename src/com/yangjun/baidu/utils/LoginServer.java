package com.yangjun.baidu.utils;



import java.io.IOException;


import java.util.TimerTask;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class LoginServer extends TimerTask {
    private String JSESSIONID;
    private String name;
		
      
    
    public String getJSESSIONID() {
		return JSESSIONID;
	}
	public void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public LoginServer(){
    	}
    public String getMyCoursesNumber()
    {
    	Document doc;
  	
  	    String res="";
  		try {
			doc = (Document) Jsoup.connect("http://218.94.159.102/tss/en/home/mycourse.html").cookie("JSESSIONID",this.JSESSIONID).get();
			Elements ets= doc.select("table.forum_text tr");
			
			for(int i=0;i<ets.size();i++) {
				Element temp=ets.get(i);
				Elements child=temp.children();
				if(child!=null&&child.size()==1) {
				
				} 
				else if(child!=null&&child.size()==4){
					if(child.get(0).hasAttr("align")) {
						if(res=="")
						res+=child.get(0).text();
						else
							res+="/"+child.get(0).text();
					}
				}
				
			}
  		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
    }
	public boolean login(String username,String passwd) {
            Connection connection = Jsoup.connect("http://218.94.159.102/GlobalLogin/login.jsp?ReturnURL=http://218.94.159.102/tss/en/home/postSignin.html");
            try {
                        Response response = connection.execute();
                        this.JSESSIONID = response.cookie("JSESSIONID");
                        System.out.println("JS:"+this.JSESSIONID);
                } catch (IOException e) {
                	   
                        return false;
                }
                connection =Jsoup.connect("http://218.94.159.102/GlobalLogin/loginservlet")
                        .data("username", username)
                        .data("password", passwd)
                        .data("days", "90")
                        .data("Submit", "Login").cookie("JSESSIONID",JSESSIONID);
                connection.request().method(Method.POST);
                Response response = null;
				try {
					response = connection.execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				if(response!=null)
                this.JSESSIONID = response.cookie("JSESSIONID");
				else
				{
					return false;
				}
                try {
					if(checkLogin()){
                  return true;}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
               
                return false;
        }
      
        public boolean checkLogin() throws IOException{
               
        	
        	  
        	   if(this.JSESSIONID!=null){
                        Document doc = Jsoup.connect("http://218.94.159.102/tss/en/home/mycourse.html").cookie("JSESSIONID", JSESSIONID).get();
                       
                        String text = doc.select("td[align=center][height=40]").first().text();
                      
                        name = text.replaceFirst("Goodnight","").replaceFirst("Goodmorning","").replaceFirst("Goodevening","").replaceFirst("Goodafternoon","");
                        return name!=null;
                }
        	   else {
        	  return false;
                }
        	  }
        
        /**
         * �˳�TSS
         */
        public void exit(){
               
               // timer.cancel();
                this.JSESSIONID = null;
                this.name = null;
        }
        
       
        @Override
        public void run() {
              
                try {
                        if(this.JSESSIONID!=null){
                        	Jsoup.connect("http://218.94.159.102/tss/active.html").cookie("JSESSIONID", JSESSIONID).execute();
                        }
                } catch (IOException e) {
                     
                }
        }
        
        
      
   
}

