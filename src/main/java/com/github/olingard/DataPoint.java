package com.github.olingard;

public record DataPoint(int time, int songID) {

  @Override
  public String toString() {
    return "DataPoint of SongId: " + songID + " at time: " + time;
  }
}
