package edu.depaul.scavi.keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {

	/**
	 * takes an arraylist of keys pressed by the user and an arraylist of potential matches,
	 * assigns weights to every word and returns the four highest weighted words in an array
	 * @param in
	 * @param m
	 */
	public static String[] match(ArrayList<String> in, ArrayList<String> m) {
		ArrayList<String> userInput = in;
		ArrayList<String> matches = m;
		
		String[] topResults = new String[4];
		
		if (matches.size() == 1) {
			topResults[0] = matches.get(0);
			return topResults;
		}
		
		ArrayList<Integer>  weightList= new ArrayList<Integer>();
		
		for (String i : matches) {
			weightList.add(0);
		}
		System.out.print("\n");
		
		for (int i = 0; i < matches.size(); i++) {
			int start = 0;
			String word = matches.get(i);
			if (word.length() > 2) {
				word = word.substring(1, word.length() - 2);
			}
			String[] letters = word.split("");
			Boolean letterMissed = false;
			for(int h = 1; h < letters.length; h++){
				if (userInput.subList(start, userInput.size()).contains(letters[h].toUpperCase())) {
					start = userInput.indexOf(letters[h].toUpperCase());
					if (start > userInput.size())
						start = userInput.size();
					weightList.set(i, weightList.get(i) + 1);  //value added to a weighting when a letter is found in user input
				}
				else if(!userInput.subList(start, userInput.size()).contains(letters[h].toUpperCase())){
					if (!letters[h].equals("'")) {
						weightList.set(i, weightList.get(i) - 2);  //value subtracted from a weighting when a letter is found in user input
						letterMissed = true;
					}
				}
			}
			if (letterMissed == false) {
				weightList.set(i, weightList.get(i) + 8);  //bonus given to a weighting when every single letter is found in user input
			}
		}
		for (int i = 0; i < matches.size(); i++) {
			System.out.print(matches.get(i) + " : " + weightList.get(i) + " . ");
		}
		System.out.print("\n");
		
		//places the four words with the highest weightings into an array which is returned.
		if (matches.size() >= 4) {
			for (int j = 0; j < 4; j++) {
				int maxIndex = weightList.indexOf(Collections.max(weightList));
				topResults[j] = matches.get(maxIndex);
				matches.remove(maxIndex);
				weightList.remove(maxIndex);
			}
		}
		else if (matches.size() == 2 || matches.size() == 3) {
			for (int j = 0; j < matches.size()+2; j++) {
				int maxIndex = weightList.indexOf(Collections.max(weightList));
				topResults[j] = matches.get(maxIndex);
				matches.remove(maxIndex);
				weightList.remove(maxIndex);
			}
		}
		else if (matches.size() == 1) {
			
		}
		return topResults;
	}
}
