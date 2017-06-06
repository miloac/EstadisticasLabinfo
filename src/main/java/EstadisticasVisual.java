
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import edu.eci.labinfo.estadisticasV2.analysis.Analysis;
import edu.eci.labinfo.estadisticasv2.generator.ReportGenerator;
import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
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
import java.util.Map;
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
    JButton generarSemana;
    JPanel opcion;
    String labels[] = {"Semana", "Tercio 1", "Tercio 2", "Tercio 3", "Consolidado"};
    static Analysis an;
    HashMap<Integer, String> semanas;
    HashMap<String, Integer> semanasReves;
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
    String semanaSelected;
    static int year;

    public static void main(String[] args) {
        GregorianCalendar fecha = new GregorianCalendar();
        year = fecha.get(Calendar.YEAR);
        an = new Analysis();
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
                refreshPanel(selectedTask);
            }
        });
        JPanel task = new JPanel();
        task.setBorder(BorderFactory.createTitledBorder("Generador de estadisticas:"));
        task.add(estadisticos);
        task.add(estadistico);
        task.setVisible(true);
        add(task, BorderLayout.CENTER);
        refreshPanel("Semana");
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

    /**
     *
     * @param estadisticas
     */
    protected void refreshPanel(String estadisticas) {
        if (opcion != null) {
            opcion.setVisible(false);
        }
        opcion = new JPanel();
        opcion.setVisible(false);
        opcion.setBorder(BorderFactory.createTitledBorder("Opcion: " + estadisticas));
        semanasReves = new HashMap<>();
        try {
            semanas = an.getIdSemanas();
            Set<Integer> modsemanas = semanas.keySet();
            String[] arr = new String[modsemanas.size()];
            arr[0] = " ";
            for (int i = 1; i < modsemanas.size(); i++) {
                System.out.println(i);
                arr[i] = semanas.get(i);
                semanasReves.put(arr[i], i);
            }
            if (estadisticas.equals("Semana")) {

                opcionSelected = new JLabel("Semana: ");
                opcionSelected.setFont(opcionSelected.getFont().deriveFont(Font.ITALIC));
                opcionSelected.setHorizontalAlignment(JLabel.LEFT);
                semanaSelected = arr[0];
                week = new JComboBox(arr);
                week.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        semanaSelected = selectedTask;
                    }
                });
                generarSemana = new JButton("Generar");
                generarSemana.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        try {
                            an.statisticWeek(semanasReves.get(semanaSelected));
                            JOptionPane.showMessageDialog(null, "Estadisticas generadas.", "OK", JOptionPane.INFORMATION_MESSAGE);
                        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | HeadlessException ex) {
                            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
                            Log.anotar(ex);
                            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
                        }
                    }
                });
                generarSemana.setVerticalTextPosition(AbstractButton.BOTTOM);
                generarSemana.setHorizontalTextPosition(AbstractButton.CENTER);
                opcion.add(opcionSelected);
                opcion.add(week);
                opcion.add(generarSemana);
                opcion.setVisible(true);
                add(opcion, BorderLayout.PAGE_END);

            } else {

                semanaInicio = new JLabel("Semana inicio: ");
                semanaInicio.setFont(semanaInicio.getFont().deriveFont(Font.ITALIC));
                semanaInicio.setHorizontalAlignment(JLabel.LEFT);
                inicio = new JComboBox(arr);
                sInicio = arr[0];
                iniS = 1;
                inicio.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        iniS = semanasReves.get(selectedTask);
                        sInicio = selectedTask;
                    }
                });
                semanaFin = new JLabel("Semana fin: ");
                semanaFin.setFont(semanaFin.getFont().deriveFont(Font.ITALIC));
                semanaFin.setHorizontalAlignment(JLabel.LEFT);
                fin = new JComboBox(arr);
                sFin = arr[0];
                finS = 1;
                fin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        JComboBox jcmbType = (JComboBox) e.getSource();
                        String selectedTask = (String) jcmbType.getSelectedItem();
                        finS = semanasReves.get(selectedTask);
                        sFin = selectedTask;
                    }
                });
                generar = new JButton("Generar");
                generar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        try {
                            if (iniS <= finS) {
                                System.out.println(iniS + " " + finS);
                                an.statisticsAll(iniS, finS, numSemanas.get(estadisticas));
                                ReportGenerator repor = new ReportGenerator(estadisticas + "-" + year, "semana " + iniS + "-" + finS + " ", sInicio + " " + sFin, an);
                                JOptionPane.showMessageDialog(null, "Estadisticas generadas.", "OK", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Por favor, revisar las fechas", "Error", JOptionPane.ERROR_MESSAGE);
                            }
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
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EstadisticasVisual.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
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
        picture.setPreferredSize(new Dimension(600, 400));
        add(picture, BorderLayout.PAGE_START);

    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path
     * @return
     */
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = EstadisticasVisual.class
                .getResource(path);
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
