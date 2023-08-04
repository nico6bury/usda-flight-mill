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
        * The amount of time elapsed since data collection started.
        The unit is in seconds. At time of writing, they seem to be in 4 milisecond intervals
        */
    public double elapsedTime;
    /*
        * Voltage measurement or something.
        * This goes up when the flag is detected.
        */
    public double value;
    /*
    * The width of this peak. Should probably reformulate as a number of miliseconds or something.
    */
    public int peakWidth;
    /*
    * This is a sentinel value indicating probably direction.
    * This only works with notched flags, so if this doesn't seem to be a reading, or we can't figure out directionallity, then this will be 0.
    * If we encounter a small peak and then a wide peak, this will be 1 (probably forward)
    * If we encounter a large peak and then a small peak, this will be -1 (probably backward)
    */
    public int direction;

    public FinalDataLine(int channel, double elapsedTime, double value, int peakWidth, int direction) {
        this.channel = channel;
        this.elapsedTime = elapsedTime;
        this.value = value;
        this.peakWidth = peakWidth;
        this.direction = direction;
    }//end parameter constructor

    public FinalDataLine(IntermediateDataLine idl) {
        this.channel = idl.channel;
        this.elapsedTime = idl.elapsedTime;
        this.value = idl.value;
        this.peakWidth = idl.peakWidth;
        this.direction = 0;
    }//end no direction constructor

    public FinalDataLine(IntermediateDataLine first, IntermediateDataLine second) {
        this.channel = first.channel;
        this.elapsedTime = first.elapsedTime;
        this.value = first.value;
        this.peakWidth = first.peakWidth + second.peakWidth;
        if (first.peakWidth < second.peakWidth) {this.direction = 1;}
        else if (first.peakWidth > second.peakWidth) {this.direction = -1;}
        else {this.direction = 0;}
    }//end directionallity constructor
}
