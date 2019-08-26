package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.service.Constants;

public class MatchingWords {

	private String enteredWord="";
	private int editDidtanceFactor=1;
	private final String DICTIONARY_FILE=Constants.WEB_DECTIONARY_PATH;
	
	public MatchingWords(String enteredWord)
	{
		this.enteredWord=enteredWord;
	}
	
	public MatchingWords(String enteredWord,int editDidtanceFactor)
	{
		this.enteredWord=enteredWord;
		this.editDidtanceFactor=editDidtanceFactor;
	}
	
	
	public ArrayList<String> getMatchingWords(int maxWords)
	{
		ArrayList<String> matchingWordlsList= new ArrayList<String>();
		try
		{			
			File dictionary = new File(DICTIONARY_FILE);
			BufferedReader br = new BufferedReader(new FileReader(dictionary));
			String line="";
			int editDistance=-1;
			int count=0;
			String matchedWord=null;
			
			while ((line = br.readLine()) != null) {
				
				if(enteredWord.equalsIgnoreCase(line))
				{
					matchedWord=line;
				}
				
				if(line.length()>=enteredWord.length())
				{
					editDistance= editDistance(enteredWord.toLowerCase(), line.toLowerCase());
					if(editDistance<=editDidtanceFactor)
					{
						matchingWordlsList.add(line);
						count++;
						if(maxWords==count)
						{
							break;
						}
					}
				}
				
			}
			
			if(matchedWord!=null)
			{
				matchingWordlsList.add(0, matchedWord);
			}
			
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return matchingWordlsList;
	}
	public  int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}
	
	public static void main(String[] args) {
		
		MatchingWords obj= new MatchingWords("bello",1);
		System.out.println(obj.getMatchingWords(20));

	}

}
