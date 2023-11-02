package com.github.olingard.input;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class WaveConverter {

  public static byte[] convertToByteArray(File file)
      throws IOException, UnsupportedAudioFileException {
    AudioInputStream ais = AudioSystem.getAudioInputStream(file);

    byte[] bytes;
    try {
      int bytesToRead = ais.available();
      bytes = new byte[bytesToRead];
      int bytesRead = ais.read(bytes);
      if (bytesToRead != bytesRead) {
        throw new IllegalStateException("Read only " + bytesRead + " of " + bytesToRead + " bytes");
      }
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Could not read '" + file.getName() + "'", ioe);
    }
    return bytes;
  }
}
