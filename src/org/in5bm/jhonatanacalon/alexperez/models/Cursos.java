package org.in5bm.jhonatanacalon.alexperez.models;

import java.sql.Date;
/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 28/04/2022
 * @time 13:48:14
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Cursos{
    private int id;
    private String nombreCurso;
    private Date ciclo;
    private int cupoMaximo;
    private int cupoMinimo;
    private String carreraTecnicaId;
    private int horarioId;
    private int instructorId;
    private String salonId;

    public Cursos(){
        
    }

    public Cursos(int id,String nombreCurso,String carreraTecnicaId,int horarioId,int instructorId,String salonId){
        this.id=id;
        this.nombreCurso=nombreCurso;
        this.carreraTecnicaId=carreraTecnicaId;
        this.horarioId=horarioId;
        this.instructorId=instructorId;
        this.salonId=salonId;
    }

    public Cursos(int id,String nombreCurso,Date ciclo,int cupoMaximo,int cupoMinimo,String carreraTecnicaId,int horarioId,int instructorId,String salonId){
        this.id=id;
        this.nombreCurso=nombreCurso;
        this.ciclo=ciclo;
        this.cupoMaximo=cupoMaximo;
        this.cupoMinimo=cupoMinimo;
        this.carreraTecnicaId=carreraTecnicaId;
        this.horarioId=horarioId;
        this.instructorId=instructorId;
        this.salonId=salonId;
    }
    
    public Cursos(int id){
        this.id=id;
    }

    @Override
    public String toString(){
        return "Cursos{" + "id=" + id + ", nombreCurso=" 
                + nombreCurso + ", ciclo=" + ciclo 
                + ", cupoMaximo=" + cupoMaximo 
                + ", cupoMinimo=" + cupoMinimo 
                + ", carreraTecnicaId=" + carreraTecnicaId 
                + ", horarioId=" + horarioId + ", instructorId=" 
                + instructorId + ", salonId=" + salonId + '}';
    }    
}