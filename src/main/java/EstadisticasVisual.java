
import com.itextpdf.text.DocumentException;
import edu.eci.labinfo.estadisticasV2.analysis.Analysis;
import edu.eci.labinfo.estadisticasV2.analysis.BarChart;
import edu.eci.labinfo.estadisticasv2.generator.CSVGenerator;
import edu.eci.labinfo.estadisticasv2.generator.Generator;
import edu.eci.labinfo.estadisticasv2.generator.PDFGenerator;
import edu.eci.labinfo.estadisticasv2.generator.ReportGenerator;
import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class EstadisticasVisual {
    
    public static void main(String[] args){  
        Analysis an=new Analysis();
        try {
            
            //primer semestre
            an.statisticsAll(1, 16,16);
            GregorianCalendar fecha= new GregorianCalendar();
            int year = fecha.get(Calendar.YEAR);
            ReportGenerator repor=new ReportGenerator("Consolidado Estadisticas"+year+"-1","semana 1-16",fecha.getTime().toString(), an);
            //segundo semestre
            // an.statisticsAll(18, 42);
            //tercer semestre
            
            //Consolidado
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        } catch (DocumentException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        } catch (IOException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        }
    }
    
}

