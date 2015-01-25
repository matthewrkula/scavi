
package edu.depaul.scavi.keyboard;

import android.content.Context;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TemplatePrune {
	private Word word;
	/**
	 * A method which returns an array containing all the four best possible word matches for the given user input text.
	 * @param str
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String[] findMatch(ArrayList<String> str, Context c) throws ClassNotFoundException, IOException{
		String word = setString(str);
		String[] split = word.toString().split("");
		ArrayList<String> splitStr = new ArrayList<String>(Arrays.asList(split));
		splitStr.remove(0);
		splitStr.remove(0);
		splitStr.remove(splitStr.size() - 1);
		int len = word.toString().length();
		ArrayList<String> matches = new ArrayList<String>();
		Dictionary.getInstance().isSetup(c);
		//prune out words that do not matche the first & last letter, and length.
		ArrayList<String> words = Dictionary.getInstance().returnWordList(word.toString());

		//DictionarySQL.queryDictionaryWord(word.toString());
		
		//get the top matches and return them to the keyboard
		String[] topResults = Algorithm.match(splitStr, words);
		return topResults;
	}
	/**
	 * takes the array containing the raw text data the user input, and concatenates it so that it becomes a string.
	 * @param str
	 * @return
	 */
	private static String setString(ArrayList<String> str) {
		// TODO Auto-generated method stub
		if(str.isEmpty() || str == null){
			throw new IllegalArgumentException("The word array is empty or null");
		}
		String word = "";
		for(int i = 0; i < str.size(); i++){
			word += str.get(i);
		}
		return word;
	}

}
