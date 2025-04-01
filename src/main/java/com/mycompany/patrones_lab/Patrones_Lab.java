package com.mycompany.patrones_lab;
import BaseDatos.AuxiliarBD;
import Fabrica_Objetos.FabricaModelo;
import Pruebas.PruebaH2;
import Pruebas.PruebasSingleton;
import Service.UniversidadManager;
import interfaz.EstudianteDetalle;
import interfaz.InscripcionApp;
import javax.swing.SwingUtilities;

public class Patrones_Lab {
    public static void main(String[] args) {

        //AuxiliarBD.crearTablas();
        UniversidadManager gestor = new UniversidadManager();
        PruebaH2 pruebash2=new PruebaH2();
        //gestor.inicializarDatosTotales();
        //gestor.guardar_en_BD();
        gestor.eliminarDuplicadosBD();
        pruebash2.EncontrarBD();
        
        
        gestor.MostrarOperacionesLista_MostrarBinarios();
        AuxiliarBD.mostrarDatosBD();
        PruebasSingleton pruebas=new PruebasSingleton();
        pruebas.Singleton_Database();
        
        
         // Ejecutar la interfaz gráfica en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            // Crear la instancia de la ventana principal EstudianteDetalle
            EstudianteDetalle ventanaPrincipal = new EstudianteDetalle();
            
            // Hacer que la ventana sea visible
            ventanaPrincipal.setVisible(true);
        });
        };
 }

