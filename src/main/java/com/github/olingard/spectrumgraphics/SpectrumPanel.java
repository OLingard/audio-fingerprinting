package com.github.olingard.spectrumgraphics;

import com.github.olingard.analysis.Complex;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

public class SpectrumPanel extends JPanel {
  @Serial
  private static final long serialVersionUID = 1L;

  private static final int blockSizeY = 3;
  private static final int blockSizeX = 2;
  private static final boolean logModeEnabled = true;
  private final Complex[][] frequencyData;
  private final int[][] keyPoints;


  public SpectrumPanel(Complex[][] frequencyData, int[][] keyPoints) {
    this.frequencyData = frequencyData;
    this.keyPoints = keyPoints;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    drawSpectrum(g2d);

  }

  public void drawSpectrum(Graphics2D g2d) {

    for (int i = 0; i < frequencyData.length; i++) {
      int freq = 0;
      int[] keyPointsByChunk = keyPoints[i];
      int size = 175;
      for (int line = 1; line < size; line++) {

        double magnitude = Math.log(frequencyData[i][freq].abs() + 1);

        boolean isKeyFreq = false;
        for (int keyPoint : keyPointsByChunk) {
          if (keyPoint == freq) {
            isKeyFreq = true;
            break;
          }
        }

        if (isKeyFreq) {
          g2d.setColor(Color.red);
        } else {
          g2d.setColor(new Color(0, (int) magnitude * 20, (int) Math.min(255, magnitude * 40)));
        }
        g2d.fillRect(i * blockSizeX, (size - line) * blockSizeY, blockSizeX, blockSizeY);


        double logScale = Math.log10(line) * Math.log10(line);
        if (logModeEnabled && logScale > 1) {
          freq += (int) logScale;
        } else {
          freq++;
        }
      }
    }
  }
}
