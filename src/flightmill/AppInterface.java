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
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.*;

import flightmill.dataStorageStructs.FinalDataLine;
import flightmill.dataStorageStructs.InputCommandLine;
import flightmill.dataStorageStructs.InputDataLine;
import flightmill.dataStorageStructs.IntermediateDataLine;

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

        // update version and date and stuff
        jTextArea1.setText("\t\tFlight Mill Data File Compression Software " + ProcessDataLoggerFile.VERSION + "\n   \t> compresses 8 channel datafile collected from WinDaq hardware/software\n\n\t\t" + ProcessDataLoggerFile.PEOPLE + "  " + ProcessDataLoggerFile.DATE() + "\n\t\t" + ProcessDataLoggerFile.LOCATION);
        this.setTitle("USDA-ARS FMDFCS " + ProcessDataLoggerFile.VERSION);
        // load config stuff from file
        inputCommandLine = ProcessDataLoggerFile.loadInputCommandLine();
        updateConfigDisplay();
        // set up config dialog
        configDialog = new ConfigDialog(this, true);
        configDialog.parent_icl = inputCommandLine;
        // initialize uxStatusText
        uxStatusText.setText("Please select a file to process.");
        // set up date time dialog

    }//end constructor

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        uxGetInputBtn = new javax.swing.JButton();
        uxGetOutputBtn = new javax.swing.JButton();
        uxGetInputTxt = new javax.swing.JTextField();
        uxGetOutputTxt = new javax.swing.JTextField();
        uxProcessBtn = new javax.swing.JButton();
        uxShowFileBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        uxConfigDisplayText = new javax.swing.JTextArea();
        uxShowConfigBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        uxStatusText = new javax.swing.JTextArea();
        uxGetCollectionTimeBtn = new javax.swing.JButton();
        uxGetCollectionTimeTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("\t\tFlight Mill Data File Compression Software v1.0.1\n   \t> compresses 8 channel datafile collected from WinDaq hardware/software\n\tNote: The entirety of this header is auto-generated within the constructor\n\t\tSixbury/Rust/Brabec  autogenerated\n\t\tUSDA-ARS   Manhattan, Kansas");
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

        uxGetInputTxt.setEditable(false);
        uxGetInputTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetInputTxt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        uxGetOutputTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetOutputTxt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        uxProcessBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        uxProcessBtn.setText("Process Sample");
        uxProcessBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxProcessBtnActionPerformed(evt);
            }
        });

        uxShowFileBtn.setText("Show Output Folder");
        uxShowFileBtn.setEnabled(false);
        uxShowFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxShowFileBtnActionPerformed(evt);
            }
        });

        uxConfigDisplayText.setEditable(false);
        uxConfigDisplayText.setColumns(20);
        uxConfigDisplayText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        uxConfigDisplayText.setRows(4);
        uxConfigDisplayText.setAutoscrolls(false);
        jScrollPane1.setViewportView(uxConfigDisplayText);

        uxShowConfigBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        uxShowConfigBtn.setText("Edit Config");
        uxShowConfigBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxShowConfigBtnActionPerformed(evt);
            }
        });

        uxStatusText.setEditable(false);
        uxStatusText.setColumns(20);
        uxStatusText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        uxStatusText.setRows(1);
        uxStatusText.setAutoscrolls(false);
        jScrollPane3.setViewportView(uxStatusText);

        uxGetCollectionTimeBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetCollectionTimeBtn.setText("Select Collection Time");
        uxGetCollectionTimeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxGetCollectionTimeBtnActionPerformed(evt);
            }
        });

        uxGetCollectionTimeTxt.setEditable(false);
        uxGetCollectionTimeTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(uxProcessBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(uxShowFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(uxShowConfigBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(uxGetInputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(uxGetOutputBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(uxGetInputTxt)
                                    .addComponent(uxGetOutputTxt)))
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(uxGetCollectionTimeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uxGetCollectionTimeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uxGetCollectionTimeBtn)
                    .addComponent(uxGetCollectionTimeTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uxShowConfigBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uxProcessBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uxShowFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            if (e.getActionCommand() == "ApproveSelection") {
                lastInputFile = fileChooser.getSelectedFile();
                uxGetInputTxt.setText(lastInputFile.getAbsolutePath());
                uxGetOutputTxt.setText(ProcessDataLoggerFile.reformatOutputFile(lastInputFile.getAbsolutePath(), false));
                uxStatusText.setText("Click \"Process Sample\" button to get results.");
            }//end if selection was approved
            else if (e.getActionCommand() == "CancelSelection") {
                uxStatusText.setText("Please select a file and click confirm on the file dialog.");
            }//end else if user cancelled
        }//end actionPerformed
    };

    private void uxGetInputBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxGetInputBtnActionPerformed
        // clear from previous operations
        uxStatusText.setText("");
        uxShowFileBtn.setEnabled(false);
        // actually show the dialog
        fileChooser.addActionListener(actionListener);
        fileChooser.showOpenDialog(this);
        // reset collection time
        FileTime fileTime = ProcessDataLoggerFile.getFileCreationDate(new File(uxGetInputTxt.getText()));
        dtDialog.dateTime = LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
        updateTimeDisplay();
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
    public InputCommandLine inputCommandLine = new InputCommandLine();

    private void uxProcessBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxProcessBtnActionPerformed
        // check that there's actually a file to open
        String inputText = uxGetInputTxt.getText();
        if (inputText.isEmpty()) {
            // display message about empty file path
            JOptionPane.showMessageDialog(this, "You haven't selected a file to process." +
            "\nPlease click the \"Select Input File\" button to " +
            "\nselect a file, or copy and paste the path yourself...");
            return;
        }//end if input text isn't filled in

        // update status label
        uxStatusText.setText("Gathering Parameters");
        uxStatusText.paintImmediately(uxStatusText.getVisibleRect());

        inputCommandLine.inputFileName = inputText;
        inputCommandLine.outputFileName = uxGetOutputTxt.getText();

        // just do all the processing, whatever
        try{
            
            if (inputCommandLine.zip_input_file) {
                // update status text
                uxStatusText.setText("Zipping Input File");
                uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
                // actually zipping
                ProcessDataLoggerFile.zipFile(inputCommandLine.inputFileName);
            }
            if (inputCommandLine.inputFileName.endsWith(".zip")) {
                // update status text
                uxStatusText.setText("Unzipping Input File");
                uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
                // actually unzipping
                String unzippedFileName = ProcessDataLoggerFile.unzipFile(inputCommandLine.inputFileName);
                inputCommandLine.inputFileName = unzippedFileName;
            }
    
            // load the imput file
            uxStatusText.setText("Loading input file into memory.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<InputDataLine> inputList = ProcessDataLoggerFile.LoadInputFile(inputCommandLine);
            double duration = ProcessDataLoggerFile.getDuration(inputList);
            // make list of individual peaks
            uxStatusText.setText("Finding list of individual peaks.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<IntermediateDataLine> processedInputList = ProcessDataLoggerFile.processInput(inputList,
                    inputCommandLine);
            // sort peaks by channel
            uxStatusText.setText("Sorting peaks by channel to ease processing.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<List<IntermediateDataLine>> channelSortedInputList = ProcessDataLoggerFile.separateIntermedDataByChannel(processedInputList);
            // figure out directionallity from those peaks
            uxStatusText.setText("Sifting through peaks to figure out directionallity.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            List<FinalDataLine> directionedInputList = ProcessDataLoggerFile.processDirectionallity(channelSortedInputList, inputCommandLine);
            // write output file
            uxStatusText.setText("Writing the output file.");
            uxStatusText.paintImmediately(uxStatusText.getVisibleRect());
            ProcessDataLoggerFile.makeOutputFile(duration, directionedInputList, inputCommandLine);
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
            Runtime.getRuntime().exec("explorer.exe /select," + inputCommandLine.outputFileName);
        }//end trying to open file explorer
        catch(Exception e) {System.out.println("Couldn't open file explorer");}
    }//GEN-LAST:event_uxShowFileBtnActionPerformed

    ConfigDialog configDialog = new ConfigDialog(this, true);

    private void uxShowConfigBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxShowConfigBtnActionPerformed
        configDialog.updateConfig(inputCommandLine);
        configDialog.setVisible(true);
        updateConfigDisplay();
    }//GEN-LAST:event_uxShowConfigBtnActionPerformed

    DateTimeDialog dtDialog = new DateTimeDialog(this, true);
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

    private void uxGetCollectionTimeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxGetCollectionTimeBtnActionPerformed
        dtDialog.updateDateTime();
        dtDialog.setVisible(true);
        updateTimeDisplay();
    }//GEN-LAST:event_uxGetCollectionTimeBtnActionPerformed

    private void updateTimeDisplay() {
        uxGetCollectionTimeTxt.setText(dtDialog.dateTime.format(dtFormatter));
    }//end updateTimeDisplay

    private void updateConfigDisplay() {
        // update config display on main window
        StringBuilder sb = new StringBuilder();
        
        sb.append("Configuration File with current parameters:");
        for (String line : ProcessDataLoggerFile.getInputCommandLineStrings(inputCommandLine)) { sb.append("\n" + line); }

        // sb.append("No Date Time: " + inputCommandLine.dateTimeFlg);
        // sb.append("\tShow Peak Width: " + inputCommandLine.peakWidthFlg);
        // sb.append("\nZip File: " + inputCommandLine.zipFileFlg);
        // sb.append("\nLines to Skip: " + inputCommandLine.skipLines);
        // sb.append("\t\tThreshold for Peak: " + inputCommandLine.threshold);

        uxConfigDisplayText.setText(sb.toString());
        uxConfigDisplayText.setSelectionStart(0);
        uxConfigDisplayText.setSelectionEnd(0);

        // write changes to config file as well
        ProcessDataLoggerFile.saveInputCommandLine(inputCommandLine);
    }//end updateConfigDisplay()

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea uxConfigDisplayText;
    private javax.swing.JButton uxGetCollectionTimeBtn;
    private javax.swing.JTextField uxGetCollectionTimeTxt;
    private javax.swing.JButton uxGetInputBtn;
    private javax.swing.JTextField uxGetInputTxt;
    private javax.swing.JButton uxGetOutputBtn;
    private javax.swing.JTextField uxGetOutputTxt;
    private javax.swing.JButton uxProcessBtn;
    private javax.swing.JButton uxShowConfigBtn;
    private javax.swing.JButton uxShowFileBtn;
    private javax.swing.JTextArea uxStatusText;
    // End of variables declaration//GEN-END:variables
}
