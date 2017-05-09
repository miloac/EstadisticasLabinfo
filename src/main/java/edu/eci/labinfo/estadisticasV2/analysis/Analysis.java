/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.analysis;

import edu.eci.labinfo.estadisticasv2.conexion.Conexion;
import edu.eci.labinfo.estadisticasv2.conexion.EstadisticasConexion;
import edu.eci.labinfo.estadisticasv2.conexion.ReservasConexion;
import edu.eci.labinfo.estadisticasv2.logs.Log;
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
    private static final HashMap<String,Integer> DIA = new HashMap<String,Integer>() {{     
       put("L", 0);
       put("M", 1);
       put("Mc",2); 
       put("J", 3); 
       put("V", 4);
       put("S", 5);  
    }};
    
    private static final HashMap<String,Integer> HORA = new HashMap<String,Integer>() {{     
       put("7:00", 0);
       put("8:30", 1);
       put("10:00", 2); 
       put("11:30", 3); 
       put("1:00", 4);
       put("2:30", 5); 
       put("4:00", 6); 
       put("5:30", 7);  
    }};
     
    private static final HashMap<String,Integer> MESES = new HashMap<String,Integer>() {{     
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
    }};
    private static final HashMap<String,int[][]> SALONES = new HashMap<String,int[][]>() {{     
       put("REDES", new int[8][6]);     
       put("INFRAESTRUCTURA", new int[8][6]);     //plataformas
       put("B-0", new int[8][6]);      //ingenieria IW
       put("C1-205B", new int[8][6]);     
       put("INTERACTIVA", new int[8][6]);
       put("TURNOS", new int[8][6]); //BO (turnos)    
    }};
    
    private static final HashMap<String,Integer> PCXSALONES = new HashMap<String,Integer>() {{     
       put("REDES", 12);     
       put("INFRAESTRUCTURA",21 );     //plataformas
       put("B-0",24 );      //ingenieria IW
       put("C1-205B", 12);     
       put("INTERACTIVA",7 );
       put("TURNOS", 25); //BO (turnos)    
    }};
    
    public Analysis(){
        
    }
    
    public void statisticWeek(int cod){
       Conexion reservas=new ReservasConexion();       
        try {
            Connection res=reservas.connection();
            String semana="SELECT semana FROM `semanas` where id="+cod;
            Statement stmt=res.prepareStatement(semana);
            ResultSet rs = stmt.executeQuery(semana);
            rs.next();
            String consulta=rs.getString(1);
            GregorianCalendar fechaInicioSemana=new GregorianCalendar();
            int year=fechaInicioSemana.get(Calendar.YEAR);
            //Format:   ejm: 17-ene-16-ene
            String [] fechas=consulta.trim().split("-");
            if(fechas.length==4){
                fechaInicioSemana=new GregorianCalendar(year,MESES.get(fechas[1]),Integer.parseInt(fechas[0]),0,0);
                GregorianCalendar fechaFinSemana=new GregorianCalendar(year,MESES.get(fechas[3]),Integer.parseInt(fechas[2]),23,59);
                controlStatistics(fechaInicioSemana, fechaFinSemana);
                String reserva="SELECT * FROM  `reservas` WHERE semana =  '"+consulta+"'";
                stmt=res.prepareStatement(reserva);
                rs = stmt.executeQuery(semana);
                Set<String> dias=DIA.keySet();
                Set<String> horario=HORA.keySet();
                while(rs.next()){
                   for (String dia: dias) {
                       for(String hora: horario){
                           String consuldia=rs.getString(dia);
                           String consulhora=rs.getString(hora);
                          if(consuldia.equals(dia) && consulhora.equals(hora)){
                              String saln=rs.getString("salon");
                              if(SALONES.containsKey(saln)){
                                    int[][] temp=SALONES.get(saln);
                                    temp[HORA.get(consulhora)][DIA.get(consuldia)]=PCXSALONES.get(saln);
                                    SALONES.put(saln, temp);
                               }
                          }
                       }
                    }
                }
            }
            res.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }   
    }
    
     public void statistics(int ini, int fin){
         for (int i = ini; i < fin+1; i++) {
             statisticWeek(i);
         }       
    }
     
    private void controlStatistics(GregorianCalendar fechaInicioSemana,GregorianCalendar fechaFinSemana){
        Conexion estadisticas=new EstadisticasConexion();
        Connection esta;
        try {
            esta = estadisticas.connection();
            Timestamp ini =new Timestamp(fechaInicioSemana.getTimeInMillis()) ;
            Timestamp fin= new Timestamp(fechaFinSemana.getTimeInMillis());
            String consulta="SELECT * FROM `datos` WHERE logon BETWEEN '"+ini+"' AND '"+fin+"'";
            Statement stmt=esta.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery(consulta);
            while(rs.next()){
                String[] eqs = rs.getString("ip").trim().split(".");
                String[] hora = rs.getString("logon").trim().split(" ");
                System.out.println(eqs[3]);
                System.out.println(Arrays.toString(hora));
                String[] hms=hora[1].split(":");
                             
            }
            esta.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
    }
    
    private boolean isNumeric(String t){
        try{
            Integer.parseInt(t);
        }catch(NumberFormatException e){
            
            return false;
        }
        return true;
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

    private void time(String salon, String[] hms) {
        int[][]temp=SALONES.get(salon);
        Set<String> horario=HORA.keySet();
        int h = Integer.valueOf(hms[0]) * 100 + Integer.valueOf(hms[1]);
       
    }
}
