package edu.depaul.scavi.keyboard;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import edu.depaul.scavi.R;

public class Dictionary {
	private volatile static Dictionary instance = null;
	private HashMap<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
	private boolean isSetup = false;

	private Dictionary() {

	}

	/**
	 * This method loads all the words from a text file into the HashMap. The
	 * key for the hashmap is the concatenation of the first and last letters of
	 * the word. The value for the hashmap are all the words that start with the
	 * first letter and end with the last.
	 * 
	 * @throws IOException
	 */
	private void setUp(Context c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(c.getResources().openRawResource(R.raw.topwords)));
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			String[] prune = line.split("\\t");
			String[] letters = prune[1].trim().split("");
			String fLetter = letters[1];
			String lLetter = letters[letters.length - 1];
			String key = fLetter + lLetter;
			if (dict.containsKey(key)) {
				ArrayList<String> value = dict.get(key);
				value.add(prune[1]);
				dict.put(key, value);
			} else {
				ArrayList<String> value = new ArrayList<String>();
				value.add(prune[1]);
				dict.put(key, value);
			}

		}
		br.close();
	}

	/**
	 * This method checks to see if Dictionary has been created. If so, it
	 * returns the current copy of Dictionary. If it has not been created
	 * Dictionary is instantiated and then returned.
	 * 
	 * @return a singleton instance of Dictionary
	 * @throws IOException
	 */
	public static Dictionary getInstance() throws IOException {
		if (instance == null) {
			synchronized (Dictionary.class) {
				if (instance == null) {
					instance = new Dictionary();
				}

			}
		}
		return instance;
	}

	/**
	 * Checks to see if words have been loaded into the hashmap.
	 * 
	 * @throws IOException
	 */
	public void isSetup(Context c) throws IOException {
		if (!isSetup) {
			setUp(c);
			isSetup = true;
		}
	}

	/**
	 * returns a list of words that begin with with the first letter of the
	 * input parameter, and end with the last letter of the input parameter.
	 * 
	 * @param string
	 * @return
	 */
	// POSSIBLY NEED TO ADD ERROR IF FIRST AND LAST LETTER COMBO DOESN'T EXIST.
	public ArrayList<String> returnWordList(String string) {
		// TODO Auto-generated method stub
		String[] letters = string.split("");
		String fLetter = letters[1];
		String lLetter = letters[letters.length - 1];
		String key = fLetter + lLetter;
		// ArrayList<String> v = dict.get(key.toLowerCase());
		ArrayList<String> h = dict.get(key.toLowerCase());
		return lengthPrune(h, string);
	}

	/**
	 * prunes out any words that are longer the the user input. E.G. if the
	 * users enters "TGYHE" the word "tactile" well be pruned out of the list.
	 * 
	 * @param wordList
	 *            the initial list of words which will be pruned
	 * @param input
	 *            the user's complete list of keys pressed
	 * @return a pruned list of words, or an identical list to the input if
	 *         nothing was removed
	 */
	public ArrayList<String> lengthPrune(ArrayList<String> wordList,
			String input) {
		ArrayList<String> result = new ArrayList<String>();
		if (wordList != null && wordList.size() > 0) {
			int length = input.length();
			for (int i = 0; i < wordList.size(); i++) {
				if (wordList.get(i).length() <= length + 1) {
					result.add(wordList.get(i));
				}
			}
		}
		return result;
	}
}
