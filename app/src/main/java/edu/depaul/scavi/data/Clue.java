package edu.depaul.scavi.data;

import java.io.Serializable;

public class Clue implements Serializable {

    long id;
    long clue_number;
    long latitude;
    long longitude;
    String question;
    String answer;
    int points;

}
