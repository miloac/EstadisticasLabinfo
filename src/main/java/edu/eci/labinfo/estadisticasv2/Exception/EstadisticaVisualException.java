/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.labinfo.estadisticasv2.Exception;

/**
 * 
 * @author Daniela Sepulveda
 * @since March 4
 * @version 2
 */
public class EstadisticaVisualException extends Exception {
    
    public final String GENERAL_ERROR="Ooops! Error inesperado.";
    
    /**
     * Throw the exception with the parameter message
     *@param message
     */
    public EstadisticaVisualException(String message) {
        super(message);
    }
    /** 
     * Throw the exception with the parameter message and the throwable cause
     * @param message
     * @param cause
     */
    public EstadisticaVisualException(String message, Throwable cause) {
        super(message, cause);
    }
    
     /** 
     * Throw the exception with the throwable cause
     * @param cause
     */
    public EstadisticaVisualException(Throwable cause) {
        super(cause);
    }

    /** 
     * Throw the complete exception 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EstadisticaVisualException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
