/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.analysis;

import edu.eci.labinfo.estadisticasv2.conexion.Conexion;
import edu.eci.labinfo.estadisticasv2.conexion.EstadisticasConexion;
import edu.eci.labinfo.estadisticasv2.conexion.ReservasConexion;
import edu.eci.labinfo.estadisticasv2.generator.CSVGenerator;
import edu.eci.labinfo.estadisticasv2.generator.Generator;
import edu.eci.labinfo.estadisticasv2.generator.PDFGenerator;
import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class Analysis {

    Generator genPDF;
    Generator genCSV;
    
    private static final HashMap<String, double[][]> CONSOLIDADO = new HashMap<String, double[][]>() {
        {
            put("REDES", new double[9][7]);
            put("INFRAESTRUCTURA", new double[9][7]);     //plataformas
            put("B-0", new double[9][7]);      //ingenieria IW
            put("C1-205B", new double[9][7]);
            put("INTERACTIVA", new double[9][7]);
            put("TURNOS", new double[9][7]); //BO (turnos)    
        }
    };

    private static final HashMap<String, Integer> DIA = new HashMap<String, Integer>() {
        {
            put("L", 0);
            put("M", 1);
            put("Mc", 2);
            put("J", 3);
            put("V", 4);
            put("S", 5);
        }
    };

    private static final HashMap<String, Integer> HORA = new HashMap<String, Integer>() {
        {
            put("7:00", 0);
            put("8:30", 1);
            put("10:00", 2);
            put("11:30", 3);
            put("1:00", 4);
            put("2:30", 5);
            put("4:00", 6);
            put("5:30", 7);
        }
    };

    private static final HashMap<String, Integer> MESES = new HashMap<String, Integer>() {
        {
            put("ene", 0);
            put("feb", 1);
            put("mar", 2);
            put("abr", 3);
            put("may", 4);
            put("jun", 5);
            put("jul", 6);
            put("ago", 7);
            put("sep", 8);
            put("oct", 9);
            put("nov", 10);
            put("dic", 11);
        }
    };

    private static final HashMap<String, int[][]> SALONES = new HashMap<String, int[][]>() {
        {
            put("REDES", new int[8][6]);
            put("INFRAESTRUCTURA", new int[8][6]);     //plataformas
            put("B-0", new int[8][6]);      //ingenieria IW
            put("C1-205B", new int[8][6]);
            put("INTERACTIVA", new int[8][6]);
            put("TURNOS", new int[8][6]); //BO (turnos)    
        }
    };

    private static final HashMap<String, Integer> PCXSALONES = new HashMap<String, Integer>() {
        {
            put("REDES", 12);
            put("INFRAESTRUCTURA", 21);     //plataformas
            put("B-0", 24);      //ingenieria IW
            put("C1-205B", 12);
            put("INTERACTIVA", 7);
            put("TURNOS", 29); //BO (turnos)    
        }
    };

    private static final HashMap<Integer, String> GREGORIAN = new HashMap<Integer, String>() {
        {
            put(2, "L");
            put(3, "M");
            put(4, "Mc");
            put(5, "J");
            put(6, "V");
            put(7, "S");
        }
    };

    private static final HashMap<String, Integer> TIME = new HashMap<String, Integer>() {
        {
            put("7:00", 700);
            put("8:30", 830);
            put("10:00", 1000);
            put("11:30", 1130);
            put("1:00", 1300);
            put("2:30", 1430);
            put("4:00", 1600);
            put("5:30", 1730);
        }
    };

    public Analysis() {
        genPDF = new PDFGenerator();
        genCSV = new CSVGenerator();
    }

    public void statisticWeek(int cod) throws FileNotFoundException {
        Conexion reservas = new ReservasConexion();
        try {
            Connection res = reservas.connection();
            String semana = "SELECT semana FROM `semanas` where id=" + cod;
            Statement stmt = res.prepareStatement(semana);
            ResultSet rs = stmt.executeQuery(semana);
            rs.next();
            String consulta = rs.getString(1);
            GregorianCalendar fechaInicioSemana = new GregorianCalendar();
            int year = fechaInicioSemana.get(Calendar.YEAR);
            //Format:   ejm: 17-ene-16-ene
            String[] fechas = consulta.trim().split("-");
            if (fechas.length == 4) {
                fechaInicioSemana = new GregorianCalendar(year, MESES.get(fechas[1]), Integer.parseInt(fechas[0]), 0, 0);
                GregorianCalendar fechaFinSemana = new GregorianCalendar(year, MESES.get(fechas[3]), Integer.parseInt(fechas[2]), 23, 59);
                controlStatistics(fechaInicioSemana, fechaFinSemana);
                Set<String> dias = DIA.keySet();
                Set<String> horario = HORA.keySet();
                for (String dia : dias) {
                    for (String hora : horario) {
                        String reserva = "SELECT * FROM  `reservas` WHERE semana =  '" + consulta + "' AND dia= '" + dia + "' AND hora='" + hora + "'";
                        stmt = res.prepareStatement(reserva);
                        rs = stmt.executeQuery(reserva);
                        while (rs.next()) {
                            String saln = rs.getString("salon");
                            if (SALONES.containsKey(saln)) {
                                int[][] temp = SALONES.get(saln);
                                temp[HORA.get(hora)][DIA.get(dia)] = PCXSALONES.get(saln);
                                SALONES.put(saln, temp);
                            }

                        }
                    }
                }
            }
            res.close();
            genPDF.documento(Integer.toString(cod), consulta, getSoftware(), getB0(), getPlataformas(), getRedes(), getMultimedia(), getInteractiva());
            genCSV.documento(Integer.toString(cod), consulta, getSoftware(), getB0(), getPlataformas(), getRedes(), getMultimedia(), getInteractiva());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
    }

    public void statisticsAll(int ini, int fin, int semanas) throws FileNotFoundException {
        Set<String> salones = CONSOLIDADO.keySet();
        for (int i = ini; i < fin + 1; i++) {
            statisticWeek(i);
            for (String sal : salones) {
                for (int j = 0; j < 8; j++) {
                    for (int h = 0; h < 6; h++) {
                        CONSOLIDADO.get(sal)[j][h] += SALONES.get(sal)[j][h];
                    }
                }
            }
        }
        for (String sal : salones) {
            for (int j = 0; j < 8; j++) {
                for (int h = 0; h < 6; h++) {
                    CONSOLIDADO.get(sal)[j][h] = CONSOLIDADO.get(sal)[j][h] / semanas;
                    CONSOLIDADO.get(sal)[j][6] += CONSOLIDADO.get(sal)[j][h];
                }
            }
            for (int h = 0; h < 6; h++) {
                for (int j = 0; j < 8; j++) {
                    CONSOLIDADO.get(sal)[8][h] += CONSOLIDADO.get(sal)[j][h];
                }
            }
            /**
             * System.out.println(sal+".............."); for (int j=0; j<9;
             * j++){
             * System.out.println(Arrays.toString(CONSOLIDADO.get(sal)[j])); }
             * System.out.println("..............");
             */
        }

    }

    private void controlStatistics(GregorianCalendar fechaInicioSemana, GregorianCalendar fechaFinSemana) {
        Conexion estadisticas = new EstadisticasConexion();
        Connection esta;
        try {
            esta = estadisticas.connection();
            int year = fechaInicioSemana.get(Calendar.YEAR);
            int month = fechaInicioSemana.get(Calendar.MONTH);
            int date = fechaInicioSemana.get(Calendar.DAY_OF_MONTH) - 1;
            for (int i = 2; i < 8; i++) {
                date += 1;
                GregorianCalendar temp = new GregorianCalendar(year, month, date, 0, 0);
                GregorianCalendar temp2 = new GregorianCalendar(year, month, date, 23, 59);
                Timestamp ini = new Timestamp(temp.getTimeInMillis());
                Timestamp fin = new Timestamp(temp2.getTimeInMillis());
                //datos
                //String consulta="SELECT * FROM `datos` WHERE logon BETWEEN '"+ini+"' AND '"+fin+"'";
                //logs
                String consulta = "SELECT * FROM `LOGS` WHERE LOGON BETWEEN '" + ini + "' AND '" + fin + "'";
                Statement stmt = esta.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery(consulta);
                while (rs.next()) {
                    //datos
                    /*String[] eqs = rs.getString("ip").trim().split(Pattern.quote("."));
                    String[] hora = rs.getString("logon").trim().split(" ");
                    String [] hms=hora[1].split(":");
                    int h = Integer.valueOf(hms[0]) * 100 + Integer.valueOf(hms[1]);
                    int ip=Integer.parseInt(eqs[3].trim());*/
                    //LOGS
                    String eq = rs.getString("EQUIPO");
                    String fecha = rs.getString("LOGON");
                    String eqs = eq.toLowerCase().contains("sistema") ? eq.substring(8) : eq.substring(5);
                    String[] hora = rs.getString("logon").trim().split(" ");
                    String[] hms = hora[1].split(":");
                    int h = Integer.valueOf(hms[0]) * 100 + Integer.valueOf(hms[1]);
                    int ip = 0;
                    try {
                        ip = Integer.parseInt(eqs);
                    } catch (Exception e) {
                        time("TURNOS", h, GREGORIAN.get(i));
                    }
                    if (ip > 0 && ip < 25) {
                        //IW
                        time("B-0", h, GREGORIAN.get(i));
                    } else if (ip > 49 && ip < 71) {
                        //plat
                        time("INFRAESTRUCTURA", h, GREGORIAN.get(i));
                    } else if (ip > 100 && ip < 113) {
                        //redes
                        time("REDES", h, GREGORIAN.get(i));
                    } else if (ip > 160 && ip < 173) {
                        //mac
                        time("C1-205B", h, GREGORIAN.get(i));
                    } else if (ip > 150 && ip < 158) {
                        //interactiva
                        time("INTERACTIVA", h, GREGORIAN.get(i));
                    } else {
                        time("TURNOS", h, GREGORIAN.get(i));
                    }
                }
            }
            esta.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
    }

    private boolean isNumeric(String t) {
        try {
            Integer.parseInt(t);
        } catch (NumberFormatException e) {

            return false;
        }
        return true;
    }

    private void time(String salon, int hora, String dia) {
        int[][] temp = SALONES.get(salon);

        if (hora < 830) {
            temp[HORA.get("7:00")][DIA.get(dia)] += 1;
        } else if (hora >= 830 && hora < 1000) {
            temp[HORA.get("8:30")][DIA.get(dia)] += 1;
        } else if (hora >= 1000 && hora < 1130) {
            temp[HORA.get("10:00")][DIA.get(dia)] += 1;
        } else if (hora >= 1130 && hora < 1300) {
            temp[HORA.get("11:30")][DIA.get(dia)] += 1;
        } else if (hora >= 1300 && hora < 1430) {
            temp[HORA.get("1:00")][DIA.get(dia)] += 1;
        } else if (hora >= 1430 && hora < 1600) {
            temp[HORA.get("2:30")][DIA.get(dia)] += 1;
        } else if (hora >= 1600 && hora < 1730) {
            temp[HORA.get("4:00")][DIA.get(dia)] += 1;
        } else if (hora >= 1730 && hora < 1900) {
            temp[HORA.get("5:30")][DIA.get(dia)] += 1;
        } else if (hora > 1900) {
            temp[HORA.get("5:30")][DIA.get(dia)] += 1;
        }
        SALONES.put(salon, temp);
    }
    
    private String getMayorHora(String sala){
        for (int j = 0; j < 8; j++) {
            double max=0.0;
            double ind=0.0;
            if(max<CONSOLIDADO.get(sala)[j][6]){
                max=CONSOLIDADO.get(sala)[j][6];
                ind=j;
            }
        }
        return "";
    }
    
    private String getMayorDia(String sala){
         for (int j = 0; j < 6; j++) {
            double max=0.0;
            double ind=0.0;
            if(max<CONSOLIDADO.get(sala)[8][j]){
                max=CONSOLIDADO.get(sala)[8][j];
                ind=j;
            }
        }
        return "";
    }
    
    public String getMayorHoraB0(){
       return getMayorHora("TURNOS");
    }
    public String getMayorDiaB0(){
        return getMayorDia("TURNOS");
    }
  
    public String getMayorDiaRedes(){
        return getMayorDia("REDES");
    }
    
    public String getMayorHoraRedes(){
       return getMayorHora("REDES");
    }
    
    public String getMayorDiaInteractiva(){
        return getMayorDia("INTERACTIVA");
    }
    
    public String getMayorHoraInteractiva(){
       return getMayorHora("INTERACTIVA");
    }
    
    public String getMayorHoraSoftware(){
       return getMayorHora("B-0");
    }
    
    public String getMayorDiaSoftware(){
        return getMayorDia("B-0");
    }
    
    public String getMayorHoraMultimedia(){
       return getMayorHora("C1-205B");
    }
    public String getMayorDiaMultimedia(){
        return getMayorDia("C1-205B");
    }
    
    public String getMayorHoraPlataformas(){
       return getMayorHora("INFRAESTRUCTURA");
    }
    
    public String getMayorDiaPlataformas(){
        return getMayorDia("INFRAESTRUCTURA");
    }
     
    public int[][] getB0() {
        return SALONES.get("TURNOS");
    }

    public int[][] getPlataformas() {
        return SALONES.get("INFRAESTRUCTURA");
    }

    public int[][] getRedes() {
        return SALONES.get("REDES");
    }

    public int[][] getSoftware() {
        return SALONES.get("B-0");
    }

    public int[][] getInteractiva() {
        return SALONES.get("INTERACTIVA");
    }

    public int[][] getMultimedia() {
        return SALONES.get("C1-205B");
    }

    public double[][] getConsolidadoB0() {
        return CONSOLIDADO.get("TURNOS");
    }

    public double[][] getConsolidadoPlataformas() {
        return CONSOLIDADO.get("INFRAESTRUCTURA");
    }

    public double[][] getConsolidadoRedes() {
        return CONSOLIDADO.get("REDES");
    }

    public double[][] getConsolidadoSoftware() {
        return CONSOLIDADO.get("B-0");
    }

    public double[][] getConsolidadoInteractiva() {
        return CONSOLIDADO.get("INTERACTIVA");
    }

    public double[][] getConsolidadoMultimedia() {
        return CONSOLIDADO.get("C1-205B");
    }
}
