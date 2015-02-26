package edu.depaul.scavi.data;

import java.io.Serializable;

public class Clue implements Serializable {

    long id;
    long clue_number;
    float latitude;
    float longitude;
    String question;
    String answer;
    int points;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClue_number() {
        return clue_number;
    }

    public void setClue_number(long clue_number) {
        this.clue_number = clue_number;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
