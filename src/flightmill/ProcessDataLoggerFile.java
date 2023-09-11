/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flightmill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import flightmill.dataStorageStructs.FinalDataLine;
import flightmill.dataStorageStructs.InputCommandLine;
import flightmill.dataStorageStructs.InputDataLine;
import flightmill.dataStorageStructs.IntermediateDataLine;

/**
 * This programs takes a csv file from a datalogger attached to a flight mill
 * and collates it.
 * 
 * @author wjrfo
 */
public class ProcessDataLoggerFile {

    // header info
    public static String LOCATION = "USDA-ARS Manhattan, KS";
    public static String DATE() {
        DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMM/yyyy");
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.format(month_year);
    }//end DATE()
    public static String PEOPLE = "Sixbury/Rust/Brabec";
    public static String PROGRAM_NAME = "Flight Mill Compression";
    public static String VERSION = "v1.6.2";

    private static AppInterface gui;

    // entry point
    public static void main(String[] args) {

        try {
            // testLocalDateTime();
            InputCommandLine inputCommandLine;
            if (args.length == 0) {
                inputCommandLine = processGUI();
            }//end if we're using a GUI for user input
            else{
                inputCommandLine = processCommandLine(args);
            }//end else we're using the old command line method
            
            if (inputCommandLine.zip_input_file) {
                zipFile(inputCommandLine.inputFileName);
            }
            if (inputCommandLine.inputFileName.endsWith(".zip")) {
                String unzippedFileName = unzipFile(inputCommandLine.inputFileName);
                inputCommandLine.inputFileName = unzippedFileName;
            }

            // load the input file
            List<InputDataLine> inputList = LoadInputFile(inputCommandLine);
            // TODO: Get last input line
            double duration = getDuration(inputList);
            // make list of individual peaks
            List<IntermediateDataLine> processedInputList = processInput(inputList,
                    inputCommandLine);
            // sort individual peaks by channel
            List<List<IntermediateDataLine>> channelSortedInputList = separateIntermedDataByChannel(processedInputList);
            // figure out direction from our list of individual peaks
            List<FinalDataLine> directionedInputList = processDirectionallity(channelSortedInputList, inputCommandLine);
            // write output file
            makeOutputFile(duration, directionedInputList, inputCommandLine);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
    }//end main method

    public static double getDuration(List<InputDataLine> inputList) {
        if (inputList.size() > 0) {
            InputDataLine lastDataLine = inputList.get(inputList.size() - 1);
            return lastDataLine.time;
        }//end if list isn't empty
        else { return 0; }
    }//end getDuration

    /*
     * Gets the path of a config file next to the jar.
     * Also makes sure the file exists, creating it if it doesn't exist.
     */
    public static File getConfigPath() {
        try {
            String executableDir = new File(ProcessDataLoggerFile.class.getProtectionDomain().getCodeSource().getLocation()
            .toURI()).getParent();

            String configPath = executableDir + "\\" + "flight_mill.config";

            File configFile = new File(configPath);
            if (!configFile.exists()) {configFile.createNewFile();}

            return configFile;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("It seems there was a problem getting the path of the executable.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("It seems there was a problem creating the config file.");
        } return null;
    }

    /*
     * Serializes and writes input command line values to a file.
     */
    public static void saveInputCommandLine(InputCommandLine icl) {
        File configFile = getConfigPath();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(configFile);
            for (String line : getInputCommandLineStrings(icl)) { pw.printf(line + "\n"); }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Couldn't find config file.");
        } finally { if (pw != null) { pw.close(); } }
    }//end saveInputCommandLine(icl)
    
    /*
     * Gets a list of lines formatted in the way we format the config file.
     */
    public static List<String> getInputCommandLineStrings(InputCommandLine icl) {
        List<String> lines = new ArrayList<String>();

        for (Field field : InputCommandLine.class.getFields()) {
            try {
                if (field.getName() != "inputFileName" && field.getName() != "outputFileName") {
                    lines.add(field.getName() + ":" + field.get(icl));
                }//end if field isn't just a file path. We don't need to save or display that.
            } catch (IllegalArgumentException e) { e.printStackTrace();
            } catch (IllegalAccessException e) { e.printStackTrace(); }
        }//end making line for each file

        return lines;
    }//end getInputCommandLineStrings(icl)

    /*
    * Reads and deserializes input command lines values from a file.
    */
    public static InputCommandLine loadInputCommandLine() {
        InputCommandLine icl = new InputCommandLine();

        File configFile = getConfigPath();
        try {
            for (String line : Files.readAllLines(configFile.toPath())) {
                // separate string information from line
                int split_index = line.indexOf(":");
                String field_name = line.substring(0, split_index);
                String field_value = line.substring(split_index + 1, line.length());
                // determine which field we're looking at
                Field this_field = InputCommandLine.class.getField(field_name);
                switch (this_field.getType().getName()) {
                    case "java.lang.String":
                        this_field.set(icl, field_value);
                        break;
                    case "int":
                        this_field.setInt(icl, Integer.parseInt(field_value));
                        break;
                    case "double":
                        this_field.setDouble(icl, Double.parseDouble(field_value));
                        break;
                    case "boolean":
                        this_field.set(icl, Boolean.parseBoolean(field_value));
                        break;
                    default:
                        System.out.println("Unsupported type for inputCommandLine: " + this_field.getType().getName());
                        break;
                }//end switch case
            }//end looping through lines in file
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem loading config file.");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("Couldn't find one of the fields specified in the config file.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Couldn't access one of the fields in inputCommandLine.");
        } return icl;
    }//end loadInputCommandLine()

    // parses options from a GUI
    public static InputCommandLine processGUI() {
        // Setup storage object for all the configuration options we get
        InputCommandLine inputCommandLine = new InputCommandLine();
        
        // Show the gui
        gui = new AppInterface();
        gui.setVisible(true);
        
        // do an application loop for the gui
        while (gui.isVisible()) {
            if (gui.isInputDataReady) {
                gui.isInputDataReady = false;
                inputCommandLine = gui.inputCommandLine;
                gui.setVisible(false);
            }
        }//end looping while gui is active
        
        gui = null;

        return inputCommandLine;
    }//end processGUI()

    // parses options on the command line
    public static InputCommandLine processCommandLine(String[] args) {

        InputCommandLine inputCommandLine = new InputCommandLine();

        //<editor-fold defaultstate="collapsed" desc="specify options on command line">
        Options options = new Options();

        Option inputFileOption = new Option("i", "input", true, "input file path");
        inputFileOption.setRequired(true);
        options.addOption(inputFileOption);

        Option outputFileOption = new Option("o", "output", true, "output file path");
        outputFileOption.setRequired(false);
        options.addOption(outputFileOption);

        Option skipLinesOption = new Option("sl", "skipLines", true, 
                "lines to skip at start of input file");
        skipLinesOption.setRequired(false);
        options.addOption(skipLinesOption);

        Option zipOption = new Option("z", "zipInput", false, 
                "zip input file and delete uncompressed file");
        zipOption.setRequired(false);
        options.addOption(zipOption);

        Option dateTimeOption = new Option("ndt", "noDateTime", false, 
                "do not add date/time to output file lines");
        dateTimeOption.setRequired(false);
        options.addOption(dateTimeOption);

        Option peakWidthOption = new Option("pw", "peakWidth", false, 
                "add peak's width to output file lines");
        peakWidthOption.setRequired(false);
        options.addOption(peakWidthOption);
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="parse command line">
        // parse command line
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose 

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
//            System.out.println(e.getMessage());
            formatter.printHelp("java flightmill", options);

            System.exit(1);
        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="save input parameters for further processing">
        String inputFilePath = cmd.getOptionValue("input");
        if (inputFilePath != null) {
            inputCommandLine.inputFileName = inputFilePath;
            inputCommandLine.outputFileName = inputFilePath + ".out";
        }

        String outputFilePath = cmd.getOptionValue("output");
        if (outputFilePath != null) {
            inputCommandLine.outputFileName = outputFilePath;
//            outputFileName = outputFilePath;
        }

        String skipLinesString = cmd.getOptionValue("skipLines");
        if (skipLinesString != null) {
            inputCommandLine.skipLines = Integer.parseInt(skipLinesString);
//            skipLines = Integer.parseInt(skipLinesString);
        }

        if (cmd.hasOption("z")) {
            inputCommandLine.zip_input_file = true;
        }

        if (cmd.hasOption("ndt")) {
            inputCommandLine.add_date_time_column = false;
        }

        if (cmd.hasOption("pw")) {
            inputCommandLine.add_peak_width_column = true;
        }
//</editor-fold>

        return inputCommandLine;
    }//end processCommandLine(args)

    // load the input file into an input list
    public static List<InputDataLine> LoadInputFile(InputCommandLine icl) 
            throws FileNotFoundException {

        String fileName = icl.inputFileName;

        List<InputDataLine> inputDataList = new ArrayList<>();

        File inputFile = new File(fileName);
        Scanner myReader = new Scanner(inputFile);
        for (int idx = 0; idx < icl.skipLines; idx++) {     // skip the header lines
            myReader.nextLine();
        }
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            try {
                InputDataLine inputData = new InputDataLine(data);
                inputDataList.add(inputData);
            } catch (NumberFormatException nfe) {
                System.out.println("format error");
            }
        }
        System.out.println("inputDataList length " + inputDataList.size());
        myReader.close();

        return inputDataList;
    }//end LoadInputFile(icl)

