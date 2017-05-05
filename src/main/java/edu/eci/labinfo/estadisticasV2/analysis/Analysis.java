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
    private static final HashMap<String,Integer> meses = new HashMap<String,Integer>() {{     
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
    //turnos
    private int[][] b0;
    //plataformas
    private int[][] plat;
    //redes
    private int[][] red;
    //ingenieria software
    private int[][] soft;
    //interactiva
    private int[][] inte;
    //multimedia
    private int[][] multi;
    
    public Analysis(){
       b0 = new int[8][6];
       plat = new int[8][6];
       red = new int[8][6];
       soft = new int[8][6];
       inte = new int[8][6];
       multi = new int[8][6];
    }
    
    public void statisticWeek(int cod){
       Conexion reservas=new ReservasConexion();
       Conexion estadisticas=new EstadisticasConexion();
        try {
            Connection res=reservas.connection();
            String semana="SELECT semana FROM `semanas` where id="+cod;
            Statement stmt=res.prepareStatement(semana);
            ResultSet rs = stmt.executeQuery(semana);
            rs.next();
            String consulta=rs.getString(1);
            try{
                GregorianCalendar fechaInicioSemana=new GregorianCalendar();
                int year=fechaInicioSemana.get(Calendar.YEAR);
                String [] fechas=consulta.trim().split("-");
                fechaInicioSemana=new GregorianCalendar(year,meses.get(fechas[1]),Integer.parseInt(fechas[0]),0,0);
                GregorianCalendar fechaFinSemana=new GregorianCalendar(year,meses.get(fechas[3]),Integer.parseInt(fechas[2]),23,59);
                Timestamp ini =new Timestamp(fechaInicioSemana.getTimeInMillis()) ;
                Timestamp fin= new Timestamp(fechaFinSemana.getTimeInMillis());  
                Connection esta=estadisticas.connection();
                Statement stmtk=esta.createStatement();
            }catch(Exception e){
                
            } 
            
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
       
        
    }
    
     public void statistic(int ini, int fin){
        
        
    }
    

    public int[][] getB0() {
        return b0;
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
    }
    
   
    
}
