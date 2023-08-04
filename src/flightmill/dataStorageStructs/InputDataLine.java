package flightmill.dataStorageStructs;

// class for input data directly mapped to input file
public class InputDataLine {

    public double time = 0.0;
    public double channels[] = null;

    public InputDataLine(String inputLine) {
        String items[] = inputLine.split(",");
        time = Double.parseDouble(items[0]);
        channels = new double[items.length - 1];
        for (int idx = 1; idx < items.length; idx++) {
            channels[idx - 1] = Double.parseDouble(items[idx]);
        }
    }//end 1-arg constructor

    public InputDataLine(double time, int channel, int numChannel) {
        this.time = time;
        channels = new double[numChannel];
        channels[channel] = 1.0;
    }//end 3-arg constructor
}//end class InputDataLine