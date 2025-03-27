package com.mycompany.patrones_lab;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import servicios.UniversidadManager;
import interfaz.InscripcionApp;
import BaseDatos.AuxiliarBD;
import java.io.File;
import javax.swing.SwingUtilities;

public class Patrones_Lab {
    public static void main(String[] args) {

        //AuxiliarBD.crearTablas();
        UniversidadManager gestor = new UniversidadManager();
        //gestor.inicializarDatosTotales();
        //gestor.guardar_en_BD();
        gestor.eliminarDuplicadosBD();
        File dbFile = new File("./database/universidadDB.mv.db");
        if (dbFile.exists()) {
            System.out.println("✅ Base de datos encontrada en: " + dbFile.getAbsolutePath());
        } else {
            System.out.println("❌ Base de datos NO encontrada. Se creará una nueva.");
        }
        
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/Recursos/configuracion.properties")); // Carga el archivo de configuración
            String dbUrl = props.getProperty("db.url");
            System.out.println("📌 URL de conexión a H2: " + dbUrl);
        } catch (IOException e) {
            System.out.println("❌ No se pudo leer config.properties: " + e.getMessage());
        }
        
        
        gestor.MostrarOperacionesLista_MostrarBinarios();
        AuxiliarBD.mostrarDatosBD();
        SwingUtilities.invokeLater(() -> {
        InscripcionApp app = new InscripcionApp();
        app.setVisible(true); // Esto hace que la ventana sea visible
});

    

    }
}
