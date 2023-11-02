package com.github.olingard;

import com.github.olingard.analysis.AudioAnalysis;
import com.github.olingard.analysis.Complex;
import com.github.olingard.analysis.KeyPointAnalysis;
import com.github.olingard.errors.RecordAgainInputError;
import com.github.olingard.input.MicrophoneInput;
import com.github.olingard.input.RecordAgainInput;
import com.github.olingard.spectrumgraphics.SpectrumFrame;
import com.github.olingard.spectrumgraphics.SpectrumPanel;

import javax.sound.sampled.LineUnavailableException;
import java.util.Map;

public class Runner {

  public Runner() {
  }

  public void run(SongRepo songRepo, boolean printSpectrum) throws
      LineUnavailableException, InterruptedException {
    boolean recordingAgain = true;
    while (recordingAgain) {
      if (recordAgainInput().contentEquals("y")) {
        byte[] recordingByteArray = new MicrophoneInput().recordSoundByteArray(30000);
        compareRecordingWithDB(songRepo, recordingByteArray, printSpectrum);
      } else {
        recordingAgain = false;
      }
    }
    System.exit(0);
  }

  private String recordAgainInput() {
    while (true) {
      try {
        return RecordAgainInput.getUserInputRecordAgain();
      } catch (RecordAgainInputError e){
        System.out.println(e.getMessage());
      }
    }
  }

  private void compareRecordingWithDB(SongRepo songRepo, byte[] soundBytesRecording, boolean printSpectrum) {
    Complex[][] frequencyDataRecording = AudioAnalysis.do8BitAudioAnalysis(soundBytesRecording);
    int[][] keyPointsRecording = KeyPointAnalysis.keyPoints(frequencyDataRecording);

    Map<Integer, Long> songMaxMatches =
        SongMatch.getMatchesBySong(keyPointsRecording, songRepo.getHashDatabase());

    songMaxMatches
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .forEach(song -> System.out.println(
            songRepo.getSongDatabase().get(song.getKey()) + " has " + song.getValue() +
                " matches."));
    if (printSpectrum) {
      SpectrumPanel spectrumPanel = new SpectrumPanel(frequencyDataRecording, keyPointsRecording);
      new SpectrumFrame(spectrumPanel, "Recording");
    }
  }
}
