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
        		int WORD_SUGGESTIONS_COUNT=10;
                response.setContentType("application/json");
                try {
                		//Getting the entered word from request parameter
                        String wordStr = request.getParameter("term");
                        System.out.println("Data from ajax call " + wordStr);
                        ArrayList<String> list=null;
                        //MatchingWords matchingWords=null;
                        
                        String wordToSearch=wordStr;
                        String wordToReturned="";
                        
                        //If multiple words
                        if(wordToSearch.contains(" "))
                		{
                			//Get the last word of the entered sentence
                			int index= wordToSearch.lastIndexOf(" ");
                			wordToSearch= wordToSearch.substring(index+1).trim();
                			wordToReturned =wordToSearch.substring(0,index)+" ";
                		      
                		}
                        
                       //Get '5' words from TST with same prefix
                        list =getSamePrefixWordsFromTST(wordToSearch, 5);
                        System.out.println("Count of Words found from TST: "+list.size());
                            
                        //If word count is less than 
                        if(list.size()<WORD_SUGGESTIONS_COUNT)
                         {
                        	//Search matching words with edit distance '1' from word dictionary 
                        	ArrayList<String> listEditDis =getMatchingWordsFromWordDictionary(wordToSearch, 1, (WORD_SUGGESTIONS_COUNT-list.size()));                           	
                           	list.addAll(listEditDis);
                           	System.out.println("Count of Words found from Dictionary (Edit Dis '1'): "+listEditDis.size());
                         } 
                        
                        //If still word count is less than WORD_SUGGESTIONS_COUNT (Run word search with edit distance '2')
                        if(list.size()<WORD_SUGGESTIONS_COUNT)
                        {
                       	   //Search matching words with edit distance '2' from word dictionary 
                       	    ArrayList<String> listEditDis =getMatchingWordsFromWordDictionary(wordToSearch, 2, (WORD_SUGGESTIONS_COUNT-list.size()));                           	
                          	list.addAll(listEditDis);
                          	System.out.println("Count of Words found from Dictionary (Edit Dis '2'): "+listEditDis.size());
                        }
                        
                        //If still word count is less than WORD_SUGGESTIONS_COUNT (Run word search with edit distance '3')
                        if(list.size()<WORD_SUGGESTIONS_COUNT)
                        {
                       	   //Search matching words with edit distance '3' from word dictionary 
                       	    ArrayList<String> listEditDis =getMatchingWordsFromWordDictionary(wordToSearch, 3, (WORD_SUGGESTIONS_COUNT-list.size()));
                       	    System.out.println("Count of Words found from Dictionary (Edit Dis '3'): "+listEditDis.size());
                          	list.addAll(listEditDis);
                        } 
                        
                                                
                        //Append the matched words with the word sentence to be returned
                        for(int i=0;i<list.size();i++)
                        {
                       	 String s= list.get(i);
                       	 list.set(i, wordToReturned+s);
                        }

                        String searchList = new Gson().toJson(list);
                        response.getWriter().write(searchList);
                        
                        
                } catch (Exception e) {
                        System.err.println(e.getMessage());
                }
        }
        
        public ArrayList<String> getSamePrefixWordsFromTST(String wordStr, int wordCount)
        {
        	ArrayList<String> list= new ArrayList<String>();
        	//Get the words from TST with same prefix
        	Iterable<String> itr= tstObject.prefixMatch(wordStr);
            for(String str:itr)
            {
            	list.add(str);
            	if(list.size()>=wordCount)
            	{
            		break;
            	}
            }
            return list;
        }
        
        public ArrayList<String> getMatchingWordsFromWordDictionary(String wordStr, int editDistance, int wordCount)
        {
        	MatchingWords matchingWords= new MatchingWords(wordStr,editDistance);
        	ArrayList<String> list =matchingWords.getMatchingWords(wordCount);
            return list;
        }
}