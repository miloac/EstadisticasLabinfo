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
import java.util.Arrays;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart extends ApplicationFrame {
    
    private Analysis analysis;
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
   
   public BarChart( String applicationTitle , String chartTitle, Analysis ana) {
      super( applicationTitle );//titulo jframe
      analysis=ana;
      System.out.println(ana);      
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
   
   private CategoryDataset createDataset( ) {
      final String B0 = "B0";        
      final String INGSOFTWARE = "Ingenieria software";        
      final String PLATAFORMAS = "Plataformas";        
      final String REDES = "Redes";
      final String INTERACTIVA = "Interactiva";
      final String MAC = "MAC";
      
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  
      //dataset.addValue( 100,"7:00", "Lunes");
      /**double [][] b0=analysis.getConsolidadoB0();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( b0[j][h] , B0, DIA.get(h));
          }
      }
      double [][] pla=analysis.getConsolidadoPlataformas();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( pla[j][h] , PLATAFORMAS, DIA.get(h));
          }
      }
      double [][] red=analysis.getConsolidadoRedes();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( red[j][h] , REDES, DIA.get(h));
          }        
      }
      double [][] sw=analysis.getConsolidadoSoftware();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( sw[j][h] , INGSOFTWARE, DIA.get(h));
          }
      }
      double [][] multi=analysis.getConsolidadoMultimedia();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( multi[j][h] , MAC, DIA.get(h));
          }
      }
      double [][] inte=analysis.getConsolidadoInteractiva();
      for (int h = 0; h < 6; h++) {
          for (int j = 0; j < 8; j++) {
            dataset.addValue( inte[j][h] , INTERACTIVA, DIA.get(h));
          }
      }*/
      return dataset; 
   }
   
   public void generarGrafico() {
      this.pack( );        
      RefineryUtilities.centerFrameOnScreen( this );        
      this.setVisible( true ); 
   }
}
