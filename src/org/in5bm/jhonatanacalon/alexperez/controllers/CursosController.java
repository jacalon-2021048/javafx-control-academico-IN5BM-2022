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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;
import org.in5bm.jhonatanacalon.alexperez.models.Cursos;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;
import java.sql.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

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
    private ImageView imgReporte;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtId;
    
    @FXML
    private Spinner<?> spnCiclo;
    
    @FXML
    private Spinner<?> spnCupoMaximo;
    
    @FXML
    private Spinner<?> spnCupoMinimo;
    
    @FXML
    private ComboBox<?> cmbCarreraTecnica;
    
    @FXML
    private ComboBox<?> cmbHorario;
    
    @FXML
    private ComboBox<?> cmbInstructor;
    
    @FXML
    private ComboBox<?> cmbSalon;
    
    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private TableView tblCursos;
    
    @FXML
    private TableColumn colId;
    
    @FXML
    private TableColumn colNombreCurso;
    
    @FXML
    private TableColumn colCiclo;
    
    @FXML
    private TableColumn colCupoMaximo;
    
    @FXML
    private TableColumn colCupoMinimo;
    
    @FXML
    private TableColumn colIdCarrera;
    
    @FXML
    private TableColumn colIdHorario;
    
    @FXML
    private TableColumn colIdInstructor;
    
    @FXML
    private TableColumn colIdSalon;
    
    private ObservableList<Cursos> listaCursos;
    
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
    
    private ObservableList getCursos(){
        ArrayList<Cursos> lista=new ArrayList<>();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try{
           pstmt=Conexion.getInstance().getConexion().prepareCall("CALL sp_cursos_read()");
           rs=pstmt.executeQuery();
            while(rs.next()){
                Cursos cursos=new Cursos();
                /*cursos.setId(rs.getInt(1));
                cursos.setNombreCurso(rs.getString(2));
                cursos.setCiclo(rs.getDate(3));
                cursos.setCupoMaximo(rs.getInt(4));
                cursos.setCupoMinimo(rs.getInt(5));
                cursos.setCarreraTecnicaId(rs.getString(6));                
                cursos.setHorarioId(rs.getInt(7));
                cursos.setInstructorId(rs.getInt(8));
                cursos.setSalonId(rs.getString(9));*/
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
    
    private void cargarDatos(){
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<Cursos,Integer>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<Cursos,String>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<Cursos,Date>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<Cursos,Integer>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<Cursos,Integer>("cupoMinimo"));
        colIdCarrera.setCellValueFactory(new PropertyValueFactory<Cursos,String>("carreraTecnicaId"));
        colIdHorario.setCellValueFactory(new PropertyValueFactory<Cursos,Integer>("horarioId"));
        colIdInstructor.setCellValueFactory(new PropertyValueFactory<Cursos,Integer>("instructorId"));
        colIdSalon.setCellValueFactory(new PropertyValueFactory<Cursos,String>("salonId"));
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}