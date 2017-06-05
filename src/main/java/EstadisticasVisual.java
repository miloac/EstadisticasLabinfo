
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import edu.eci.labinfo.estadisticasV2.analysis.Analysis;
import edu.eci.labinfo.estadisticasV2.analysis.BarChart;
import edu.eci.labinfo.estadisticasv2.generator.CSVGenerator;
import edu.eci.labinfo.estadisticasv2.generator.Generator;
import edu.eci.labinfo.estadisticasv2.generator.PDFGenerator;
import edu.eci.labinfo.estadisticasv2.generator.ReportGenerator;
import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class EstadisticasVisual extends JPanel implements ActionListener {
    
    public static void main(String[] args){  
        try {
            GregorianCalendar fecha= new GregorianCalendar();
            int year = fecha.get(Calendar.YEAR);
            Analysis an=new Analysis();
            HashMap<String,Integer>semanas=an.getIdSemanas();
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
            });
            
            
            
            
            
            //primer semestre
            an.statisticsAll(1, 16,16);
            ReportGenerator repor=new ReportGenerator("Consolidado Estadisticas "+year+"-1","semana 1-16","", an);
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        } catch (DocumentException | InterruptedException | IOException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
////////////////////////
    JLabel picture;

    public EstadisticasVisual() {
        super(new BorderLayout());

        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

        //Create the combo box, select the item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(4);
        petList.addActionListener(this);

        //Set up the picture.
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        updateLabel(petStrings[petList.getSelectedIndex()]);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        //The preferred size is hard-coded to be the width of the
        //widest image and the height of the tallest image + the border.
        //A real program would compute this.
        picture.setPreferredSize(new Dimension(177, 122+10));

        //Lay out the demo.
        add(petList, BorderLayout.PAGE_START);
        add(picture, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        updateLabel(petName);
    }

    protected void updateLabel(String name) {
        ImageIcon icon = createImageIcon("images/" + name + ".gif");
        picture.setIcon(icon);
        picture.setToolTipText("A drawing of a " + name.toLowerCase());
        if (icon != null) {
            picture.setText(null);
        } else {
            picture.setText("Image not found");
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = EstadisticasVisual.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Estadisticas Laboratorio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new EstadisticasVisual();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}

