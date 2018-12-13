/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.conexion;

import edu.eci.labinfo.estadisticasV2.logs.*;
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
public class EstadisticasConexion extends Conexion{
    
    public EstadisticasConexion(){
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
            input=new FileInputStream(new File(System.getProperty("user.dir"),"applicationconfig.properties"));
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
        
        conexion = null;
        //**********conexion con laboratorio**********	
        //direccion de la base de datos
        DriverManager.setLoginTimeout(40);
        conexion = DriverManager.getConnection (url, userName, password);    
        
        return conexion;
    }

    @Override
    public void close() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        conexion.close();
    }
}
