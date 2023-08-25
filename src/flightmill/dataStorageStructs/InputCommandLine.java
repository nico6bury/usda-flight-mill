package flightmill.dataStorageStructs;

// class to hold parsed input command options
public class InputCommandLine {
    public String inputFileName = "default_input.csv";
    public String outputFileName = "default_input.out";
    public int skipLines = 4;    // header lines at top of data logger file
    public int numberOfChannelsUsed = 0;    // self explanitory
    public boolean zipFileFlg = false;              // zip input file
    public boolean dateTimeFlg = false;  // add date time to each output line
    public boolean peakWidthFlg = true; // add date time to each output line
    public boolean doubleColumnFlg = true; // add second peakwidth and elapsedTime
    public boolean widthRatioFlg = false;
    public boolean revolutionChk = true;
    public double threshold = 1.5;
    public double thresh_seconds_fast = 0.300;
    public double thresh_seconds_medium = 0.500;
    public double thresh_seconds_slow = 1.000;
    public double thresh_peakWidth_fast = 0;
    public double thresh_peakWidth_medium = 50;
    public double thresh_peakWidth_slow = 100;
}//end class InputCommandLine