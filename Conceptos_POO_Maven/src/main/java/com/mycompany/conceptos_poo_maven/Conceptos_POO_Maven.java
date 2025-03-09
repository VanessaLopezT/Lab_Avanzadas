package com.mycompany.conceptos_poo_maven;

import servicios.UniversidadManager;
import interfaz.InscripcionApp;
import BaseDatos.ConexionBD;
import DAO.DAOFactory;
import javax.swing.SwingUtilities;

public class Conceptos_POO_Maven {
    public static void main(String[] args) {

        ConexionBD.crearTablas();
        UniversidadManager gestor = new UniversidadManager();
        gestor.inicializarDatosTotales();
        gestor.guardar_en_BD();
        gestor.eliminarDuplicadosBD();
        gestor.MostrarOperacionesLista_MostrarBinarios();
        ConexionBD.mostrarDatosBD();

 
        SwingUtilities.invokeLater(() -> {
        InscripcionApp app = new InscripcionApp();
        app.setVisible(true); // Esto hace que la ventana sea visible
        gestor.guardar_en_BD();
        
        DAOFactory DAOfactory=new DAOFactory();
        
});

    

    }
}
