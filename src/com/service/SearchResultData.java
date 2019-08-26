package com.service;

import java.util.Arrays;

public class SearchResultData implements Comparable<SearchResultData>{

	private String pagePath;
	private String pageName;
	private String wordStr;
	private int wordCount;
	private int[] wordOffsets;
	
	public SearchResultData() {
		
	}
	
	public SearchResultData(String pageName, String wordStr, int wordCount, int[] wordOffsets,String pagePath) {
		super();
		this.pageName = pageName;
		this.wordStr = wordStr;
		this.wordCount = wordCount;
		this.wordOffsets = wordOffsets;
		this.pagePath= pagePath;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getWordStr() {
		return wordStr;
	}
	public void setWordStr(String wordStr) {
		this.wordStr = wordStr;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int[] getWordOffsets() {
		return wordOffsets;
	}
	public void setWordOffsets(int[] wordOffsets) {
		this.wordOffsets = wordOffsets;
	}
	
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public String getWordOffsetsStr() {
		String wordOffsetsStr="";
		if(wordOffsets!=null)
		{
			for(int i=0;i<wordOffsets.length;i++)
			{
				if(i==0)
				{
					wordOffsetsStr+=wordOffsets[i];
				}
				else
				{
					wordOffsetsStr+=","+wordOffsets[i];
				}
				
			}
		}		
		return wordOffsetsStr;
	}
	@Override
	public String toString() {
		return "[pageName=" + pageName + ", wordStr=" + wordStr + ", wordCount=" + wordCount
				+ ", wordOffsets=" + Arrays.toString(wordOffsets) + "]";
	}
	@Override
	public int compareTo(SearchResultData obj) {
		
		return (obj.getWordCount()-this.wordCount);
	}
	
	@Override
	public SearchResultData clone(){
		//super.clone();
		SearchResultData newObj= new SearchResultData();
		
		newObj.pageName = pageName;
		newObj.wordStr = wordStr;
		newObj.wordCount = wordCount;
		newObj.wordOffsets = wordOffsets;
		newObj.pagePath= pagePath;
		
		return newObj;
	}
	
	
	
}
