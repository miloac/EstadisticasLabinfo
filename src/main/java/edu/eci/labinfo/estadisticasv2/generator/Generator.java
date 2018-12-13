/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.generator;

import java.io.FileNotFoundException;

/**
 * Gera el documento de las estadisticas
 * @author 
 */
public abstract class Generator {
    /**
     * Archivo para la generacion de las estadisticas
     * @param Semana
     * @param fecha, fec.ha de fin de la semana
     * @param soft , estadisticas de la sala de ingenieria de software
     * @param b0 , estadisticas de la sala de b0 turnos
     * @param plat , estadisticas de la sala de plataformas
     * @param red , estadisticas de la sala de redes
     * @param multi , estadisticas de la sala de multimedia
     * @param inte , estadisticas de la sala interativa
     * @throws java.io.FileNotFoundException
     */
     public abstract void documento(String Semana, String fecha, int [][] soft, int [][] b0, int [][] plat, int [][] red,int [][] multi, int [][]inte) throws FileNotFoundException;
     
}
