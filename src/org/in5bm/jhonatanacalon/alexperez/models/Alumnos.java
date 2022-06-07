package org.in5bm.jhonatanacalon.alexperez.models;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 26/04/2022
 * @time 17:49:24
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Alumnos{
    private String carne;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;

    public Alumnos(){
        
    }
    
    public Alumnos(String carne,String nombre1,String apellido1){
        this.carne=carne;
        this.nombre1=nombre1;
        this.apellido1=apellido1;
    }
    
    public Alumnos(String carne,String nombre1,String nombre2,String nombre3,String apellido1,String apellido2){
        this.carne=carne;
        this.nombre1=nombre1;
        this.nombre2=nombre2;
        this.nombre3=nombre3;
        this.apellido1=apellido1;
        this.apellido2=apellido2;
    }
    
    public Alumnos(String carne){
        this.carne=carne;
    }
    
    public String getCarne(){
        return carne;
    }
    
    public void setCarne(String carne){
        this.carne=carne;
    }
    
    public String getNombre1(){
        return nombre1;
    }
    
    public void setNombre1(String nombre1){
        this.nombre1=nombre1;
    }
    
    public String getNombre2(){
        return nombre2;
    }
    
    public void setNombre2(String nombre2){
        this.nombre2=nombre2;
    }
    
    public String getNombre3(){
        return nombre3;
    }
    
    public void setNombre3(String nombre3){
        this.nombre3=nombre3;
    }
    
    public String getApellido1(){
        return apellido1;
    }
    
    public void setApellido1(String apellido1){
        this.apellido1=apellido1;
    }
    
    public String getApellido2(){
        return apellido2;
    }
    
    public void setApellido2(String apellido2){
        this.apellido2=apellido2;
    }

    @Override
    public String toString() {
        return "Alumnos{" + "carne=" + carne + ", nombre1=" + nombre1 + ", nombre2=" 
                + nombre2 + ", nombre3=" 
                + nombre3 + ", apellido1=" 
                + apellido1 + ", apellido2=" 
                + apellido2 + '}';
    }
}