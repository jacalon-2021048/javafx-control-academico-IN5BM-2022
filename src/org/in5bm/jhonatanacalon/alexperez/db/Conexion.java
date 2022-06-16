package org.in5bm.jhonatanacalon.alexperez.db;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @author Alex Gabriel Pérez Dubon <aperez-2021095@kinal.edu.gt>
 * @date 3/05/2022
 * @time 11:09:02
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class Conexion{
    private final String URL;    
    private final String IP_SERVER="127.0.0.1";
    private final String PORT="3306";
    private final String DB="db_control_academico_in5bm";
    private final String USER="kinal";
    private final String PASSWORD="admin";
    private Connection conexion;
    private static Conexion instancia;
    
    public static Conexion getInstance(){
        if(instancia==null){
            instancia=new Conexion();
        }
        return instancia;
    }
    
    private Conexion(){
        URL="jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion=DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("La conexion a la base de datos fue exitosa");
            DatabaseMetaData dma=conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
        }catch(ClassNotFoundException e){
            System.err.println("No se encuentra ninguna definición para esta clase");
            e.printStackTrace();
        }catch(CommunicationsException e){
            System.err.println("No se puede establecer comunicación con el servidor de MySQL");
            System.err.println("Verifique que el servicio de MySQL este levantado,"
                    + "que la IP_SERVER y el puerto sean los correctos");
            e.printStackTrace();
        }catch(SQLException e){
            System.err.println("Se produjo un error de tipo SQLException");
            System.err.println("SQLSate: " + e.getSQLState());
            System.err.println("ErrorCode: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println("\n");
            e.printStackTrace();
        }catch(Exception e){
            System.err.println("Se produjo una excepcion en la conexión a la base de datos");
            e.printStackTrace();
        }
    }
    
    public Connection getConexion(){
        return conexion;
    }
}