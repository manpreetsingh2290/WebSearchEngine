<%@page import="com.service.SearchResultData"%> 
<%@page import="java.util.ArrayList"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SCOUT Web Search Engine</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="autocompleter.js"></script>
<link rel="stylesheet" 
  href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet" href="style.css">

</head>
<body>

<form action="SerachWordController" method="get">
<div class="header" align="center">
        <h3>SCOUT Web Search Engine</h3>
</div>
<br />
<br />
<div class="search-container" align="center">
        <div class="ui-widget">
                <input type="text" id="search" name="search" class="search" />
                <br><br>
               <!--  <button type="submit">Search</button>  -->
                 <input type="submit" name="action" value="SearchWord">
                  <input type="submit" name="action" value="SpellCheck">
                <br>
        </div>
       
       <% 
       boolean createTable=false;
       ArrayList<SearchResultData> dataList = (ArrayList<SearchResultData>)request.getAttribute("SearchResultData");
       if(dataList!=null && dataList.size()>0) { 
    	   createTable=true;
       %>
       <br><div align="center">'<%=request.getAttribute("WordForSearch")%>'&nbsp;&nbsp;word/s found in below pages</div><br>
         <table border ="1" width="500" align="center"> 
         <tr bgcolor="00FF7F"> 
         <th><b>Page Rank</b></th> 
          <th><b>HTML Page</b></th> 
          <th><b>Word Count</b></th> 
         </tr>
        
         <%
			int pageRank=0;
        for(SearchResultData data:dataList){
        	 pageRank++;
        %> 
       
         
            <tr> 
            <td><%=pageRank%></td> 
                <td><a href="${pageContext.request.contextPath}/StaticPageController<%=data.getPagePath()%>"><%=data.getPageName()%></a></td> 
                <td><%=data.getWordCount()%></td> 
            </tr> 
            <%}%> 
       
        </table>  
       <%}
       else if(request.getAttribute("WordForSearch")!=null)
       { %>
    	<br><font color="red"><b>word/s&nbsp;&nbsp;'<%=request.getAttribute("WordForSearch")%>'&nbsp;&nbsp; not found in any file.</font></b>
    	<%   
       }%>
       
       <% 
       String spellCheckWord=null;
       if(request.getAttribute("WordForSpellCheck")!=null)
       {
    	    spellCheckWord=(String)request.getAttribute("WordForSpellCheck");
    	    %>
    	   <br><b><%=spellCheckWord%></b>
      <%  }       
       %>
     
</div>
</form>
</body>
</html>