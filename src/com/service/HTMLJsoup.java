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

		
		// accessing the HTML file directory
		File directory = new File(Constants.WEB_PAGES_PATH);
		Stack<File> s = new Stack<File>();
		for( File file : directory.listFiles()) {
			s.push(file);
			while(!s.empty()) {
				File f = s.pop();
				if(f.isDirectory()) {
					for(File ff : f.listFiles())
						s.push(ff);
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
	
//		Pattern p = Pattern.compile("\\w+");
//		Matcher m = p.matcher(page);
//		while(m.find()) {
//			hm.put(m.group(), 1);
//		}
//		int count=0;
//		FileWriter fw = new FileWriter(new File("src/dic.txt"));
//		for(Object ss : hm.keySet().toArray()) {
//			count++;
//			fw.write(ss.toString()+"\n");
//			System.out.println(ss.toString());
//		}
//		fw.close();
		System.out.println(count);
		
		
	}
	
	private static void HTMLtoText(File file)  {
		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.parse(file, "UTF-8");
			String fname = file.getName().substring(0, file.getName().lastIndexOf('.') );
			String destination = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") )+"\\" +fname+"_converted.txt";
			//System.out.println(destination);
			FileWriter fw = new FileWriter(destination);
			//page +=" "+ doc.text();
			fw.write(doc.text());								// writting parsed HTML to file
			fw.close();
			count++;
			System.out.println(file.getName()+" Converted");
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
		
	}
}
