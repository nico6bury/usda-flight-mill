/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package flightmill;

import flightmill.dataStorageStructs.InputCommandLine;

/**
 *
 * @author Nicholas.Sixbury
 */
public class ConfigDialog extends javax.swing.JDialog {

    /**
     * Creates new form ConfigDialog
     */
    public ConfigDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public InputCommandLine parent_icl;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uxGetThresholdTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        uxLineSkipChk = new javax.swing.JCheckBox();
        uxNoDateTimeChk = new javax.swing.JCheckBox();
        uxZipInputChk = new javax.swing.JCheckBox();
        uxAddPeakWidthChk = new javax.swing.JCheckBox();
        uxLineSkipTxt = new javax.swing.JTextField();
        uxConfirmConfigBtn = new javax.swing.JButton();
        uxCancelConfigBtn = new javax.swing.JButton();
        uxRevolutionChk = new javax.swing.JCheckBox();
        uxDoubleColumnChk = new javax.swing.JCheckBox();
        uxWidthRatioChk = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Configuration Options");

        uxGetThresholdTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxGetThresholdTxt.setText("1.5");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(" LED signal threshold:");

        uxLineSkipChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxLineSkipChk.setSelected(true);
        uxLineSkipChk.setText("Skip, a number of header lines in input file");

        uxNoDateTimeChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxNoDateTimeChk.setText("Include Date/Time in each Line of output");

        uxZipInputChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxZipInputChk.setText("Zip Input File and Delete Uncompressed File");

        uxAddPeakWidthChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxAddPeakWidthChk.setSelected(true);
        uxAddPeakWidthChk.setText("Add Width of each Peak as a column in output");

        uxLineSkipTxt.setText("4");

        uxConfirmConfigBtn.setText("Confirm");
        uxConfirmConfigBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxConfirmConfigBtnActionPerformed(evt);
            }
        });

        uxCancelConfigBtn.setText("Cancel");
        uxCancelConfigBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uxCancelConfigBtnActionPerformed(evt);
            }
        });

        uxRevolutionChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxRevolutionChk.setSelected(true);
        uxRevolutionChk.setText("Add Peak Time difference (revolution) column");

        uxDoubleColumnChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxDoubleColumnChk.setSelected(true);
        uxDoubleColumnChk.setText("Add Second Peak Width and Peak Time columns");

        uxWidthRatioChk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uxWidthRatioChk.setText("Add Peak Width ratio column");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(uxCancelConfigBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uxConfirmConfigBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(uxLineSkipChk, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uxLineSkipTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uxGetThresholdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(uxZipInputChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(uxAddPeakWidthChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                                .addComponent(uxNoDateTimeChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(uxRevolutionChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(uxDoubleColumnChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                .addComponent(uxWidthRatioChk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(uxAddPeakWidthChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uxZipInputChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uxNoDateTimeChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uxLineSkipChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uxLineSkipTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uxGetThresholdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uxDoubleColumnChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uxRevolutionChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uxWidthRatioChk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uxCancelConfigBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uxConfirmConfigBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uxConfirmConfigBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxConfirmConfigBtnActionPerformed
        // Update parent icl based on controls
        parent_icl.dateTimeFlg = uxNoDateTimeChk.isSelected();
        parent_icl.peakWidthFlg = uxAddPeakWidthChk.isSelected();
        if (uxLineSkipChk.isSelected()) {
            parent_icl.skipLines = Integer.parseInt(uxLineSkipTxt.getText());
        }
        else parent_icl.skipLines = 0;
        parent_icl.zipFileFlg = uxZipInputChk.isSelected();
        parent_icl.threshold = Double.parseDouble(uxGetThresholdTxt.getText());
        // close this dialog window
        this.setVisible(false);
    }//GEN-LAST:event_uxConfirmConfigBtnActionPerformed

    private void uxCancelConfigBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uxCancelConfigBtnActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_uxCancelConfigBtnActionPerformed

    /*
     * Updates the controls in this window based on the configuration in the inputCommandLine.
     */
    public void updateConfig(InputCommandLine icl) {
        this.uxAddPeakWidthChk.setSelected(icl.peakWidthFlg);
        this.uxGetThresholdTxt.setText(icl.threshold + "");
        this.uxLineSkipChk.setSelected(icl.skipLines > 0);
        this.uxLineSkipTxt.setText("" + icl.skipLines);
        this.uxNoDateTimeChk.setSelected(icl.dateTimeFlg);
        this.uxZipInputChk.setSelected(icl.zipFileFlg);
    }//end updateConfig(icl)

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfigDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigDialog dialog = new ConfigDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox uxAddPeakWidthChk;
    private javax.swing.JButton uxCancelConfigBtn;
    private javax.swing.JButton uxConfirmConfigBtn;
    private javax.swing.JCheckBox uxDoubleColumnChk;
    private javax.swing.JTextField uxGetThresholdTxt;
    private javax.swing.JCheckBox uxLineSkipChk;
    private javax.swing.JTextField uxLineSkipTxt;
    private javax.swing.JCheckBox uxNoDateTimeChk;
    private javax.swing.JCheckBox uxRevolutionChk;
    private javax.swing.JCheckBox uxWidthRatioChk;
    private javax.swing.JCheckBox uxZipInputChk;
    // End of variables declaration//GEN-END:variables
}
