# usda-flight-mill

This program is meant to take output from the Flight Mill through WinDaq. Files from WinDaq are converted to csv files, and then entered into this application to be processed. This results in a text file, which might or might not be compressed. The original program was developed by Bill Rust, but continuing development will be done by Nicholas Sixbury as of March 2023.

## Explanation of Flight Mill Terms

[Add something about peaks and bouts and all that, what we learn from the data, and how to read output files]

## Explanation of Sample Files

[Add something here about running the sample files so anyone can look at the input/output themselves. I should maybe also add the Flight Mill user guide to this repo.]

## Compilation and Environment

Please note that for compilation purposes, the main method in AppInterface.java should be treated as the true main method. The main method in ProcessDataLoggerFile is an artifact from an earlier point in this software's history, back when it was a CLI app.

In terms of development environments, the repository contains configurations for both VS Code and Apache NetBeans. Apache NetBeans should be able to compile everything as is, but with VS Code, you'll need the java extension.

## Version Information

The recommended version of java to use is Java 8.

One other thing to note is how the version number for this program works. Of course, it's completely arbitrary, but to make it easier to switch the number over, everything relates back to the static string in ProcessDataLoggerFile, called VERSION. So, to change the version, just change that variable, and everything else should follow suit.

## Source File Explanation

In this section, I'll give a brief rundown of how each code file works with the rest of the program.

- ProcessDataLoggerFile.java : This file was originally the only file in the program, back when it was created by Bill as a CLI app. Most of the methods are static, and the methods in this file are where most of the actual data processing happens. As such, the GUI makes calls to this file in order to do processing.
- AppInterface.java/.form : This swing form represents the main window of the application. It makes calls to ProcessDataLogger for data processing and File I/O, and it also uses ConfigDialog.java/.form and DateTimeDialog.java/.form to display custom dialog. Within this class, there is a convention that swing controls which are referred to within the java code have the prefix "ux" appended to the variable name.
- ConfigDialog.java/.form : This swing form contains options allowing the user to change some of the configurations stored in InputCommandLine.
- DateTimeDialog.java/.form : This swing form contains a datetimepicker from LGoodDatePicker. This window is used to prompt the user to provide a time and date when data collection on the flight mill started.
- InputCommandLine.java : This class is essentially a struct which just holds various configuration options that can be passed around for various methods to refer to. The non-String fields of this class are saved and loaded from a configuration file. In Bill's original code, this class was an inner class of ProcessDataLoggerFile, but it was moved to a separate file for organizational reasons.
- InputDataLine.java : This class represents one line from the input file. A list of InputDataLines holds the raw data before it has been processed. In Bill's original code, this class was an inner class of ProcessDataLoggerFile, but it was moved to a separate file for organizational reasons.
- IntermediateDataLine.java : This class represents one peak, without taking notched flags into account. Some number of InputDataLines are essentially turned into one IntermediateDataLines, so the conversion of InputDataLine to IntermediateDataLine cuts down the amount of data to work with significantly. In Bill's original code, this class was an inner class of ProcessDataLoggerFile, but it was moved to a separate file for organizational reasons.
- FinalDataLine.java : This class represents one peak, taking notched flags into account, and therefore also tracking the direction at that point in time. It generally consists of two IntermediateDataLines merged together, though it can consist of only one IntermediateDataLine if a pairing cannot be found in that instance. This class was added by Nicholas in the GUI version to start tracking direction without needing to refactor Bill's processing methods for InputDataLines and IntermediateDataLines.