    public static String OUTPUT_FOLDER_NAME = "output-folder";

    /**
     * This method reformats a given path in order to change the extension, put it into a new directory in the location of the original file, and name that directory based on the current date.
     * @param outputFilePath The path you want to format
     * @param ensureDirectoryExists If this is true, then this method will create a new directory if it doesn't already exist.
     * @return Returns a resulting path as a String
     */
    public static File reformatOutputFile(String outputFilePath, boolean ensureDirectoryExists) {
        // figure out the path of the output directory
        File outputFile = new File(outputFilePath);
        File parentDirectory = outputFile.getParentFile();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dir_formatter = DateTimeFormatter.ofPattern("yyyy-MM-");
        DateTimeFormatter file_formatter = DateTimeFormatter.ofPattern(";d-H-m-s");
        File newDirectory = new File(parentDirectory.getAbsolutePath() + File.separator + currentDateTime.format(dir_formatter) + OUTPUT_FOLDER_NAME);
        // create the directory if it doesn't exist
        if (ensureDirectoryExists && !newDirectory.exists()) {
            newDirectory.mkdir();
        }//end if new directory needs to be created
        // create the resulting path of the output file
        String priorFileName = outputFile.getName();
        String newExtension = ".OUT";
        String current_time_stamp = currentDateTime.format(file_formatter);
        String newFileName = priorFileName.substring(0, priorFileName.lastIndexOf(".")) + current_time_stamp + newExtension;
        outputFile = new File(newDirectory.getAbsolutePath() + File.separator + newFileName);

        return outputFile;
    }//end reformatOutputFile(outputFilePath)

