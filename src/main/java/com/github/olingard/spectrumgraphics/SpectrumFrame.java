package com.github.olingard.spectrumgraphics;

import javax.swing.JFrame;
import java.awt.HeadlessException;

public class SpectrumFrame extends JFrame {

  public SpectrumFrame(SpectrumPanel spectrumPanel, String title) throws HeadlessException {
    super(title);
    this.setSize(600, 525);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(spectrumPanel);
    this.setVisible(true);
  }
}
