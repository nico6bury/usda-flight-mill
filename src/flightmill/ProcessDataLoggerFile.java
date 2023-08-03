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
    private static String TITLE = "USDA-ARS Manhattan, KS\tMar/2023\tSixbury/Rust/Brabec";
    private static String PROGRAM_NAME = "Flight Mill Compression";
    private static String VERSION = "v.2.23";

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
            
            if (inputCommandLine.isZipFileFlg()) {
                zipFile(inputCommandLine.getInputFileName());
            }
            if (inputCommandLine.getInputFileName().endsWith(".zip")) {
                String unzippedFileName = unzipFile(inputCommandLine.getInputFileName());
                inputCommandLine.setInputFileName(unzippedFileName);
            }

            // load the imput file
            List<InputDataLine> inputList = LoadInputFile(inputCommandLine);
            // make list of individual peaks
            List<IntermediateDataLine> processedInputList = processInput(inputList,
                    inputCommandLine);
            // write output file
            makeOutputFile(processedInputList, inputCommandLine);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
    }//end main method

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
            inputCommandLine.setInputFileName(inputFilePath);
            inputCommandLine.setOutputFileName(inputFilePath + ".out");
        }

        String outputFilePath = cmd.getOptionValue("output");
        if (outputFilePath != null) {
            inputCommandLine.setOutputFileName(outputFilePath);
//            outputFileName = outputFilePath;
        }

        String skipLinesString = cmd.getOptionValue("skipLines");
        if (skipLinesString != null) {
            inputCommandLine.setSkipLines(Integer.parseInt(skipLinesString));
//            skipLines = Integer.parseInt(skipLinesString);
        }

        if (cmd.hasOption("z")) {
            inputCommandLine.setZipFileFlg(true);
        }

        if (cmd.hasOption("ndt")) {
            inputCommandLine.setDataTimeFlg(false);
        }

        if (cmd.hasOption("pw")) {
            inputCommandLine.setPeakWidthFlg(true);
        }
//</editor-fold>

        return inputCommandLine;
    }//end processCommandLine(args)

    // load the input file into an input list
    public static List<InputDataLine> LoadInputFile(InputCommandLine icl) 
            throws FileNotFoundException {

        String fileName = icl.getInputFileName();

        List<InputDataLine> inputDataList = new ArrayList<>();

        File inputFile = new File(fileName);
        Scanner myReader = new Scanner(inputFile);
        for (int idx = 0; idx < icl.getSkipLines(); idx++) {     // skip the header lines
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
    public static String reformatOutputFile(String outputFilePath, boolean ensureDirectoryExists) {
        // figure out the path of the output directory
        File outputFile = new File(outputFilePath);
        File parentDirectory = outputFile.getParentFile();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-");
        File newDirectory = new File(parentDirectory.getAbsolutePath() + File.separator + currentDateTime.format(formatter) + OUTPUT_FOLDER_NAME);
        // create the directory if it doesn't exist
        if (ensureDirectoryExists && !newDirectory.exists()) {
            newDirectory.mkdir();
        }//end if new directory needs to be created
        // create the resulting path of the output file
        String priorFileName = outputFile.getName();
        String newExtension = ".OUT";
        String newFileName = priorFileName.substring(0, priorFileName.lastIndexOf(".")) + newExtension;
        outputFile = new File(newDirectory.getAbsolutePath() + File.separator + newFileName);

        return outputFile.getAbsolutePath();
    }//end reformatOutputFile(outputFilePath)

    // write out the collated data
    public static void makeOutputFile(List<IntermediateDataLine> inputList,
            InputCommandLine icl) throws FileNotFoundException {

        // get the file modified time to adjust output date/times to 
        //   something close to real time
        FileTime fileTime = getFileCreationDate(new File(icl.getInputFileName()));

        // set up array to hold counts of peaks per channel
        int[] channelCounts = new int[icl.getNumberOfChannelsUsed()];

        // count the number of peaks per channel
        for (IntermediateDataLine idl : inputList) {    
            channelCounts[idl.channel]++;
        }

        // save to output file
        File outputFile = new File(icl.getOutputFileName());
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }//end if we need to make the resulting directories
        PrintWriter pw = new PrintWriter(outputFile);

        // print first section of header
        pw.printf("%s  %s\n%s\n", PROGRAM_NAME, VERSION, TITLE);

        // print second section of header
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        pw.printf("File name: %s\nData Processed: %s\n", icl.getInputFileName(), 
                dateFormat.format(cal.getTime()));
        
        // print third section of header
        if(inputList.size() > 0){
            // do some time calculations
            IntermediateDataLine lastDataLine = inputList.get(inputList.size() - 1);
            long tim1 = fileTime.toMillis() - (long)(lastDataLine.elapsedTime * 1000);
            long tim2 = tim1 + (long)(lastDataLine.elapsedTime * 1000);
            double minutesDuration = lastDataLine.elapsedTime / 60;
            // print out the time stuff
            pw.printf("Data Collected in %1.1f minutes\t", minutesDuration);

            // new line with date
            pw.printf("\nStart-End date/time: ");
            pw.printf(dateFormat.format(new Date(tim1)));
            pw.printf("  -  ");
            pw.printf(dateFormat.format(new Date(tim2)));
            pw.printf("\n");
        }//end if there are any inputs

        // print counts per channel
        for (int idx = 0; idx < icl.getNumberOfChannelsUsed(); idx++) {
            pw.printf("Chan %2d  Peaks: %5d\n", idx + 1, channelCounts[idx]);
        }
        
        // print headers for the columns we're about to print
        pw.printf("Chan#\tPkTime");
        if(icl.isDataTimeFlg()){
            pw.printf("\tDtTim");
        }//end if we should print the data time data
        if(icl.isPeakWidthFlg()){
            pw.printf("\tPkWidth");
        }//end if we should print the width of the peak
        pw.printf("\n");
        
        // print out data ordered by channel and in elapsed time order
        for (int jdx = 0; jdx < icl.getNumberOfChannelsUsed(); jdx++) {
            for (IntermediateDataLine outputData : inputList) {
                if (outputData.channel == jdx) {
                    pw.printf("%2d\t%9.3f", jdx + 1, outputData.elapsedTime);
                    if (icl.isDataTimeFlg()) {
                        pw.printf("\t");
                        long tim = new Double(outputData.elapsedTime * 1000).intValue();
                        pw.printf(dateFormat.format(new Date(tim + fileTime.toMillis())));
                    }
                    if (icl.isPeakWidthFlg()) {
                        pw.printf("\t%d", outputData.peakWidth);
                    }
                    pw.printf("\n");
                }
            }
        }
        
        // close output file
        pw.close();

    }//end makeOutputFile(inputList, icl)

    // process input 
    public static List<IntermediateDataLine> processInput(List<InputDataLine> list,
            InputCommandLine icl) {

        List<IntermediateDataLine> outputList = new ArrayList<>();

        int nocu = list.get(0).channels.length;

        // find the threshold for each channel
        icl.setNumberOfChannelsUsed(list.get(0).channels.length);
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
