package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.apis.TST;
//import com.dao.DataDao;
import com.google.gson.Gson;
import com.service.DictonaryCreator;
import com.service.MatchingWords;
import com.service.SearchResultData;

public class AutoCompleteController extends HttpServlet {
        private static final long serialVersionUID = 1L;
        public static  TST<ArrayList<SearchResultData>> tstObject=null;
        static {		
    		tstObject= DictonaryCreator.getGeneratedTST();
    		System.out.println("TST Genetared");
    	}

        protected void doGet(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {

                response.setContentType("application/json");
                try {
                        String wordStr = request.getParameter("term");
                        System.out.println("Data from ajax call " + wordStr);
                        ArrayList<String> list=null;
                        MatchingWords matchingWords=null;
                        
                        if(wordStr.contains(" "))
                		{
                			//String strary[]=wordStr.split(" ");
                			
                			int index= wordStr.lastIndexOf(" ");
                			String wordToSearch= wordStr.substring(index+1).trim();
                			
                			 matchingWords= new MatchingWords(wordToSearch,1);
                             list= matchingWords.getMatchingWords(10);
                             
                             if(list.size()<1)
                             {
                             	matchingWords= new MatchingWords(wordToSearch,2);
                                 list = matchingWords.getMatchingWords(10);
                             }
                             
                             for(int i=0;i<list.size();i++)
                             {
                            	 String s= list.get(i);
                            	 list.set(i, wordStr.substring(0,index)+" "+s);
                             }
                		}
                        else {
				/*
				 * 
				 * 
				 */
                        	list= new ArrayList<String>();
                        	Iterable<String> itr= tstObject.prefixMatch(wordStr);
                            for(String str:itr)
                            {
                            	list.add(str);
                            	if(list.size()>10)
                            	{
                            		break;
                            	}
                            }
                            
                            if(list.size()<5)
                            {
                            	matchingWords= new MatchingWords(wordStr,1); 
                            	ArrayList<String> listEditDis =matchingWords.getMatchingWords(5);
                            	
                            	list.addAll(listEditDis);
                            }
                            
                        }
                        
                        
                        
                        if(list.size()<1)
                        {
                        	matchingWords= new MatchingWords(wordStr,3);
                            list = matchingWords.getMatchingWords(10);
                        }
                        
                        

                        String searchList = new Gson().toJson(list);
                        response.getWriter().write(searchList);
                        
                        
                } catch (Exception e) {
                        System.err.println(e.getMessage());
                }
        }
}