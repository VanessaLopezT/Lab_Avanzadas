/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import java.io.File;

/**
 *
 * @author VANESA
 */
public class PruebaH2 {
    public void EncontrarBD(){
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
    }
}
