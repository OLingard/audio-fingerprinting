package com.github.olingard;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {

  public static void main(String[] args)
      throws LineUnavailableException, IOException, UnsupportedAudioFileException,
      InterruptedException {
    File wavSongs =  new File(
        Objects.requireNonNull(Main.class.getClassLoader().getResource("wavs")).getFile());
    SongRepo songRepo = new SongRepo(wavSongs, true);
    new Runner().run(songRepo, true);
  }
}

