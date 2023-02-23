/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package flightmill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author wjrfo
 */
public class FlightMill {


    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        String fileName = "test_data.csv";
        
        if (args.length == 1) {
            fileName = args[0];
        }
        
        Config config = Config.loadConfig();
        
        int numberOfChannels = config.getNumberOfChannels();
        
        long sampleLength = config.getSampleLength();
        int hertz = config.getHertz();
        
        long runLength = sampleLength * hertz;
        double inverseHertz = 1.0 / hertz;
        
        System.out.println("Hello World");

        System.out.println(fileName);
        PrintWriter pw = new PrintWriter(new File(fileName));
        Random random = new Random(0l);
        double channels[] = new double[numberOfChannels];
        for (int idx = 0; idx < numberOfChannels; idx++) {
            channels[idx] = random.nextDouble();
        }
        
        double tim = 0.0;
        for (long sampleX = 0; sampleX < runLength; sampleX++) {
            double ran = random.nextDouble();
            pw.printf("%6.2f", tim);
            tim += inverseHertz;
            for (int idx = 0; idx < channels.length; idx++) {
                pw.printf(",  %f", (channels[idx] < ran && (channels[idx] + 0.05) > ran) ? 5.0 : 1.0);
            }
            pw.println();
        }
        pw.close();
        // TODO code application logic here
    }

}
