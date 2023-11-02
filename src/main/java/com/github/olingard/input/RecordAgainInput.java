package com.github.olingard.input;

import com.github.olingard.errors.RecordAgainInputError;

import java.util.Scanner;

public class RecordAgainInput {
  public static String getUserInputRecordAgain() {
    System.out.println("Would you like to record? (y/n) ");
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine().toLowerCase();
    if (input.contentEquals("y") || input.contentEquals("n")) {
      return input;
    } else {
      throw new RecordAgainInputError("Enter y or n.");
    }
  }
}
