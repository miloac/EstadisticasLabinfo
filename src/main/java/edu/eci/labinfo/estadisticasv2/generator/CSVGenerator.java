/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasV2.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author Daniela Sepulveda
 */
public class CSVGenerator extends Generator {

    String salto = ",,,,,,,,,,";
    String semana = ",,LUNES,MARTES,MIÉRCOLES,JUEVES,VIERNES,SÁBADO,,,";
    String[] horario = {"7:00", "8:30", "10:00", "11:30", "1:00", "2:30", "4:00", "5:30"};
    String[] salas = {"Laboratorio de Ingeniería de Software", "Laboratorio de turnos (B0)", "Laboratorio de Plataformas Computacionales", "Laboratorio de Redes de Computadores","Laboratorio de Multimedia y móviles", "Laboratorio de Aula inteligente"};
    File archivo;
    PrintWriter pw;
    
    @Override
    public void documento(String semana, String fecha, int[][] soft, int[][] b0, int[][] plat, int[][] red, int[][] multi, int[][] inte) throws FileNotFoundException {
        String nombre = "Estadisticas-"+semana+"-" +fecha+ ".csv";
        archivo = new File(nombre);
        pw = new PrintWriter(archivo);
        pw.println("Estad\u00edsticas semana: " + semana + " " + fecha + ",,,,,,,,,,");
        pw.println(salto);
        pw.println(semana);
        for (int i = 0; i < soft.length; i++) {
            String linea = ",";
            linea += horario[i] + ",";
            for (int j = 0; j < soft[i].length; j++) {
                int tp = 0;
                tp += soft[i][j];
                tp += b0[i][j];
                tp += plat[i][j];
                tp += red[i][j];
                tp += multi[i][j];
                tp += inte[i][j];
                linea += tp + ",";
            }
            pw.println(linea);
           
        }
        pw.println(salto);
        pw.println(salto);
        imprimirSala(salas[0],soft);
        imprimirSala(salas[1],b0);
        imprimirSala(salas[2],plat);
        imprimirSala(salas[3],red);
        imprimirSala(salas[4],multi);
        imprimirSala(salas[5],inte);
        pw.close();
    }
    /**
     * Imprimir en formato CSV de la sala
     * @param nombre nombre de la sala
     * @param fechai, fecha de incio
     * @param fechaf fecha de fin
     * @param sala 
     */
    private void imprimirSala(String nombre, int[][] sala){
        pw.println(nombre+":,,,,,,,,,,");
        pw.println(salto);
        pw.println(semana);
        for (int i = 0; i < sala.length; i++) {
            String linea = ",";
            linea+= horario[i] + ",";
            for (int j = 0; j < sala[i].length; j++) {
                linea += sala[i][j] + ",";
            }
            pw.println(linea);
        }
        pw.println(salto);
        pw.println(salto);
    }
}
