package com.github.olingard;

import com.github.olingard.analysis.AudioAnalysis;
import com.github.olingard.analysis.Complex;
import com.github.olingard.analysis.KeyPointAnalysis;
import com.github.olingard.input.WaveConverter;
import com.github.olingard.spectrumgraphics.SpectrumFrame;
import com.github.olingard.spectrumgraphics.SpectrumPanel;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SongRepo {

  Map<Integer, List<DataPoint>> hashDatabase = new HashMap<>();
  List<String> songDatabase = new ArrayList<>();

  public SongRepo(File wavSongs, boolean printSpectrogram) throws IOException, UnsupportedAudioFileException {
    System.out.println("Starting loading the files!");
    for (File song : Objects.requireNonNull(wavSongs.listFiles())) {
      int songID = songDatabase.size();
      songDatabase.add(song.getName());

      byte[] soundBytes = WaveConverter.convertToByteArray(song);
      Complex[][] frequencyData = AudioAnalysis.do16BitAudioAnalysis(soundBytes, false);
      int[][] keyPoints = KeyPointAnalysis.keyPoints(frequencyData);
      if (printSpectrogram) {
        SpectrumPanel spectrumPanel = new SpectrumPanel(frequencyData, keyPoints);
        SpectrumFrame spectrumFrame = new SpectrumFrame(spectrumPanel, song.getName());
      }

      for (int i = 0; i < keyPoints.length; i++) {
        Integer hashing = HashCode.hash(keyPoints[i]);
        List<DataPoint> bucket = hashDatabase.get(hashing);
        if (bucket == null) {
          bucket = new ArrayList<>();
        }
        bucket.add(new DataPoint(i, songID));
        hashDatabase.put(hashing, bucket);
      }
    }
    System.out.println("Finished loading files!");
  }

  public Map<Integer, List<DataPoint>> getHashDatabase() {
    return hashDatabase;
  }

  public List<String> getSongDatabase() {
    return songDatabase;
  }
}
