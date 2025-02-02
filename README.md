# WebSearchEngine
Web search engine to find and rank web pages using web crawling and optimizing techniques with word suggestion and spell check features

### Technologies: 		
* Backend : **Java, JSP, Servlet**
* Front end: **HTML, CSS, Ajax**
* Algorithms/Data structures: **Ternary Search Tree(TST), Quick Sort, Hash Map**

## Project flow and Steps to run the project:
1. Open the project (WebSearchEngine) in Eclipse.
2. Change the path of the folders for websites, Dictionary file location and others in JAVA file (com.service.Constants.java)
3. Run the main method in java file (com.service.HTMLJsoup.java) to convert HTML files to text files. This will save the converted files (fileName_converted.txt) in same folder location as of HTML files.
4. Run the main method in java file (com.service.DictonaryCreator.java) to create the Dictonary of words retrieved from HTML files.
5. Run the (WebSearchEngine) project on web server (Tomcat).
6. Open URL (http://localhost:[port]/WebSearchEngine/) in browser.
7. Enter one letter in search box, it will start the TST creation in server (it can take few minutes depending upon the number of HTML files), after TST is loaded in memory, all search operations can be performed (Word Suggestions, Spell Check and Page Search) in very fast way.

## Screen Shots:
---
<img src="Screen_Shots/Word_Suggestions1.JPG">
---
<img src="Screen_Shots/Word_Suggestions2.JPG">
---
<img src="Screen_Shots/Word_Search.JPG">
---
<img src="Screen_Shots/Spell_Check.jpg">
