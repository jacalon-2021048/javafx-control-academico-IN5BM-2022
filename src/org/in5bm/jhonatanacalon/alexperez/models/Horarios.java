package org.in5bm.jhonatanacalon.alexperez.models;

import java.time.LocalTime;
/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 28/04/2022
 * @time 13:48:05
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Horarios{
    private int id;
    private LocalTime horarioInicio;
    private LocalTime horarioFinal;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    
    public Horarios(){
        
    }
    
    public Horarios(int id,LocalTime horarioInicio,LocalTime horarioFinal){
        this.id=id;
        this.horarioInicio=horarioInicio;
        this.horarioFinal=horarioFinal;
    }
    
    public Horarios(int id,LocalTime horarioInicio,LocalTime horarioFinal,boolean lunes,boolean martes,boolean miercoles,boolean jueves,boolean viernes){
        this.id=id;
        this.horarioInicio=horarioInicio;
        this.horarioFinal=horarioFinal;
        this.lunes=lunes;
        this.martes=martes;
        this.miercoles=miercoles;
        this.jueves=jueves;
        this.viernes=viernes;
    }
    
    public Horarios(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public LocalTime getHorarioInicio(){
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio){
        this.horarioInicio=horarioInicio;
    }

    public LocalTime getHorarioFinal(){
        return horarioFinal;
    }

    public void setHorarioFinal(LocalTime horarioFinal){
        this.horarioFinal=horarioFinal;
    }

    public boolean getLunes(){
        return lunes;
    }
    
    public void setLunes(boolean lunes){
        this.lunes=lunes;
    }

    public boolean getMartes(){
        return martes;
    }

    public void setMartes(boolean martes){
        this.martes=martes;
    }

    public boolean getMiercoles(){
        return miercoles;
    }

    public void setMiercoles(boolean miercoles){
        this.miercoles=miercoles;
    }

    public boolean getJueves(){
        return jueves;
    }

    public void setJueves(boolean jueves){
        this.jueves=jueves;
    }

    public boolean getViernes(){
        return viernes;
    }

    public void setViernes(boolean viernes){
        this.viernes=viernes;
    }

    @Override
    public String toString() {
        return "Horarios{" + "id=" + id + ", horarioInicio=" + horarioInicio + ", horarioFinal=" + horarioFinal 
                + ", lunes=" + lunes 
                + ", martes=" + martes 
                + ", miercoles=" + miercoles 
                + ", jueves=" + jueves + ", viernes=" + viernes + '}';
    }       
}