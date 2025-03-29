package BaseDatos;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AuxiliarBD {
    //AuxiliarBD es un Singleton de la conexión a la base de datos. 
     private static Properties propiedades = new Properties();
    private static AuxiliarBD instancia;  // Singleton
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
    private AuxiliarBD() {
        try {
            String userDir = System.getProperty("user.dir");
            String url = propiedades.getProperty("db.url");
            String user = propiedades.getProperty("db.user");
            String password = propiedades.getProperty("db.password");

            if (url.startsWith("jdbc:h2:file:")) {
                url = "jdbc:h2:file:" + userDir + "/" + url.substring(13);
            }

            if (!usuarioExiste(user, url)) {
                crearUsuario(user, password, url);
            }

            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static AuxiliarBD getInstance() {
        if (instancia == null) {
            instancia = new AuxiliarBD();
        }
        return instancia;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return conexion;
    }

private static boolean usuarioExiste(String usuario, String url) {
    String checkUserSQL = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.USERS WHERE USER_NAME = UPPER(?)";

    try (Connection conn = DriverManager.getConnection(url, "sa", "");
         PreparedStatement checkStmt = conn.prepareStatement(checkUserSQL)) {

        checkStmt.setString(1, usuario);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0; // Retorna true si el usuario ya existe

    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al verificar la existencia del usuario.");
    }
}


    

public static void crearUsuario(String usuario, String contraseña, String url) {
    String checkUserSQL = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.USERS WHERE USER_NAME = UPPER(?)";
    String createUserSQL = "CREATE USER IF NOT EXISTS \"" + usuario + "\" PASSWORD '" + contraseña + "'";
    String grantSchemaSQL = "GRANT ALL PRIVILEGES ON SCHEMA PUBLIC TO \"" + usuario + "\"";
    String grantDatabaseSQL = "GRANT ALL PRIVILEGES TO \"" + usuario + "\"";

    try (Connection conn = DriverManager.getConnection(url, "sa", "");
         PreparedStatement checkStmt = conn.prepareStatement(checkUserSQL);
         Statement stmt = conn.createStatement()) {

        // Verificar si el usuario ya existe
        checkStmt.setString(1, usuario);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int userExists = rs.getInt(1);

        if (userExists == 0) {
            stmt.execute(createUserSQL);
            System.out.println("Usuario " + usuario + " creado.");
        } else {
            System.out.println("El usuario " + usuario + " ya existe.");
        }

        // Asegurar que tiene permisos adecuados
        stmt.execute(grantSchemaSQL);
        stmt.execute(grantDatabaseSQL);
        System.out.println("Permisos asignados correctamente.");

    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al crear el usuario o asignar permisos.");
    }
}

    
   public static void crearTablas() {
       Connection conexion = getInstance().getConnection();
    try ( Statement stmt = conexion.createStatement()) {
        String sqlPersona = "CREATE TABLE IF NOT EXISTS persona (" +
                "id INT PRIMARY KEY, nombres VARCHAR(255), apellidos VARCHAR(255), email VARCHAR(255));";
        stmt.execute(sqlPersona);

         String sqlProfesor = "CREATE TABLE IF NOT EXISTS profesor (" +
                "id INT PRIMARY KEY, " +
                "tipo_contrato VARCHAR(255), " +
                "FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE ON UPDATE CASCADE);";
        stmt.execute(sqlProfesor);

        String sqlInscripcionPersona = "CREATE TABLE IF NOT EXISTS inscripciones_personas (" +
        "id INT PRIMARY KEY AUTO_INCREMENT, " +
        "persona_id INT, " +
        "nombres VARCHAR(255), " +
        "apellidos VARCHAR(255), " +
        "email VARCHAR(255), " +
        "FOREIGN KEY (persona_id) REFERENCES persona(id) ON DELETE CASCADE ON UPDATE CASCADE);";
        stmt.execute(sqlInscripcionPersona);

        String sqlFacultad = "CREATE TABLE IF NOT EXISTS facultad (" +
                "id INT PRIMARY KEY, nombre VARCHAR(255), decano_id INT, " +
                "FOREIGN KEY (decano_id) REFERENCES persona(id));";
        stmt.execute(sqlFacultad);

        String sqlPrograma = "CREATE TABLE IF NOT EXISTS programa (" +
                "id INT PRIMARY KEY, nombre VARCHAR(255), duracion DOUBLE, registro DATE, facultad_id INT, " +
                "FOREIGN KEY (facultad_id) REFERENCES facultad(id));";
        stmt.execute(sqlPrograma);

        String sqlCurso = "CREATE TABLE IF NOT EXISTS curso (" +
                "id INT PRIMARY KEY, nombre VARCHAR(255), programa_id INT, activo BOOLEAN, " +
                "FOREIGN KEY (programa_id) REFERENCES programa(id));";
        stmt.execute(sqlCurso);
        
         String sqlInscripcion = "CREATE TABLE IF NOT EXISTS inscripcion (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "curso_id INT NOT NULL, " +
                "anio INT NOT NULL, " +
                "semestre INT NOT NULL, " +
                "estudiante_id INT NOT NULL, " +
                "FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (estudiante_id) REFERENCES estudiante(id) ON DELETE CASCADE, " +
                "UNIQUE (curso_id, estudiante_id, anio, semestre)" + // Evita duplicados en un mismo año y semestre
                ");";
        stmt.execute(sqlInscripcion);

        
         String sqlCursoProfesor = "CREATE TABLE IF NOT EXISTS curso_profesor (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "profesor_id INT NOT NULL, " +
                "curso_id INT NOT NULL, " +
                "anio INT NOT NULL, " +
                "semestre INT NOT NULL, " +
                "FOREIGN KEY (profesor_id) REFERENCES profesor(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE, " +
                "UNIQUE (profesor_id, curso_id, anio, semestre)" + // Evita duplicados en un mismo año y semestre
                ");";
        stmt.execute(sqlCursoProfesor);

        
        String sqlEstudiante = "CREATE TABLE IF NOT EXISTS estudiante (" +
                "id INT PRIMARY KEY, codigo INT, programa_id INT, activo BOOLEAN, promedio DOUBLE, " +
                "FOREIGN KEY (id) REFERENCES persona(id), " +
                "FOREIGN KEY (programa_id) REFERENCES programa(id));";
        stmt.execute(sqlEstudiante);

        
        String sqlCursoProfesores = "CREATE TABLE IF NOT EXISTS curso_profesores (" +
        "id INT AUTO_INCREMENT PRIMARY KEY, " + 
        "profesor_id INT, " +
        "curso_id INT, " +
        "anio INT NOT NULL, " + 
        "semestre INT NOT NULL, " + 
        "FOREIGN KEY (profesor_id) REFERENCES persona(id) ON DELETE CASCADE, " + 
        "FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE, " + 
        "UNIQUE (profesor_id, curso_id, anio, semestre)" + // Evita duplicados en un mismo año y semestre
        ");";

        stmt.execute(sqlCursoProfesores);

        String sqlCursosInscritos = "CREATE TABLE IF NOT EXISTS cursos_inscritos (" +
        "id INT AUTO_INCREMENT PRIMARY KEY, " +
        "inscripcion_id INT NOT NULL, " +  // Inscripción del curso
        "estudiante_id INT NOT NULL, " +  // ID del estudiante que se inscribe
        "FOREIGN KEY (inscripcion_id) REFERENCES curso(id) ON DELETE CASCADE, " +
        "FOREIGN KEY (estudiante_id) REFERENCES estudiante(id) ON DELETE CASCADE);";
        stmt.execute(sqlCursosInscritos);
            
        
    
    
    } catch (SQLException e) {
                e.printStackTrace();
                }
          }


    public static void mostrarDatosBD() {
    Connection conexion = getInstance().getConnection(); // Obtener conexión sin cerrarla

    System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

    mostrarTabla(conexion, "persona");
    mostrarTabla(conexion, "facultad");
    mostrarTabla(conexion, "programa");
    mostrarTabla(conexion, "curso");
    mostrarTabla(conexion, "profesor");
    mostrarTabla(conexion, "estudiante");
    mostrarTabla(conexion, "curso_profesor");
    mostrarTabla(conexion, "inscripcion"); // Singular, no "inscripciones"
    mostrarTabla(conexion, "cursos_inscritos"); // Guion bajo en lugar de junto
    mostrarTabla(conexion, "curso_profesores");
    mostrarTabla(conexion, "inscripciones_personas");// Guion bajo en lugar de junto
}

     public static void mostrarDatosBD_PERSONA() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "persona");
     
   
}
    
      public static void mostrarDatosBD_FACULTAD() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "facultad");
   
}
      
       public static void mostrarDatosBD_PROGRAMA() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "programa");

}
       
    public static void mostrarDatosBD_CURSO() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
   
        mostrarTabla(conexion, "curso");

}
     public static void mostrarDatosBD_PROFESOR() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "profesor");
 
}
      public static void mostrarDatosBD_ESTUDIANTE() {
     Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "estudiante");

    
}
      
       public static void mostrarDatosBD_CURSO_PROFESOR() {
     Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "curso_profesor");
  
}
        public static void mostrarDatosBD_INSCRIPCION() {
     Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "inscripcion"); // Singular, no "inscripciones"

   
}
         public static void mostrarDatosBD_CURSOS_INSCRITOS() {
     Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
        mostrarTabla(conexion, "cursos_inscritos"); // Guion bajo en lugar de junto
       
}
          public static void mostrarDatosBD_CURSO_PROFESORES() {
     Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
        mostrarTabla(conexion, "curso_profesores");
}
           public static void mostrarDatosBD_INSCRIPCIONES_PERSONAS() {
    Connection conexion = getInstance().getConnection();
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "inscripciones_personas");// Guion bajo en lugar de junto
}
     
     public static void mostrarDatos_CursosInscritos() {
    Connection conexion = getInstance().getConnection();
        mostrarTabla(conexion, "cursos_inscritos"); // Guion bajo en lugar de junto
}

     public static void mostrarDatos_cursoProfesores() {
     Connection conexion = getInstance().getConnection();
        mostrarTabla(conexion, "curso_profesores"); // Guion bajo en lugar de junto
}

     
     public static void mostrarDatos_inscripcionesPersonas() {
         System.out.print("mostrando inscripciones personas");
     Connection conexion = getInstance().getConnection();
        mostrarTabla(conexion, "inscripciones_personas"); 
    
} 

    private static void mostrarTabla(Connection conexion, String nombreTabla) {
        System.out.println("\n🔹 TABLA: " + nombreTabla.toUpperCase());
        String sql = "SELECT * FROM " + nombreTabla + ";";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int columnas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("⚠️ No se pudieron recuperar los datos de " + nombreTabla);
        }
    }
    
    
}

