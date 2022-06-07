package org.in5bm.jhonatanacalon.alexperez.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Salones;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 27/04/2022
 * @time 21:49:00
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class SalonesController implements Initializable{    
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
    private TextField txtDescripcion;
    
    @FXML
    private TextField txtEdificio;
    
    @FXML
    private Label lblCodigo;
    
    @FXML
    private Label lblDescripcion;
    
    @FXML
    private Label lblEdificio;
    
    @FXML
    private Label lblNivel;
    
    @FXML
    private Label lblCapacidadMaxima;
    
    @FXML
    private Spinner<Integer> spnCapacidad;
    
    private SpinnerValueFactory<Integer> valueFactoryCapacidad;
    
    @FXML
    private Spinner<Integer> spnNivel;
    
    private SpinnerValueFactory<Integer> valueFactoryNivel;
    
    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMaxima;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;
    
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
    
    private ObservableList<Salones> listaSalones;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        iniciarSpinner();
        deshabilitarCampos();
        cargarDatos();
    }
    
    @FXML
    void clicAgregar(ActionEvent ae){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblSalones.setDisable(true);
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
                        if(agregarSalon()){
                            limpiarCampos();
                            limpiarLabel();
                            deshabilitarCampos();
                            tblSalones.setDisable(false);
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
                tblSalones.setDisable(false);
                limpiarLabel();
                limpiarCampos();               
                deshabilitarCampos();
                operacion=Operacion.NINGUNO;
                break;
                
            case ACTUALIZAR:
                if(existeElementoSeleccionado()){
                    if(evaluacionCamposVacios()){
                        if(evaluacionCantCar()){
                            if(actualizarSalon()){
                                limpiarCampos();
                                cargarDatos();
                                deshabilitarCampos();
                                tblSalones.setDisable(false);
                                tblSalones.getSelectionModel().clearSelection();
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
        limpiarCampos();
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
                        if(eliminarSalon()){
                            eliminarSalon();
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
                        tblSalones.getSelectionModel().clearSelection();
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
                tblSalones.getSelectionModel().clearSelection();
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
            txtCodigo.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            spnCapacidad.getValueFactory().setValue(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima());
            txtEdificio.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            spnNivel.getValueFactory().setValue(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getNivel());
        }
    }
    
    private boolean agregarSalon(){
        if(evaluacionPK()){
            Salones salon = new Salones();
            salon.setCodigoSalon(txtCodigo.getText());
            salon.setDescripcion(txtDescripcion.getText());
            salon.setCapacidadMaxima(spnCapacidad.getValue());
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(spnNivel.getValue());
            
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_create(?,?,?,?,?)}");
                pstmt.setString(1,salon.getCodigoSalon());
                pstmt.setString(2,salon.getDescripcion());
                pstmt.setString(3,String.valueOf(salon.getCapacidadMaxima()));
                pstmt.setString(4,salon.getEdificio());
                pstmt.setString(5,String.valueOf(salon.getNivel()));
                System.out.println(pstmt.toString());
                pstmt.execute();
                listaSalones.add(salon);
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al insertar el siguiente registro: " + salon.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            alertaPK();
        }
        return false;
    }

    private boolean actualizarSalon() {
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigo.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(spnCapacidad.getValue());
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(spnNivel.getValue());
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_update(?,?,?,?,?)}");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setString(3, String.valueOf(salon.getCapacidadMaxima()));
            pstmt.setString(4, salon.getEdificio());
            pstmt.setString(5, String.valueOf(salon.getNivel()));
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al actualizar el siguiente registro: " + salon.toString());
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

    private boolean eliminarSalon() {
        if(existeElementoSeleccionado()){
            Salones salon=((Salones)tblSalones.getSelectionModel().getSelectedItem());
            PreparedStatement pstmt=null;

            try{
                pstmt=Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1,salon.getCodigoSalon());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\nSe produjo un error al eliminar el siguiente registro: " + salon.toString());
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
        return (tblSalones.getSelectionModel().getSelectedItem()!=null);
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
        for(int i=0;i<listaSalones.size();i++){
            if(txtCodigo.getText().equals(listaSalones.get(i).getCodigoSalon())){
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
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones,String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones,String>("descripcion"));
        colCapacidadMaxima.setCellValueFactory(new PropertyValueFactory<Salones,Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones,String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones,Integer>("nivel"));
    }
    
    private void habilitarCampos(){
        txtEdificio.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCodigo.setEditable(false);
        
        txtDescripcion.setDisable(false);
        spnCapacidad.setDisable(false);
        txtEdificio.setDisable(false);
        spnNivel.setDisable(false);        
        txtCodigo.setDisable(true);
    }
    
    private void deshabilitarCampos(){
        txtDescripcion.setEditable(false);
        txtCodigo.setEditable(false);
        txtEdificio.setEditable(false);
        
        txtDescripcion.setDisable(true);
        spnCapacidad.setDisable(true);
        txtEdificio.setDisable(true);
        spnNivel.setDisable(true);
        txtCodigo.setDisable(true);
    }
    
    private void limpiarCampos(){
        txtDescripcion.clear();
        spnCapacidad.getValueFactory().setValue(1);
        txtEdificio.clear();
        spnNivel.getValueFactory().setValue(1);
        txtCodigo.clear();
    }
    
    private void limpiarLabel(){
        lblCodigo.setText("");
        lblDescripcion.setText("");
        lblEdificio.setText("");
        lblNivel.setText("");
        lblCapacidadMaxima.setText("");
    }
    
    private boolean evaluacionCamposVacios(){
        return (!(txtCodigo.getText().isEmpty())) && (!(spnCapacidad.getValueFactory().getValue().equals(1)));
    }
    
    private boolean evaluacionCantCar(){
        return (txtCodigo.getText().length()<=5) 
                && (txtDescripcion.getText().length()<=45) 
                && (txtEdificio.getText().length()<=15);
    }
    
    private void evaluacionDigitos(){
        if (txtCodigo.getText().length() > 5) {
            lblCodigo.setText("No más de 5 digitos");
        } else {
            if (txtDescripcion.getText().length() > 45) {
                lblDescripcion.setText("No más de 45 digitos");
            } else {
                if (txtEdificio.getText().length() > 15) {
                    lblEdificio.setText("No más de 15 digitos");
                }
            }
        }
    }
    
    private void camposObligatorios(){
        if (txtCodigo.getText().isEmpty()) {
            lblCodigo.setText("Campo obligatorio");
        } else if (spnCapacidad.getValueFactory().getValue().equals(1)) {
            lblCapacidadMaxima.setText("Elija un valor");
        }
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    } 
    
    private void iniciarSpinner(){
        valueFactoryCapacidad=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,40,1);
        spnCapacidad.setValueFactory(valueFactoryCapacidad);
        
        valueFactoryNivel=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        spnNivel.setValueFactory(valueFactoryNivel);
    }
}