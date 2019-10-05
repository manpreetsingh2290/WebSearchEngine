package com.service;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.*;

import com.service.Constants;

public class HTMLJsoup { 
	static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	static String page="";
	static int count=0;
	public static void main(String[] args) throws IOException {

		
		// Accessing the HTML files directory
		File directory = new File(Constants.WEB_PAGES_PATH);
		Stack<File> s = new Stack<File>();
		for( File file : directory.listFiles()) { //Iterating through directories to find files
			s.push(file);
			while(!s.empty()) {
				File f = s.pop();
				if(f.isDirectory()) {
					for(File ff : f.listFiles())
						s.push(ff); //Pushing files from the directory into stack
				}
				else {
					if(f.getName().contains(".") )
					{	
						if( f.getName().substring( f.getName().lastIndexOf(".") ).contains("htm") )
							HTMLtoText(f);
						
					}
						
				}
			}
		}
	
		System.out.println(count);
		
		
	}
	//Method to extract text from HTML file using Java Jsoup library
	private static void HTMLtoText(File file)  {
		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.parse(file, "UTF-8");
			String fname = file.getName().substring(0, file.getName().lastIndexOf('.') );
			String destination = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") )+"\\" +fname+"_converted.txt";
			//System.out.println(destination);
			FileWriter fw = new FileWriter(destination);
			fw.write(doc.text());// Writing parsed HTML to text file
			fw.close();
			count++;
			System.out.println(file.getName()+" Converted");
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
		
	}
}
