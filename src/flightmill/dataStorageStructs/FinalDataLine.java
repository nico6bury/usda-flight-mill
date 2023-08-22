package flightmill.dataStorageStructs;

/*
 * This class is meant to hold data which has been processed from IntermediateDataLines in order to determine directionality.
 */
public class FinalDataLine {
    /*
     * The "channel", or section of the flight mill that this data point was taken from.
     */
    public int channel;
    /*
     * The amount of time elapsed since data collection started, from the first peak.
     * The unit is in seconds. At time of writing, they seem to be in 4 milisecond intervals.
     */
    public double elapsedTime1;
    /*
     * The amount of time elapsed since data collection started, from the second peak.
     * The unit is in seconds. At time of writing, they seem to be in 4 milisecond intervals.
     */
    public double elapsedTime2;
    /*
     * Voltage measurement or something.
     * This goes up when the flag is detected.
     */
    public double value;
    /*
     * The width of the first peak. Should probably reformulate as a number of miliseconds or something.
     */
    public int peakWidth1;
    /*
     * The width of the second peak. Should probably reformulate peak width as a number of miliseconds or something.
     */
    public int peakWidth2;
    /*
     * This is a sentinel value indicating probably direction.
     * This only works with notched flags, so if this doesn't seem to be a reading, or we can't figure out directionallity, then this will be 0.
     * If we encounter a small peak and then a wide peak, this will be 1 (probably forward)
     * If we encounter a large peak and then a small peak, this will be -1 (probably backward)
     */
    public int direction;

    public FinalDataLine(int channel, double elapsedTime1, double elapsedTime2, double value, int peakWidth1, int peakWidth2, int direction) {
        this.channel = channel;
        this.elapsedTime1 = elapsedTime1;
        this.elapsedTime2 = elapsedTime2;
        this.value = value;
        this.peakWidth1 = peakWidth1;
        this.peakWidth2 = peakWidth2;
        this.direction = direction;
    }//end parameter constructor

    public FinalDataLine(IntermediateDataLine idl) {
        this.channel = idl.channel;
        this.elapsedTime1 = idl.elapsedTime;
        this.elapsedTime2 = idl.elapsedTime;
        this.value = idl.value;
        this.peakWidth1 = idl.peakWidth;
        this.peakWidth2 = 0;
        this.direction = 0;
    }//end no direction constructor

    public FinalDataLine(IntermediateDataLine first, IntermediateDataLine second) {
        this.channel = first.channel;
        this.elapsedTime1 = first.elapsedTime;
        this.elapsedTime2 = second.elapsedTime;
        this.value = first.value;
        this.peakWidth1 = first.peakWidth;
        this.peakWidth2 = second.peakWidth;
        if (first.peakWidth < second.peakWidth) {this.direction = 1;}
        else if (first.peakWidth > second.peakWidth) {this.direction = -1;}
        else {this.direction = -2;} // this should never happen
    }//end directionallity constructor
}
