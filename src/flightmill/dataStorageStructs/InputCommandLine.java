package flightmill.dataStorageStructs;

// class to hold parsed input command options
public class InputCommandLine {
    public String inputFileName = "default_input.csv";
    public String outputFileName = "default_input.out";
    public int skipLines = 4;    // header lines at top of data logger file
    public int numberOfChannelsUsed = 0;    // number of channels used, should probably end up being 8
    public boolean zip_input_file = false;              // zip input file
    public boolean add_date_time_column = false;  // add date time to each output line
    public boolean add_peak_width_column = true; // add peak width to each output line
    public boolean add_second_peak_columns = true; // add second peakwidth and elapsedTime to each output line
    public boolean add_width_ratio_column = false; // add ratio between first and second peakwidth to each output line
    public boolean add_revolution_column = true; // add difference in time between two peaks to each output line
    public double threshold = 1.5; // threshold to identify a peak
    public double thresh_seconds_fast = 0.300;
    public double thresh_seconds_medium = 0.500;
    public double thresh_seconds_slow = 1.000;
    public double thresh_peakWidth_fast = 0;
    public double thresh_peakWidth_medium = 50;
    public double thresh_peakWidth_slow = 100;
}//end class InputCommandLine