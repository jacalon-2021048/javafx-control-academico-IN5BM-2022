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
import javafx.collections.ObservableList;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Instructores;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

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
public class InstructoresController implements Initializable { 
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
    private TextField txtNacimiento;
    
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
    private void clicAgregar(ActionEvent ae){
        
    }

    @FXML
    private void clicCambiar(ActionEvent ae){
        
    }

    @FXML
    private void clicEliminar(ActionEvent ae){

    }    
       
    @FXML
    private void clicReporte(ActionEvent ae){

    }
    
    @FXML
    private void clicRegresar(MouseEvent me){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    @FXML
    private void seleccionarElemento(){
        
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
                instructores.setFechaNacimiento(rs.getDate(10));
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
        colNacimiento.setCellValueFactory(new PropertyValueFactory<Instructores,Date>("fechaNacimiento"));
    }
    
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }    
}