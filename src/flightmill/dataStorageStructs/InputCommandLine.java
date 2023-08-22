package flightmill.dataStorageStructs;

// class to hold parsed input command options
public class InputCommandLine {
    public String inputFileName = "ns-1-26-23-meeting-test.csv";
    public String outputFileName = "ns-1-26-23-meeting-test.csv.out";
    public int skipLines = 3;    // header lines at top of data logger file
    public double timeInterval = 0.01;  // time between data logger readings
    public int numberOfChannelsUsed = 0;    // self explanitory
    public boolean zipFileFlg = false;              // zip input file
    public boolean dataTimeFlg = true;  // add date time to each output line
    public boolean peakWidthFlg = false; // add date time to each output line
    public boolean doubleColumnFlg = true; // add second peakwidth and elapsedTime
    public double threshold = 1.5;
    public double thresh_seconds_normal = 0.300;
    public double thresh_seconds_little_slow = 0.500;
    public double thresh_seconds_slow = 1.000;
    public double thresh_peakWidth_normal = 0;
    public double thresh_peakWidth_little_slow = 50;
    public double thresh_peakWidth_slow = 100;
}//end class InputCommandLine