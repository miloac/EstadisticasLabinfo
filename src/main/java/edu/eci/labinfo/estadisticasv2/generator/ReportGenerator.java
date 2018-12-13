/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.generator;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.eci.labinfo.estadisticasV2.analysis.Analysis;
import edu.eci.labinfo.estadisticasV2.analysis.BarChart;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Daniela Sepulveda
 */
public class ReportGenerator {
    private Analysis ana;
    private final String[] HORA = {"7:00", "8:30", "10:00", "11:30", "1:00", "2:30", "4:00", "5:30"};
    private final String[] DIA = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    private Document document;
    private Font fontContenido;
    private Font fontTitulos;
    private Font fontMarcoTabla;
    
    public ReportGenerator(String titulo,String semana, String fecha,Analysis an) throws FileNotFoundException, DocumentException, IOException, InterruptedException{
            ana=an;
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("ReporteEstadisticas-"+titulo+"-"+semana+"-" +fecha+ ".pdf"));
            document.open();
            document.newPage();
            document.addTitle("Estad\u00edsticas laboratorio informatica: "+titulo+ " " +semana + " " + fecha);
            document.addSubject("Reporte de estadisticas de uso de los laboratorios");
            document.addKeywords("Estad\u00edsticas de uso, Labinfo, Reporte");
            document.addAuthor("Estadisticas.jar Daniela Sepulveda Alzate");
            document.addCreator("Estadisticas.jar Daniela Sepulveda Alzate"); 
            Image image =Image.getInstance("src/main/resources/logolaboratorio.png");
            image.scaleAbsolute(250f, 150f);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
            // Crear las fuentes para el contenido y los titulos
            fontContenido = FontFactory.getFont(
            FontFactory.HELVETICA.toString(), 10, Font.NORMAL,BaseColor.BLACK);
            fontMarcoTabla = FontFactory.getFont(
            FontFactory.HELVETICA.toString(), 10, Font.BOLD,new BaseColor(0,135,80));
            fontTitulos = FontFactory.getFont(
            FontFactory.HELVETICA, 14, Font.BOLD,new BaseColor(0,135,80));  
            Paragraph para = new Paragraph();
            para.add(new Phrase("Estad\u00edsticas laboratorio informatica: \n"
                    + ""+titulo+ " " +semana + " " + fecha, fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase("Reporte de estadisticas de uso de los laboratorios", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            para = new Paragraph();
            para.add(new Phrase("Laboratorio de Ingenieria de Software", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de ingeniería de software se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaSoftware().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraSoftware()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]iw=ana.getConsolidadoSoftware();
            generarTabla("Ingenieria Software",iw);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            //generarGrafica("Estadisticas","Ingenieria software",iw);
            //plataformas
            para = new Paragraph();
            para.add(new Phrase("Laboratorio de plataformas computacionales", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de plataformas computacionales se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaPlataformas().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraPlataformas()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]pla=ana.getConsolidadoPlataformas();
            generarTabla("Plataformas computacionales",pla);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            //generarGrafica("Estadisticas","Plataformas computacionales",pla);
            //Redes
            para = new Paragraph();
            para.add(new Phrase("Laboratorio redes de computadores", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de redes de computadores se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaRedes().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraRedes()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]red=ana.getConsolidadoRedes();
            generarTabla("Redes de computadores",red);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            //generarGrafica("Estadisticas","Redes de computadores",red);
            //B0
            para = new Paragraph();
            para.add(new Phrase("Laboratorio B0 turnos", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de B0 turnos se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaB0().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraB0()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]b0=ana.getConsolidadoB0();
            generarTabla("B0 Turnos",b0);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            //generarGrafica("Estadisticas","B0 Turnos",b0);
            document.add(para);
            //Interactiva
            para = new Paragraph();
            para.add(new Phrase("Aula inteligente", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de aula inteligente se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaInteractiva().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraInteractiva()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]inte=ana.getConsolidadoInteractiva();
            generarTabla("Aula inteligente",inte);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            //generarGrafica("Estadisticas","Aula Inteligente",inte);
            //Mac
            para = new Paragraph();
            para.add(new Phrase("Laboratorio de multimedia y móviles", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(
            "Para el laboratorio de multimedia y móviles se puede observar "
            + "que el uso del laboratorio el día más alto son los "+ana.getMayorDiaMultimedia().toLowerCase()+
              ", mientras que la hora más utilizada es a las "+ana.getMayorHoraMultimedia()+"."
            ,fontContenido));
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            double [][]mul=ana.getConsolidadoMultimedia();
            generarTabla("Multimedia y móviles",mul);
            para = new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            para.add(new Phrase("Graficas estadisticas de uso", fontTitulos));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            generarGrafica("Estadisticas","Ingenieria software",iw);
            generarGrafica("Estadisticas","Plataformas computacionales",pla);
            generarGrafica("Estadisticas","Redes de computadores",red);
            generarGrafica("Estadisticas","B0 Turnos",b0);
            generarGrafica("Estadisticas","Aula Inteligente",inte);
            generarGrafica("Estadisticas","Multimedia y móviles",mul);
            document.close();
    }
    
    private void generarGrafica(String titulo,String subtitulo,double[][]iw) throws BadElementException, IOException, DocumentException, InterruptedException{
            Paragraph para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
            BarChart graficaIW=new BarChart(titulo,subtitulo,iw);
            JFreeChart barChart=graficaIW.getbarChart();
            barChart.setBackgroundPaint(Color.white);
            java.awt.Image originalImage = barChart.createBufferedImage(500, 300);
            Image image1 = Image.getInstance(originalImage,Color.white);
            document.add(image1);
            para=new Paragraph();
            para.add(new Phrase(Chunk.NEWLINE));
            para.add(new Phrase(Chunk.NEWLINE));
            document.add(para);
    }

    private void generarTabla(String sala, double[][]sal) throws DocumentException{
           PdfPTable table = new PdfPTable(8); 
            PdfPCell celdatitulo = new PdfPCell(new Paragraph(sala,fontMarcoTabla));  
            celdatitulo.setColspan(8);
            table.addCell(celdatitulo);
            PdfPCell vacio = new PdfPCell(new Paragraph(""));  
            table.addCell(vacio);
            for (int i = 0; i < 6; i++) {
                PdfPCell dia = new PdfPCell(new Paragraph(DIA[i],fontMarcoTabla));  
                table.addCell(dia);
            }
            PdfPCell total = new PdfPCell(new Paragraph("Total",fontMarcoTabla));  
            table.addCell(total);
            for (int i = 0; i < sal.length; i++) {
                if(i==8){
                    table.addCell(total);
                }else{
                    PdfPCell hora = new PdfPCell(new Paragraph(HORA[i],fontMarcoTabla));  
                    table.addCell(hora);
                }
                for (int j = 0; j < sal[i].length; j++) {
                   PdfPCell tmp = new PdfPCell(new Paragraph((int)Math.ceil(sal[i][j])+"",fontContenido));  
                   table.addCell(tmp);
                }
            }
            
            document.add(table);
    }
}