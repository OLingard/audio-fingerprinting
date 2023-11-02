package com.github.olingard.analysis;

import java.util.Objects;

public class Complex {
  private final double re;   // the real part
  private final double im;   // the imaginary part

  // create a new object with the given real and imaginary parts
  public Complex(double real, double imag) {
    re = real;
    im = imag;
  }

  // return a string representation of the invoking Complex object
  public String toString() {
    if (im == 0) {
      return re + "";
    }
    if (re == 0) {
      return im + "i";
    }
    if (im < 0) {
      return re + " - " + (-im) + "i";
    }
    return re + " + " + im + "i";
  }

  // return abs/modulus/magnitude
  public double abs() {
    return Math.hypot(re, im);
  }

  // return a new Complex object whose value is (this + b)
  public Complex plus(Complex b) {
    Complex a = this;             // invoking object
    double real = a.re + b.re;
    double imag = a.im + b.im;
    return new Complex(real, imag);
  }

  // return a new Complex object whose value is (this - b)
  public Complex minus(Complex b) {
    Complex a = this;
    double real = a.re - b.re;
    double imag = a.im - b.im;
    return new Complex(real, imag);
  }

  // return a new Complex object whose value is (this * b)
  public Complex times(Complex b) {
    Complex a = this;
    double real = a.re * b.re - a.im * b.im;
    double imag = a.re * b.im + a.im * b.re;
    return new Complex(real, imag);
  }

  // return the real or imaginary part
  public double re() {
    return re;
  }

  public double im() {
    return im;
  }

  public boolean equals(Object x) {
    if (x == null) {
      return false;
    }
    if (this.getClass() != x.getClass()) {
      return false;
    }
    Complex that = (Complex) x;
    return (this.re == that.re) && (this.im == that.im);
  }

  public int hashCode() {
    return Objects.hash(re, im);
  }
}