    // write out the collated data
    public static void makeOutputFile(double duration, List<FinalDataLine> inputList,
            InputCommandLine icl) throws FileNotFoundException {

        // get the file modified time to adjust output date/times to 
        //   something close to real time
        FileTime fileTime = getFileCreationDate(new File(icl.inputFileName));

        // set up array to hold counts of peaks per channel
        int[] channelCounts = new int[icl.numberOfChannelsUsed];

        // count the number of peaks per channel
        for (FinalDataLine idl : inputList) {    
            channelCounts[idl.channel]++;
        }

        // save to output file
        File outputFile = new File(icl.outputFileName);
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }//end if we need to make the resulting directories
        PrintWriter pw = new PrintWriter(outputFile);

        // print first section of header
        pw.printf("%s  %s\n%s\n", PROGRAM_NAME, VERSION, LOCATION + "\t" + DATE() + "\t" + PEOPLE);

        // print second section of header
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        pw.printf("File name: %s\nData Processed: %s\n", icl.inputFileName, 
                dateFormat.format(cal.getTime()));
        
        // print third section of header
        if(inputList.size() > 0){
            // do some time calculations
            long tim1 = fileTime.toMillis() - (long)(duration * 1000);
            long tim2 = tim1 + (long)(duration * 1000);
            double minutesDuration = duration / 60;
            // print out the time stuff
            pw.printf("Data Collected in %1.1f minutes\t", minutesDuration);

            // new line with date
            pw.printf("\nStart-End date/time: ");
            pw.printf(dateFormat.format(new Date(tim1)));
            pw.printf("  -  ");
            pw.printf(dateFormat.format(new Date(tim2)));
            pw.printf("\n");
        }//end if there are any inputs
        else { pw.close(); return; }

        // print configuration settings
        pw.printf("Parameters from Config File:\n");
        for (String line : getInputCommandLineStrings(icl)) {
            pw.printf(line + "\n");
        }

        // print counts per channel
        for (int idx = 0; idx < icl.numberOfChannelsUsed; idx++) {
            pw.printf("Chan %2d  Peaks: %5d\n", idx + 1, channelCounts[idx]);
        }
        
