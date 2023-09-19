# usda-flight-mill

This program is meant to take output from the Flight Mill through WinDaq. Files from WinDaq are converted to csv files, and then entered into this application to be processed. This results in a text file, which might or might not be compressed. The original program was developed by Bill Rust, but continuing development will be done by Nicholas Sixbury as of March 2023.

## Compilation and Environment

Please note that for compilation purposes, the main method in AppInterface.java should be treated as the true main method. The main method in ProcessDataLoggerFile is an artifact from an earlier point in this software's history, back when it was a CLI app.

In terms of development environments, the repository contains configurations for both VS Code and Apache NetBeans. Apache NetBeans should be able to compile everything as is, but with VS Code, you'll need the java extension.

## Version Information

The recommended version of java to use is Java 8.

One other thing to note is how the version number for this program works. Of course, it's completely arbitrary, but to make it easier to switch the number over, everything relates back to the static string in ProcessDataLoggerFile, called VERSION. So, to change the version, just change that variable, and everything else should follow suit.
