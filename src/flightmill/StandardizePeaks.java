/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flightmill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wjrfo
 */
public class StandardizePeaks {

    /* 
#************************************************************************************************************
# This function performs a standardization of the voltage data for each channel. This is achieved by defining a
# confidence interval around the mean voltage value using a low (min_val) and high (max_val) threshold. These
# values can be defined by the user according to the characteristics of the voltage recording data. Voltages
# lower than min_val are set to 0 and define the base level. Voltages above max_val are set to 1 and identify
# the presence of a peak. Finally a list of peaks is created. 
#************************************************************************************************************

def peak_standardization(colum):
    format_colum = []
    new_list=[]
    peaks=[]
    
    for i in range(0, len(colum)):
        format_colum.append(round(colum[i], 2))      
        
    min_val=round((sum(format_colum)/len(format_colum)) - 0.01, 2) #threshold values can be 
    max_val=round((sum(format_colum)/len(format_colum)) + 0.02, 2) #modified accordingly
    for ii in range(0, len(format_colum)):
        x=(format_colum[ii]-min_val)/(max_val-min_val)
        if x > 1:
            new_list.append(1)
        else:
            new_list.append(0)

    for iii in range(0, len(new_list)-1):
        if new_list[iii] > new_list[iii-1] and new_list[iii] >= new_list[iii+1]:
            peaks.append(1)
        else:
            peaks.append(0)
    peaks.append(0)

    return peaks 

#************************************************************************************************************
# The flight data file can be called by either defining the complete filepath (for example c:\desktop\recordings
# \filename.txt) or defining a default path in the section "write the path here" that will be automatically
# recalled each time the function is run. In the latter case the user will only need to type the name of
# the .txt or .dat file to process when requested.
#************************************************************************************************************

filename = input("File path or file name -> ")
InputFile = open("./" + filename, "r")

#************************************************************************************************************
# The flight recording data is a .csv file in which the first column represents the time of the voltage event,
# while columns from 2 onwards represent the reading from each data-logger channels. The number of columns to
# process depends on the number of channels used to record the flight data. The example below is for a case in 
# which 5 channels were used. If the number of channels is different the script needs to be edited accordingly. 
#************************************************************************************************************

Lines = InputFile.readlines()
time_colum = []
first_colum = []
second_colum = []
#third_colum = []
#fourth_colum = []
#fifth_colum = []
#sixth_colum = []
#seventh_colum = []
#eighth_colum = []
for i in range(0, len(Lines)):
    raw = Lines[i]
#    a,b,c,d,e,f = raw.split(",") # if > 5 channels then a,b,c,d,e,f,g,h,j
    a,b,c = raw.split(",") # if > 5 channels then a,b,c,d,e,f,g,h,j
    time_colum.append(float(a))
    first_colum.append(float(b))
    second_colum.append(float(c))
#    third_colum.append(float(d))
#    fourth_colum.append(float(e))
#    fifth_colum.append(float(f))
    #sixth_colum.append(float(g)) 
    #seventh_colum.append(float(h))
    #eighth_colum.append(float(j))

InputFile.close()

first_colum = peak_standardization(first_colum)
second_colum = peak_standardization(second_colum)
#third_colum = peak_standardization(third_colum)
#fourth_colum = peak_standardization(fourth_colum)
#fifth_colum = peak_standardization(fifth_colum)
#sixth_colum = peak_standardization(sixth_colum)
#seventh_colum = peak_standardization(seventh_colum)
#eighth_colum = peak_standardization(eighth_colum)


#************************************************************************************************************
# Define the filepath of the output file. Add more channels to the write command line if needed. 
#************************************************************************************************************

#OutputFile = open("type file path here","w")
OutputFile = open(filename + ".out","w")
for i in range(0, len(Lines)):
    OutputFile.write('%.1f' % time_colum[i] + ", " + '%.2f' % first_colum[i] + ", " + '%.2f' % second_colum[i]+"\n")
#    OutputFile.write('%.1f' % time_colum[i] + ", " + '%.2f' % first_colum[i] + ", " + '%.2f' % second_colum[i]
#                     + ", " + '%.2f' % third_colum[i] + ", " +  '%.2f' % fourth_colum[i] + ", "
#                     + '%.2f' % fifth_colum[i] + ", " + '%.2f' % sixth_colum[i] + ", "
#                     + '%.2f' % seventh_colum[i] + ", " + '%.2f' % eighth_colum[i] +"\n") 
OutputFile.close()

     */
    private static final String fileName = "ns-1-26-23-meeting-test.csv";
    private static int skipLines = 3;

