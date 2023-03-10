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
    private static String VERSION = "v.2.22"; 

    // entry point
    public static void main(String[] args) {
        
        try {
//            testLocalDateTime();
            InputCommandLine inputCommandLine = processCommandLine(args);
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
    }

    // parses options on the command line
    private static InputCommandLine processCommandLine(String[] args) {

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
    }

    // load the input file into an input list
    private static List<InputDataLine> LoadInputFile(InputCommandLine icl) 
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
    }

    // write out the collated data
    private static void makeOutputFile(List<IntermediateDataLine> inputList,
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
            long tim1 = fileTime.toMillis();
            IntermediateDataLine lastDataLine = inputList.get(inputList.size() - 1);
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

    }

    // process input 
    private static List<IntermediateDataLine> processInput(List<InputDataLine> list,
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
    }

    // get input file modizfication time to set beginning time for output date/time
    private static FileTime getFileCreationDate(File file) {

        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime modTime = attrs.creationTime();

            return modTime;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    }

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
    }

    // class for input data directly mapped to input file
    private static class InputDataLine {

        double time = 0.0;
        double channels[] = null;

        public InputDataLine(String inputLine) {
            String items[] = inputLine.split(",");
            time = Double.parseDouble(items[0]);
            channels = new double[items.length - 1];
            for (int idx = 1; idx < items.length; idx++) {
                channels[idx - 1] = Double.parseDouble(items[idx]);
            }
        }

        public InputDataLine(double time, int channel, int numChannel) {
            this.time = time;
            channels = new double[numChannel];
            channels[channel] = 1.0;
        }
    }

    // class to hold data lines that hold only peaks
    private static class IntermediateDataLine {

        int channel;
        double elapsedTime;
        double value;
        int peakWidth = 1;

        public IntermediateDataLine(int channel, double elapsedTime, double value) {
            this.channel = channel;
            this.elapsedTime = elapsedTime;
            this.value = value;
        }
    }

    // class to hold parsed input command options
    private static class InputCommandLine {

        private String inputFileName = "ns-1-26-23-meeting-test.csv";
        private String outputFileName = "ns-1-26-23-meeting-test.csv.out";
        private int skipLines = 3;    // header lines at top of data logger file
        private double timeInterval = 0.01;  // time between data logger readings
        private int numberOfChannelsUsed = 0;    // self explanitory
        private boolean zipFileFlg = false;              // zip input file
        private boolean dataTimeFlg = true;  // add date time to each output line
        private boolean peakWidthFlg = false; // add date time to each output line

        //<editor-fold defaultstate="collapsed" desc="getters/setters">
        /**
         * @return the inputFileName
         */
        public String getInputFileName() {
            return inputFileName;
        }

        /**
         * @param aInputFileName the inputFileName to set
         */
        public void setInputFileName(String aInputFileName) {
            inputFileName = aInputFileName;
        }

        /**
         * @return the outputFileName
         */
        public String getOutputFileName() {
            return outputFileName;
        }

        /**
         * @param aOutputFileName the outputFileName to set
         */
        public void setOutputFileName(String aOutputFileName) {
            outputFileName = aOutputFileName;
        }

        /**
         * @return the skipLines
         */
        public int getSkipLines() {
            return skipLines;
        }

        /**
         * @param aSkipLines the skipLines to set
         */
        public void setSkipLines(int aSkipLines) {
            skipLines = aSkipLines;
        }

        /**
         * @return the timeInterval
         */
        public double getTimeInterval() {
            return timeInterval;
        }

        /**
         * @param aTimeInterval the timeInterval to set
         */
        public void setTimeInterval(double aTimeInterval) {
            timeInterval = aTimeInterval;
        }

        /**
         * @return the numberOfChannelsUsed
         */
        public int getNumberOfChannelsUsed() {
            return numberOfChannelsUsed;
        }

        /**
         * @param aNumberOfChannelsUsed the numberOfChannelsUsed to set
         */
        public void setNumberOfChannelsUsed(int aNumberOfChannelsUsed) {
            numberOfChannelsUsed = aNumberOfChannelsUsed;
        }

        /**
         * @return the zipFileFlg
         */
        public boolean isZipFileFlg() {
            return zipFileFlg;
        }

        /**
         * @param aZipFileFlg the zipFileFlg to set
         */
        public void setZipFileFlg(boolean aZipFileFlg) {
            zipFileFlg = aZipFileFlg;
        }

        /**
         * @return the dataTimeFlg
         */
        public boolean isDataTimeFlg() {
            return dataTimeFlg;
        }

        /**
         * @param dataTimeFlg the dataTimeFlg to set
         */
        public void setDataTimeFlg(boolean dataTimeFlg) {
            this.dataTimeFlg = dataTimeFlg;
        }

        /**
         * @return the peakWidthFlg
         */
        public boolean isPeakWidthFlg() {
            return peakWidthFlg;
        }

        /**
         * @param peakWidthFlg the peakWidthFlg to set
         */
        public void setPeakWidthFlg(boolean peakWidthFlg) {
            this.peakWidthFlg = peakWidthFlg;
        }

//</editor-fold>
    }

}
