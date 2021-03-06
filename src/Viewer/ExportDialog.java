/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Viewer;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author ntx
 */
public class ExportDialog extends javax.swing.JDialog {

    public static enum ExportedFileType {
        EXPORT_CSV,
        EXPORT_SQL
    };
    
    class NumberLimiter extends PlainDocument  
    {  
      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException  
      {  
        if(str.length() > 1 || "0123456789".indexOf(str) < 0)  
        {  
          java.awt.Toolkit.getDefaultToolkit().beep();  
          return;  
        }  
        super.insertString(offs, str, a);  
      }  
    }  
    /**
     * Creates new form ExportDialog
     */
    public ExportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        selectedFile = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExportType = new javax.swing.ButtonGroup();
        DataExportType = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        SQLExportOptions = new javax.swing.JPanel();
        ExportData = new javax.swing.JCheckBox();
        CreateTable = new javax.swing.JCheckBox();
        DataExportPanel = new javax.swing.JPanel();
        Single = new javax.swing.JRadioButton();
        MultiInsert = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        MultiInsertRows = new javax.swing.JFormattedTextField();
        TableName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Export Wizard");
        setModal(true);
        setResizable(false);

        jLabel1.setText("Export Format:");

        ExportType.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("CSV");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        ExportType.add(jRadioButton2);
        jRadioButton2.setText("SQL");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addContainerGap())
        );

        SQLExportOptions.setEnabled(false);

        ExportData.setText("Export Data");
        ExportData.setEnabled(false);
        ExportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportDataActionPerformed(evt);
            }
        });

        CreateTable.setText("Create Table");
        CreateTable.setEnabled(false);
        CreateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateTableActionPerformed(evt);
            }
        });

        DataExportPanel.setEnabled(false);

        DataExportType.add(Single);
        Single.setSelected(true);
        Single.setText("Single insert");
        Single.setEnabled(false);
        Single.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SingleActionPerformed(evt);
            }
        });

        DataExportType.add(MultiInsert);
        MultiInsert.setText("Multiple Inserts");
        MultiInsert.setEnabled(false);
        MultiInsert.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MultiInsertselected(evt);
            }
        });
        MultiInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultiInsertActionPerformed(evt);
            }
        });

        jLabel2.setText("Data Export settings");
        jLabel2.setEnabled(false);

        jPanel3.setEnabled(false);

        jLabel3.setText("Rows Per Insert");
        jLabel3.setEnabled(false);

        MultiInsertRows.setDocument(new NumberLimiter());
        MultiInsertRows.setText("1");
        MultiInsertRows.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MultiInsertRows, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(MultiInsertRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout DataExportPanelLayout = new javax.swing.GroupLayout(DataExportPanel);
        DataExportPanel.setLayout(DataExportPanelLayout);
        DataExportPanelLayout.setHorizontalGroup(
            DataExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataExportPanelLayout.createSequentialGroup()
                .addGroup(DataExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataExportPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DataExportPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2))
                    .addGroup(DataExportPanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(DataExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Single)
                            .addComponent(MultiInsert))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DataExportPanelLayout.setVerticalGroup(
            DataExportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataExportPanelLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Single)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MultiInsert)
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TableName.setText("table_name");
        TableName.setEnabled(false);

        jLabel5.setText("Table Name:");
        jLabel5.setEnabled(false);

        javax.swing.GroupLayout SQLExportOptionsLayout = new javax.swing.GroupLayout(SQLExportOptions);
        SQLExportOptions.setLayout(SQLExportOptionsLayout);
        SQLExportOptionsLayout.setHorizontalGroup(
            SQLExportOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SQLExportOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SQLExportOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DataExportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(SQLExportOptionsLayout.createSequentialGroup()
                        .addGroup(SQLExportOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ExportData)
                            .addComponent(CreateTable)
                            .addComponent(jLabel5)
                            .addComponent(TableName, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE))))
        );
        SQLExportOptionsLayout.setVerticalGroup(
            SQLExportOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SQLExportOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CreateTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExportData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DataExportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SQLExportOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SQLExportOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        SQLExportOptions.setEnabled(true);
        CreateTable.setEnabled(true);
        ExportData.setEnabled(true);
        if (ExportData.isSelected())
        {
            MultiInsert.setEnabled(true);
            Single.setEnabled(true);
            if (MultiInsert.isSelected())
                MultiInsertRows.setEnabled(true);
        }
        if (ExportData.isSelected() || CreateTable.isSelected())
            TableName.setEnabled(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void MultiInsertselected(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MultiInsertselected

    }//GEN-LAST:event_MultiInsertselected

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        SQLExportOptions.setEnabled(false);
        CreateTable.setEnabled(false);
        ExportData.setEnabled(false);
        MultiInsert.setEnabled(false);
        Single.setEnabled(false);
        MultiInsertRows.setEnabled(false);
        TableName.setEnabled(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void CreateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateTableActionPerformed
        if (ExportData.isSelected() || CreateTable.isSelected())
        {
            jLabel5.setEnabled(true);
            TableName.setEnabled(true);
        }
        else
        {
            jLabel5.setEnabled(false);
            TableName.setEnabled(false);
        }
    }//GEN-LAST:event_CreateTableActionPerformed

    private void ExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportDataActionPerformed
        if (ExportData.isSelected() || CreateTable.isSelected())
        {
            jLabel5.setEnabled(true);
            TableName.setEnabled(true);
            DataExportPanel.setEnabled(true);
            for (Component c : DataExportPanel.getComponents())
                c.setEnabled(true);
            if (MultiInsert.isSelected())
            {
                jPanel3.setEnabled(true);
                for (Component c : jPanel3.getComponents())
                    c.setEnabled(true);
            }
        }
        else
        {
            jLabel5.setEnabled(false);
            TableName.setEnabled(false);
            DataExportPanel.setEnabled(false);
            for (Component c : DataExportPanel.getComponents())
                c.setEnabled(false);
            jPanel3.setEnabled(false);
            for (Component c : jPanel3.getComponents())
                c.setEnabled(false);
        }
    }//GEN-LAST:event_ExportDataActionPerformed

    private void SingleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SingleActionPerformed
        jPanel3.setEnabled(false);
        for (Component c : jPanel3.getComponents())
            c.setEnabled(false);
    }//GEN-LAST:event_SingleActionPerformed

    private void MultiInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultiInsertActionPerformed
        jPanel3.setEnabled(true);
        for (Component c : jPanel3.getComponents())
            c.setEnabled(true);
    }//GEN-LAST:event_MultiInsertActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jRadioButton2.isSelected() && !(CreateTable.isSelected() || ExportData.isSelected()))
        {
            JOptionPane.showMessageDialog(this, "Please select at least one of: Export Data, Create Table!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (MultiInsertRows.isEnabled() && MultiInsertRows.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(this, "Please set rows per insert value!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((CreateTable.isSelected() || ExportData.isSelected()) && TableName.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(this, "Please set table name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser(selectedFile);
        int ret = chooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION)
        {
            selectedFile = chooser.getSelectedFile();
            dispose();
        }                    
    }//GEN-LAST:event_jButton1ActionPerformed

    public File getSelectedFile()
    {
        return selectedFile;
    }
    
    public ExportedFileType getExportType()
    {
        if (jRadioButton1.isSelected())
            return ExportedFileType.EXPORT_CSV;
        return ExportedFileType.EXPORT_SQL;
    }
    
    public boolean createTable()
    {
        return CreateTable.isSelected();
    }
    
    public boolean exportData()
    {
        return ExportData.isSelected();
    }
    
    public boolean singleRowExport()
    {
        return Single.isSelected();
    }
    
    public int getRowsPerInsert()
    {
        return Integer.parseInt(MultiInsertRows.getText());
    }
    
    public String getTableName()
    {
        return TableName.getText();
    }
    
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
            java.util.logging.Logger.getLogger(ExportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ExportDialog dialog = new ExportDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox CreateTable;
    private javax.swing.JPanel DataExportPanel;
    private javax.swing.ButtonGroup DataExportType;
    private javax.swing.JCheckBox ExportData;
    private javax.swing.ButtonGroup ExportType;
    private javax.swing.JRadioButton MultiInsert;
    private javax.swing.JFormattedTextField MultiInsertRows;
    private javax.swing.JPanel SQLExportOptions;
    private javax.swing.JRadioButton Single;
    private javax.swing.JTextField TableName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    // End of variables declaration//GEN-END:variables
    private File selectedFile;

}
