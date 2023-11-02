package com.github.olingard;

public class DataPoint {

  private int time;
  private int songID;

  public DataPoint(int time, int songID) {
    this.time = time;
    this.songID = songID;
  }

  public int getTime() {
    return time;
  }

  public int getSongID() {
    return songID;
  }

  @Override
  public String toString() {
    return "DataPoint of SongId: " + songID + " at time: " + time;
  }
}
