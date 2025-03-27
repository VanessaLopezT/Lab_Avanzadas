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
            // Obtener la ruta absoluta sin la extensión .mv.db
            String dbPath = dbFile.getAbsolutePath().replace(".mv.db", "");

            // Construir la URL JDBC sin la extensión
            String jdbcUrl = "jdbc:h2:file:" + dbPath;

            System.out.println("📌 URL de conexión a H2: " + jdbcUrl);

        } else {
            System.out.println("❌ Base de datos NO encontrada. Se creará una nueva.");
        }
        
        
        gestor.MostrarOperacionesLista_MostrarBinarios();
        AuxiliarBD.mostrarDatosBD();
        SwingUtilities.invokeLater(() -> {
        InscripcionApp app = new InscripcionApp();
        app.setVisible(true); // Esto hace que la ventana sea visible
});

    

    }
}
