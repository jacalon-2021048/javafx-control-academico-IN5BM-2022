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
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.AsignacionesAlumnos;
import org.in5bm.jhonatanacalon.alexperez.models.Alumnos;
import org.in5bm.jhonatanacalon.alexperez.models.Cursos;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private ComboBox<Alumnos> cmbAlumnoId;
    
    @FXML
    private ComboBox<Cursos> cmbCurso;
    
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
    
    @FXML
    private Label lblCurso;
    
    @FXML
    private Label lblCarne;
    
    private ObservableList<AsignacionesAlumnos> listaAsignaciones;
    
    private ObservableList<Cursos> listaCursos;
    
    private ObservableList<Alumnos> listaAlumnos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        deshabilitarCampos();
        cargarDatos();
    }
    
    @FXML
    void clicAgregar(ActionEvent ae) {
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblAsignaciones.setDisable(true);
                limpiarCampos();
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image(PAQUETE_IMAGES+"guardar.png"));
                btnCambiar.setText("Cancelar");
                imgCambiar.setImage(new Image(PAQUETE_IMAGES+"cancelar.png"));
                btnEliminar.setDisable(true);
                btnReporte.setDisable(true);
                operacion=Operacion.GUARDAR;
                break;
                
            case GUARDAR:
                if(evaluacionCamposVacios()){
                    if (agregarAsignacion()){
                        limpiarCampos();
                        limpiarLabel();
                        deshabilitarCampos();
                        cargarDatos();
                        tblAsignaciones.setDisable(false);
                        btnAgregar.setText("Agregar");
                        imgAgregar.setImage(new Image(PAQUETE_IMAGES+"agregar.png"));
                        btnCambiar.setText("Cambiar");
                        imgCambiar.setImage(new Image(PAQUETE_IMAGES+ "modificar.png"));
                        btnEliminar.setDisable(false);
                        btnReporte.setDisable(false);
                        operacion=Operacion.NINGUNO;
                    }
                }else{
                    camposObligatorios();
                }                
                break;
        }
    }

    @FXML
    void clicCambiar(ActionEvent ae) {
        switch(operacion){
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    habilitarCampos();
                    btnCambiar.setText("Guardar");
                    imgCambiar.setImage(new Image(PAQUETE_IMAGES+"guardar.png"));
                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES+"cancelar.png"));
                    btnAgregar.setDisable(true);
                    btnReporte.setDisable(true);
                    operacion=Operacion.ACTUALIZAR;
                }else{
                    alertasWarning("Antes de continuar seleccione un registro");
                }
                break;
                
            case GUARDAR:
                btnAgregar.setText("Agregar");
                imgAgregar.setImage(new Image(PAQUETE_IMAGES+"agregar.png"));
                btnCambiar.setText("Cambiar");
                imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);
                tblAsignaciones.setDisable(false);
                limpiarLabel();
                limpiarCampos();
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
            
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(actualizarAsignacion()){
                            limpiarCampos();
                            limpiarLabel();
                            cargarDatos();
                            deshabilitarCampos();
                            tblAsignaciones.setDisable(false);
                            tblAsignaciones.getSelectionModel().clearSelection();
                            btnCambiar.setText("Cambiar");
                            imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                            btnEliminar.setText("Eliminar");
                            imgEliminar.setImage(new Image(PAQUETE_IMAGES+"eliminar.png"));
                            btnAgregar.setDisable(false);
                            btnReporte.setDisable(false);
                            operacion=Operacion.NINGUNO;
                        }
                    }else{
                        camposObligatorios();
                    }
                }
                break;       
        }
    }

    @FXML
    void clicEliminar(ActionEvent ae) {
        switch(operacion){
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Control Académico - Confirmación");
                    alerta.setHeaderText(null);
                    alerta.setContentText("¿Desea eliminar este registro?");
                    Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
                    Image icon=new Image(PAQUETE_IMAGES+"icono.png");
                    stage.getIcons().add(icon);
                    Optional<ButtonType> result=alerta.showAndWait();
                    if(result.get().equals(ButtonType.OK)){
                        if(eliminarAsignacion()){
                            limpiarCampos();
                            cargarDatos();
                            Alert info=new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Control Académico - AVISO!!!");
                            info.setHeaderText(null);
                            info.setContentText("El registro se ha eliminado correctamente");
                            info.show();
                            Stage stag=(Stage) info.getDialogPane().getScene().getWindow();
                            Image ico=new Image(PAQUETE_IMAGES+"icono.png");
                            stag.getIcons().add(ico);
                        }
                    }else if(result.get().equals(ButtonType.CANCEL)){
                        alerta.close();
                        tblAsignaciones.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                }else{
                    alertasWarning("Antes de continuar selecciona un registro");
                }
                break;
                
            case ACTUALIZAR:
                btnCambiar.setText("Cambiar");
                imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES+"eliminar.png"));
                btnAgregar.setDisable(false);
                btnReporte.setDisable(false);
                limpiarCampos();
                limpiarLabel();
                deshabilitarCampos();
                tblAsignaciones.getSelectionModel().clearSelection();
                operacion=Operacion.NINGUNO;
                break;
        }
    }

    @FXML
    void clicReporte(ActionEvent ae) {
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Control Academico - AVISO!!!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion solo esta disponible en la version premium");
        alerta.show();
        Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
        Image ico=new Image(PAQUETE_IMAGES+"icono.png");
        stage.getIcons().add(ico);
    }

    @FXML
    void clicRegresar(MouseEvent me){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    @FXML
    void seleccionarElemento(){
        if(existeElementoSeleccionado()){
            txtId.setText(String.valueOf(((AsignacionesAlumnos)tblAsignaciones.getSelectionModel().getSelectedItem())
                    .getId()));
            cmbAlumnoId.getSelectionModel().select(buscarAlumno(((AsignacionesAlumnos)tblAsignaciones.getSelectionModel()
                    .getSelectedItem()).getAlumnoId()));
            cmbCurso.getSelectionModel().select(buscarCurso(((AsignacionesAlumnos)tblAsignaciones.getSelectionModel()
                    .getSelectedItem()).getCursoId()));
            dpkFechaAsignacion.setValue(((AsignacionesAlumnos)tblAsignaciones.getSelectionModel()
                    .getSelectedItem()).getFechaAsignacion().toLocalDate());
        }
    }
    
    private boolean agregarAsignacion(){
        AsignacionesAlumnos asignaciones=new AsignacionesAlumnos();
        asignaciones.setAlumnoId(((Alumnos)cmbAlumnoId.getSelectionModel()
                .getSelectedItem()).getCarne());
        asignaciones.setCursoId(((Cursos)cmbCurso.getSelectionModel()
                .getSelectedItem()).getId());
        asignaciones.setFechaAsignacion(evaluacionfecha(dpkFechaAsignacion.getValue()));
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_create(?,?,?)}");
            pstmt.setString(1,asignaciones.getAlumnoId());
            pstmt.setInt(2,asignaciones.getCursoId());
            pstmt.setTimestamp(3,Timestamp.valueOf(asignaciones.getFechaAsignacion()));
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAsignaciones.add(asignaciones);
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al insertar el siguiente registro:\n " + asignaciones.toString());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt!=null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean actualizarAsignacion(){        
        AsignacionesAlumnos asignaciones=new AsignacionesAlumnos();
        asignaciones.setId(Integer.parseInt(txtId.getText()));
        asignaciones.setAlumnoId(((Alumnos)cmbAlumnoId.getSelectionModel()
                .getSelectedItem()).getCarne());
        asignaciones.setCursoId(((Cursos)cmbCurso.getSelectionModel()
                .getSelectedItem()).getId());
        asignaciones.setFechaAsignacion(dpkFechaAsignacion.getValue().atStartOfDay());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_update(?,?,?,?)}");
            pstmt.setInt(1,asignaciones.getId());
            pstmt.setString(2,asignaciones.getAlumnoId());
            pstmt.setInt(3,asignaciones.getCursoId());
            pstmt.setTimestamp(4,Timestamp.valueOf(asignaciones.getFechaAsignacion()));
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al insertar el siguiente registro: \n" + asignaciones.toString());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt!=null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean eliminarAsignacion(){
        AsignacionesAlumnos asignaciones=(AsignacionesAlumnos)tblAsignaciones.getSelectionModel().getSelectedItem();
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_delete(?)}");
            pstmt.setInt(1,asignaciones.getId());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAsignaciones.remove(tblAsignaciones.getSelectionModel().getFocusedIndex());
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al eliminar el siguiente registro: " + asignaciones.toString());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt!=null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    private LocalDateTime evaluacionfecha(LocalDate ldt){
        if(dpkFechaAsignacion.getValue()==null){
            return LocalDateTime.now();
        }
        return ldt.atStartOfDay();
    }
    
    private boolean existeElementoSeleccionado(){
        return (tblAsignaciones.getSelectionModel().getSelectedItem()!=null);
    }
    
    private ObservableList getAsignaciones(){
        ArrayList<AsignacionesAlumnos> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_read()}");
            rs=pstmt.executeQuery();
            System.out.println(pstmt.toString());
            while(rs.next()){
                AsignacionesAlumnos asignaciones=new AsignacionesAlumnos();
                asignaciones.setId(rs.getInt(1));
                asignaciones.setAlumnoId(rs.getString(2));
                asignaciones.setCursoId(rs.getInt(3));
                asignaciones.setFechaAsignacion(rs.getTimestamp(4).toLocalDateTime());
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
    
    private Cursos buscarCurso(int id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Cursos cursos=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");
            pstmt.setInt(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                cursos=new Cursos();
                cursos.setId(rs.getInt(1));
                cursos.setNombreCurso(rs.getString(2));
                cursos.setCiclo(rs.getInt(3));
                cursos.setCupoMaximo(rs.getInt(4));
                cursos.setCupoMinimo(rs.getInt(5));
                cursos.setCarreraTecnicaId(rs.getString(6));                
                cursos.setHorarioId(rs.getInt(7));
                cursos.setInstructorId(rs.getInt(8));
                cursos.setSalonId(rs.getString(9));
            }
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar el Curso de ID:" + cursos.getCarreraTecnicaId());
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
        return cursos;
    }
    
    private Alumnos buscarAlumno(String id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Alumnos alumnos=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read_by_id(?)}");
            pstmt.setString(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                alumnos=new Alumnos();
                alumnos.setCarne(rs.getString(1));
                alumnos.setNombre1(rs.getString(2));
                alumnos.setNombre2(rs.getString(3));
                alumnos.setNombre3(rs.getString(4));
                alumnos.setApellido1(rs.getString(5));
                alumnos.setApellido2(rs.getString(6));                
            }            
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar al Alumno de carne:" + alumnos.getCarne());
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
        return alumnos;
    }
    
    private void alertasWarning(String mensaje){
        Alert alerta=new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Control Académico - AVISO!!!");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
        Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
        Image ico=new Image(PAQUETE_IMAGES+"icono.png");
        stage.getIcons().add(ico);
    }

    private void cargarDatos(){
        tblAsignaciones.setItems(getAsignaciones());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdAlumno.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colIdCurso.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        
        cmbAlumnoId.setItems(getAlumnos());
        cmbCurso.setItems(getCursos());
    }
    
    private void habilitarCampos(){
        txtId.setEditable(false);
        cmbCurso.setEditable(false);
        cmbAlumnoId.setEditable(false);
        dpkFechaAsignacion.setEditable(false);
        
        txtId.setDisable(true);
        cmbCurso.setDisable(false);
        cmbAlumnoId.setDisable(false);
        dpkFechaAsignacion.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        txtId.setEditable(false);
        cmbCurso.setEditable(false);
        cmbAlumnoId.setEditable(false);
        dpkFechaAsignacion.setEditable(false);
        
        txtId.setDisable(true);
        cmbCurso.setDisable(true);
        cmbAlumnoId.setDisable(true);
        dpkFechaAsignacion.setDisable(true);
    }
    
    private void limpiarCampos(){
        txtId.clear();
        cmbCurso.valueProperty().set(null);
        cmbAlumnoId.valueProperty().set(null);
        dpkFechaAsignacion.getEditor().clear();
    }
    
    private void limpiarLabel(){
        lblCarne.setText("");
        lblCurso.setText("");
    }
    
    private boolean evaluacionCamposVacios(){        
        return (!(cmbAlumnoId.getValue()==null)) && (!(cmbCurso.getValue()==null));
    }
    
    private void camposObligatorios(){
        if(cmbCurso.getValue()==null){
            lblCurso.setText("Campo obligatorio");
        }
        if(cmbAlumnoId.getValue()==null){
            lblCarne.setText("Campo obligatorio");
        }
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
    
    private ObservableList getCursos(){
        ArrayList<Cursos> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
           pstmt=Conexion.getInstance().getConexion().prepareCall("CALL sp_cursos_read()");
           rs=pstmt.executeQuery();
            while(rs.next()){
                Cursos cursos=new Cursos();
                cursos.setId(rs.getInt(1));
                cursos.setNombreCurso(rs.getString(2));
                cursos.setCiclo(rs.getInt(3));
                cursos.setCupoMaximo(rs.getInt(4));
                cursos.setCupoMinimo(rs.getInt(5));
                cursos.setCarreraTecnicaId(rs.getString(6));                
                cursos.setHorarioId(rs.getInt(7));
                cursos.setInstructorId(rs.getInt(8));
                cursos.setSalonId(rs.getString(9));
                System.out.println(cursos.toString());
                lista.add(cursos);
            }
            listaCursos=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Cursos");
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
        return listaCursos;
    }

    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}