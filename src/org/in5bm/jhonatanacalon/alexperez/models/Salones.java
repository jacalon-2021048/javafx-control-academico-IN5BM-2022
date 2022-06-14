package org.in5bm.jhonatanacalon.alexperez.models;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 26/04/2022
 * @time 21:04:09
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Salones{
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    
    public Salones(){
        
    }
    
    public Salones(String codigoSalon,int capacidadMaxima){
        this.codigoSalon=codigoSalon;
        this.capacidadMaxima=capacidadMaxima;
    }
    
    public Salones(String codigoSalon,String descripcion,int capacidadMaxima,String edificio,int nivel){
        this.codigoSalon=codigoSalon;
        this.descripcion=descripcion;
        this.capacidadMaxima=capacidadMaxima;
        this.edificio=edificio;
        this.nivel=nivel;
    }
    
    public Salones(String codigoSalon){
        this.codigoSalon=codigoSalon;
    }

    public String getCodigoSalon(){
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon){
        this.codigoSalon=codigoSalon;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public int getCapacidadMaxima(){
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima){
        this.capacidadMaxima=capacidadMaxima;
    }

    public String getEdificio(){
        return edificio;
    }

    public void setEdificio(String edificio){
        this.edificio=edificio;
    }

    public int getNivel(){
        return nivel;
    }

    public void setNivel(int nivel){
        this.nivel=nivel;
    }

    @Override
    public String toString() {
        return codigoSalon + " | " + capacidadMaxima + " | " + edificio + " | " + nivel;
    }
}