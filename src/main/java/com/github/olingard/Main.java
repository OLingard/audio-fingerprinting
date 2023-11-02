package com.github.olingard;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args)
      throws LineUnavailableException, IOException, UnsupportedAudioFileException,
      InterruptedException {
    File wavSongs = new File("src/main/wavs");
    SongRepo songRepo = new SongRepo(wavSongs, true);
    new Runner().run(songRepo, true);
  }
}

