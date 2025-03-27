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
    private static Properties propiedades = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/Recursos/configuracion.properties")) {
            propiedades.load(input); // Cargar las propiedades correctamente
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo de configuración.");
        }
    }

    private AuxiliarBD() {}

public static Connection conectar() throws SQLException {
    String url = propiedades.getProperty("db.url");
    String user = propiedades.getProperty("db.user");
    String password = propiedades.getProperty("db.password");

    if (url == null || url.isEmpty()) {
        throw new SQLException("Error: La URL de la base de datos no puede ser nula.");
    }

    // Verificar si el usuario existe antes de intentar crearlo
    if (!usuarioExiste(user, url)) { 
        crearUsuario(user, password, url);
    }

    return DriverManager.getConnection(url, user, password);
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


    
public static void probarConexion() {
    try (Connection conexion = conectar()) {
        if (conexion != null) {
            System.out.println("✅ Conexión exitosa a la base de datos H2.");
        } else {
            System.out.println("❌ No se pudo conectar a la base de datos.");
        }
    } catch (SQLException e) {
        System.out.println("Error al conectar: " + e.getMessage());
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
    try (Connection conexion = conectar(); Statement stmt = conexion.createStatement()) {
        String sqlPersona = "CREATE TABLE IF NOT EXISTS persona (" +
                "id INT PRIMARY KEY, nombres VARCHAR(255), apellidos VARCHAR(255), email VARCHAR(255));";
        stmt.execute(sqlPersona);

        
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
    try (Connection conexion = conectar()) {
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

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

     public static void mostrarDatosBD_PERSONA() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "persona");
     
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
      public static void mostrarDatosBD_FACULTAD() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "facultad");
   
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
      
       public static void mostrarDatosBD_PROGRAMA() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "programa");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
       
    public static void mostrarDatosBD_CURSO() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
   
        mostrarTabla(conexion, "curso");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void mostrarDatosBD_PROFESOR() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "profesor");
 
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
      public static void mostrarDatosBD_ESTUDIANTE() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "estudiante");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
      
       public static void mostrarDatosBD_CURSO_PROFESOR() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "curso_profesor");
  
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
        public static void mostrarDatosBD_INSCRIPCION() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "inscripcion"); // Singular, no "inscripciones"

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
         public static void mostrarDatosBD_CURSOS_INSCRITOS() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
        mostrarTabla(conexion, "cursos_inscritos"); // Guion bajo en lugar de junto
       
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
          public static void mostrarDatosBD_CURSO_PROFESORES() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");
        mostrarTabla(conexion, "curso_profesores");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
           public static void mostrarDatosBD_INSCRIPCIONES_PERSONAS() {
    try (Connection conexion = conectar()) {
        System.out.println("\n📌 MOSTRANDO DATOS ALMACENADOS EN LA BASE DE DATOS:");

        mostrarTabla(conexion, "inscripciones_personas");// Guion bajo en lugar de junto

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     
     public static void mostrarDatos_CursosInscritos() {
    try (Connection conexion = conectar()) {
        mostrarTabla(conexion, "cursos_inscritos"); // Guion bajo en lugar de junto
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

     public static void mostrarDatos_cursoProfesores() {
    try (Connection conexion = conectar()) {
        mostrarTabla(conexion, "curso_profesores"); // Guion bajo en lugar de junto
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

     
     public static void mostrarDatos_inscripcionesPersonas() {
         System.out.print("mostrando inscripciones personas");
    try (Connection conexion = conectar()) {
        mostrarTabla(conexion, "inscripciones_personas"); 
    } catch (SQLException e) {
        e.printStackTrace();
    }
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
    
    public static boolean existePersona(int personaID) {
        String sql = "SELECT COUNT(*) FROM persona WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, personaID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeFacultad(int facultadID) {
        String sql = "SELECT COUNT(*) FROM facultad WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, facultadID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existePrograma(int programaID) {
        String sql = "SELECT COUNT(*) FROM programa WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, programaID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean existeInscripcion(int cursoID, int estudianteID) {
    String sql = "SELECT COUNT(*) FROM inscripcion WHERE curso_id = ? AND estudiante_id = ?;";
    try (Connection conexion = conectar();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, cursoID);
        pstmt.setInt(2, estudianteID);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public static boolean existeCurso(int cursoID) {
        String sql = "SELECT COUNT(*) FROM curso WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cursoID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeProfesor(int profesorID) {
        String sql = "SELECT COUNT(*) FROM profesor WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, profesorID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeEstudiante(int estudianteID) {
        String sql = "SELECT COUNT(*) FROM estudiante WHERE id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, estudianteID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeCursoProfesor(int profesorID, int cursoID) {
        String sql = "SELECT COUNT(*) FROM curso_profesor WHERE profesor_id = ? AND curso_id = ?;";
        try (Connection conexion = conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, profesorID);
            pstmt.setInt(2, cursoID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

