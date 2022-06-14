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
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Cursos;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.jhonatanacalon.alexperez.models.CarrerasTecnicas;
import org.in5bm.jhonatanacalon.alexperez.models.Horarios;
import org.in5bm.jhonatanacalon.alexperez.models.Instructores;
import org.in5bm.jhonatanacalon.alexperez.models.Salones;

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
public class CursosController implements Initializable{
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private Operacion operacion=Operacion.NINGUNO;
    
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
    private TableView<Cursos> tblCursos;
    
    @FXML
    private TableColumn<Cursos, Integer> colId;
    
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    
    @FXML
    private TableColumn<Cursos, Integer> colMaximo;
    
    @FXML
    private TableColumn<Cursos, Integer> colMinimo;
    
    @FXML
    private TableColumn<Cursos, String> colCodigoTecnico;
    
    @FXML
    private TableColumn<Cursos, Integer> colHorario;
    
    @FXML
    private TableColumn<Cursos, Integer> colInstructor;
    
    @FXML
    private TableColumn<Cursos, String> colSalon;

    @FXML
    private TextField txtId;
    
    @FXML
    private TextField txtNombreCurso;

    @FXML
    private Spinner<Integer> spnCiclo;

    private SpinnerValueFactory<Integer> valueFactoryCiclo;

    @FXML
    private Spinner<Integer> spnCupoMaximo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;

    @FXML
    private Spinner<Integer> spnCupoMinimo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;

    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    
    @FXML
    private ComboBox<Horarios> cmbHorario;
    
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    
    @FXML
    private ComboBox<Salones> cmbSalon;
    
    @FXML
    private Label lblNombreCurso;
    
    @FXML
    private Label lblCarreraTecnica;
    
    @FXML
    private Label lblHorario;
    
    @FXML
    private Label lblInstructor;
    
    @FXML
    private Label lblSalon;
    
    @FXML
    private ImageView imgRegresar;
    
    private ObservableList<Cursos> listaCursos;
    
    private ObservableList<Instructores> listaInstructores;
    
    private ObservableList<Salones> listaSalones;
    
    private ObservableList<CarrerasTecnicas> listaCarreras;
    