    private List<DataLine> outputList = null;

    public static void main(String[] args) {
        try {
            StandardizePeaks standardizePeaks = new StandardizePeaks(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StandardizePeaks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    List<DataLine> inputDataList = new ArrayList<>();

    public StandardizePeaks(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
//        System.out.println("file: " + inputFile.getAbsolutePath() + " " + inputFile.exists());
        Scanner myReader = new Scanner(inputFile);
        for (int idx = 0; idx < skipLines; idx++) {     // skip the header lines
            myReader.nextLine();
        }
        int cnt = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            try {
//                System.out.println(cnt + " " + inputDataList.size());
                DataLine inputData = new DataLine(data);
                inputDataList.add(inputData);
            } catch (NumberFormatException nfe) {
                System.out.println("format error");
            }
//            System.out.println(data);
//            if (cnt++ > 15) {
//                break;
//            }
        }
        System.out.println("inputDataList length " + inputDataList.size());
        myReader.close();

        outputList = processInput(inputDataList);

        File outputFileName = new File(fileName + ".out");
        PrintWriter pw = new PrintWriter(outputFileName);
        for (DataLine outputData : outputList) {
            pw.printf("%8.3f", outputData.time);
            for (int idx = 0; idx < outputData.channels.length; idx++) {
                pw.printf(", %2.1f", outputData.channels[idx]);
            }
            pw.printf("\n");
        }
        pw.close();
    }

    private static List<DataLine> processInput(List<DataLine> list) {

        List<DataLine> outputList = new ArrayList<>();

        double[] minVal = new double[list.get(0).channels.length];
        double[] maxVal = new double[list.get(0).channels.length];
        double[] threshold = new double[list.get(0).channels.length];

        for (DataLine inputData : list) {
            for (int idx = 0; idx < minVal.length; idx++) {
                if (inputData.channels[idx] < minVal[idx]) {
                    minVal[idx] = inputData.channels[idx];
                }
                if (inputData.channels[idx] > maxVal[idx]) {
                    maxVal[idx] = inputData.channels[idx];
                }
            }
        }

        for (int idx = 0; idx < minVal.length; idx++) {

            threshold[idx] = (minVal[idx] + maxVal[idx]) / 2;
            System.out.printf("%2d  %5.3f  %5.3f\n", idx, minVal[idx], maxVal[idx]);
        }

        Iterator<DataLine> it = list.iterator();
        DataLine lastInputData = it.next();
        for (; it.hasNext();) {
            DataLine inputData = it.next();
            for (int idx = 0; idx < lastInputData.channels.length; idx++) {
                if (lastInputData.channels[idx] < threshold[idx] && inputData.channels[idx] > threshold[idx]) {
                    DataLine newInputData = new DataLine(inputData.time, idx, inputData.channels.length);
                    outputList.add(newInputData);
                }
            }
        }

        return outputList;
    }

    
//<editor-fold defaultstate="collapsed" desc="getters/setters">

    /**
     * @return the outputList
     */
    public List<DataLine> getOutputList() {
        return outputList;
    }

    /**
     * @param outputList the outputList to set
     */
    public void setOutputList(List<DataLine> outputList) {
        this.outputList = outputList;
    }
//</editor-fold>
}
