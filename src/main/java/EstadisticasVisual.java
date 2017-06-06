
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import edu.eci.labinfo.estadisticasV2.analysis.Analysis;
import edu.eci.labinfo.estadisticasv2.generator.ReportGenerator;
import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class EstadisticasVisual extends JPanel {

    JComboBox estadistico;
    JComboBox inicio;
    JComboBox fin;
    JComboBox week;
    JLabel picture;
    JLabel semanaInicio;
    JLabel semanaFin;
    JLabel estadisticos;
    JLabel opcionSelected;
    JButton generar;
    String labels[] = {"Semana", "Tercio 1", "Tercio 2", "Tercio 3", "Consolidado"};
    static Analysis an;
    HashMap<String, Integer> semanas;
    HashMap<String, Integer> numSemanas = new HashMap<String, Integer>() {
        {
            put("Consolidado", 16);
            put("Tercio 1", 5);
            put("Tercio 2", 5);
            put("Tercio 3", 5);
        }
    };
    int iniS, finS;
    String sInicio;
    String sFin;
    static int year;

    public static void main(String[] args) {
        //try {
        GregorianCalendar fecha = new GregorianCalendar();
        year = fecha.get(Calendar.YEAR);
        an = new Analysis();
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

    }

    public EstadisticasVisual() {
        super(new BorderLayout());
        //Set up logo laboratorio.
        setupLogo();
        estadisticos = new JLabel("Opciones:");
        estadisticos.setFont(picture.getFont().deriveFont(Font.ITALIC));
        estadisticos.setHorizontalAlignment(JLabel.LEFT);
        estadistico = new JComboBox(labels);
        estadistico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JComboBox jcmbType = (JComboBox) e.getSource();
                String selectedTask = (String) jcmbType.getSelectedItem();
                System.out.println(selectedTask);
                refreshPanel(selectedTask);
            }
        });
        JPanel task = new JPanel();
        task.setBorder(BorderFactory.createTitledBorder("Generador de estadisticas:"));
        task.add(estadisticos);
        task.add(estadistico);
        task.setVisible(true);
        add(task, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     *
     * @param estadisticas
     */
    protected void refreshPanel(String estadisticas) {
        JPanel opcion = new JPanel();
        opcion.setBorder(BorderFactory.createTitledBorder("Opcion: " + estadisticas));
        if (estadisticas.equals("Semana")) {
            try {
                opcionSelected = new JLabel("Semana: ");
                opcionSelected.setFont(opcionSelected.getFont().deriveFont(Font.ITALIC));
                opcionSelected.setHorizontalAlignment(JLabel.LEFT);
                semanas = an.getIdSemanas();
                Set<String> modsemanas = semanas.keySet();
                String[] arr = modsemanas.toArray(new String[modsemanas.size()]);
                Arrays.sort(arr);
                week = new JComboBox(arr);
                week.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        System.out.println(selectedTask + " en semana.");
                        try {
                            an.statisticWeek(semanas.get(selectedTask));
                        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                            Log.anotar(ex);
                            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
                        }
                    }
                });
                opcion.add(opcionSelected);
                opcion.add(week);
                opcion.setVisible(true);
                add(opcion, BorderLayout.PAGE_END);

            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                Log.anotar(ex);
                JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
            }

        } else {
            try {
                semanaInicio = new JLabel("Semana inicio: ");
                semanaInicio.setFont(semanaInicio.getFont().deriveFont(Font.ITALIC));
                semanaInicio.setHorizontalAlignment(JLabel.LEFT);
                semanas = an.getIdSemanas();
                Set<String> modsemanas = semanas.keySet();
                String[] arr = modsemanas.toArray(new String[modsemanas.size()]);
                Arrays.sort(arr);
                inicio = new JComboBox(arr);
                inicio.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        iniS = semanas.get(selectedTask);
                        sInicio=selectedTask;
                    }
                });
                semanaFin = new JLabel("Semana fin: ");
                semanaFin.setFont(semanaFin.getFont().deriveFont(Font.ITALIC));
                semanaFin.setHorizontalAlignment(JLabel.LEFT);
                fin = new JComboBox(arr);
                fin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        finS = semanas.get(selectedTask);
                        sFin=selectedTask;
                    }
                });
                generar = new JButton("Generar");
                generar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        try {
                            an.statisticsAll(iniS, finS, numSemanas.get(estadisticas));
                            ReportGenerator repor=new ReportGenerator("Estadisticas "+estadisticas+"-"+year,"semana "+iniS+"-"+finS+" ",sInicio+" "+sFin,an);
                        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                            Log.anotar(ex);
                            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
                        } catch (DocumentException | IOException | InterruptedException ex) {
                            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                            Log.anotar(ex);
                            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
                        }
                    }
                });
                generar.setVerticalTextPosition(AbstractButton.BOTTOM);
                generar.setHorizontalTextPosition(AbstractButton.CENTER);
                opcion.add(semanaInicio);
                opcion.add(inicio);
                opcion.add(semanaFin);
                opcion.add(fin);
                opcion.add(generar, BorderLayout.PAGE_END);
                opcion.setVisible(true);
                add(opcion, BorderLayout.PAGE_END);

            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                Log.anotar(ex);
                JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
            }
        }
    }

    /**
     * Create ImageIcon
     */
    protected void putLabel() {
        ImageIcon icon = createImageIcon("logolaboratorio.png");
        picture.setIcon(icon);
        picture.setToolTipText("Labinfo");
        if (icon != null) {
            picture.setText(null);
        } else {
            picture.setText("Image not found");
        }
    }

    /**
     * Add label
     */
    protected void setupLogo() {
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        putLabel();
        picture.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        picture.setPreferredSize(new Dimension(400, 310));
        add(picture, BorderLayout.PAGE_START);

    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path
     * @return
     */
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = EstadisticasVisual.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
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
