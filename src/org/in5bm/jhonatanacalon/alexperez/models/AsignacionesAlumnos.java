package org.in5bm.jhonatanacalon.alexperez.models;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 28/04/2022
 * @time 13:48:28
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class AsignacionesAlumnos{
    private IntegerProperty id;    
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDate> fechaAsignacion;

    public AsignacionesAlumnos(){
        this.id=new SimpleIntegerProperty();
        this.alumnoId=new SimpleStringProperty();
        this.cursoId=new SimpleIntegerProperty();
        this.fechaAsignacion=new SimpleObjectProperty<>();        
    }
    
    public AsignacionesAlumnos(int id,String alumnoId,int cursoId,LocalDate fechaAsignacion){
        this.id=new SimpleIntegerProperty(id);
        this.alumnoId=new SimpleStringProperty(alumnoId);
        this.cursoId=new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion=new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public IntegerProperty id(){
        return this.id;
    }
    
    public int getId(){
        return this.id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public StringProperty alumnoId(){
        return this.alumnoId;
    }
    
    public String getAlumnoId(){
        return this.alumnoId.get();
    }
    
    public void setAlumnoId(String alumnoId){
        this.alumnoId.set(alumnoId);        
    }
    
    public IntegerProperty cursoId(){
        return this.cursoId;
    }
    
    public int getCursoId(){
        return this.cursoId.get();
    }
    
    public void setCursoId(int cursoId){
        this.cursoId.set(cursoId);
    }
    
    public ObjectProperty<LocalDate> fechaAsignacion(){
        return this.fechaAsignacion;
    }
    
    public LocalDate getFechaAsignacion(){
        return fechaAsignacion.get();
    }
    
    public void setFechaAsignacion(LocalDate fechaAsignacion){
        this.fechaAsignacion.set(fechaAsignacion);
    }
}