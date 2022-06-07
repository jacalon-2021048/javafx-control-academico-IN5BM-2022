package org.in5bm.jhonatanacalon.alexperez.system;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.jhonatanacalon.alexperez.controllers.AlumnosController;
import org.in5bm.jhonatanacalon.alexperez.controllers.AsignacionesAlumnosController;
import org.in5bm.jhonatanacalon.alexperez.controllers.CarrerasTecnicasController;
import org.in5bm.jhonatanacalon.alexperez.controllers.CursosController;
import org.in5bm.jhonatanacalon.alexperez.controllers.HorariosController;
import org.in5bm.jhonatanacalon.alexperez.controllers.InstructoresController;
import org.in5bm.jhonatanacalon.alexperez.controllers.MenuPrincipalController;
import org.in5bm.jhonatanacalon.alexperez.controllers.SalonesController;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 26/04/2022
 * @time 18:02:55
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Principal extends Application{
    private Stage escenarioPrincipal;
    private final String PAQUETE_VIEW="../views/";
    private final String PAQUETE_IMAGES="org/in5bm/jhonatanacalon/alexperez/resources/images/";
    private final int ANCHO_ESCENA=1068;
    private final int ALTO_ESCENA=676;
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.escenarioPrincipal=primaryStage;
        primaryStage.setTitle("Control Académico");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES+"icono.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        this.mostrarEscenaPrincipal();
        this.escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String vistaFXML, int ancho, int alto) throws IOException{
        System.out.println("Cambiando escena: " + this.PAQUETE_VIEW + vistaFXML);
	FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFXML));
	Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
	this.escenarioPrincipal.setScene(scene);
	this.escenarioPrincipal.sizeToScene();
	this.escenarioPrincipal.show();
	return (Initializable) cargadorFXML.getController();
    }
    
    public void mostrarEscenaPrincipal(){
        try{
            MenuPrincipalController menuController=((MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",ANCHO_ESCENA,ALTO_ESCENA));
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex){
            System.err.print("Se produjo un error al cargar la vista del menu principal");            
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaAlumnos(){
        try{
            AlumnosController alumnosController=((AlumnosController) cambiarEscena("AlumnosView.fxml",ANCHO_ESCENA,ALTO_ESCENA));
            alumnosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.print("Se produjo un error al cargar la vista de los alumnos");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCarreras(){
        try{
            CarrerasTecnicasController carrerasController=((CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml",ANCHO_ESCENA,ALTO_ESCENA));
            carrerasController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.print("Se produjo un error al cargar la vista de las carreras");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaSalones(){
        try{
            SalonesController salonesController=((SalonesController) cambiarEscena("SalonesView.fxml",ANCHO_ESCENA,ALTO_ESCENA));
            salonesController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.print("Se produjo un error al cargar la vista de los salones");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaInstructores(){
        try{
            InstructoresController instructoresController=((InstructoresController) cambiarEscena("InstructoresView.fxml", ANCHO_ESCENA, ALTO_ESCENA));
            instructoresController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.print("Se produjo un error al cargar la vista de los instructores");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios(){
        try{
            HorariosController horariosController=((HorariosController) cambiarEscena("HorariosView.fxml",ANCHO_ESCENA,ALTO_ESCENA));
            horariosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.print("Se produjo un error al cargar la vista de los horarios");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCursos(){
        try{
            CursosController cursosController=((CursosController) cambiarEscena("CursosView.fxml",ANCHO_ESCENA, ALTO_ESCENA));
            cursosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.err.print("Se produjo un error al cargar la vista de los cursos");
            e.printStackTrace();
        }
    }
    
    public void mostrarEscenaAsignaciones(){
        try{
            AsignacionesAlumnosController asignacionesController=
                    ((AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", ANCHO_ESCENA, ALTO_ESCENA));
            asignacionesController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.err.print("Se produjo un error al cargar la vista de las asignaciones");
            e.printStackTrace();
        }
    }
}