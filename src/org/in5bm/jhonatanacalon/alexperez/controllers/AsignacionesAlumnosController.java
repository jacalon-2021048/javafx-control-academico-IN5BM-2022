package org.in5bm.jhonatanacalon.alexperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.AsignacionesAlumnos;
import org.in5bm.jhonatanacalon.alexperez.models.Alumnos;
import org.in5bm.jhonatanacalon.alexperez.models.Cursos;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 03/05/2022
 * @time 15:17:23
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class AsignacionesAlumnosController implements Initializable{
    private enum Operacion{
        NINGUNO,GUARDAR,ACTUALIZAR
    }
    private Operacion operacion=Operacion.NINGUNO ;
    
    private final String PAQUETE_IMAGES="org/in5bm/jhonatanacalon/alexperez/resources/images/";
    
    private Principal escenarioPrincipal;

    @FXML
    private Button btnAgregar;
    
    @FXML
    private ImageView imgAgregar;
    
    @FXML
    private Button btnCambiar;
    
    @FXML
    private ImageView imgCambiar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private TextField txtId;
  
    @FXML
    private ComboBox<?> cmbAlumnoId;
    
    @FXML
    private ComboBox<?> cmbCurso;
    
    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private TableView<AsignacionesAlumnos> tblAsignaciones;
    
    @FXML
    private TableColumn<AsignacionesAlumnos,Integer> colId;
    
    @FXML
    private TableColumn colIdAlumno;
    
    @FXML
    private TableColumn<AsignacionesAlumnos,Integer> colIdCurso;
    
    @FXML
    private TableColumn<AsignacionesAlumnos,LocalDate> colFechaAsignacion;
    
    @FXML
    private DatePicker dpkFechaAsignacion;
    
    private ObservableList<AsignacionesAlumnos> listaAsignaciones;
    
    private ObservableList<Cursos> listaCursos;
    
    private ObservableList<Alumnos> listaAlumnos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
  
    @FXML
    private void clicRegresar(MouseEvent me){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    private ObservableList getAsignaciones(){
        ArrayList<AsignacionesAlumnos> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
           pstmt=Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignaciones_alumnos_read()}");
           rs=pstmt.executeQuery();
            System.out.println(pstmt.toString());
            while(rs.next()){
                AsignacionesAlumnos asignaciones=new AsignacionesAlumnos();
                asignaciones.setId(rs.getInt(1));
                asignaciones.setAlumnoId(rs.getString(2));
                asignaciones.setCursoId(rs.getInt(3));
                asignaciones.setFechaAsignacion(rs.getDate(4).toLocalDate());
                System.out.println(asignaciones.toString());
                lista.add(asignaciones);
            }          
            listaAsignaciones=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Asignaciones Alumnos");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if((rs != null) && (pstmt!=null)){
                    rs.close();
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return listaAsignaciones;
    }
    
    private void cargarDatos(){
        tblAsignaciones.setItems(getAsignaciones());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdAlumno.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colIdCurso.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        
        cmbAlumnoId.setItems(getAlumnos());
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }

    @FXML
    private void clicAgregar(ActionEvent event) {
    }

    @FXML
    private void clicCambiar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicReporte(ActionEvent event) {
    }
    
    
    private void deshabilitarCampos(){
        txtId.setEditable(false);
        cmbCurso.setEditable(false);
        
    }
    
    private ObservableList getAlumnos(){
        ArrayList<Alumnos> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){                
                Alumnos alumno=new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                lista.add(alumno);
            }
            listaAlumnos=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Alumnos\n");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if((rs != null) && (pstmt!=null)){
                    rs.close();
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
}