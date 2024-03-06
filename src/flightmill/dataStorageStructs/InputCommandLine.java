package flightmill.dataStorageStructs;

// class to hold parsed input command options
public class InputCommandLine {
    public String inputFileName = "default_input.csv";
    public String outputFileName = "default_input.out";
    public int skipLines = 4;    // header lines at top of data logger file
    public int numberOfChannelsUsed = 0;    // number of channels used, should probably end up being 8
    public boolean zip_input_file = false;              // zip input file
    public boolean add_date_time_column = true;  // add date time to each output line
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

    /**
     * Sets all to default
     */
    public InputCommandLine() {}

    /**
     * Copy constructor
     */
    public InputCommandLine(InputCommandLine other) {
        this.inputFileName = other.inputFileName;
        this.outputFileName = other.outputFileName;
        this.skipLines = other.skipLines;
        this.numberOfChannelsUsed = other.numberOfChannelsUsed;
        this.zip_input_file = other.zip_input_file;
        this.add_date_time_column = other.add_date_time_column;
        this.add_peak_width_column = other.add_peak_width_column;
        this.add_second_peak_columns = other.add_second_peak_columns;
        this.add_width_ratio_column = other.add_width_ratio_column;
        this.add_revolution_column = other.add_revolution_column;
        this.threshold = other.threshold;
        this.thresh_seconds_fast = other.thresh_seconds_fast;
        this.thresh_seconds_medium = other.thresh_seconds_medium;
        this.thresh_seconds_slow = other.thresh_seconds_slow;
        this.thresh_peakWidth_fast = other.thresh_peakWidth_fast;
        this.thresh_peakWidth_medium = other.thresh_peakWidth_medium;
        this.thresh_peakWidth_slow = other.thresh_peakWidth_slow;
    }//end copy constructor
}//end class InputCommandLine