/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

/**
 *
 * @author Daniela Sepulveda Alzate
 */
public class Analysis {
        
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
    
    public void revisarSemana(){
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
