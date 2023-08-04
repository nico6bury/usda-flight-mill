package flightmill.dataStorageStructs;

// class to hold data lines that hold only peaks
public class IntermediateDataLine {
    public int channel;
    public double elapsedTime;
    public double value;
    public int peakWidth = 1;

    public IntermediateDataLine(int channel, double elapsedTime, double value) {
        this.channel = channel;
        this.elapsedTime = elapsedTime;
        this.value = value;
    }//end 3-arg constructor
}
