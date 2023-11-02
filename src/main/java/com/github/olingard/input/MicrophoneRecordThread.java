package com.github.olingard.input;

import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MicrophoneRecordThread extends Thread {

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private volatile TargetDataLine line;
  private final byte[] buffer;
  private boolean isRunning = true;

  public MicrophoneRecordThread(TargetDataLine line) {
    super();
    this.line = line;
    this.buffer = new byte[line.getBufferSize() / 2];
  }

  @Override
  public void run() {
    System.out.println("Listening...");
    while (isRunning) {
      int count = line.read(buffer, 0, buffer.length);
      if (count > 0) {
        out.write(buffer, 0, count);
      }
    }
    System.out.println("Interrupted");
  }

  public byte[] getOutput() {
    return out.toByteArray();
  }

  public void stopRecording() {
    isRunning = false;
  }
}
