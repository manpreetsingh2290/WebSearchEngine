package com.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import com.apis.StdOut;
import com.apis.TST;

public class StaticTST {

	 public static TST<ArrayList<SearchResultData>> tstObject= new TST<ArrayList<SearchResultData>>();
	 
	    public static void main(String[] args) {
	    	
   	
	    	String[] keys = {"she","sells","sea","shells","by","the","sea","shore","she"};
	        String pageName="AAAA";
	    	processPageDataInTST(pageName,null,keys);
	    	
	    	System.out.println();
	    	keys = new String[]{"she","hello","hi","shells","by","by","by"};
	        pageName="BBBB";
	    	processPageDataInTST(pageName,null,keys);
	    	

	        for (String key : tstObject.keys()) {
	            StdOut.println(key + " " + tstObject.get(key));
	        }
	        
	    	
	    }
	 
    public static void processPageDataInTST(String pageName,String pagePath, String[] keys) 
    {
        
        ArrayList<SearchResultData> list= null;
        SearchResultData dataObj;
        for (int i = 0; i < keys.length; i++) {
        	list=tstObject.get(keys[i]);
        	if(list==null)
        	{
        		list= new ArrayList<SearchResultData>();
        		list.add(new SearchResultData(pageName, keys[i], 1, null,pagePath));
        	}
        	else
        	{
        		boolean found=false;
        		for(int j=0;j<list.size();j++)
        		{
        			dataObj =list.get(j);
        			
        			if(pageName.equals(dataObj.getPageName()))
        			{
        				int wordCount= dataObj.getWordCount();
        				dataObj.setWordCount(wordCount+1);
        				found=true;
                		
        			}
        			
        		}
        		
        		if(!found)
        		{
        			list.add(new SearchResultData(pageName, keys[i], 1, null,pagePath));
        		}
        	}
        	
        	
        	tstObject.put(keys[i], list);
        }

		/*
		 * 
		 * // print value of a key String key = "shells";
		 * StdOut.println("key = shells, value = "+ tstObject.get(key));
		 */
    }
}
