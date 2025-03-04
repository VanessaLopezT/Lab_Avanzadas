package com.mycompany.conceptos_poo_maven;

import javax.swing.SwingUtilities;

public class Conceptos_POO_Maven {
    public static void main(String[] args) {

        ConexionBD.crearTablas();
        GestorUniversidad gestor = new GestorUniversidad();
        gestor.inicializarDatosTotales();
        gestor.guardar_en_BD();
        gestor.eliminarDuplicadosBD();
        ConexionBD.mostrarDatosBD();

 
        SwingUtilities.invokeLater(() -> {
        InscripcionApp app = new InscripcionApp();
        app.setVisible(true); // Esto hace que la ventana sea visible
        gestor.guardar_en_BD();
});

    

    }
}
