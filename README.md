# usda-flight-mill

This program is meant to take output from the Flight Mill through WinDaq. Files from WinDaq are converted to csv files, and then entered into this application to be processed.

This results in a text file, which might or might not be compressed.

The original program was developed by Bill Rust, but continuing development will be done by Nicholas Sixbury as of March 2023.

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

## Reading the Output Files

[The following section about the output file will assume that you are using the default config. In order to reset your config, simply delete the flight_mill.config file and restart the program.]

As discussed previously, each time the flag passes the sensor in the flight mill in a normal fashion, two peaks will be generated, and these two peaks will be put on one line in the output file. Of these two peaks, the first peak occured at PkTime1, and the second peak occured at PkTime2.

The columns for peak width (PkWdth1 and PkWdth2) work similarly. The peak width, for any that might wonder, is the number of 4-milisecond readings that are labelled as one peak. So, a peak width of 8, for example, would indicate that the peak lasted approximately 32 miliseconds.

From the peak widths, we can tell direction. If the first peak width is smaller than the second, then we know the insect is flying forwards, which is indicated by a direction of 1. If the insect went backwards, as indicated by the first peak width being higher than the second, that would give a direction of -1. If the direction is 0, that indicates that we couldn't find a second part of the flag for that peak reading. This generally occurs when the insect is going extremely slowly, so direction readings of 0 generally indicate the insect is at rest.

Based on the given date and time when collections started, the program will give a date and time of each peak. This can be useful for 24-hour-plus data collection, as you can see when the insects are most active.

## Sample Files

Several sample files can be foung in the /data/old or obsolete files/ folder from earlier in development. If you'd simply like to take a look at an hour or real data, then please use the /data/hour_long_sample_data.csv file.

For additional information on operating the software and collecting data with the flight mill, please refer to the flight mill user guide.

## Peaks, Bouts, and Revolutions

The flight mill has flags that spin in a circle, as propelled by the attached insect. Each flag has a notch, such that whenever the flag passes the sensor, the flag will be detected twice. Now, it should be noted that the flight mill currently has eight channels, and a flag for each. The channels are all independent, and simply allow for multiple readings at once.

However, the flight mill's hardware does not output that information in a way that's useful for a human to read through. For every four miliseconds, the flight mill will give a voltage reading. When there's nothing in front of the sensor, the reading will be close to zero, and when there is something in front of the sensor, the reading will be closer to 3. As such, we decide that if the voltage reading is higher than 1.5, then there is something in front of the sensor.

From this, we can determine the points at which the flag was in front of the sensor, but there's a lot of empty space as well. Each time part of the flag passes in front of the sensor, it will generate multiple lines of data, proportional to how long it was in front of the sensor. From this, we can understand our definition of a peak.

In flight mill terms, a peak is a contiguous stretch of readings in which the flight mill registered an object being detected. Each time the flag passes the sensor in a normal fashion, we expect to see two peaks. The flags are shaped so that one peak will be bigger than the other, so from the ordering of these peaks, we can determine the direction.

It should be noted that for the purposes of output by the program, the final output file will have one line for each flag pass, instead of keeping the peaks split up.

Now, the insect attached to the flight mill won't necessarily fly for the enitre time it's on the flight mill; it might rest for a bit. In flight mill terms, we refer to the periods of activity in between rest as bouts. Bouts are not currently processed by the flight mill software.

Another flight mill term is revolution. A revolution is the time it took for the flag to go all the way around the flight mill in a complete circle. If the option is enabled, then this reading will be shown in seconds under the "Rev" heading.
