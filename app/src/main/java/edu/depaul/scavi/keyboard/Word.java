package edu.depaul.scavi.keyboard;

import java.util.ArrayList;

public class Word {
	private String word;

	Word(ArrayList<String> w) {
		setWord(w);
	}

	Word(String w) {
		setWord(w);
	}

	private void setWord(ArrayList<String> str) {
		// TODO Auto-generated method stub
		if (str.isEmpty() || str == null) {
			throw new IllegalArgumentException(
					"The word array is empty or null");
		}
		word = "";
		for (int i = 0; i < str.size(); i++) {
			word += str.get(i);
		}
	}

	private void setWord(String str) {
		String[] splitword = str.split("'");
		String finalword = "";
		for (String k : splitword) {
			finalword = finalword + k;
		}
		word = finalword;
	}

	public String toString() {
		String w = word;
		return w;
	}
}
