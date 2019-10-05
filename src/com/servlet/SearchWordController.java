package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apis.Quick;
import com.apis.TST;
import com.service.SearchResultData;


public class SearchWordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//public static final TST<ArrayList<SearchResultData>> tstObject=DictCreator.getGeneratedTST();
	static {		
		//tstObject= DictCreator.getGeneratedTST();
		System.out.println("TST Genetared");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchWordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TST<ArrayList<SearchResultData>> tstObject=AutoCompleteController.tstObject;
		response.setContentType("text/html;charset=UTF-8"); 
		
		String wordStr = request.getParameter("search");
		
		request.setAttribute("EnteredWordForSearch",wordStr);
		
		String action = request.getParameter("action");

		//If action is to Search the word
		if ("SearchWord".equals(action)) {
			//If null or empty string
			if(wordStr==null || "".equalsIgnoreCase(wordStr))
			{
				RequestDispatcher rd =  request.getRequestDispatcher("mainpage.jsp"); 
				rd.forward(request, response);
				return;
			}
			
			//Map to store page-wise count of word's occurrences
			//Key is the path to the HTML page to uniquely track the pages
			HashMap<String,SearchResultData> mapObj= new HashMap<String,SearchResultData>();
			ArrayList<SearchResultData> dataList=null;
			SearchResultData obj=null;
			//If multiple words
			if(wordStr.contains(" "))
	 		{
	 			String strary[]=wordStr.split(" ");
	 			
	 			//Loop over the entered words and search each word in TST
	 			for(String str:strary)
	 			{
	 				dataList= tstObject.get(str);
	 				
	 				//If atleast one page found containing the word
	 				if(dataList!=null && dataList.size()>0)
	 				{
	 					for(int i=0;i<dataList.size();i++)
	 	 	 			{
	 	 	 				obj =dataList.get(i);
	 	 	 				SearchResultData objNew=mapObj.get(obj.getPagePath());
	 	 	 				if(objNew!=null)
	 	 	 				{
	 	 	 					objNew.setWordCount(obj.getWordCount()+objNew.getWordCount());
	 	 	 					objNew.setWordStr(obj.getWordStr()+":"+obj.getWordCount()+","+objNew.getWordStr());
	 	 	 					mapObj.put(objNew.getPagePath(), objNew);
	 	 	 					
	 	 	 				}
	 	 	 				else
	 	 	 				{
	 	 	 					SearchResultData temp = new SearchResultData(obj.getPageName(), 
	 	 	 							obj.getWordStr()+":"+obj.getWordCount(), 
	 	 	 							obj.getWordCount(), obj.getWordOffsets(), obj.getPagePath());
	 	 	 					mapObj.put(temp.getPagePath(), temp);
	 	 	 				}	 				
	 	 	 				
	 	 	 			}	
	 				} 											
	 				
	 			}
	 			
	 			dataList= new ArrayList<SearchResultData>();
	 			
	 			//Converting Map to ArrayList
	 			for(String key:mapObj.keySet())
	 			{
	 				dataList.add(mapObj.get(key));
	 			}
	 			
			
	 		}
			else //If single word
			{
				dataList= tstObject.get(wordStr);
				if(dataList==null) dataList= new ArrayList<SearchResultData>();
			}
			 
			
			//Converting list to array (Quick select API is designed for Arrays)
			SearchResultData objArray[]= (SearchResultData[])dataList.toArray(new SearchResultData[dataList.size()]);
			ArrayList<SearchResultData> list= new ArrayList<SearchResultData>();
			int count=objArray.length;
			
			
			//Select top 10 pages from the list
			for(int i=0;i<10;i++)
			{
				if(i>=count)
				{
					break;
				}
				//Getting ith ranked page and adding it to the list
				list.add((SearchResultData)Quick.select(objArray, i));
				
			}
			
			wordStr=wordStr.replace(" ", ", ");
			request.setAttribute("WordForSearch",wordStr);
				
			request.setAttribute("SearchResultData",list);
			
			RequestDispatcher rd =  request.getRequestDispatcher("mainpage.jsp"); 
			rd.forward(request, response);
		} 
		//If action is to check the spellings
		else if ("SpellCheck".equals(action)) {
		    
			System.out.println("In Spell Check");
			String spellCheckProcessedStr="";
						
			String strArry[]= wordStr.split(" ");
			
			String color="red";
			for(String str:strArry)
			{
				color="red";
				if(!isContainSpecialCharacters(str))
				{
					//Check if word is present in TST
					if(tstObject.contains(str))
					{
						color="green";
					}
				}
				
				
				spellCheckProcessedStr+="<font color=\""+color+"\">"+str+" "+"</font>";
			}
			
			
			request.setAttribute("WordForSpellCheck",spellCheckProcessedStr);
			RequestDispatcher rd =  request.getRequestDispatcher("mainpage.jsp"); 
			 rd.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private boolean isContainSpecialCharacters(String input)
	{
		Pattern pattern= Pattern.compile("[a-zA-Z0-9]");
		Matcher match = pattern.matcher(input);
		return !(match.find());
	}

}
