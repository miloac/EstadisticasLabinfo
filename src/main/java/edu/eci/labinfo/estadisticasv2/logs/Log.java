/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasv2.logs;

/**
 * Logs
 * 
 * @author Daniela Sepulveda
 * @since March 4
 * @version 2
 */
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

public class Log {
   
        /**
         * nombre del archivo logs
         */
        public static String nombre = "estadisticasvisual";

    /**
     * Creacion del archivo .log
     * @param e
     */
    public static void anotar(Exception e) {
        try {
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler(nombre + ".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE, e.toString(), e);
            file.close();
        } catch (IOException | SecurityException oe) {
            oe.printStackTrace();
        }
    }
}
