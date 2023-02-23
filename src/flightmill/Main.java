/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flightmill;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wjrfo
 */
public class Main {
        private static final String fileName = "ns-1-26-23-meeting-test.csv";

    public static void main(String[] args) {
        try {
            StandardizePeaks standardizePeaks = new StandardizePeaks(fileName);
            List<DataLine> dataList = standardizePeaks.getOutputList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StandardizePeaks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }


}