        // print headers for the columns we're about to print
        pw.printf("Chan#");
        if (icl.add_second_peak_columns) { pw.printf("\tPkTime1\t        PkTime2  "); }
        else { pw.printf("\tPkTime"); }
        if (icl.add_date_time_column) {
            pw.printf("\tDate and Time");
        }//end if we should print the data time data
        if (icl.add_peak_width_column) {
            if (icl.add_second_peak_columns) { pw.printf("\t    PkWdth1\tPkWdth2"); }
            else { pw.printf("\tPkWidth"); }
        }//end if we should print the width of the peak
        // print out direction
        pw.printf("\tDirec");
        // print out revolution
        if (icl.add_revolution_column) {
            pw.printf("\t  Rev");
        }//end if we should print out diff between start peak times
        if (icl.add_width_ratio_column) {
            pw.printf("\tWRtio");
        }//end if we should print out width ratio
        
        pw.printf("\n");
        // print out data ordered by channel and in elapsed time order
        for (int i = 0; i < inputList.size(); i++) {
            FinalDataLine outputData = inputList.get(i);
            pw.printf("%2d\t%9.3f", outputData.channel + 1, outputData.elapsedTime1);
            if (icl.add_second_peak_columns) { pw.printf("\t%9.3f", outputData.elapsedTime2); }
            if (icl.add_date_time_column) {
                pw.printf("\t");
                long tim = new Double(outputData.elapsedTime1 * 1000).intValue() - (long)(duration * 1000);
                pw.printf(dateFormat.format(new Date(tim + fileTime.toMillis())));
            }//end if we're printing time for output
            if (icl.add_peak_width_column) {
                pw.printf("\t%d", outputData.peakWidth1);
                if (icl.add_second_peak_columns) { pw.printf("\t%d", outputData.peakWidth2); }
            }//end if we're printing peak width
            // print out direction
            pw.printf("\t%d", outputData.direction);
            if (icl.add_revolution_column) {
                if (i == 0 || inputList.get(i-1).channel != outputData.channel) { pw.printf("\t%6.3f", outputData.elapsedTime1); }
                else { pw.printf("\t%6.3f", outputData.elapsedTime1 - inputList.get(i-1).elapsedTime1); }
            }//end if we should print revolution time (diff between peak times)
            if (icl.add_width_ratio_column) {
                if (outputData.direction != 0) {
                    double minPeakWidth = Math.min(outputData.peakWidth1, outputData.peakWidth2);
                    double maxPeakWidth = Math.max(outputData.peakWidth1, outputData.peakWidth2);
                    double ratio = minPeakWidth / maxPeakWidth;
                    pw.printf("\t%4.3f", ratio);
                }//end if we have valid direction here probably
                else { pw.printf("\t   "); }
            }//end if we should print width ratio
            pw.printf("\n");
        }//end looping over all the stuff to print out
        
