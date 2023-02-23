/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flightmill;

/**
 *
 * @author wjrfo
 */
public class DataLine {

    double time = 0.0;
    double channels[] = null;

    public DataLine(String inputLine) {
        String items[] = inputLine.split(",");
//            System.out.println("inputLine length " + items.length);
        time = Double.parseDouble(items[0]);
        channels = new double[items.length - 1];
        for (int idx = 1; idx < items.length; idx++) {
            channels[idx - 1] = Double.parseDouble(items[idx]);
        }
    }

    public DataLine(double time, int channel, int numChannel) {
        this.time = time;
        channels = new double[numChannel];
        channels[channel] = 1.0;
    }
}
