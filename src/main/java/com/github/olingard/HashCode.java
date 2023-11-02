package com.github.olingard;

import java.util.Arrays;

public class HashCode {

  private static final int FUZZ_FACTOR = 3;

  public static int hash(int[] keyPoints) {
    for (int i = 0; i < keyPoints.length; i++) {
      keyPoints[i] = keyPoints[i] - keyPoints[i] % FUZZ_FACTOR;
    }
    return Arrays.hashCode(keyPoints);
  }

}