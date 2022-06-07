package org.in5bm.jhonatanacalon.alexperez.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.in5bm.jhonatanacalon.alexperez.models.Horarios;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import java.time.LocalTime;
import javafx.scene.control.CheckBox;
import com.jfoenix.controls.JFXTimePicker;
import java.time.format.FormatStyle;
import java.util.Locale;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * 
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 03/05/2022
 * @time 12:14:45
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class HorariosController implements Initializable{
    private enum Operacion{
        NINGUNO,GUARDAR,ACTUALIZAR
    }
    private Operacion operacion=Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGES="org/in5bm/jhonatanacalon/alexperez/resources/images/";
    
    private Principal escenarioPrincipal;    

    @FXML
    private TableView tblHorarios;
    
    @FXML
    private TableColumn colId;
    
    @FXML
    private TableColumn colHorarioInicio;
    
    @FXML
    private TableColumn colHorarioFinal;
    
    @FXML
    private TableColumn colLunes;
    
    @FXML
    private TableColumn colMartes;
    
    @FXML
    private TableColumn colMiercoles;
    
    @FXML
    private TableColumn colJueves;
    
    @FXML
    private TableColumn colViernes;
    
    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnCambiar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private TextField txtId;
    
     @FXML
    private JFXTimePicker tpHorarioInicio;
     
    @FXML
    private JFXTimePicker tpHorarioFinal;
    
    @FXML
    private CheckBox chkJueves;
    
    @FXML
    private CheckBox chkLunes;
    
    @FXML
    private CheckBox chkMartes;
    
    @FXML
    private CheckBox chkMiercoles;
    
    @FXML
    private CheckBox chkViernes;
    
    @FXML
    private ImageView imgAgregar;
    
    @FXML
    private ImageView imgCambiar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private Label lblHorarioInicio;
    
    @FXML
    private Label lblHorarioFinal;
    
    private ObservableList<Horarios> listaHorarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        deshabilitarCampos();
        iniciarTimePicker();
        cargarDatos();
    }    

    @FXML
    void clicAgregar(ActionEvent ae){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                tblHorarios.setDisable(true);
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
                if (evaluacionCamposVacios()) {
                    if(agregarHorario()){
                        limpiarCampos();
                        limpiarLabel();
                        deshabilitarCampos();
                        tblHorarios.setDisable(false);
                        btnAgregar.setText("Agregar");
                        imgAgregar.setImage(new Image(PAQUETE_IMAGES+"agregar.png"));
                        btnCambiar.setText("Cambiar");
                        imgCambiar.setImage(new Image(PAQUETE_IMAGES+"modificar.png"));
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
    void clicCambiar(ActionEvent ae){
        
    }
    
    @FXML
    void clicEliminar(ActionEvent ae){
        
    }

    @FXML
    void clicReporte(ActionEvent ae){
        
    }

    @FXML
    void clicRegresar(MouseEvent me){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    @FXML
    void seleccionarElemento(){
        if(existeElementoSeleccionado()){
            txtId.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tpHorarioInicio.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio());
            tpHorarioFinal.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal());
            chkLunes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getLunes());
            chkMartes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getMartes());
            chkMiercoles.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getMiercoles());
            chkJueves.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getJueves());
            chkViernes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getViernes());
        }
    }
    
    private boolean agregarHorario(){
        Horarios horarios=new Horarios();
        horarios.setHorarioInicio(tpHorarioInicio.getValue());
        horarios.setHorarioFinal(tpHorarioFinal.getValue());
        horarios.setLunes(chkLunes.isSelected());
        horarios.setMartes(chkMartes.isSelected());
        horarios.setMiercoles(chkMiercoles.isSelected());
        horarios.setJueves(chkJueves.isSelected());
        horarios.setViernes(chkViernes.isSelected());
        PreparedStatement pstmt=null;
        
        try{
            pstmt=Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_create(?,?,?,?,?,?,?)}");
            pstmt.setString(1,String.valueOf(horarios.getHorarioInicio()));
            pstmt.setString(2,String.valueOf(horarios.getHorarioFinal()));
            pstmt.setString(3,String.valueOf(enviarDatosBD(horarios.getLunes())));
            pstmt.setString(4,String.valueOf(enviarDatosBD(horarios.getMartes())));
            pstmt.setString(5,String.valueOf(enviarDatosBD(horarios.getMiercoles())));
            pstmt.setString(6,String.valueOf(enviarDatosBD(horarios.getJueves())));
            pstmt.setString(7,String.valueOf(enviarDatosBD(horarios.getViernes())));
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horarios);
            return true;
        }catch(SQLException e){
                System.err.println("\nSe produjo un error al insertar el siguiente registro: " + horarios.toString());
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
    
    private byte enviarDatosBD(boolean a){
        if(a==true){
            return 1;
        }
        return 0;
    }
    
    private boolean existeElementoSeleccionado(){
        return (tblHorarios.getSelectionModel().getSelectedItem()!=null);
    }
    
    private ObservableList getHorarios(){
        ArrayList<Horarios> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try {
            pstmt=Conexion.getInstance().getConexion().prepareCall("CALL sp_horarios_read()");
            rs=pstmt.executeQuery();
            while(rs.next()){
                Horarios horarios=new Horarios();
                horarios.setId(rs.getInt(1));
                horarios.setHorarioInicio(evaluacionesHorarios(rs,2));
                horarios.setHorarioFinal(evaluacionesHorarios(rs,3));
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
    
    private LocalTime evaluacionesHorarios(ResultSet rs,int i) throws SQLException{
        return (rs.getTime(i).toLocalTime());
    }
    
    private void iniciarTimePicker(){
        tpHorarioFinal.set24HourView(false);
        tpHorarioInicio.set24HourView(false);
        
        StringConverter<LocalTime> defaultConverter
                =new LocalTimeStringConverter(FormatStyle. SHORT,Locale.US);
        tpHorarioFinal.setConverter(defaultConverter);
        
        StringConverter<LocalTime> defaultConverter2
                =new LocalTimeStringConverter(FormatStyle.SHORT,Locale.US);
        tpHorarioInicio.setConverter(defaultConverter2);
    }
    
    private void cargarDatos(){
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("id"));
        colHorarioInicio.setCellValueFactory(new PropertyValueFactory<Horarios,LocalTime>("horarioInicio"));
        colHorarioFinal.setCellValueFactory(new PropertyValueFactory<Horarios,LocalTime>("horarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("viernes"));
    }
    
    private void habilitarCampos(){
        txtId.setEditable(false);
        tpHorarioInicio.setEditable(false);
        tpHorarioFinal.setEditable(false);
        
        txtId.setDisable(true);
        tpHorarioInicio.setDisable(false);
        tpHorarioFinal.setDisable(false);
        chkLunes.setDisable(false);
        chkMartes.setDisable(false);
        chkMiercoles.setDisable(false);
        chkJueves.setDisable(false);
        chkViernes.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        txtId.setEditable(false);
        tpHorarioInicio.setEditable(false);
        tpHorarioFinal.setEditable(false);
        
        txtId.setDisable(true);
        tpHorarioInicio.setDisable(true);
        tpHorarioFinal.setDisable(true);
        chkLunes.setDisable(true);
        chkMartes.setDisable(true);
        chkMiercoles.setDisable(true);
        chkJueves.setDisable(true);
        chkViernes.setDisable(true);
    }
    
    private void limpiarCampos(){
        txtId.clear();
        tpHorarioInicio.getEditor().clear();
        tpHorarioFinal.getEditor().clear();
        chkLunes.setSelected(false);
        chkMartes.setSelected(false);
        chkMiercoles.setSelected(false);
        chkJueves.setSelected(false);
        chkViernes.setSelected(false);
    }
    
    private void limpiarLabel(){
        lblHorarioInicio.setText("");
        lblHorarioFinal.setText("");
    }
    
    private boolean evaluacionCamposVacios() {
        return (!(tpHorarioInicio.getValue()==null) && (!(tpHorarioFinal.getValue()==null)));
    }
    
    private void camposObligatorios(){
        if(tpHorarioInicio.getValue()==null){
            lblHorarioInicio.setText("Campo obligatorio");
        }else if(tpHorarioFinal.getValue()==null){
            lblHorarioFinal.setText("Campo obligatorio");
        }
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}