    private ObservableList<Horarios> listaHorarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);

        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);

        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);
        deshabilitarCampos();
        cargarDatos();
    }    

    @FXML
    void clicAgregar(ActionEvent ae){
        switch (operacion) {
            case NINGUNO:                
                habilitarCampos();
                tblCursos.setDisable(true);
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
                    if(evaluacionCantCar()){
                        if(agregarCursos()){
                            limpiarCampos();
                            limpiarLabel();
                            deshabilitarCampos();
                            cargarDatos();
                            tblCursos.setDisable(false);
                            btnAgregar.setText("Agregar");
                            imgAgregar.setImage(new Image(PAQUETE_IMAGES + "agregar.png"));
                            btnCambiar.setText("Cambiar");
                            imgCambiar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));
                            btnEliminar.setDisable(false);
                            btnReporte.setDisable(false);
                            operacion = Operacion.NINGUNO;
                        }
                    } else {
                        evaluacionDigitos();
                    }
                } else {
                    camposObligatorios();
                }
                break;
        }
    }

    @FXML
    void clicCambiar(ActionEvent ae){
        switch (operacion) {
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
                    alertasWarning("Antes de continuar selecciona un registro");
                }
                break;
                
            case GUARDAR:
                btnAgregar.setText("Agregar");
                imgAgregar.setImage(new Image(PAQUETE_IMAGES+"agregar.png"));
                btnCambiar.setText("Cambiar");
                imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);
                tblCursos.setDisable(false);
                limpiarLabel();
                limpiarCampos();
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
                
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(actualizarCursos()){
                            limpiarCampos();
                            limpiarLabel();
                            cargarDatos();
                            deshabilitarCampos();
                            tblCursos.setDisable(false);
                            tblCursos.getSelectionModel().clearSelection();
                            btnCambiar.setText("Cambiar");
                            imgCambiar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));
                            btnEliminar.setText("Eliminar");
                            imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
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
    void clicEliminar(ActionEvent ae){
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Control Académico - Confirmación");
                    alerta.setHeaderText(null);
                    alerta.setContentText("¿Desea eliminar este registro?");
                    Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
                    Image icon=new Image(PAQUETE_IMAGES+"icono.png");
                    stage.getIcons().add(icon);
                    Optional<ButtonType> result=alerta.showAndWait();
                    if(result.get().equals(ButtonType.OK)){
                        if(eliminarCursos()){
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
                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alerta.close();
                        tblCursos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    alertasWarning("Antes de continuar, seleccione un registro");
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
                tblCursos.getSelectionModel().clearSelection();
                operacion=Operacion.NINGUNO;
                break;
        }
    }

    @FXML
    void clicReporte(ActionEvent ae){
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
            txtId.setText(String.valueOf(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getId()));
            txtNombreCurso.setText(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getNombreCurso());
            spnCiclo.getValueFactory().setValue(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getCiclo());
            spnCupoMaximo.getValueFactory().setValue(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getCupoMaximo());
            spnCupoMinimo.getValueFactory().setValue(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getCupoMinimo());
            cmbCarreraTecnica.getSelectionModel().select(buscarCarrera(((Cursos)tblCursos.getSelectionModel()
                    .getSelectedItem()).getCarreraTecnicaId()));
            cmbHorario.getSelectionModel().select(buscarHorarios(((Cursos)tblCursos.getSelectionModel()
                    .getSelectedItem()).getHorarioId()));
            cmbInstructor.getSelectionModel().select(buscarInstructor(((Cursos)tblCursos.getSelectionModel()
                    .getSelectedItem()).getInstructorId()));
            cmbSalon.getSelectionModel().select(buscarSalon(((Cursos)tblCursos.getSelectionModel()
                    .getSelectedItem()).getSalonId()));
        }
    }
    
    
    public boolean agregarCursos(){
        Cursos cursos=new Cursos();
        cursos.setNombreCurso(txtNombreCurso.getText());
        cursos.setCiclo(spnCiclo.getValue());
        cursos.setCupoMaximo(spnCupoMaximo.getValue());
        cursos.setCupoMinimo(spnCupoMinimo.getValue());
        cursos.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica.getSelectionModel()
                .getSelectedItem()).getCodigoTecnico());
        cursos.setHorarioId(((Horarios)cmbHorario.getSelectionModel().getSelectedItem()).getId());
        cursos.setInstructorId(((Instructores)cmbInstructor.getSelectionModel().getSelectedItem()).getId());
        cursos.setSalonId(((Salones)cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_create(?,?,?,?,?,?,?,?)}");
            pstmt.setString(1,cursos.getNombreCurso());
            pstmt.setInt(2,cursos.getCiclo());
            pstmt.setInt(3,cursos.getCupoMaximo());
            pstmt.setInt(4,cursos.getCupoMinimo());
            pstmt.setString(5,cursos.getCarreraTecnicaId());
            pstmt.setInt(6,cursos.getHorarioId());
            pstmt.setInt(7,cursos.getInstructorId());
            pstmt.setString(8,cursos.getSalonId());
            System.out.println(pstmt.toString());     
            pstmt.execute();
            listaCursos.add(cursos);
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al insertar el siguiente registro: " + cursos.toString());
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

    public boolean actualizarCursos(){
        Cursos cursos=new Cursos();
        cursos.setId(Integer.parseInt(txtId.getText()));
        cursos.setNombreCurso(txtNombreCurso.getText());
        cursos.setCiclo(spnCiclo.getValue());
        cursos.setCupoMaximo(spnCupoMaximo.getValue());
        cursos.setCupoMinimo(spnCupoMinimo.getValue());
        cursos.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica.getSelectionModel()
                .getSelectedItem()).getCodigoTecnico());
        cursos.setHorarioId(((Horarios)cmbHorario.getSelectionModel().getSelectedItem()).getId());
        cursos.setInstructorId(((Instructores)cmbInstructor.getSelectionModel().getSelectedItem()).getId());
        cursos.setSalonId(((Salones)cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_update(?,?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1,cursos.getId());
            pstmt.setString(2,cursos.getNombreCurso());
            pstmt.setInt(3,cursos.getCiclo());
            pstmt.setInt(4,cursos.getCupoMaximo());
            pstmt.setInt(5,cursos.getCupoMinimo());
            pstmt.setString(6,cursos.getCarreraTecnicaId());
            pstmt.setInt(7,cursos.getHorarioId());
            pstmt.setInt(8,cursos.getInstructorId());
            pstmt.setString(9,cursos.getSalonId());
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al insertar el siguiente registro: " + cursos.toString());
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

    public boolean eliminarCursos(){
        Cursos cursos=(Cursos)tblCursos.getSelectionModel().getSelectedItem();
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_delete(?)}");
            pstmt.setInt(1,cursos.getId());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al eliminar el siguiente registro: " + cursos.toString());
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
    
    private boolean existeElementoSeleccionado(){
        return (tblCursos.getSelectionModel().getSelectedItem() != null);
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
    
    private CarrerasTecnicas buscarCarrera(String id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        CarrerasTecnicas carreras=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");
            pstmt.setString(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                carreras=new CarrerasTecnicas();
                carreras.setCodigoTecnico(rs.getString(1));
                carreras.setCarrera(rs.getString(2));
                carreras.setGrado(rs.getString(3));
                carreras.setSeccion(rs.getString(4).charAt(0));
                carreras.setJornada(rs.getString(5));                
            }
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar la Carrera Tecnica de codigo:" + carreras.getCarrera());
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
        return carreras;
    }
    
    private Horarios buscarHorarios(int id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Horarios horarios=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_read_by_id(?)}");
            pstmt.setInt(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                horarios=new Horarios();
                horarios.setId(rs.getInt(1));
                horarios.setHorarioInicio(rs.getTime(2).toLocalTime());
                horarios.setHorarioFinal(rs.getTime(3).toLocalTime());
                horarios.setLunes(evaluacionDatos(rs,4));
                horarios.setMartes(evaluacionDatos(rs,5));
                horarios.setMiercoles(evaluacionDatos(rs,6));
                horarios.setJueves(evaluacionDatos(rs,7));
                horarios.setViernes(evaluacionDatos(rs,8));
            }
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar el Horario de ID:" + horarios.getId());
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
        return horarios;        
    }
    
    private Instructores buscarInstructor(int id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Instructores instructores=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");
            pstmt.setInt(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                instructores=new Instructores();
                instructores.setId(rs.getInt(1));
                instructores.setNombre1(rs.getString(2));
                instructores.setNombre2(rs.getString(3));
                instructores.setNombre3(rs.getString(4));
                instructores.setApellido1(rs.getString(5));
                instructores.setApellido2(rs.getString(6));
                instructores.setDireccion(rs.getString(7));
                instructores.setEmail(rs.getString(8));
                instructores.setTelefono(rs.getString(9));
                instructores.setFechaNacimiento(rs.getDate(10).toLocalDate());
            }   
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar el Instructor de ID:" + instructores.getId());
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
        return instructores;
        
    }
    
    private Salones buscarSalon(String id){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Salones salones=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");
            pstmt.setString(1,id);
            System.out.println(pstmt.toString());
            rs=pstmt.executeQuery();
            while(rs.next()){
                salones=new Salones();
                salones.setCodigoSalon(rs.getString(1));
                salones.setDescripcion(rs.getString(2));
                salones.setCapacidadMaxima(rs.getInt(3));
                salones.setEdificio(rs.getString(4));
                salones.setNivel(rs.getInt(5));
            }
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al buscar el Salon de codigo:" + salones.getCodigoSalon());
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
        return salones;
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
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));
        
        cmbCarreraTecnica.setItems(getCarreras());
        cmbHorario.setItems(getHorarios());        
        cmbInstructor.setItems(getInstructores());        
        cmbSalon.setItems(getSalones());
    }

    private void habilitarCampos(){
        txtId.setEditable(false);
        txtNombreCurso.setEditable(true);
        
        txtId.setDisable(true);
        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    private void deshabilitarCampos(){
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);

        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);
    }
    
    private void limpiarCampos() {
        txtId.clear();
        txtNombreCurso.clear();
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }
    
    private void limpiarLabel(){        
        lblNombreCurso.setText("");
        lblCarreraTecnica.setText("");
        lblHorario.setText("");
        lblInstructor.setText("");
        lblSalon.setText("");
    }
    
    private boolean evaluacionCamposVacios(){
        return ((!(txtNombreCurso.getText().isEmpty())) && (!(cmbCarreraTecnica.getValue()==null)) 
                && (!(cmbHorario.getValue()==null)) && (!(cmbInstructor.getValue()==null)) && (!(cmbSalon.getValue()==null)));
    }
    
    private boolean evaluacionCantCar(){
        return (txtNombreCurso.getText().length()<=255);
    }
    
    private void evaluacionDigitos(){
        if(txtNombreCurso.getText().length()>255){
            lblNombreCurso.setText("No más de 255 digitos");
        }
    }
    
    
    private void camposObligatorios(){
        if(txtNombreCurso.getText().isEmpty()){
            lblNombreCurso.setText("Campo obligatorio");
        }
        if(cmbCarreraTecnica.getValue()==null){
            lblCarreraTecnica.setText("Campo obligatorio");
        }
        if(cmbHorario.getValue()==null){
            lblHorario.setText("Campo obligatorio");
        }
        if(cmbInstructor.getValue()==null){
            lblInstructor.setText("Campo obligatorio");
        }
        if(cmbSalon.getValue()==null){
            lblSalon.setText("Campo obligatorio");
        }
    }
    
    private ObservableList getInstructores(){
        ArrayList<Instructores> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion().prepareCall("CALL sp_instructores_read()");
            rs=pstmt.executeQuery();
            while(rs.next()){
                Instructores instructores=new Instructores();
                instructores.setId(rs.getInt(1));
                instructores.setNombre1(rs.getString(2));
                instructores.setNombre2(rs.getString(3));
                instructores.setNombre3(rs.getString(4));
                instructores.setApellido1(rs.getString(5));
                instructores.setApellido2(rs.getString(6));
                instructores.setDireccion(rs.getString(7));
                instructores.setEmail(rs.getString(8));
                instructores.setTelefono(rs.getString(9));
                instructores.setFechaNacimiento(rs.getDate(10).toLocalDate());
                lista.add(instructores);
            }
            listaInstructores=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Instructores");
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
        return listaInstructores;
    }
    
    private ObservableList getSalones(){
        ArrayList<Salones> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read()}");
            rs=pstmt.executeQuery();
            while(rs.next()){
                Salones salon=new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                lista.add(salon);
            }
            listaSalones=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Salones");
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
        return listaSalones;
    }
    
    public ObservableList getCarreras(){
        ArrayList<CarrerasTecnicas> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read()}");
            rs=pstmt.executeQuery();
            while(rs.next()){
                CarrerasTecnicas carrera=new CarrerasTecnicas();
                carrera.setCodigoTecnico(rs.getString(1));
                carrera.setCarrera(rs.getString(2));
                carrera.setGrado(rs.getString(3));
                carrera.setSeccion(rs.getString(4).charAt(0));
                carrera.setJornada(rs.getString(5));
                lista.add(carrera);
            }
            listaCarreras=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Carreras Tecnicas");
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
        return listaCarreras;
    }
    
    private ObservableList getHorarios(){
        ArrayList<Horarios> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try {
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_read()}");
            rs=pstmt.executeQuery();
            while(rs.next()){
                Horarios horarios=new Horarios();
                horarios.setId(rs.getInt(1));
                horarios.setHorarioInicio(rs.getTime(2).toLocalTime());
                horarios.setHorarioFinal(rs.getTime(3).toLocalTime());
                horarios.setLunes(evaluacionDatos(rs,4));
                horarios.setMartes(evaluacionDatos(rs,5));
                horarios.setMiercoles(evaluacionDatos(rs,6));
                horarios.setJueves(evaluacionDatos(rs,7));
                horarios.setViernes(evaluacionDatos(rs,8));
                lista.add(horarios);
            }
            listaHorarios=FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
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
        return listaHorarios;
    }
    
    private boolean evaluacionDatos(ResultSet rs,int i) throws SQLException{
        return (rs.getByte(i)==1);
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}