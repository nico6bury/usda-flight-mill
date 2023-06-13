# usda-flight-mill

This program is meant to take output from the Flight Mill through WinDaq. Files from WinDaq are converted to csv files, and then entered into this application to be processed. This results in a text file, which might or might not be compressed. The original program was developed by Bill Rust, but continuing development will be done by Nicholas Sixbury as of March 2023.

Please note that for compilation purposes, the main method in AppInterface.java should be treated as the true main method. The main method in ProcessDataLoggerFile is an artifact from an earlier point in this software's history, back when it was a CLI app.