/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable_PDF_Excel;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


        
        //XSSFWorkbook

/**
 *
 * @author GACA1186
 */
public class JExport extends javax.swing.JFrame {

    /**
     * Creates new form JExport
     */
    private javax.swing.JTable jTable1;
    public JExport() {
        
        initComponents();
        
        jFileChooser1.setApproveButtonText("Guardar"); 
        jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xls)", "xls"));
        jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter("Documento PDF (*.pdf)", "pdf"));
        jFileChooser1.setAcceptAllFileFilterUsed(false);

    }
    public void setTable(javax.swing.JTable jTable){
        this.jTable1 =jTable;
    }
    public void Exportar(){
        // if (jFileChooser1.showDialog(null, "Exportar Archivo") == jFileChooser1.APPROVE_OPTION) {

            String nameExtension = jFileChooser1.getFileFilter().getDescription();

            // Exportar a PDF
            if (nameExtension.equals("Documento PDF (*.pdf)")) {
                try {
                    // We create the document and set the file name.        
                    // Creamos el documento e indicamos el nombre del fichero.
                    Document document = new Document(PageSize.B2.rotate(),10,3,20,20);
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream(jFileChooser1.getSelectedFile() + ".pdf"));
                    } catch (FileNotFoundException fileNotFoundException) {

                    }
                    document.open();

                    // First page (Primera pÃ¡gina)
                    Anchor anchor = new Anchor();
                    anchor.setName("");

                    // Second parameter is the number of the chapter (El segundo parámetro es el número del capí­tulo).
                    Chapter catPart = new Chapter(new Paragraph(anchor), 1);

                    Paragraph subPara = new Paragraph("");
                    Section subCatPart = catPart.addSection(subPara);
                    subCatPart.add(new Paragraph(""));

                    // Create the table (Creamos la tabla)
                    PdfPTable table = new PdfPTable(jTable1.getColumnCount());

                    // Now we fill the rows of the PdfPTable (Ahora llenamos las filas de PdfPTable)
                    PdfPCell columnHeader;
                    // Fill table columns header 
                    // Rellenamos las cabeceras de las columnas de la tabla.                
                    for (int column = 0; column < jTable1.getColumnCount(); column++) {
                        columnHeader = new PdfPCell(new Phrase(jTable1.getColumnName(column)));
                        columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(columnHeader);
                    }
                    table.setHeaderRows(1);
                    // Fill table rows (rellenamos las filas de la tabla).                
                    for (int row = 0; row < jTable1.getRowCount(); row++) {
                        for (int column = 0; column < jTable1.getColumnCount(); column++) {
                            table.addCell(jTable1.getValueAt(row, column).toString());
                        }
                    }
                    subCatPart.add(table);

                    document.add(catPart);

                    document.close();
                    JOptionPane.showMessageDialog(jTable1, "Your PDF file has been generated! ** ¡Se ha generado tu hoja PDF!)",
                            "RESULTADO", JOptionPane.INFORMATION_MESSAGE);
                } catch (DocumentException documentException) {
                    System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
                    JOptionPane.showMessageDialog(jTable1, "The file not exists (Se ha producido un error al generar un documento): " + documentException,
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            // Exportar a EXCEL
            if (nameExtension.equals("Libro de Excel (*.xls)")) {
                File archivo;
                archivo = jFileChooser1.getSelectedFile();
                int cantFila = jTable1.getRowCount();
                int cantColumna = jTable1.getColumnCount();
                Workbook wb;
                wb = new HSSFWorkbook();
                Sheet hoja = wb.createSheet("Hoja-01");

                try {
                    for (int i = 0; i <= cantFila; i++) {
                        Row fila = hoja.createRow(i + 1);
                        for (int j = 0; j < cantColumna; j++) {
                            Cell celda = fila.createCell(j);
                            if (i == 0) {
                                celda.setCellValue(String.valueOf(jTable1.getColumnName(j)));
                            } else {
                                celda.setCellValue(String.valueOf(jTable1.getValueAt(i-1, j)));
                            }
                            
                        }
                    }
                    FileOutputStream Fl = new FileOutputStream(archivo + ".xls");
                    wb.write(Fl);
                    Fl.close();
                    JOptionPane.showMessageDialog(null, "Exportacion exitosa");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
                }
                
            }
            this.dispose();
        //}
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jFileChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFileChooser1MouseClicked(evt);
            }
        });
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFileChooser1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jFileChooser1MouseClicked

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        Exportar();
    }//GEN-LAST:event_jFileChooser1ActionPerformed

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
            java.util.logging.Logger.getLogger(JExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JExport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
