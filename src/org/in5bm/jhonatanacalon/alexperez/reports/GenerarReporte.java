package org.in5bm.jhonatanacalon.alexperez.reports;

import java.net.URL;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.in5bm.jhonatanacalon.alexperez.db.Conexion;

/**
 *
 * @author Jhonatan Jose Acalón Ajanel <jacalon-2021048@kinal.edu.gt>
 * @date 16/06/2022
 * @time 10:32:00
 * @codigo IN5BM
 * @jornada Matutina
 * @grupo 1
 */
public class GenerarReporte{
    private JasperViewer jasperViewer;
    private static GenerarReporte instance;
    
    public static GenerarReporte getInstance(){
        if(instance==null){
            instance=new GenerarReporte();            
        }
        return instance;
    }
 
    private GenerarReporte(){
        Locale locale=new Locale("es", "GT");
        Locale.setDefault(locale);        
    }
    
    public void mostrarReporte(String nombreReporte, Map<String,Object>  parametros,String titulo){
        /*
            CLAVE - VALOR
            nombre  :  "Jhonatan Jose"
            apellido  :  "Acalón Ajanel"
            -----------------------------------------------
            INDICE     -    VALOR
            0              -     "Jhonatan José"
            1              -   "Acalón Ajanel"
        */
        try{
            URL urlFile=new URL(getClass().getResource(nombreReporte).toString());
            JasperReport jasperReport=(JasperReport) JRLoader.loadObject(urlFile);
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parametros,Conexion.getInstance().getConexion());
            jasperViewer=new JasperViewer(jasperPrint,false);
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
}