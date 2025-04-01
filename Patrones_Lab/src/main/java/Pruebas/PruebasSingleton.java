package Pruebas;
import BaseDatos.DatabaseConnection;

public class PruebasSingleton {
    public void Singleton_Database(){
        DatabaseConnection conexion1 = DatabaseConnection.getInstance();
        DatabaseConnection conexion2 = DatabaseConnection.getInstance();

        // Verificamos si ambas referencias apuntan al mismo objeto
        if (conexion1 == conexion2) {
            System.out.println("✅ Singleton funciona: Ambas conexiones son la misma instancia.");
        } else {
            System.out.println("❌ ERROR: Se están creando nuevas instancias de la conexión.");
        }
        
        //DatabaseConnection otraConexion = new DatabaseConnection();  // ❌ ERROR! CONSTRUCTOR PRIVADO

        
        // Comprobamos que la conexión es la misma
        System.out.println("Conexión 1: " + conexion1.getConnection());
        System.out.println("Conexión 2: " + conexion2.getConnection());
    }
}
