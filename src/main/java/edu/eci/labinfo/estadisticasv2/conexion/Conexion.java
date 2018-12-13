/*
 * Decompiled with CFR 0_115.
 */
package edu.eci.labinfo.estadisticasV2.conexion;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Conexion {
    
    protected Connection conexion;
    
    public Conexion(){
    }
    
    /**
     * Conexion base de datos 
     * @return
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public abstract Connection connection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
    
    /**
     * Cerrar conexion base de datos 
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public abstract void close() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}

