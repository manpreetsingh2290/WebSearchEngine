package com.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apis.TST;

public class DictonaryCreator {
	static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	public static TST<ArrayList<SearchResultData>> tstObject= new TST<ArrayList<SearchResultData>>();
	static long words =0;
	static long pgcount=0;
	
	public static void main(String args[])
	{
		//generateTST();
		generateDict();
	}
	
	//Returns the TST generated with information of all the words found and their corresponding page information
	public static TST<ArrayList<SearchResultData>> getGeneratedTST()
	{
		if(tstObject.size()<1)
		{
			System.out.println("Generating TST for the first time");
			generateTST();
			System.out.println("TST generation completed");
		}		 
		 return tstObject;
	}
	
	//Create the dictionary file containing all the words parsed from HTML pages
	public static void generateDict() {
		try {
		File directory = new File(Constants.WEB_PAGES_PATH);
		Stack<File> s = new Stack<File>();
		//int fileCount=0;
		for( File file : directory.listFiles()) { //Iterating through directories to find files
			s.push(file);
			while(!s.empty()) {
				File f = s.pop();
				if(f.isDirectory()) {
					for(File ff : f.listFiles())
						s.push(ff); //Pushing the files into stack
				}
				else {
						
					//If it is the converted text file with text data from HTML page
					if(f.getName().contains("_converted"))
					{
						//Calling putInMap method on the file to retrieve all the words from the file
						ArrayList<String> keys  = putInMap(f);
						
					}				
					
				}
			}
		}
		// Reading all the words from the hashmap into array
		Object[] arry =  hm.keySet().toArray();
		Arrays.sort(arry);
		FileWriter fw = new FileWriter(new File(Constants.WEB_DECTIONARY_PATH));
		for(Object ss : arry) {
			words++;
			fw.write(ss.toString()+"\n"); //Writing the words in the word dictionary file
			//System.out.println(ss.toString());
		}
		fw.close();
		System.out.println("Word count:"+words);
		System.out.println("Page count:"+pgcount);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Create the TST containing all the words parsed from HTML pages
	public static void generateTST() {
		try {
		File directory = new File(Constants.WEB_PAGES_PATH);
		Stack<File> s = new Stack<File>();
		//int fileCount=0;
		for( File file : directory.listFiles()) {  //Iterating through directories to find files and inner directories
			s.push(file);
			while(!s.empty()) {
				File f = s.pop();
				if(f.isDirectory()) {
					for(File ff : f.listFiles())
						s.push(ff); //Pushing the files into stack
				}
				else {
					
					if(f.getName().contains("_converted"))
					{
						//Calling putInMap method on the file to retrieve all the words from the file
						ArrayList<String> keys  = putInMap(f);
						
						//Get the actual HTML file's path location
						String pagePath=f.getAbsolutePath().replaceAll("_converted.txt", ".html");
						pagePath =pagePath.substring(pagePath.indexOf("\\"+Constants.WEB_PAGES_BEGIN_FOLDER_NAME));
						
						//Get the actual HTML file's name(with html extension)
						String pageName= f.getName().replaceAll("_converted.txt", ".html");
						
						
						processPageDataInTST(pageName,pagePath,keys);
					}				
					
				}
			}
		}
			
		System.out.println(words);
		System.out.println(pgcount);
		
		FileWriter fileWrit= new FileWriter(new File(Constants.TST_FILE_LOCATION));
		 for (String key : tstObject.keys()) {
	           // StdOut.println(key + " " + tstObject.get(key));
	            fileWrit.write(key + " " + tstObject.get(key)+"\n");
	        }
		 fileWrit.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Method to fetch the words from the file and put them into Hashmap with their corresponding count 
	private static ArrayList<String> putInMap(File f) throws Exception {
		pgcount++;
		System.out.println(pgcount);
		Scanner in = new Scanner(f);
		ArrayList<String> keys = new ArrayList<String>();
		StringBuilder page= new StringBuilder("");
		
		//Reading text file line by line 
		while(in.hasNext())
		{
			page.append("\n"+in.nextLine());
		}
		
		//Applying regular expression on the text to find the words without digits
		Pattern p = Pattern.compile("[^\\d\\W]+");
		Matcher m = p.matcher(page.toString());
		while(m.find()) {
			//Adding words to the list
			keys.add(m.group());
			Integer i=hm.get(m.group());
			
			//If word is not present in the hashmap
			if(i==null)
			{	
								
				hm.put(m.group(),1);
			}
			else //If word is present in the hashmap increment the count and save the new count value with the word
			{	i++;
				hm.put(m.group(), i);
			}
			
		}
			
		in.close();
		return keys;
	}
	
	//Method to save the words in TST with their corresponding page information and count
	 public static void processPageDataInTST(String pageName,String pagePath, ArrayList<String> keys) 
	    {
	        
	        ArrayList<SearchResultData> list= null;
	        //Object to save word's information
	        SearchResultData dataObj;
	        
	        //Loop through all the words contained by the file
	        for (int i = 0; i < keys.size(); i++) {
	        	list=tstObject.get(keys.get(i));
	        	
	        	//If TST does not already has entry of the word 
	        	if(list==null)
	        	{
	        		list= new ArrayList<SearchResultData>();
	        		//Save the object containing word's information in the list with word count 1 for the new HTML page
	        		list.add(new SearchResultData(pageName, keys.get(i), 1, null,pagePath));
	        	}
	        	else //If TST already has entry of the word 
	        	{
	        		boolean found=false;
	        		//Loop through the list(word's page-wise count information) 
	        		for(int j=0;j<list.size();j++)
	        		{
	        			dataObj =list.get(j);
	        			
	        			//If the list contains the object corresponding to the HTML page 
	        			if(pageName.equals(dataObj.getPageName()))
	        			{
	        				int wordCount= dataObj.getWordCount();
	        				//Increment the count of the word for the same HTML page
	        				dataObj.setWordCount(wordCount+1);
	        				found=true;
	                		
	        			}
	        			
	        		}
	        		
	        		//If the list does not contains the object corresponding to the HTML page 
	        		if(!found)
	        		{
	        			//Save the object containing word's information in the list with word count 1 for the new HTML page
	        			list.add(new SearchResultData(pageName, keys.get(i), 1, null,pagePath));
	        		}
	        	}
	        	
	        	//Put the Word and created list into the TST
	        	tstObject.put(keys.get(i), list);
	        }

			
	    }
}
