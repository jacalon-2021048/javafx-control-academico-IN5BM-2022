package org.in5bm.jhonatanacalon.alexperez.controllers;


import java.net.URL;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.Optional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.net.URISyntaxException;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import org.in5bm.jhonatanacalon.alexperez.models.CarrerasTecnicas;


/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 27/04/2022
 * @time 21:47:53
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class CarrerasTecnicasController implements Initializable{    
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
    private TextField txtCodigo;
    
    @FXML
    private TextField txtCarrera;
    
    @FXML
    private TextField txtJornada;
    
    @FXML
    private TextField txtSeccion;
    
    @FXML
    private TextField txtGrado;
    
    @FXML
    private TableView tblCarreras;
    
    @FXML
    private TableColumn colCodigoTecnico;
    
    @FXML
    private TableColumn colCarrera;
    
    @FXML
    private TableColumn colGrado;
    
    @FXML
    private TableColumn colSeccion;
    
    @FXML
    private TableColumn colJornada;
  
    @FXML
    private ImageView imgAgregar;
 
    @FXML
    private ImageView imgCambiar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private Label lblCodigo;
    
    @FXML
    private Label lblGrado;
    
    @FXML
    private Label lblSeccion;
    
    @FXML
    private Label lblJornada;
    
    @FXML
    private Label lblCarrera;
        
    private ObservableList<CarrerasTecnicas> listaCarreras;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        deshabilitarCampos();
        cargarDatos();
    }
    
    @FXML
    void clicAgregar(ActionEvent ae){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblCarreras.setDisable(true);
                txtCodigo.setEditable(true);
                txtCodigo.setDisable(false);
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
                        if(agregarCarrera()){
                            limpiarCampos();
                            limpiarLabel();
                            deshabilitarCampos();
                            tblCarreras.setDisable(false);
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
                tblCarreras.setDisable(false);
                limpiarLabel();
                limpiarCampos();
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
                
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(evaluacionCantCar()){
                            if(actualizarCarreras()){
                                limpiarCampos();
                                limpiarLabel();                                
                                cargarDatos();
                                deshabilitarCampos();
                                tblCarreras.setDisable(false);
                                tblCarreras.getSelectionModel().clearSelection();
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
                        if(eliminarCarrera()){
                            eliminarCarrera();
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
                        tblCarreras.getSelectionModel().clearSelection();
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
                tblCarreras.getSelectionModel().clearSelection();
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
            txtCodigo.setText(((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(Character.toString(((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem()).getSeccion()));
            txtJornada.setText(((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem()).getJornada());            
        }
    }
  
    private boolean agregarCarrera(){
        if(evaluacionPK()){
            CarrerasTecnicas carrera=new CarrerasTecnicas();
            carrera.setCodigoTecnico(txtCodigo.getText());
            carrera.setCarrera(txtCarrera.getText());
            carrera.setGrado(txtGrado.getText());
            carrera.setSeccion(txtSeccion.getText().charAt(0));
            carrera.setJornada(txtJornada.getText());
            PreparedStatement pstmt=null;
            
            try{
                pstmt=Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_carreras_tecnicas_create(?,?,?,?,?)}");
                pstmt.setString(1,carrera.getCodigoTecnico());
                pstmt.setString(2,carrera.getCarrera());
                pstmt.setString(3,carrera.getGrado());
                pstmt.setString(4,Character.toString(carrera.getSeccion()));
                pstmt.setString(5,carrera.getJornada());
                System.out.println(pstmt.toString());
                pstmt.execute();
                listaCarreras.add(carrera);
                return true;
            }catch(SQLException e){
                System.err.println("\nSe produjo un error al insertar el siguiente registro: " + carrera.toString());
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
    
    private boolean actualizarCarreras(){
        CarrerasTecnicas carrera=new CarrerasTecnicas();
        carrera.setCodigoTecnico(txtCodigo.getText());
        carrera.setCarrera(txtCarrera.getText());
        carrera.setGrado(txtGrado.getText());
        carrera.setSeccion(txtSeccion.getText().charAt(0));
        carrera.setJornada(txtJornada.getText());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_update(?,?,?,?,?)}");
            pstmt.setString(1,carrera.getCodigoTecnico());
            pstmt.setString(2,carrera.getCarrera());
            pstmt.setString(3,carrera.getGrado());
            pstmt.setString(4,Character.toString(carrera.getSeccion()));
            pstmt.setString(5,carrera.getJornada());
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.out.println("\nSe produjo un error al actualizar el siguiente registro: " + carrera.toString());
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
    
    private boolean eliminarCarrera(){
        if(existeElementoSeleccionado()){
            CarrerasTecnicas carreras=((CarrerasTecnicas)tblCarreras.getSelectionModel().getSelectedItem());
            PreparedStatement pstmt=null;
            
            try{
                pstmt=Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1,carreras.getCodigoTecnico());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\nSe produjo un error al eliminar el siguiente registro: " + carreras.toString());
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
        return (tblCarreras.getSelectionModel().getSelectedItem()!=null);
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
    
    private void alertaPK(){
        Alert alerta=new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Académico - CUIDADO!!!");
        alerta.setHeaderText(null);
        alerta.setContentText("El codigo ingresado ya existe en la base de datos");
        alerta.show();
        Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
        Image ico=new Image(PAQUETE_IMAGES+"icono.png");
        stage.getIcons().add(ico);
    }
    
    private boolean evaluacionPK(){
        boolean opcion=true;
        for(int i=0;i<listaCarreras.size();i++){
            if(txtCodigo.getText().equals(listaCarreras.get(i).getCodigoTecnico())){
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
        tblCarreras.setItems(getCarreras());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas,String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas,String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas,String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas,Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas,String>("jornada"));
    }
    
    private void habilitarCampos(){
        txtCodigo.setEditable(false);
        txtJornada.setEditable(true);
        txtSeccion.setEditable(true);
        txtGrado.setEditable(true);
        txtCarrera.setEditable(true);
        
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
        txtCodigo.setDisable(true);
    }
    
    private void deshabilitarCampos(){
        txtCodigo.setEditable(false);
        txtJornada.setEditable(false);
        txtSeccion.setEditable(false);
        txtGrado.setEditable(false);
        txtCarrera.setEditable(false);
        
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
        txtCodigo.setDisable(true);
    }
    
    private void limpiarCampos(){
        txtCarrera.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtJornada.clear();
        txtCodigo.clear();
    }
    
    private void limpiarLabel(){
        lblCarrera.setText("");
        lblCodigo.setText("");
        lblGrado.setText("");
        lblJornada.setText("");
        lblSeccion.setText("");
    }        
    
    private boolean evaluacionCamposVacios(){
        return (!(txtCodigo.getText().isEmpty())) 
                && (!(txtCarrera.getText().isEmpty())) 
                && (!(txtGrado.getText().isEmpty())) 
                && (!(txtSeccion.getText().isEmpty()))
                && (!(txtJornada.getText().isEmpty()));
    }
    
    private boolean evaluacionCantCar(){
        return (txtCodigo.getText().length()<=6) && (txtCarrera.getText().length()<=45) 
                && (txtGrado.getText().length()<=10) 
                && (txtSeccion.getText().length()==1) && (txtJornada.getText().length()<=10);
    }
    
    private void evaluacionDigitos(){
        if (txtCodigo.getText().length() > 6) {
            lblCodigo.setText("No más de 6 digitos");
        } else {
            if (txtCarrera.getText().length() > 45) {
                lblCarrera.setText("No más de 45 digitos");
            } else {
                if (txtGrado.getText().length() > 10) {
                    lblGrado.setText("No más de 10 digitos");
                } else {
                    if (txtSeccion.getText().length() > 1) {
                        lblSeccion.setText("No más de 1 digito");
                    } else {
                        if (txtJornada.getText().length() > 10) {
                            lblJornada.setText("No más de 10 digitos");
                        }
                    }
                }
            }
        }
    }
    
    private void camposObligatorios(){
        if (txtCodigo.getText().length() == 0) {
            lblCodigo.setText("Campo obligatorio");
        } else if (txtCarrera.getText().length() == 0) {
            lblCarrera.setText("Campo obligatorio");
        } else if (txtGrado.getText().length() == 0) {
            lblGrado.setText("Campo obligatorio");
        } else if (txtSeccion.getText().length() == 0) {
            lblSeccion.setText("Campo obligatorio");
        } else if (txtJornada.getText().length() == 0) {
            lblJornada.setText("Campo obligatorio");
        }
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}