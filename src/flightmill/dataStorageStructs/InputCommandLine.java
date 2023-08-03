package flightmill.dataStorageStructs;

// class to hold parsed input command options
public class InputCommandLine {
    private String inputFileName = "ns-1-26-23-meeting-test.csv";
    private String outputFileName = "ns-1-26-23-meeting-test.csv.out";
    private int skipLines = 3;    // header lines at top of data logger file
    private double timeInterval = 0.01;  // time between data logger readings
    private int numberOfChannelsUsed = 0;    // self explanitory
    private boolean zipFileFlg = false;              // zip input file
    private boolean dataTimeFlg = true;  // add date time to each output line
    private boolean peakWidthFlg = false; // add date time to each output line
    private double threshold = 1.5;

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

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}//end class InputCommandLine