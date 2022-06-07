package org.in5bm.jhonatanacalon.alexperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.in5bm.jhonatanacalon.alexperez.system.Principal;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 27/04/2022
 * @time 21:23:17
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class MenuPrincipalController implements Initializable{
    
    private Principal escenarioPrincipal;

    @FXML
    private Button btnAlumnos;

    @FXML
    private Button btnInstructores;

    @FXML
    private Button btnSalones;

    @FXML
    private Button btnCarreras;

    @FXML
    private Button btnHorarios;

    @FXML
    private Button btnCursos;

    @FXML
    private Button btnAsignacion;

    @FXML
    private Button btnAcercaDe;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        
    }
    
    @FXML
    void clicPrincipal(ActionEvent ae) {
        if(ae.getSource().equals(btnAlumnos)){
            escenarioPrincipal.mostrarEscenaAlumnos();
        }else if(ae.getSource().equals(btnInstructores)){
            escenarioPrincipal.mostrarEscenaInstructores();
        }else if(ae.getSource().equals(btnSalones)){
            escenarioPrincipal.mostrarEscenaSalones();
        }else if(ae.getSource().equals(btnCarreras)){
            escenarioPrincipal.mostrarEscenaCarreras();
        }else if(ae.getSource().equals(btnHorarios)){
            escenarioPrincipal.mostrarEscenaHorarios();
        }else if(ae.getSource().equals(btnCursos)){
            escenarioPrincipal.mostrarEscenaCursos();
        }else if(ae.getSource().equals(btnAsignacion)){
            escenarioPrincipal.mostrarEscenaAsignaciones();
        }else if(ae.getSource().equals(btnAcercaDe)){
            System.out.println("Bienvenido al menu acerca de");
        }
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
}