package org.in5bm.jhonatanacalon.alexperez.controllers;

import com.jfoenix.controls.JFXDatePicker;
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
import javafx.collections.ObservableList;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Instructores;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 03/05/2022
 * @time 10:55:00
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class InstructoresController implements Initializable{         

    @FXML
    private TextField txtId;
    private enum Operacion{
        NINGUNO,GUARDAR,ACTUALIZAR
    }
    private Operacion operacion=Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGES="org/in5bm/jhonatanacalon/alexperez/resources/images/";
    
    private Principal escenarioPrincipal;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private TextField txtNombre2;
    
    @FXML
    private TextField txtNombre3;
    
    @FXML
    private TextField txtApellido1;
    
    @FXML
    private TextField txtApellido2;
    
    @FXML
    private TextField txtDireccion;
    
    @FXML
    private TextField txtNombre1;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private JFXDatePicker dtpNacimiento;
    
    @FXML
    private Label lblNombre1;
    
    @FXML
    private Label lblNombre2;
    
    @FXML
    private Label lblNombre3;
    
    @FXML
    private Label lblApellido1;
    
    @FXML
    private Label lblApellido2;
    
    @FXML
    private Label lblDireccion;
    
    @FXML
    private Label lblEmail;
    
    @FXML
    private Label lblTelefono;
    
    @FXML
    private Label lblNacimiento;

    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private TableView tblInstructores;
    
    @FXML
    private TableColumn colId;
    
    @FXML
    private TableColumn colNombre1;
    
    @FXML
    private TableColumn colNombre2;
    
    @FXML
    private TableColumn colNombre3;
    
    @FXML
    private TableColumn colApellido1;
    
    @FXML
    private TableColumn colApellido2;
    
    @FXML
    private TableColumn colDireccion;
    
    @FXML
    private TableColumn colEmail;
    
    @FXML
    private TableColumn colTelefono;
    
    @FXML
    private TableColumn colNacimiento;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnCambiar;
    
    @FXML
    private ImageView imgAgregar;
    
    @FXML
    private ImageView imgCambiar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgReporte;
    
    private ObservableList<Instructores> listaInstructores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        cargarDatos();
    }
    
    @FXML
    void clicAgregar(ActionEvent ae){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblInstructores.setDisable(true);
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
                        //ESPACIO AGREGAR INSTRUCTOR
                    }else{
                        evaluacionDigitos();
                    }
                }else{
                    camposObligatorios();
                }
                
                break;
        }
    }

    @FXML
    void clicCambiar(ActionEvent ae){
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
                tblInstructores.setDisable(false);
                limpiarLabel();
                limpiarCampos();
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
                
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(evaluacionCantCar()){
                            //ESPACIO ACTUALIZAR INSTRUCTORES
                        }else{
                            evaluacionDigitos();
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
                        //ESPACIO ELIMINAR INSTRUCTOR
                    }else if(result.get().equals(ButtonType.CANCEL)){
                        alerta.close();
                        tblInstructores.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                }else{
                    alertasWarning("Antes de continuar seleccione un registro");
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
                tblInstructores.getSelectionModel().clearSelection();
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
            txtId.setText(String.valueOf(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            dtpNacimiento.setValue(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());            
        }
    }
    
    /*private boolean agregarInstructor(){
        
    }*/
    
    /*private boolean actualizarInstructor(){
        
    }*/
    
    /*private boolean eliminarInstructor(){
        
    }*/

    private boolean existeElementoSeleccionado(){
        return (tblInstructores.getSelectionModel().getSelectedItem()!=null);
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
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores,Integer>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores,String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores,String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores,String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores,String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores,String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores,String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores,String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores,String>("telefono"));
        colNacimiento.setCellValueFactory(new PropertyValueFactory<Instructores,LocalDate>("fechaNacimiento"));
    }
    
    private void habilitarCampos(){
        
    }
    
    private void deshabilitarCampos(){
        
    }
    
    private void limpiarCampos(){
        
    }
    
    private void limpiarLabel(){
        
    }
    
    private boolean evaluacionCamposVacios(){
        return ((!(txtNombre1.getText().isEmpty())) && (!(txtApellido1.getText().isEmpty()))  
                &&  (!(txtEmail.getText().isEmpty())) && (!(txtTelefono.getText().isEmpty())));
    }
    
    private boolean evaluacionCantCar() {
        return ((txtNombre1.getText().length()<=15) && (txtNombre2.getText().length()<=15)
                            && (txtNombre3.getText().length()<=15) && (txtApellido1.getText().length()<=15) 
                            && (txtApellido2.getText().length()<=15) && (txtDireccion.getText().length()<=45) 
                            && (txtEmail.getText().length()<=45) && (txtTelefono.getText().length()<=8));
    }
    
    private void evaluacionDigitos(){
        if(txtNombre1.getText().length() > 15){
                lblNombre1.setText("No más de 15 digitos");
            }else{
                if(txtNombre2.getText().length() > 15){
                    lblNombre2.setText("No más de 15 digitos");
                }else{
                    if(txtNombre3.getText().length() > 15){
                        lblNombre3.setText("No más de 15 digitos");
                    }else{
                        if(txtApellido1.getText().length() > 15){
                            lblApellido1.setText("No más de 15 digitos");
                        }else{
                            if(txtApellido2.getText().length() > 15){
                                lblApellido2.setText("No más de 15 digitos");
                            }else{
                                if(txtDireccion.getText().length()>45){
                                    lblDireccion.setText("No más de 45 digitos");
                                }else{
                                    if(txtEmail.getText().length()>45){
                                        lblEmail.setText("No más de 45 digitos");
                                    }else{
                                        if(txtTelefono.getText().length()>8){
                                            lblTelefono.setText("No más de 8 digitos");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }
    
    
    private void camposObligatorios(){
        if (txtNombre1.getText().isEmpty()){
            lblNombre1.setText("Campo obligatorio");
        }else if(txtApellido1.getText().isEmpty()){
            lblApellido1.setText("Campo obligatorio");
        }else if(txtEmail.getText().isEmpty()){
            lblEmail.setText("Campo obligatorio");
        }else if(txtTelefono.getText().isEmpty()){
            lblTelefono.setText("Campo obligatorio");
        }
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }    
}