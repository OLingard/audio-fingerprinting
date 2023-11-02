package com.github.olingard.analysis;

public class AudioAnalysis {
  private static final int CHUNK_SIZE = 2048;
  private static final double MAX_16_BIT = 32768;
  private static final double MAX_8_BIT = 128;


  /**
   * Converts a byte array into a Complex number array representing the amplitude (Modulo of the
   * Complex number) and frequency (Index in the inner array) of sample chunks of size {@value CHUNK_SIZE}.
   * Each inner Complex array represents a sequential time period of the song.
   *
   * @param soundBytes: A byte array with one byte representing one sample of the sound
   */
  public static Complex[][] do8BitAudioAnalysis(byte[] soundBytes) {
    double[] data = new double[soundBytes.length];
    for (int i = 0; i < soundBytes.length; i++) {
      data[i] = 1.0 * soundBytes[i] / ((double) MAX_8_BIT);
    }
    return doFFTAnalysis(data);
  }

  /**
   * Converts a byte array into a Complex number array representing the amplitude (Modulo of the
   * Complex number) and frequency (Index in the inner array) of sample chunks of size {@value CHUNK_SIZE}.
   * Each inner Complex array represents a sequential time period of the song.
   *
   * @param bytes: A byte array with two byte representing one sample of the sound - either in little or big endian form.
   * @param bigEndian: A boolean for whether the input byte array is in little or big endian form.
   */
  public static Complex[][] do16BitAudioAnalysis(byte[] bytes, boolean bigEndian) {
    int n = bytes.length;

    double[] data = new double[n / 2];
    for (int i = 0; i < n / 2; i++) {
      if (bigEndian) {
        data[i] = bigEndian2ByteConversionToDouble(bytes[2 * i], bytes[2 * i + 1]);
      } else {
        data[i] = littleEndian2ByteConversionToDouble(bytes[2 * i], bytes[2 * i + 1]);
      }
    }
    return doFFTAnalysis(data);
  }

  private static double littleEndian2ByteConversionToDouble(byte leftByte, byte rightByte) {
    return ((short) (((rightByte & 0xFF) << 8) | (leftByte & 0xFF))) / ((double) MAX_16_BIT);
  }

  private static double bigEndian2ByteConversionToDouble(byte leftByte, byte rightByte) {

    return ((short) (((leftByte & 0xFF) << 8) | (rightByte & 0xFF))) / ((double) MAX_16_BIT);
  }

  private static Complex[][] doFFTAnalysis(double[] sampledData) {

    final int totalSize = sampledData.length;

    int amountPossible = totalSize / CHUNK_SIZE;

    // When turning into frequency domain we'll need complex numbers:
    Complex[][] results = new Complex[amountPossible][];

    // For all the chunks:
    for (int times = 0; times < amountPossible; times++) {
      Complex[] complex = new Complex[CHUNK_SIZE];
      for (int i = 0; i < CHUNK_SIZE; i++) {
        // Put the time domain data into a complex number with imaginary part as 0:
        complex[i] = new Complex(sampledData[(times * CHUNK_SIZE) + i], 0);
      }

      results[times] = FastFourierTransformer.fft(complex);
    }
    return results;
  }
}
