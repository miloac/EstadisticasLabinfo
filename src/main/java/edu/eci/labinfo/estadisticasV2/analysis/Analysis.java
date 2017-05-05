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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class Analysis {
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
                Timestamp ini =new Timestamp(fechaInicioSemana.getTimeInMillis()) ;
                Timestamp fin= new Timestamp(fechaFinSemana.getTimeInMillis());
                controlStatistics(ini,fin);
                String reserva="SELECT * FROM  `reservas` WHERE semana =  '"+consulta+"'";
                stmt=res.prepareStatement(reserva);
                rs = stmt.executeQuery(semana);                
                while(rs.next()){
                   for (int i = 1; i < 7; i++) {
                       
                    
                    
                    }
                }
                
            }

            
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
     
    private void controlStatistics(Timestamp ini,Timestamp fin){
        Conexion estadisticas=new EstadisticasConexion();
        Connection esta;
        try {
            esta = estadisticas.connection();
            Statement stmtk=esta.createStatement();
            String consulta="SELECT * FROM `datos` "
                    + "WHERE logon BETWEEN '2017-04-29 09:11:56' AND '2017-05-29 09:11:56'"
                    + "AND logoff BETWEEN '2017-04-29 09:11:56' AND '2017-05-29 09:11:56'";
            
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
        
    }
    

    /*public int[][] getB0() {
        return salones.get("B-0");
    }

    public int[][] getPlat() {
        return plat;
    }

    public int[][] getRed() {
        return red;
    }

    public int[][] getSoft() {
        return soft;
    }

    public int[][] getInte() {
        return inte;
    }

    public int[][] getMulti() {
        return multi;
    }*/
    
   
    
}
