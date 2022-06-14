package org.in5bm.jhonatanacalon.alexperez.controllers;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Alumnos;

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
public class AlumnosController implements Initializable{    
    private enum Operacion{
        NINGUNO,GUARDAR,ACTUALIZAR
    }
    private Operacion operacion=Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGES="org/in5bm/jhonatanacalon/alexperez/resources/images/";
    
    private Principal escenarioPrincipal;

    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnCambiar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCarne;
        
    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;
    
    @FXML
    private TableView tblAlumnos;
    
    @FXML
    private TableColumn colCarne;
    
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
    private ImageView imgRegresar;
    
    @FXML
    private ImageView imgAgregar;

    @FXML
    private ImageView imgCambiar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private Label lblCarne;
    
    @FXML
    private Label lblNombre1;
    
    @FXML
    private Label lblApellido1;
    
    @FXML
    private Label lblNombre3;
    
    @FXML
    private Label lblNombre2;
    
    @FXML
    private Label lblApellido2;
    
    private ObservableList<Alumnos> listaAlumnos;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deshabilitarCampos();
        cargarDatos();
    }
    
    @FXML
    void clicAgregar(ActionEvent ae){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblAlumnos.setDisable(true);
                txtCarne.setEditable(true);
                txtCarne.setDisable(false);
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
                        if(agregarAlumno()){
                            limpiarCampos();
                            limpiarLabel();
                            deshabilitarCampos();
                            tblAlumnos.setDisable(false);
                            btnAgregar.setText("Agregar");
                            imgAgregar.setImage(new Image(PAQUETE_IMAGES+"agregar.png"));
                            btnCambiar.setText("Cambiar");
                            imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                            btnEliminar.setDisable(false);
                            btnReporte.setDisable(false);
                            operacion=Operacion.NINGUNO;
                        }
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
                tblAlumnos.setDisable(false);
                limpiarLabel();
                limpiarCampos();
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
                
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(evaluacionCantCar()){
                            if(actualizarAlumno()){
                                limpiarCampos();
                                limpiarLabel();
                                cargarDatos();
                                deshabilitarCampos();
                                tblAlumnos.setDisable(false);
                                tblAlumnos.getSelectionModel().clearSelection();
                                btnCambiar.setText("Cambiar");
                                imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
                                btnEliminar.setText("Eliminar");
                                imgEliminar.setImage(new Image(PAQUETE_IMAGES+"eliminar.png"));
                                btnAgregar.setDisable(false);
                                btnReporte.setDisable(false);
                                operacion=Operacion.NINGUNO;
                            }
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
                        if(eliminarAlumno()){
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
                        tblAlumnos.getSelectionModel().clearSelection();
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
                tblAlumnos.getSelectionModel().clearSelection();
                operacion=Operacion.NINGUNO;
                break;
        }
    }
    
    @FXML
    void clicReporte(ActionEvent ae) throws URISyntaxException{
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
            txtCarne.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }
    
    private boolean agregarAlumno(){        
        if(evaluacionPK()){
            Alumnos alumno=new Alumnos();
            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());        
            PreparedStatement pstmt=null;
            
            try{
                pstmt=Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_create(?,?,?,?,?,?)}");
                pstmt.setString(1,alumno.getCarne());
                pstmt.setString(2,alumno.getNombre1());
                pstmt.setString(3,alumno.getNombre2());
                pstmt.setString(4,alumno.getNombre3());
                pstmt.setString(5,alumno.getApellido1());
                pstmt.setString(6,alumno.getApellido2());
                System.out.println(pstmt.toString());
                pstmt.execute();            
                listaAlumnos.add(alumno);
                return true;
            }catch(SQLException e){
                System.err.println("\nSe produjo un error al insertar el siguiente registro: " + alumno.toString());
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
        }else{
            alertaPK();
        }
        return false;
    }
    
    private boolean actualizarAlumno(){
        Alumnos alumno=new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_update(?,?,?,?,?,?)}");
            pstmt.setString(1,alumno.getCarne());
            pstmt.setString(2,alumno.getNombre1());
            pstmt.setString(3,alumno.getNombre2());
            pstmt.setString(4,alumno.getNombre3());
            pstmt.setString(5,alumno.getApellido1());
            pstmt.setString(6,alumno.getApellido2());
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.out.println("\nSe produjo un error al actualizar el siguiente registro: " + alumno.toString());
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
    
    private boolean eliminarAlumno(){
        if(existeElementoSeleccionado()){
            Alumnos alumno=((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem());            
            PreparedStatement pstmt=null;
            
            try{
                pstmt=Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_delete(?)}");
                pstmt.setString(1,alumno.getCarne());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\nSe produjo un error al eliminar el siguiente registro: " + alumno.toString());
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
        }
        return false;
    }
    
    private boolean existeElementoSeleccionado(){
        return (tblAlumnos.getSelectionModel().getSelectedItem()!=null);
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
    
    private void alertaPK(){
        Alert alerta=new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Académico - CUIDADO!!!");
        alerta.setHeaderText(null);
        alerta.setContentText("El carné ingresado ya existe en la base de datos");
        alerta.show();
        Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
        Image ico=new Image(PAQUETE_IMAGES+"icono.png");
        stage.getIcons().add(ico);
    }
    
    private boolean evaluacionPK(){
        boolean opcion=true;
        for(int i=0;i<listaAlumnos.size();i++){            
            if(txtCarne.getText().equals(listaAlumnos.get(i).getCarne())){
                opcion=false;
                break;                
            }
        }
        return opcion;
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
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos,String>("apellido2"));
    }
    
    private void habilitarCampos(){
        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        
        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        
        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }
    
    private void limpiarCampos(){
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }
    
    private void limpiarLabel(){
        lblCarne.setText("");
        lblNombre1.setText("");
        lblNombre2.setText("");
        lblNombre3.setText("");
        lblApellido1.setText("");
        lblApellido2.setText("");
    }
    
    private boolean evaluacionCamposVacios(){
        return (!(txtCarne.getText().isEmpty())) && (!(txtNombre1.getText().isEmpty())) && (!(txtApellido1.getText().isEmpty()));
    }
    
    private boolean evaluacionCantCar(){
        return (txtCarne.getText().length()<=7) && (txtNombre1.getText().length()<=15) && (txtNombre2.getText().length()<=15)
                            && (txtNombre3.getText().length()<=15) && (txtApellido1.getText().length()<=15) 
                            && (txtApellido2.getText().length()<=15);
    }
    
    private void evaluacionDigitos(){
        if(txtCarne.getText().length() > 7){
            lblCarne.setText("No más de 7 digitos");
        }else{
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
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void camposObligatorios(){
        if(txtCarne.getText().isEmpty()){
            lblCarne.setText("Campo obligatorio");
        }else if (txtNombre1.getText().isEmpty()){
            lblNombre1.setText("Campo obligatorio");
        }else if(txtApellido1.getText().isEmpty()){
            lblApellido1.setText("Campo obligatorio");
        }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }    
}