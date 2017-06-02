/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.analysis;

/**
 *
 * @author Daniela Sepulveda
 */
import static com.oracle.jrockit.jfr.ContentType.Bytes;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart extends ApplicationFrame {
    
    private double[][] sala;
    private String nsala;
    private static final HashMap<Integer, String> DIA = new HashMap<Integer, String>() {
        {
            put(0, "Lunes");
            put(1, "Martes");
            put(2,"Miercoles");
            put(3,"Jueves");
            put(4,"Viernes");
            put(5,"Sabado");
        }
    };
    
    private static final HashMap<Integer, String> HORA = new HashMap<Integer, String>() {
        {
            put(0,"7:00");
            put(1,"8:30");
            put(2,"10:00");
            put(3,"11:30");
            put(4,"1:00");
            put(5,"2:30");
            put(6,"4:00");
            put(7,"5:30");
        }
    };
   
   public BarChart( String applicationTitle , String chartTitle, double[][] sal) {
      super( applicationTitle );//titulo jframe
      sala=sal;  
      nsala=chartTitle;
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           //titulo grafico
         "Dias semana",            
         "Horario",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 600 , 500 ) );        
      setContentPane( chartPanel ); 
   }
   
   private CategoryDataset createDataset() {
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( sala[j][h],HORA.get(j), DIA.get(h));
          }
      }
      
      return dataset; 
   }
   
   public void generarGrafico() {
        this.pack( );        
        RefineryUtilities.centerFrameOnScreen( this );       
        this.setVisible( true ); 
   }
   public byte[] getImage() throws IOException{
        this.pack( );        
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        this.paint(graphics2D);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        this.setVisible( false ); 
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        dispose();
        return bytes;
   }
}
