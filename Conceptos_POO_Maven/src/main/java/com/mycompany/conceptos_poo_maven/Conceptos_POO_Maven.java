package com.mycompany.conceptos_poo_maven;

public class Conceptos_POO_Maven {
    public static void main(String[] args) {
        // 🔹 1. Crear conexión y tablas
        ConexionBD.crearTablas();
       
     
        // 🔹 2. Inicializar datos en memoria
        GestorUniversidad gestor = new GestorUniversidad();
        gestor.inicializarDatosTotales();
        gestor.guardar_en_BD();

        gestor.mostrarOperaciones();
        ConexionBD.mostrarDatosBD();
 
    

    }
}
