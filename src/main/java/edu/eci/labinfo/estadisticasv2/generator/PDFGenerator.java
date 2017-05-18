/*
 * Decompiled with CFR 0_115.
 */
package edu.eci.labinfo.estadisticasv2.generator;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
/**
 * 
 * @author 
 */
public class PDFGenerator extends Generator{
    private void propiedades(Document document, String semana, String fecha) {
        document.addTitle("Estad\u00edsticas semana: " + semana + " " + fecha);
        document.addSubject("Tablas estad\u00edsticas semana correspontiende.");
        document.addKeywords("Estad\u00edsticas de uso, Labinfo, Reporte semanal");
        document.addAuthor("Estadisticas.jar (Santiago A. Alzate S.)");
        document.addCreator("Estadisticas.jar (Santiago A. Alzate S.)");
    }

    @Override
    public void documento(String semana, String fecha, int [][] soft, int [][] b0, int [][] plat, int [][] red,int [][] multi, int [][]inte) throws FileNotFoundException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Estadisticas-"+semana+"-" +fecha+ ".pdf"));
            document.open();
            this.propiedades(document, semana, fecha);
            document.newPage();
            Chunk tab1 = new Chunk(new VerticalPositionMark(), 50.0f, false);
            Chunk tab2 = new Chunk(new VerticalPositionMark(), 200.0f, false);
            Paragraph para = new Paragraph();
            para.add(new Paragraph(" "));
            para.add(new Chunk("Estad\u00edsticas semana: " + semana + " " + fecha));
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio de Ingenier\u00eda de Software:\n"));
            this.guardar(para, tab1, tab2, soft);
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio B0 Turnos:\n"));
            this.guardar(para, tab1, tab2, b0);
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio de Multimedia y m\u00f3viles:\n"));
            this.guardar(para, tab1, tab2, multi);
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio de Plataformas Computacionales:\n"));
            this.guardar(para, tab1, tab2, plat);
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio de Redes de Computadores:\n"));
            this.guardar(para, tab1, tab2, red);
            para.add(new Paragraph(" "));
            para.add(new Chunk("Laboratorio de Aula inteligente:\n"));
            this.guardar(para, tab1, tab2, inte);
            document.add(para);
            document.close();
        }
        catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el Documento. Se gener\u00f3 un error ingresando la informaci\u00f3n al documento.", "Error", 0);
            e.printStackTrace();
        }
    }

    private void guardar(Paragraph para, Chunk tab1, Chunk tab2, int[][] lab) {
        para.add(new Chunk(tab1));
        para.add(new Chunk("D\u00eda:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk("L       M      M      J       V       S\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("07:00:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[0][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[0][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[0][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[0][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[0][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[0][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("08:30:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[1][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[1][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[1][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[1][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[1][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[1][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("10:00:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[2][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[2][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[2][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[2][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[2][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[2][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("11:30:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[3][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[3][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[3][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[3][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[3][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[3][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("13:00:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[4][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[4][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[4][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[4][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[4][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[4][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("14:30:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[5][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[5][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[5][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[5][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[5][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[5][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("16:00:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[6][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[6][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[6][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[6][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[6][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[6][5]) + "\n"));
        para.add(new Chunk(tab1));
        para.add(new Chunk("17:30:"));
        para.add(new Chunk(tab2));
        para.add(new Chunk(String.valueOf(lab[7][0])));
        para.add(new Chunk(new VerticalPositionMark(), 230.0f, false));
        para.add(new Chunk(String.valueOf(lab[7][1])));
        para.add(new Chunk(new VerticalPositionMark(), 260.0f, false));
        para.add(new Chunk(String.valueOf(lab[7][2])));
        para.add(new Chunk(new VerticalPositionMark(), 290.0f, false));
        para.add(new Chunk(String.valueOf(lab[7][3])));
        para.add(new Chunk(new VerticalPositionMark(), 320.0f, false));
        para.add(new Chunk(String.valueOf(lab[7][4])));
        para.add(new Chunk(new VerticalPositionMark(), 350.0f, false));
        para.add(new Chunk(String.valueOf(lab[7][5]) + "\n"));
    }
}

