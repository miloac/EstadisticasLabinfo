/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasv2.generator;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author monitor
 */
public class Horario {
    //////////////////////////////////////////////////////////otro
    //turnos
    private int[][] b0 = new int[8][6];
    //plataformas
    private int[][] plat = new int[8][6];
    //redes
    private int[][] red = new int[8][6];
    //ingenieria software
    private int[][] soft = new int[8][6];
    //interactiva
    private int[][] inte = new int[8][6];
    //multimedia
    private int[][] multi = new int[8][6];

    public Horario(Calendar cal) {
        String connectionURL = "jdbc:mysql://laboratorio.is.escuelaing.edu.co/control";
        String usuario = "control";
        String passw = "control20101";
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(connectionURL, usuario, passw);
            Statement stmt = con.createStatement();
            int mes = cal.get(2) + 1;
            String primera = String.valueOf(cal.get(1)) + "-" + mes + "-" + cal.get(5);
            cal.add(5, 6);
            mes = cal.get(2) + 1;
            String ultima = String.valueOf(cal.get(1)) + "-" + mes + "-" + cal.get(5);
            ResultSet rs = stmt.executeQuery("select * from LOGS where LOGON BETWEEN '" + primera + "' AND '" + ultima + "'");
            while (rs.next()) {
                this.analizar(rs);
            }
            this.guardar(primera, ultima);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error." + e, "Error", 0);
            e.printStackTrace();
        }
    }

    private void guardar(String a, String b) throws FileNotFoundException {
        Generator pdf = new PDFGenerator();
        Generator csv = new CSVGenerator();
        pdf.documento(a, b, this.soft, this.b0, this.plat, this.red,this.multi, this.inte);
        csv.documento(a, b, this.soft, this.b0, this.plat, this.red,this.multi, this.inte);
    }
    
    private boolean isNumeric(String t){
        try{
            Integer.parseInt(t);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    private void analizar(ResultSet rs) {
        String eq = null;
        try {
            eq = rs.getString("EQUIPO");
            String fecha = rs.getString("LOGON");
            String[] hora = fecha.split(" ");
            String eqs = eq.toLowerCase().contains("sistema") ? eq.substring(8) : eq.substring(5);
            if(isNumeric(eqs)){
                int equipo = Integer.valueOf(eqs);
                if (equipo > 0 && equipo < 25) {
                    this.guardar(this.soft, hora[1], hora[0]);
                }else if (equipo > 49 && equipo < 71) {
                    this.guardar(this.plat, hora[1], hora[0]);
                } else if (equipo > 100 && equipo < 113) {
                    this.guardar(this.red, hora[1], hora[0]);
                } else if (equipo > 160 && equipo < 173) {
                    this.guardar(this.multi, hora[1], hora[0]);
                }else if (equipo > 150 && equipo < 158) {
                    this.guardar(this.inte, hora[1], hora[0]);
                }else  {
                    this.guardar(this.b0, hora[1], hora[0]);
                }
            }else{
                int t = Integer.parseInt(JOptionPane.showInputDialog(null, "En que sala va este equipo "+eq+" (1) software, (2) plataformas (3) redes "
                        + "(4) multimedia (5) interactiva (6) B0"));
                switch(t){
                    case 1:
                        this.guardar(this.soft, hora[1], hora[0]);
                        break;
                    case 2:
                        this.guardar(this.plat, hora[1], hora[0]);
                        break;
                    case 3:
                        this.guardar(this.red, hora[1], hora[0]);
                        break;
                    case 4:
                        this.guardar(this.multi, hora[1], hora[0]);
                        break;
                    case 5:
                        this.guardar(this.inte, hora[1], hora[0]);
                        break;
                    case 6:
                        this.guardar(this.b0, hora[1], hora[0]);
                        break;
                    default:
                        break;
                }
            }
            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, eq, "Error", 0);
        }
    }

    private void guardar(int[][] lab, String hour, String f) {
        String[] fecha = f.split("-");
        int mes = Integer.valueOf(fecha[1]) - 1;
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(fecha[0]), mes, Integer.valueOf(fecha[2]));
        String[] hora = hour.split(":");
        int h = Integer.valueOf(hora[0]) * 100 + Integer.valueOf(hora[1]);
        if (h < 830) {
            int[] arrn = lab[0];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 830 && h < 1000) {
            int[] arrn = lab[1];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1000 && h < 1130) {
            int[] arrn = lab[2]; 
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1130 && h < 1300) {
            int[] arrn = lab[3];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1300 && h < 1430) {
            int[] arrn = lab[4];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1430 && h < 1600) {
            int[] arrn = lab[5];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1600 && h < 1730) {
            int[] arrn = lab[6];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        } else if (h >= 1730) {
            int[] arrn = lab[7];
            int n = cal.get(7) - 2;
            arrn[n] = arrn[n] + 1;
        }
    }

    public static void main2(String[] args) {
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
                    new Horario(cal);
                }
            }
            JOptionPane.showMessageDialog(null, "Programa terminado.", "Atencion", 1);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 un error inesperado en el procedimiento.", "Error", 0);
        }
    }
}
