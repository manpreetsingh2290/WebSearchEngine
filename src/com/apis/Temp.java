package com.apis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {

	public static void main(String args[])
	{
		String regexp = "(http|https)://(\\w+\\.)+(edu|com|gov|org)";
        Pattern pattern = Pattern.compile(regexp);

        Matcher matcher = pattern.matcher("https://www.google.com/search?q=activity+diagram+of+google+map+vehicle+tracking&");

        // find and print all matches
        while (matcher.find()) {
            String w = matcher.group(0);
            System.out.println(w);
        }
	}
}
