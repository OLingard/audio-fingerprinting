package com.github.olingard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SongMatch {

  private static final int FUZZ_FACTOR = 10;

  /**
   * @param keyPointsRecording Key Points of the recording we want to match to songs in the database.
   * @param hashDatabase       Key: Hash of keyPoints, Value: List of Datapoints with the specific hash
   *                           which includes their songId and time the specific hash occurred in the song.
   * @return A Map of songId and the greatest number of matches found in the song with a similar offset.
   */
  public static Map<Integer, Long> getMatchesBySong(int[][] keyPointsRecording,
                                                    Map<Integer, List<DataPoint>> hashDatabase) {
    Map<Integer, List<Integer>> songMatchesByID =
        getMatchesForRecordingBySongID(keyPointsRecording, hashDatabase);
    //Integer - songID, long - max matches for certain time offsets.
    Map<Integer, Long> songMatchesCount = new HashMap<>();
    for (Map.Entry<Integer, List<Integer>> song : songMatchesByID.entrySet()) {
      long maxMatches = song.getValue().stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .values()
          .stream()
          .max(Comparator.naturalOrder())
          .get();
      songMatchesCount.put(song.getKey(), maxMatches);
    }
    return songMatchesCount;
  }


  /**
   * @return Key: SongId, Value: The time offset of every match with our database
   */
  private static Map<Integer, List<Integer>> getMatchesForRecordingBySongID(
      int[][] keyPointsRecording,
      Map<Integer, List<DataPoint>> hashDatabase) {

    Map<Integer, List<Integer>> songMatchesByTimeOffset = new HashMap<>();
    for (int i = 0; i < keyPointsRecording.length; i++) {
      Integer hashing = HashCode.hash(keyPointsRecording[i]);
      List<DataPoint> bucket = hashDatabase.get(hashing);
      if (bucket != null && Arrays.stream(keyPointsRecording[i]).sum() != 0) {
        for (DataPoint data : bucket) {
          List<Integer> timeDifference = songMatchesByTimeOffset.get(data.getSongID());
          if (timeDifference == null) {
            timeDifference = new ArrayList<>();
          }
          timeDifference.add((data.getTime() - i) - (data.getTime() - i) % FUZZ_FACTOR);
          songMatchesByTimeOffset.put(data.getSongID(), timeDifference);
        }
      }
    }
    return songMatchesByTimeOffset;
  }
}
