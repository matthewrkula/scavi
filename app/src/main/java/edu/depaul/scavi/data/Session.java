package edu.depaul.scavi.data;

import java.io.Serializable;

/**
 * Created by matt on 3/17/15.
 */
public class Session implements Serializable {
    public String next_clue_id;
    public int user_id;
    public int current_clue_number;
    public int points;
    public int hunt_id;
    public boolean is_in_progress;
    public int id;
}