        // close output file
        pw.close();

    }//end makeOutputFile(inputList, icl)

    // process input 
    public static List<IntermediateDataLine> processInput(List<InputDataLine> list,
            InputCommandLine icl) {

        List<IntermediateDataLine> outputList = new ArrayList<>();

        int nocu = list.get(0).channels.length;

        // find the threshold for each channel
        icl.numberOfChannelsUsed = list.get(0).channels.length;
        double[] minVal = new double[nocu];
        double[] maxVal = new double[nocu];
        double[] threshold = new double[nocu];

        for (InputDataLine inputData : list) {
            for (int idx = 0; idx < nocu; idx++) {
                if (inputData.channels[idx] < minVal[idx]) {
                    minVal[idx] = inputData.channels[idx];
                }
                if (inputData.channels[idx] > maxVal[idx]) {
                    maxVal[idx] = inputData.channels[idx];
                    
                }
            }
        }

        // print out threshold value for each channel
        for (int idx = 0; idx < minVal.length; idx++) {

            threshold[idx] = 1.5; //(minVal[idx] + maxVal[idx]) / 2;
            System.out.printf("%2d  %5.3f  %5.3f\n", idx, minVal[idx], maxVal[idx]);
        }

        Iterator<InputDataLine> it = list.iterator();
        InputDataLine lastInputData = it.next();
        IntermediateDataLine[] idla = new IntermediateDataLine[nocu];
        for (; it.hasNext();) {
            InputDataLine inputData = it.next();
            for (int idx = 0; idx < nocu; idx++) {
                if (inputData.channels[idx] > threshold[idx] && 
                        lastInputData.channels[idx] < threshold[idx]) {
                    idla[idx] = new IntermediateDataLine(idx, inputData.time, 
                            inputData.channels[idx]);
                    outputList.add(idla[idx]);
                }
                else if (inputData.channels[idx] > threshold[idx] ) {
                    if (idla[idx] != null){
                        idla[idx].peakWidth++;
                    }
                }
            }
            lastInputData = inputData;
        }

        return outputList;
    }//end processInput(list, icl)

    public static List<List<IntermediateDataLine>> separateIntermedDataByChannel(List<IntermediateDataLine> intermedDatas) {
        List<List<IntermediateDataLine>> chan_sorted = new ArrayList<>();

        // put channel data into proper list
        for (int i = 0; i < intermedDatas.size(); i++) {
            IntermediateDataLine this_intermed_data = intermedDatas.get(i);
            int this_channel = this_intermed_data.channel;
            // check to make sure bounds of array are good
            while (chan_sorted.size() <= this_channel) {
                chan_sorted.add(new ArrayList<>());
            }//end looping while we need to expand chan_sorted
            // actually add this_intermed_data into proper list based on channel
            chan_sorted.get(this_channel).add(this_intermed_data);
        }//end sorting every intermediate data line into proper channel

        return chan_sorted;
    }//end sortIntermediateDataByChannel(intermedDatas)

    public static List<FinalDataLine> processDirectionallity(List<List<IntermediateDataLine>> sortedIntermedDatas, InputCommandLine icl) {
        /*
         * Ideas that went into making this method:
         * From beginning of loop, only look forward for pairs. When we find pairs, skip forward to after what we just did.
         * FDL constructor should take care of directionallity as long as we can pair up idls.
         * Anything we can't pair up, we immediately add as singleton fdl with direction 0.
         */
        // initialize variables to help with loop and stuff
        List<FinalDataLine> fdls = new ArrayList<>();

        for (List<IntermediateDataLine> intermedDatas : sortedIntermedDatas) {
            // try to group all the idls in intermedDatas into fdls
            for (int i = 0; i < intermedDatas.size(); i++) {
                // let's get our references for this iteration
                IntermediateDataLine this_idl = intermedDatas.get(i);
    
                if (i+1 < intermedDatas.size()) {
                    // Figure out if the time difference between this_idl and the next one is very small
                    IntermediateDataLine next_idl = intermedDatas.get(i+1);
                    // make sure to test for being really slow
                    double thresh_to_use = icl.thresh_seconds_fast;
                    // get max width of peak, indicator of speed
                    int max_pw = Math.max(this_idl.peakWidth, next_idl.peakWidth);
                    if (max_pw > icl.thresh_peakWidth_fast) {thresh_to_use = icl.thresh_seconds_fast;}
                    if (max_pw > icl.thresh_peakWidth_medium) {thresh_to_use = icl.thresh_seconds_medium;}
                    if (max_pw > icl.thresh_peakWidth_slow) {thresh_to_use = icl.thresh_seconds_slow;}
                    // figure out if we're probably looking at a pair of notches
                    if (Math.abs(this_idl.elapsedTime - next_idl.elapsedTime) < thresh_to_use) {
                        // we found a pairing
                        fdls.add(new FinalDataLine(this_idl, next_idl));
                        // loop maintenance, take us to element after next
                        i = i + 1;
                    }//end if we found a pairing
                    else {
                        // if pairing was not found, then add this_idl as singleton fdl
                        fdls.add(new FinalDataLine(this_idl));
                    }//end else we can't pair this_idl
                }//end if we have a next element we can look at
                else {
                    // this is the last element, might as well add as singleton fdl
                    fdls.add(new FinalDataLine(this_idl));
                }//end else this is the last element
            }//end trying to group idls into fdls
        }//end looping over lists sorted by channel


        return fdls;
    }//end processDirectionallity(intermedDatas)

    // get input file modizfication time to set beginning time for output date/time
    public static FileTime getFileCreationDate(File file) {

        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime modTime = attrs.lastModifiedTime();

            return modTime;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }//end getFileCreationDate(file)

    // zip input file to save space
    public static void zipFile(String sourceFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(sourceFile + ".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.close();
        fis.close();
        fos.close();
    }//end zipFile(sourceFile)

    // unzip input file
    public static String unzipFile(String zipFileName) throws IOException {
        if (!zipFileName.endsWith(".zip")) {
            System.err.println(zipFileName + " is not a zip file");
        }

        File destDir = new File(zipFileName).getAbsoluteFile();

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry zipEntry = zis.getNextEntry();
        File newFile = new File(destDir, zipEntry.getName());
        File parent = newFile.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            zis.close();
            throw new IOException("Failed to create directory " + parent);
        }

        // write file content
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();
        zis.closeEntry();
        zis.close();

        return newFile.getAbsolutePath();
    }//end unzipFile(zipFileName)
}//end class ProcessDataLoggerFile
