package org.in5bm.jhonatanacalon.alexperez.models;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 26/04/2022
 * @time 21:04:24
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class CarrerasTecnicas{
    private String codigoTecnico;
    private String carrera;
    private String grado;
    private char seccion;
    private String jornada;
    
    public CarrerasTecnicas(){
        
    }
    
    public CarrerasTecnicas(String codigoTecnico,String carrera,String grado,char seccion,String jornada){
        this.codigoTecnico=codigoTecnico;
        this.carrera=carrera;
        this.grado=grado;
        this.seccion=seccion;
        this.jornada=jornada;
    }    
    
    public CarrerasTecnicas(String codigoTecnico){
        this.codigoTecnico=codigoTecnico;
    }
    
    public String getCodigoTecnico(){
        return codigoTecnico;
    }
    
    public void setCodigoTecnico(String codigoTecnico){
        this.codigoTecnico=codigoTecnico;
    }
    
    public String getCarrera(){
        return carrera;
    }
    
    public void setCarrera(String carrera){
        this.carrera=carrera;
    }
    
    public String getGrado(){
        return grado;
    }
    
    public void setGrado(String grado){
        this.grado=grado;
    }
    
    public char getSeccion(){
        return seccion;
    }
    
    public void setSeccion(char seccion){
        this.seccion=seccion;
    }
    
    public String getJornada(){
        return jornada;
    }
    
    public void setJornada(String jornada){
        this.jornada=jornada;
    }
    
    @Override
    public String toString() {
        return codigoTecnico + " | " + carrera;
    }    
}