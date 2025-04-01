package BaseDatos;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    //DatabaseConnection es un Singleton de la conexión a la base de datos. 
    private static Properties propiedades = new Properties();
    private static DatabaseConnection instancia;  // Singleton
    private static Connection conexion;   // Almacenar conexión única

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/Recursos/configuracion.properties")) {
            propiedades.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo de configuración.");
        }
    }
    // Constructor privado para evitar instancias externas
    //gestiona una única conexión a la base de datos
    private DatabaseConnection() {
        try {
            String userDir = System.getProperty("user.dir");
            String url = propiedades.getProperty("db.url");
            String user = propiedades.getProperty("db.user");
            String password = propiedades.getProperty("db.password");

            if (url.startsWith("jdbc:h2:file:")) {
                url = "jdbc:h2:file:" + userDir + "/" + url.substring(13);
            }

            if (!AuxiliarBD.usuarioExiste(user, url)) {
                AuxiliarBD.crearUsuario(user, password, url);
            }

            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static DatabaseConnection getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return conexion;
    }
 
}

