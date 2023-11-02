package com.github.olingard.analysis;

public class KeyPointAnalysis {


  /**
   * Analyses the sample chunks of FFT data to return the most prominent frequency band within each
   * of the levels specified by the upperBounds. These bounds can be changed to increase or decrease
   * the accuracy needed to match the song.
   *
   * @param frequencyData: FFT-transformed sound data
   */
  public static int[][] keyPoints(Complex[][] frequencyData) {
    int upperLimit = 150;
    int[] upperBounds = {40, 90, upperLimit};
    int[][] recordPoints = new int[frequencyData.length][upperBounds.length];

    for (int line = 0; line < frequencyData.length; line++) {
      int index = 0;
      double[] highScores = new double[upperBounds.length];
      for (int freq = 1; freq < upperLimit - 1; freq++) {
        double mag = Math.log(frequencyData[line][freq].abs() + 1);
        if (freq > upperBounds[index]) {
          index++;
        }
        if (mag > highScores[index]) {
          highScores[index] = mag;
          recordPoints[line][index] = freq;
        }
      }

    }
    return recordPoints;

  }

}
