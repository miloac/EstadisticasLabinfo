
import java.util.Calendar;
import java.util.Date;
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
        try {
            String fecha = JOptionPane.showInputDialog(null, "Ingresar fecha del lunes de la semana a consultar, en el formato AAAA-MM-DD. \nEjemplo para elegir Febrero 25 de 2013 se debe ingresar: 2013-02-25", "Ingresar", 3);
            if (fecha == null || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresar fecha.", "Error", 0);
            } else {
                String[] fechas = fecha.split("-");
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.valueOf(fechas[0]), Integer.valueOf(fechas[1]) - 1, Integer.valueOf(fechas[2]));
                Date dateRepresentation = cal.getTime();
                String[] date = dateRepresentation.toString().split(" ");
                if (!date[0].equals("Mon")) {
                    JOptionPane.showMessageDialog(null, "La fecha ingresada no corresponde a un lunes.", "Error", 0);
                } else {
                    //new Horario(cal);
                }
            }
            JOptionPane.showMessageDialog(null, "Programa terminado.", "Atencion", 1);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        }
    }
    
}
