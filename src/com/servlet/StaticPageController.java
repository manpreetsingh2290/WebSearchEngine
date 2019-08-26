package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apis.In;
import com.service.Constants;

/**
 * Servlet implementation class StaticPageController
 */

public class StaticPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Set the response message's MIME type
	      response.setContentType("text/html;charset=UTF-8");
	      // Allocate a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	      	     	      
	      
	      // Write the response message, in an HTML page
	      try {
	    	  if(request.getPathInfo()!=null && request.getPathInfo().endsWith(".html"))
	    	  {
	    		  System.out.println("Getting HTML File: "+request.getPathInfo());
	    		  String pathAppend=Constants.WEB_PAGES_PATH;
	    		  pathAppend=pathAppend.substring(0, pathAppend.indexOf(Constants.WEB_PAGES_BEGIN_FOLDER_NAME));
	    		  //System.out.println(pathAppend);
	    		  In in= new In(pathAppend+request.getPathInfo());
			      String text= in.readAll();
		    	  out.print(text); 
	    	  }
	    	 
	      } finally {
	         out.close();  //Closing the output writer
	      }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
