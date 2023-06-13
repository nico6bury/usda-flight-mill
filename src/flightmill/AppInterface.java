/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package flightmill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import flightmill.ProcessDataLoggerFile.InputCommandLine;
import flightmill.ProcessDataLoggerFile.InputDataLine;
import flightmill.ProcessDataLoggerFile.IntermediateDataLine;

import com.formdev.flatlaf.*;

/**
 *
 * @author Nicholas.Sixbury
 */
public class AppInterface extends javax.swing.JFrame {

    /**
     * Creates new form test
     */
    public AppInterface() {
        boolean darkMode = false;
        if(darkMode) {
            FlatDarkLaf.setup();
        }
        else {
            FlatLightLaf.setup();
        }

        initComponents();
    }//end constructor

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        uxGetInputBtn = new javax.swing.JButton();
        uxGetOutputBtn = new javax.swing.JButton();
        uxGetInputTxt = new javax.swing.JTextField();
        uxGetOutputTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        uxGetThresholdTxt = new javax.swing.JTextField();
        uxLineSkipChk = new javax.swing.JCheckBox();
        uxZipInputChk = new javax.swing.JCheckBox();
        uxNoDateTimeChk = new javax.swing.JCheckBox();
        uxAddPeakWidthChk = new javax.swing.JCheckBox();
        uxLineSkipTxt = new javax.swing.JTextField();
        uxProcessBtn = new javax.swing.JButton();
        uxStatusText = new javax.swing.JLabel();
        uxShowFileBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.setTitle("Flight Mill Data File Compression Software");
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("\t\tFlight Mill Data File Compression Software v.2.23" +
        "\n   \t> compresses 8 channel datafile collected from WinDaq hardware/software" +
        "\n\n\t\tSixbury/Rust/Brabec  June 2023" +
        "\n\t\tUSDA-ARS   Manhattan, Kansas");
        jScrollPane2.setViewportView(jTextArea1);

        uxGetInputBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetInputBtn.setText("Select Input File");
        uxGetInputBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxGetInputBtnActionPerformed(evt);
            }
        });

        uxGetOutputBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetOutputBtn.setText("Output File");
        uxGetOutputBtn.setEnabled(false);
        uxGetOutputBtn.setOpaque(true);
        uxGetOutputBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxGetOutputBtnActionPerformed(evt);
            }
        });

        uxGetInputTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        uxGetOutputTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(" LED signal threshold:");

        uxGetThresholdTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetThresholdTxt.setText("1.5");

        uxLineSkipChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxLineSkipChk.setSelected(true);
        uxLineSkipChk.setText("Skip, a number of header lines in input file");

        uxZipInputChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxZipInputChk.setText("Zip Input File and Delete Uncompressed File");

        uxNoDateTimeChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxNoDateTimeChk.setText("Include Date/Time in each Line of output");

        uxAddPeakWidthChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxAddPeakWidthChk.setSelected(true);
        uxAddPeakWidthChk.setText("Add Width of each Peak as a column in output");

        uxLineSkipTxt.setText("4");

        uxProcessBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        uxProcessBtn.setText("Process Sample");
        uxProcessBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxProcessBtnActionPerformed(evt);
            }
        });

        uxShowFileBtn.setText("Show in Folder");
        uxShowFileBtn.setEnabled(false);
        uxShowFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxShowFileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(uxGetOutputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(uxGetInputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(uxGetInputTxt)
                                    .addComponent(uxGetOutputTxt)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(uxGetThresholdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(uxStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(uxNoDateTimeChk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(uxAddPeakWidthChk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(uxZipInputChk)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(uxLineSkipChk, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uxLineSkipTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(uxProcessBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(uxShowFileBtn)
                                .addGap(102, 102, 102))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uxGetInputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(uxGetInputTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uxGetOutputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(uxGetOutputTxt))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(uxAddPeakWidthChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(uxZipInputChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uxNoDateTimeChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uxLineSkipChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uxLineSkipTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(uxProcessBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uxShowFileBtn)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uxGetThresholdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uxStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected File lastInputFile;
    public File getLastInputFile() {
        return lastInputFile;
    }//end getLastInputFile()
    protected File lastOutputFile;
    public File getLastOutputFile() {
        return lastOutputFile;
    }//end getLastOutputFile()

    /**
        File chooser for both opening and saving files
     */ 
    private JFileChooser fileChooser = new JFileChooser();
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            lastInputFile = fileChooser.getSelectedFile();
            uxGetInputTxt.setText(lastInputFile.getAbsolutePath());
            uxGetOutputTxt.setText(lastInputFile.getAbsolutePath() + ".out");
        }//end actionPerformed
    };

    private void uxGetInputBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxGetInputBtnActionPerformed
        fileChooser.addActionListener(actionListener);
        fileChooser.showOpenDialog(this);
        uxStatusText.setText("");
    }//GEN-LAST:event_uxGetInputBtnActionPerformed

    private void uxGetOutputBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxGetOutputBtnActionPerformed
        fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                lastOutputFile = fileChooser.getSelectedFile();
                uxGetOutputTxt.setText(lastOutputFile.getAbsolutePath());
            }
        } );
        fileChooser.showSaveDialog(this);
    }//GEN-LAST:event_uxGetOutputBtnActionPerformed

    public boolean isInputDataReady = false;
    public InputCommandLine inputCommandLine = null;

    private void uxProcessBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxProcessBtnActionPerformed
        // disable "show in folder" button
        uxShowFileBtn.setEnabled(false);

        // check that there's actually a file to open
        String inputText = uxGetInputTxt.getText();
        if (inputText.isEmpty()) {
            // display message about empty file path
            JOptionPane.showMessageDialog(this, "You haven't selected a file to process." +
            "\nPlease click the \"Select Input File\" button to " +
            "\nselect a file, or copy and paste the path yourself...");
            return;
        }//end if input text isn't filled in
        
        // set up InputCommandLine for all the stuff we need to send
        inputCommandLine = new InputCommandLine();

        // update status label
        uxStatusText.setText("Gathering Parameters");
        uxStatusText.paintImmediately(uxStatusText.getVisibleRect());

        inputCommandLine.setDataTimeFlg(uxNoDateTimeChk.isSelected());
        inputCommandLine.setInputFileName(inputText);
        inputCommandLine.setOutputFileName(ProcessDataLoggerFile.reformatOutputFile(uxGetOutputTxt.getText()));
        inputCommandLine.setPeakWidthFlg(uxAddPeakWidthChk.isSelected());
        if (uxLineSkipChk.isSelected()) {
            inputCommandLine.setSkipLines(Integer.parseInt(uxLineSkipTxt.getText()));
        }
        else inputCommandLine.setSkipLines(0);
        inputCommandLine.setZipFileFlg(uxZipInputChk.isSelected());
        inputCommandLine.setThreshold(Double.parseDouble(uxGetThresholdTxt.getText()));

        // just do all the processing, whatever
        try{
            
            if (inputCommandLine.isZipFileFlg()) {
                // update status text
                uxStatusText.setText("Zipping Input File");
                uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
                // actually zipping
                ProcessDataLoggerFile.zipFile(inputCommandLine.getInputFileName());
            }
            if (inputCommandLine.getInputFileName().endsWith(".zip")) {
                // update status text
                uxStatusText.setText("Unzipping Input File");
                uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
                // actually unzipping
                String unzippedFileName = ProcessDataLoggerFile.unzipFile(inputCommandLine.getInputFileName());
                inputCommandLine.setInputFileName(unzippedFileName);
            }
    
            // load the imput file
            uxStatusText.setText("Loading input file into memory.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<InputDataLine> inputList = ProcessDataLoggerFile.LoadInputFile(inputCommandLine);
            // make list of individual peaks
            uxStatusText.setText("Finding list of individual peaks.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<IntermediateDataLine> processedInputList = ProcessDataLoggerFile.processInput(inputList,
                    inputCommandLine);
            // write output file
            uxStatusText.setText("Writing the output file.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            ProcessDataLoggerFile.makeOutputFile(processedInputList, inputCommandLine);
            // tell the user what happened
            uxStatusText.setText("Files have finished processing.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            JOptionPane.showMessageDialog(this, "Files have finished processing.");
            // enable "show in folder" button
            uxShowFileBtn.setEnabled(true);
        }//end trying to do whatever
        catch (FileNotFoundException ex) {
            uxStatusText.setText("File not found. Aborting...");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }//end catching FileNotFoundException
        catch (IOException ex) {
            uxStatusText.setText("An unspecified IO error has occured. Consider closing applications that might still be writing or reading from the file. Aborting...");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            Logger.getLogger(ProcessDataLoggerFile.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }//end catching IOExceptions
    }//GEN-LAST:event_uxProcessBtnActionPerformed

    private void uxShowFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxShowFileBtnActionPerformed
        // open the file in file explorer
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + inputCommandLine.getOutputFileName());
        }//end trying to open file explorer
        catch(Exception e) {System.out.println("Couldn't open file explorer");}
    }//GEN-LAST:event_uxShowFileBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox uxAddPeakWidthChk;
    private javax.swing.JButton uxGetInputBtn;
    private javax.swing.JTextField uxGetInputTxt;
    private javax.swing.JButton uxGetOutputBtn;
    private javax.swing.JTextField uxGetOutputTxt;
    private javax.swing.JTextField uxGetThresholdTxt;
    private javax.swing.JCheckBox uxLineSkipChk;
    private javax.swing.JTextField uxLineSkipTxt;
    private javax.swing.JCheckBox uxNoDateTimeChk;
    private javax.swing.JButton uxProcessBtn;
    private javax.swing.JButton uxShowFileBtn;
    private javax.swing.JLabel uxStatusText;
    private javax.swing.JCheckBox uxZipInputChk;
    // End of variables declaration//GEN-END:variables
}
