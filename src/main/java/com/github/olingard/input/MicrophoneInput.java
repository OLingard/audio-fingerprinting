package com.github.olingard.input;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.util.Scanner;

public class MicrophoneInput {

  private final TargetDataLine line;
  private final AudioFormat audioFormat;
  private final MicrophoneRecordThread microphoneRecordThread;

  public MicrophoneInput() throws LineUnavailableException {
    this.audioFormat = getFormat();
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
    this.line = (TargetDataLine) AudioSystem.getLine(info);
    this.microphoneRecordThread = new MicrophoneRecordThread(line);
  }

  /**
   * Records microphone input until enter is pressed.
   * @return A byte array representing the input using the format: {@link #getFormat()}
   */
  public byte[] recordSoundByteArray() throws LineUnavailableException {
    byte[] soundBytesRecording = new byte[0];
    Scanner scanner = new Scanner(System.in);
    startRecording();
    boolean exit = false;
    System.out.println("Press enter to stop recording");
    while (!exit) {
      String input = scanner.nextLine();
      if (input != null) {
        soundBytesRecording = stopRecordingAndReturnByteArray();
        exit = true;
      }
    }
    return soundBytesRecording;
  }

  /**
   * Records microphone input until enter is pressed.
   * @param millisecondsToRecord length of time to record input for
   * @return A byte array representing the input using the format: {@link #getFormat()}
   */
  public byte[] recordSoundByteArray(long millisecondsToRecord)
      throws LineUnavailableException, InterruptedException {
    byte[] soundBytesRecording;
    startRecording();
    System.out.println(millisecondsToRecord / 1000 + " seconds of recording...");
    Thread.sleep(millisecondsToRecord);
    soundBytesRecording = stopRecordingAndReturnByteArray();
    return soundBytesRecording;
  }

  private void startRecording() throws LineUnavailableException {
    line.open(audioFormat, line.getBufferSize());
    line.start();
    microphoneRecordThread.start();
  }

  private byte[] stopRecordingAndReturnByteArray() {
    System.out.println("Stopping listening...");
    byte[] output = microphoneRecordThread.getOutput();
    line.stop();
    microphoneRecordThread.stopRecording();
    return output;
  }

  private AudioFormat getFormat() {
    float sampleRate = 44100;
    int sampleSizeInBits = 8;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;

    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  }

}
