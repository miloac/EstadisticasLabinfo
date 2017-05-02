/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasv2.conexion;

import edu.eci.labinfo.estadisticasv2.logs.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class ReservasConexion extends Conexion {
    
    public ReservasConexion(){
        super();
    }
    
    @Override
    public Connection connection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Properties conf=new Properties();
        String userName="";
        String password="";
        String url="";
        InputStream input;
        try {       
            input=new FileInputStream(new File(System.getProperty("user.dir"),"applicationconfigreservas.properties"));
            conf.load(input);
            userName=conf.getProperty("user");
            password=conf.getProperty("pwd");
            url= conf.getProperty("url");
        } catch (FileNotFoundException ex) {
            System.out.println("properties file not found");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
            
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            Log.anotar(ex);
        }
        
        Connection conn = null;
        //**********conexion con laboratorio**********	
        //direccion de la base de datos
        DriverManager.setLoginTimeout(40);
        conn = DriverManager.getConnection (url, userName, password);    
        
        return conn;
    }

    @Override
    public void close() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        conexion.close();
    }
}
