package com.mycompany.conceptos_poo_maven;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class InscripcionApp extends JFrame {
 private JTextField idPersona, nombres, apellidos, email;
private JTextField idProfesor, idCursoProfesor, anio, semestre, TipoContrato;
private JTextField idInscripcion, idEstudiante;
private JTextField idCurso, nombreCurso, idProgramaCurso;
private JTextField idFacultad, nombreFacultad, idDecano;
private JTextField idPrograma, nombrePrograma, duracionPrograma, fechaRegistroPrograma, idFacultadPrograma;
private JTextField idCursoInscripcion, idEstudianteInscripcion, semestreInscripcion;
private JTextArea outputArea;


    public InscripcionApp() {
        setTitle("Sistema de Inscripciones");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inscribir Persona", crearPanelPersona());
        tabbedPane.addTab("Inscribir Profesor", crearPanelProfesor());
        tabbedPane.addTab("Inscribir Facultad", crearPanelFacultad());
        tabbedPane.addTab("Inscribir Programa", crearPanelPrograma());
        tabbedPane.addTab("Inscribir Curso", crearPanelCurso());
        tabbedPane.addTab("Inscribir Estudiante", crearPanelEstudiante());
        tabbedPane.addTab("Agregar Inscripcion", crearPanelInscripcion());
        tabbedPane.addTab("Asignar CursoProfesor", crearPanelCursoProfesor());
        tabbedPane.addTab("CRUD CursosInscritos", crearPanelCursosInscritos());
        tabbedPane.addTab("CURD CursosProfesores", crearPanelCursoProfesores());
        tabbedPane.addTab("CURD InscripcionesPersonas", crearPanelInscripcionPersona());
        
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);

        add(tabbedPane, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }


private JPanel crearPanelPersona() {
    JPanel panel = new JPanel(new GridLayout(6, 2));

    JTextField idPersona = new JTextField("9966");
    JTextField nombresPersona = new JTextField("Giovany");
    JTextField apellidosPersona = new JTextField("Ferrero");
    JTextField emailPersona = new JTextField("GioFerrS@example.com");

    JButton btnGuardarPersona = new JButton("Guardar Persona");

    // Acción para guardar persona con validación de ID
    btnGuardarPersona.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idPersona.getText().trim()); // Intenta convertir a entero
            guardarPersona(idPersona, nombresPersona, apellidosPersona, emailPersona);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("Ingrese ID Persona:")); panel.add(idPersona);
    panel.add(new JLabel("Nombres:")); panel.add(nombresPersona);
    panel.add(new JLabel("Apellidos:")); panel.add(apellidosPersona);
    panel.add(new JLabel("Email:")); panel.add(emailPersona);
    panel.add(btnGuardarPersona);
    return panel;
}

private void guardarPersona(JTextField idPersona, JTextField nombresPersona, JTextField apellidosPersona, JTextField emailPersona) {
    try {
        int idPersonaVal = Integer.parseInt(idPersona.getText());
        String nombresVal = nombresPersona.getText();
        String apellidosVal = apellidosPersona.getText();
        String emailVal = emailPersona.getText();
        
        if (nombresVal.isEmpty() || apellidosVal.isEmpty() || emailVal.isEmpty()) {
            mostrarError("Todos los campos deben estar completos.");
            return;
        }
        // Crear el objeto Persona y guardarlo
        Persona nuevaPersona = new Persona(idPersonaVal, nombresVal, apellidosVal, emailVal);
        try (Connection conexion = ConexionBD.conectar()) {
            nuevaPersona.guardarPersonaBD(conexion);
            outputArea.append("Persona guardada: " + idPersonaVal + "\n");
            ConexionBD.mostrarDatosBD_PERSONA();
        } catch (SQLException e) {
            mostrarError("Error al guardar la persona: " + e.getMessage());
        }
    } catch (NumberFormatException ex) {
        mostrarError("Datos inválidos. Asegúrate de ingresar valores numéricos para el ID.");
    }
}

    private JPanel crearPanelInscripcionPersona() {
    JPanel panel = new JPanel(new GridLayout(5, 2));
    JTextField idPersona = new JTextField("9176");
    JButton btnInscribir = new JButton("Inscribir Persona");
    JButton btnEliminar = new JButton("Eliminar Persona");
    JButton btnActualizar = new JButton("Actualizar Persona");

    // Acción para inscribir en inscripciones_personas con validaciones
    btnInscribir.addActionListener(e -> {
        try {
            int idPersonaVal = Integer.parseInt(idPersona.getText().trim());

            // Validar si la persona existe en la tabla PERSONA
            if (!existePersona(idPersonaVal)) {
                JOptionPane.showMessageDialog(panel, "Error: La persona no está registrada en la tabla PERSONA.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Inscribir en inscripciones_personas
            inscribirPersona(idPersonaVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Acción para eliminar una persona de inscripciones_personas
    btnEliminar.addActionListener(e -> {
        try {
            int idPersonaVal = Integer.parseInt(idPersona.getText().trim());
            eliminarPersona(idPersonaVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Acción para actualizar una persona en inscripciones_personas
    btnActualizar.addActionListener(e -> {
        try {
            int idPersonaVal = Integer.parseInt(idPersona.getText().trim());
            actualizarPersona(idPersonaVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID de Persona:"));
    panel.add(idPersona);
    panel.add(btnInscribir);
    panel.add(btnEliminar);
    panel.add(btnActualizar);
    
    return panel;
}

private void eliminarPersona(int idPersona) {
    String sqlEliminar = "DELETE FROM inscripciones_personas WHERE persona_id = ?";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmtEliminar = conexion.prepareStatement(sqlEliminar)) {
        stmtEliminar.setInt(1, idPersona);
        
        int filasAfectadas = stmtEliminar.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            outputArea.append("Persona eliminada correctamente de inscripciones.\n");
        } else {
            JOptionPane.showMessageDialog(null, "La persona no está en inscripciones_personas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al eliminar persona: " + e.getMessage());
    }
}

private void actualizarPersona(int idPersona) {
    String sqlActualizar = "UPDATE inscripciones_personas SET nombres = ?, apellidos = ?, email = ? WHERE persona_id = ?";
    String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement stmtActualizar = conexion.prepareStatement(sqlActualizar)) {
        
        stmtBuscar.setInt(1, idPersona);
        ResultSet rs = stmtBuscar.executeQuery();
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "La persona no está registrada en la tabla PERSONA.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nombres = rs.getString("NOMBRES");
        String apellidos = rs.getString("APELLIDOS");
        String email = rs.getString("EMAIL");
        
        stmtActualizar.setString(1, nombres);
        stmtActualizar.setString(2, apellidos);
        stmtActualizar.setString(3, email);
        stmtActualizar.setInt(4, idPersona);
        
        int filasAfectadas = stmtActualizar.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            outputArea.append("Persona actualizada correctamente en inscripciones.\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la persona en inscripciones_personas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al actualizar persona: " + e.getMessage());
    }
}


private boolean existePersona(int idPersona) {
    String sql = "SELECT COUNT(*) FROM PERSONA WHERE ID = ?";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idPersona);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al validar existencia en PERSONA: " + e.getMessage());
    }
    return false;
}

private void inscribirPersona(int idPersona) {
    String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
    String sqlInsertar = "INSERT INTO inscripciones_personas (persona_id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement stmtInsertar = conexion.prepareStatement(sqlInsertar)) {
        
        // Obtener datos de la persona
        stmtBuscar.setInt(1, idPersona);
        ResultSet rs = stmtBuscar.executeQuery();
        if (!rs.next()) return;
        
        String nombres = rs.getString("NOMBRES");
        String apellidos = rs.getString("APELLIDOS");
        String email = rs.getString("EMAIL");

        // Insertar en inscripciones_personas
        stmtInsertar.setInt(1, idPersona);
        stmtInsertar.setString(2, nombres);
        stmtInsertar.setString(3, apellidos);
        stmtInsertar.setString(4, email);
        
        int filasAfectadas = stmtInsertar.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            outputArea.append("Persona Agregada a Inscripcipcion correctamente.\n");} else {
            JOptionPane.showMessageDialog(null, "No se pudo inscribir a la persona.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al inscribir persona: " + e.getMessage());
    }
}


private JPanel crearPanelProfesor() {
    JPanel panel = new JPanel(new GridLayout(6, 2));

    JTextField idProfesor = new JTextField("7755");
    JTextField nombresProfesor = new JTextField("Juan");
    JTextField apellidosProfesor = new JTextField("Pérez");
    JTextField emailProfesor = new JTextField("juan@example.com");
    JTextField tipoContratoProfesor = new JTextField("Catedratico");

    JButton btnGuardarProfesor = new JButton("Guardar Profesor");

    // Acción para guardar profesor con validación de ID
    btnGuardarProfesor.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idProfesor.getText().trim()); // Intenta convertir a entero
            guardarProfesor(idProfesor, nombresProfesor, apellidosProfesor, emailProfesor, tipoContratoProfesor);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("Ingrese ID Profesor:")); panel.add(idProfesor);
    panel.add(new JLabel("Nombres:")); panel.add(nombresProfesor);
    panel.add(new JLabel("Apellidos:")); panel.add(apellidosProfesor);
    panel.add(new JLabel("Email:")); panel.add(emailProfesor);
    panel.add(new JLabel("Tipo de Contrato:")); panel.add(tipoContratoProfesor);
    panel.add(btnGuardarProfesor);
   

    return panel;
}


private void guardarProfesor(JTextField idProfesor, JTextField nombresProfesor, JTextField apellidosProfesor, JTextField emailProfesor, JTextField tipoContratoProfesor) {
    try {
        int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
        String nombresVal = nombresProfesor.getText().trim();
        String apellidosVal = apellidosProfesor.getText().trim();
        String emailVal = emailProfesor.getText().trim();
        String tipoContratoVal = tipoContratoProfesor.getText().trim();

        if (nombresVal.isEmpty() || apellidosVal.isEmpty() || emailVal.isEmpty() || tipoContratoVal.isEmpty()) {
            mostrarError("Todos los campos deben estar completos.");
            return;
        }

        // Crear el objeto Profesor y guardarlo
        Profesor nuevoProfesor = new Profesor(idProfesorVal, nombresVal, apellidosVal, emailVal, tipoContratoVal);
        try (Connection conexion = ConexionBD.conectar()) {
            nuevoProfesor.guardarProfesorBD(conexion);
            ConexionBD.mostrarDatosBD_PROFESOR();
            outputArea.append("Profesor guardado: " + idProfesorVal + "\n");
        } catch (SQLException e) {
            mostrarError("Error al guardar el profesor: " + e.getMessage());
        }
    } catch (NumberFormatException ex) {
        mostrarError("Datos inválidos. Asegúrate de ingresar valores numéricos para el ID.");
    }
}


private JPanel crearPanelEstudiante() {
    JPanel panel = new JPanel(new GridLayout(5, 2));
    
    JTextField idEstudiante = new JTextField("1199");
    JTextField codigoEstudiante = new JTextField("333337777");
    JTextField nombresEstudiante = new JTextField("Carla");
    JTextField apellidosEstudiante = new JTextField("Ramirez");
    JTextField emailEstudiante = new JTextField("CarlaR@gmail.com");
    JTextField promedioEstudiante = new JTextField("4.5");
    JTextField idPrograma = new JTextField("603");
    JCheckBox activoEstudiante = new JCheckBox("Activo", true);
    JButton btnGuardarEstudiante = new JButton("Guardar Estudiante");

    // Acción para guardar Estudiante con validación de ID y Programa existente
    btnGuardarEstudiante.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idEstudiante.getText().trim());
            int codigo = Integer.parseInt(codigoEstudiante.getText().trim());
            String nombres = nombresEstudiante.getText().trim();
            String apellidos = apellidosEstudiante.getText().trim();
            String email = emailEstudiante.getText().trim();
            double promedio = Double.parseDouble(promedioEstudiante.getText().trim());
            int idProgramaVal = Integer.parseInt(idPrograma.getText().trim());
            boolean activo = activoEstudiante.isSelected();

            if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Ningún campo de texto puede estar vacío.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Programa programa = obtenerProgramaPorID(idProgramaVal);
            if (programa == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un programa con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarEstudiante(id, codigo, nombres, apellidos, email, promedio, programa, activo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID, código y promedio deben ser valores numéricos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    panel.add(new JLabel("Ingrese ID Estudiante:")); panel.add(idEstudiante);
    panel.add(new JLabel("Código Estudiantil:")); panel.add(codigoEstudiante);
    panel.add(new JLabel("Nombres:")); panel.add(nombresEstudiante);
    panel.add(new JLabel("Apellidos:")); panel.add(apellidosEstudiante);
    panel.add(new JLabel("Email:")); panel.add(emailEstudiante);
    panel.add(new JLabel("Promedio:")); panel.add(promedioEstudiante);
    panel.add(new JLabel("ID Programa(Defecto):")); panel.add(idPrograma);
    panel.add(new JLabel("Activo:")); panel.add(activoEstudiante);
    panel.add(btnGuardarEstudiante);
    
    return panel;
}



private void guardarEstudiante(int id, int codigo, String nombres, String apellidos, String email, double promedio, Programa programa, boolean activo) {
    try (Connection conexion = ConexionBD.conectar()) {
        Estudiante nuevoEstudiante = new Estudiante(codigo, programa, activo , promedio,id, nombres, apellidos, email);
        nuevoEstudiante.guardarEstudianteBD(conexion);
        ConexionBD.mostrarDatosBD_ESTUDIANTE();
        outputArea.append("Estudiante guardado: " + id + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar el estudiante: " + e.getMessage());
    }
}


private JPanel crearPanelInscripcion() {
    JPanel panel = new JPanel(new GridLayout(5, 2));
    
    JTextField idCurso = new JTextField("912");
    JTextField idEstudiante = new JTextField("1123498175");
    JTextField año = new JTextField("1997");
    JTextField semestre = new JTextField("2");
    JButton btnGuardarInscripcion = new JButton("Guardar Inscripción");

    // Acción para guardar Inscripción con validaciones
    btnGuardarInscripcion.addActionListener(e -> {
        try {
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());
            int añoVal = Integer.parseInt(año.getText().trim());
            int semestreVal = Integer.parseInt(semestre.getText().trim());

            // Validación de semestre (solo 1 o 2)
            if (semestreVal < 1 || semestreVal > 2) {
                JOptionPane.showMessageDialog(panel, "Error: El semestre debe ser 1 o 2.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso curso = obtenerCursoPorID(idCursoVal);
            if (curso == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un curso con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Estudiante estudiante = obtenerEstudiantePorID(idEstudianteVal);
            if (estudiante == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un estudiante con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarInscripcion(curso, añoVal, semestreVal, estudiante);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID, año y semestre deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID Curso(O Dejelo por defecto):")); panel.add(idCurso);
    panel.add(new JLabel("ID Estudiante(O Dejelo por defecto):")); panel.add(idEstudiante);
    panel.add(new JLabel("Año:")); panel.add(año);
    panel.add(new JLabel("Semestre:")); panel.add(semestre);
    panel.add(btnGuardarInscripcion);

    return panel;
}

private Curso obtenerCursoPorID(int idCurso) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idCurso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        obtenerProgramaPorID(rs.getInt("programa_id")),
                        rs.getBoolean("activo")
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar el curso: " + e.getMessage());
    }
    return null;
}

private Estudiante obtenerEstudiantePorID(int idEstudiante) {
    try (Connection conexion = ConexionBD.conectar()) {
        // Crear la tabla si no existe
        String sqlEstudiante = "CREATE TABLE IF NOT EXISTS estudiante (" +
                "id INT PRIMARY KEY, " +
                "codigo INT, " +
                "programa_id INT, " +
                "activo BOOLEAN, " +
                "promedio DOUBLE, " +
                "FOREIGN KEY (id) REFERENCES persona(id), " +
                "FOREIGN KEY (programa_id) REFERENCES programa(id));";

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlEstudiante);
        }

        // Consulta para obtener el estudiante con sus datos personales
        String sql = "SELECT e.id, e.codigo, e.programa_id, e.activo, e.promedio, " +
                     "p.nombres, p.apellidos, p.email " +
                     "FROM estudiante e " +
                     "JOIN persona p ON e.id = p.id " +
                     "WHERE e.id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Estudiante(
                        rs.getInt("codigo"),
                        obtenerProgramaPorID(rs.getInt("programa_id")),
                        rs.getBoolean("activo"),
                        rs.getDouble("promedio"),
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar el estudiante: " + e.getMessage());
    }
    return null;
}



private void guardarInscripcion(Curso curso, int año, int semestre, Estudiante estudiante) {
    try (Connection conexion = ConexionBD.conectar()) {
        Inscripcion nuevaInscripcion = new Inscripcion(curso, año, semestre, estudiante);
        nuevaInscripcion.guardarInscripcionBD(conexion);
        ConexionBD.mostrarDatosBD_INSCRIPCION();
        outputArea.append("Inscripción guardada: Curso " + curso.getID() + " - Estudiante " + estudiante.getID() + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar la inscripción: " + e.getMessage());
    }
}

private JPanel crearPanelCursoProfesor() {
    JPanel panel = new JPanel(new GridLayout(5, 2));
    
    JTextField idCurso = new JTextField("912");
    JTextField idProfesor = new JTextField("5789");
    JTextField anio = new JTextField("2024");
    JTextField semestre = new JTextField("2");
    JButton btnGuardarCursoProfesor = new JButton("Asignar Profesor");

    // Acción para guardar la asignación con validaciones
    btnGuardarCursoProfesor.addActionListener(e -> {
        try {
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
            int anioVal = Integer.parseInt(anio.getText().trim());
            int semestreVal = Integer.parseInt(semestre.getText().trim());

            // Validación de semestre (solo 1 o 2)
            if (semestreVal < 1 || semestreVal > 2) {
                JOptionPane.showMessageDialog(panel, "Error: El semestre debe ser 1 o 2.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso curso = obtenerCursoPorID(idCursoVal);
            if (curso == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un curso con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Profesor profesor = obtenerProfesorPorID(idProfesorVal);
            if (profesor == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un profesor con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarCursoProfesor(curso, anioVal, semestreVal, profesor);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID, año y semestre deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID Curso (O déjelo por defecto):")); panel.add(idCurso);
    panel.add(new JLabel("ID Profesor (O déjelo por defecto):")); panel.add(idProfesor);
    panel.add(new JLabel("Año:")); panel.add(anio);
    panel.add(new JLabel("Semestre:")); panel.add(semestre);
    panel.add(btnGuardarCursoProfesor);

    return panel;
}

private Profesor obtenerProfesorPorID(int idProfesor) {
    try (Connection conexion = ConexionBD.conectar()) {
        // 🔹 Consulta combinando profesor y persona para obtener toda la información
        String sql = "SELECT p.id, pe.nombres, pe.apellidos, pe.email, p.tipo_contrato " +
                     "FROM profesor p " +
                     "JOIN persona pe ON p.id = pe.id " +
                     "WHERE p.id = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idProfesor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Profesor(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("tipo_contrato")
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar el profesor: " + e.getMessage());
    }
    return null;
}


private void guardarCursoProfesor(Curso curso, int anio, int semestre, Profesor profesor) {
    try (Connection conexion = ConexionBD.conectar()) {
        CursoProfesor nuevaAsignacion = new CursoProfesor(profesor, anio, semestre, curso);
        nuevaAsignacion.guardarCursoProfesorBD(conexion);
        ConexionBD.mostrarDatosBD_CURSO_PROFESOR();
        outputArea.append("Asignación guardada: Curso " + curso.getID() + " - Profesor " + profesor.getID() + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar la asignación: " + e.getMessage());
    }
}



private JPanel crearPanelCurso() {
    JPanel panel = new JPanel(new GridLayout(6, 2));
    
    JTextField idCurso = new JTextField("4411");
    JTextField nombreCurso = new JTextField("Matemáticas");
    JTextField idPrograma = new JTextField("603");
    JCheckBox activoCurso = new JCheckBox("Activo", true);
    JButton btnGuardarCurso = new JButton("Guardar Curso");

    // Acción para guardar Curso con validación de ID y Programa existente
    btnGuardarCurso.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idCurso.getText().trim());
            String nombre = nombreCurso.getText().trim();
            int idProgramaVal = Integer.parseInt(idPrograma.getText().trim());
            boolean activo = activoCurso.isSelected();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: El nombre del curso no puede estar vacío.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Programa programa = obtenerProgramaPorID(idProgramaVal);
            if (programa == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un programa con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarCurso(id, nombre, programa, activo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    panel.add(new JLabel("Ingrese ID Curso:")); panel.add(idCurso);
    panel.add(new JLabel("Nombre:")); panel.add(nombreCurso);
    panel.add(new JLabel("ID Programa (O Déjelo por defecto):")); panel.add(idPrograma);
    panel.add(new JLabel("Activo:")); panel.add(activoCurso);
    panel.add(btnGuardarCurso);
    
    return panel;
}

private Programa obtenerProgramaPorID(int idPrograma) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM programa WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPrograma);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Programa(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        obtenerFacultadPorID(rs.getInt("facultad_id")) // Obtener la facultad asociada
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar el programa: " + e.getMessage());
    }
    return null;
}

private void guardarCurso(int idCurso, String nombre, Programa programa, boolean activo) {
    try (Connection conexion = ConexionBD.conectar()) {
        Curso nuevoCurso = new Curso(idCurso, nombre, programa, activo);
        nuevoCurso.guardarCursoBD(conexion);
        ConexionBD.mostrarDatosBD_CURSO();
        outputArea.append("Curso guardado: " + idCurso + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar el curso: " + e.getMessage());
    }
}


private JPanel crearPanelPrograma() {
    JPanel panel = new JPanel(new GridLayout(6, 2));
    
    JTextField idPrograma = new JTextField("1188");
    JTextField nombrePrograma = new JTextField("Ingeniería de Software");
    JTextField duracionPrograma = new JTextField("8");
    JTextField idFacultad = new JTextField("1234");
    JButton btnGuardarPrograma = new JButton("Guardar Programa");

    // Acción para guardar Programa con validación de ID y Facultad existente
    btnGuardarPrograma.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idPrograma.getText().trim());
            String nombre = nombrePrograma.getText().trim();
            double duracion = Double.parseDouble(duracionPrograma.getText().trim());
            int idFacultadVal = Integer.parseInt(idFacultad.getText().trim());
            Date fechaRegistro = new Date();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: El nombre no puede estar vacío.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Facultad facultad = obtenerFacultadPorID(idFacultadVal);
            if (facultad == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una facultad con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarPrograma(id, nombre, duracion, fechaRegistro, facultad);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID y la duración deben ser números.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    panel.add(new JLabel("Ingrese ID Programa:")); panel.add(idPrograma);
    panel.add(new JLabel("Nombre:")); panel.add(nombrePrograma);
    panel.add(new JLabel("Duración (semestres):")); panel.add(duracionPrograma);
    panel.add(new JLabel("ID Facultad (O Dejelo por defecto):")); panel.add(idFacultad);
    panel.add(btnGuardarPrograma);
    
    return panel;
}

private Facultad obtenerFacultadPorID(int idFacultad) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idFacultad);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Facultad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        obtenerPersonaPorID(rs.getInt("decano_id")) // Recuperar también al decano
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar la facultad: " + e.getMessage());
    }
    return null;
}

private void guardarPrograma(int idPrograma, String nombre, double duracion, Date registro, Facultad facultad) {
    try (Connection conexion = ConexionBD.conectar()) {
        Programa nuevoPrograma = new Programa(idPrograma, nombre, duracion, registro, facultad);
        nuevoPrograma.guardarProgramaBD(conexion);
        ConexionBD.mostrarDatosBD_PROGRAMA();
        outputArea.append("Programa guardado: " + idPrograma + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar el programa: " + e.getMessage());
    }
}


private JPanel crearPanelFacultad() {
    JPanel panel = new JPanel(new GridLayout(4, 2));
    
    JTextField idFacultad = new JTextField("8271");
    JTextField nombreFacultad = new JTextField("Facultad de Economia");
    JTextField idDecano = new JTextField("7392");
    JButton btnGuardarFacultad = new JButton("Guardar Facultad");

    // Acción para guardar Facultad con validación de ID y decano existente
    btnGuardarFacultad.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idFacultad.getText().trim());
            int idDecanoVal = Integer.parseInt(idDecano.getText().trim());
            String nombre = nombreFacultad.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: El nombre no puede estar vacío.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Persona decano = obtenerPersonaPorID(idDecanoVal);
            if (decano == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una persona con ese ID en la base de datos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            guardarFacultad(id, nombre, decano);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    panel.add(new JLabel("Ingrese ID Facultad:")); panel.add(idFacultad);
    panel.add(new JLabel("Nombre:")); panel.add(nombreFacultad);
    panel.add(new JLabel("ID Decano(O Dejelo por defecto):" +
"    ")); panel.add(idDecano);
    panel.add(btnGuardarFacultad);
    
    return panel;
}

private Persona obtenerPersonaPorID(int idPersona) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM persona WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar la persona: " + e.getMessage());
    }
    return null;
}

private void guardarFacultad(int idFacultad, String nombreFacultad, Persona decano) {
    try (Connection conexion = ConexionBD.conectar()) {
        Facultad nuevaFacultad = new Facultad(idFacultad, nombreFacultad, decano);
        nuevaFacultad.guardarFacultadBD(conexion);
        ConexionBD.mostrarDatosBD_FACULTAD();
        outputArea.append("Facultad guardada: " + idFacultad + "\n");
    } catch (SQLException e) {
        mostrarError("Error al guardar la facultad: " + e.getMessage());
    }
}

private JPanel crearPanelCursosInscritos() { 
    JPanel panel = new JPanel(new GridLayout(6, 2));
    
    JTextField idCurso = new JTextField("912");
    JTextField idEstudiante = new JTextField("1123981625");
    JButton btnGuardarInscripcion = new JButton("Guardar Inscripción");
    JButton btnEliminarInscripcion = new JButton("Eliminar Inscripción");
    JButton btnActualizarInscripcion = new JButton("Actualizar Inscripción");
    // Acción para guardar inscripción en cursos_inscritos
    btnGuardarInscripcion.addActionListener(e -> {
        try {
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

            // Validar si el estudiante existe
            Estudiante estudiante = obtenerEstudiantePorID(idEstudianteVal);
            if (estudiante == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un estudiante con ese ID.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar inscripción con ese curso y estudiante
            Inscripcion inscripcion = obtenerInscripcionPorCursoYEstudiante(idCursoVal, idEstudianteVal);
            if (inscripcion == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una inscripción para este curso y estudiante.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar inscripción en cursos_inscritos
            inscribirEnCursos(inscripcion);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    // Acción para eliminar inscripción en cursos_inscritos
    btnEliminarInscripcion.addActionListener(e -> {
        try {
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());
            
            // Buscar inscripción con ese curso y estudiante
            Inscripcion inscripcion = obtenerInscripcionPorCursoYEstudiante(idCursoVal, idEstudianteVal);
            if (inscripcion == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una inscripción para este curso y estudiante.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Eliminar inscripción
            eliminarInscripcionDeCursos(inscripcion);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    // Acción para actualizar inscripción en cursos_inscritos
    btnActualizarInscripcion.addActionListener(e -> {
        try {
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

            // Validar si existe la inscripción antes de actualizar
            if (!existeInscripcionEnCurso(idCursoVal, idEstudianteVal)) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una inscripción para actualizar.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar inscripción
            actualizarInscripcionCurso(idCursoVal, idEstudianteVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID Curso:")); panel.add(idCurso);
    panel.add(new JLabel("ID Estudiante:")); panel.add(idEstudiante);
    panel.add(btnGuardarInscripcion);
    panel.add(btnEliminarInscripcion);
    panel.add(btnActualizarInscripcion);
    
    return panel;
}


private boolean existeInscripcionEnCurso(int idCurso, int idEstudiante) {
    String sql = "SELECT COUNT(*) FROM cursos_inscritos WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?";
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idCurso);
        stmt.setInt(2, idEstudiante);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        mostrarError("Error al verificar la inscripción: " + e.getMessage());
    }
    return false;
}


private void actualizarInscripcionCurso(int idCurso, int idEstudiante) {
    String sql = "UPDATE cursos_inscritos SET INSCRIPCION_ID = ?, ESTUDIANTE_ID = ? WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?";
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idCurso);
        stmt.setInt(2, idEstudiante);
        stmt.setInt(3, idCurso);
        stmt.setInt(4, idEstudiante);
        
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
            outputArea.append("Inscripción actualizada correctamente.\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la inscripción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al actualizar la inscripción: " + e.getMessage());
    }
}

private boolean existeInscripcion(int idCurso, int idEstudiante) {
    String sql = "SELECT COUNT(*) FROM cursos_inscritos WHERE curso_id = ? AND estudiante_id = ?";
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idCurso);
        stmt.setInt(2, idEstudiante);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al verificar la inscripción: " + e.getMessage());
    }
    return false;
}


private void eliminarInscripcionDeCursos(Inscripcion inscripcion) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "DELETE FROM cursos_inscritos WHERE inscripcion_id = ? AND estudiante_id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, inscripcion.getCurso().getID());
            pstmt.setInt(2, inscripcion.getEstudiante().getID());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
                outputArea.append("Inscripcion de curso eliminado correctamente.\n");
       } else {
                JOptionPane.showMessageDialog(null, "No se encontró la inscripción para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al eliminar la inscripción: " + e.getMessage());
    }
}


private Inscripcion obtenerInscripcionPorCursoYEstudiante(int cursoID, int estudianteID) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM inscripcion WHERE curso_id = ? AND estudiante_id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cursoID);
            pstmt.setInt(2, estudianteID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Inscripcion(
                        obtenerCursoPorID(rs.getInt("curso_id")),
                        rs.getInt("anio"),
                        rs.getInt("semestre"),
                        obtenerEstudiantePorID(rs.getInt("estudiante_id"))
                    );
                }
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al buscar la inscripción: " + e.getMessage());
    }
    return null;
}


private void inscribirEnCursos(Inscripcion inscripcion) {
    String sql = "INSERT INTO cursos_inscritos (inscripcion_id, estudiante_id) VALUES (?, ?)";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, inscripcion.getCurso().getID());
        stmt.setInt(2, inscripcion.getEstudiante().getID());
        
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
            outputArea.append("Curso inscrito correctamente.\n");
             } 
        else {
            JOptionPane.showMessageDialog(null, "No se pudo inscribir al estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al inscribir en cursos: " + e.getMessage());
    }
}

private JPanel crearPanelCursoProfesores() {
    JPanel panel = new JPanel(new GridLayout(5, 2));
    
    JTextField idProfesor = new JTextField("9176");
    JTextField idCurso = new JTextField("912");
    JButton btnGuardarCursoProfesor = new JButton("Guardar Asignación");
    JButton btnEliminarCursoProfesores = new JButton("Eliminar Asignación");
    JButton btnActualizarCursoProfesor = new JButton("Actualizar Asignación");
   
    // Acción para guardar asignación en curso_profesores con validaciones
    btnGuardarCursoProfesor.addActionListener(e -> {
        try {
            int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());

            // Validar si la combinación existe en curso_profesor
            if (!existeCursoProfesor(idProfesorVal, idCursoVal)) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una asignación de este profesor a este curso en curso_profesor.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Guardar asignación en curso_profesores
            inscribirCursoProfesor(idProfesorVal, idCursoVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los valores deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Acción para eliminar la asignación en curso_profesores
    btnEliminarCursoProfesores.addActionListener(e -> {
        try {
            int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());

            if (!existeCursoProfesores(idProfesorVal, idCursoVal)) {
                JOptionPane.showMessageDialog(panel, "Error: No se encontró la asignación a eliminar en curso_profesores.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            eliminarCursoProfesores(idProfesorVal, idCursoVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los valores deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    // Acción para actualizar la asignación en curso_profesores
    btnActualizarCursoProfesor.addActionListener(e -> {
        try {
            int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
            int idCursoVal = Integer.parseInt(idCurso.getText().trim());
            
            if (!existeCursoProfesor(idProfesorVal, idCursoVal)) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una asignación para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            actualizarCursoProfesor(idProfesorVal, idCursoVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los valores deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID Profesor (O Dejar por defecto):")); panel.add(idProfesor);
    panel.add(new JLabel("ID Curso (O Dejar por defecto):")); panel.add(idCurso);
    panel.add(btnGuardarCursoProfesor);
    panel.add(btnEliminarCursoProfesores);
    panel.add(btnActualizarCursoProfesor);
    
    return panel;
}

private void actualizarCursoProfesor(int idProfesor, int idCurso) {
    String sql = "UPDATE curso_profesores SET profesor_id = ?, curso_id = ? WHERE profesor_id = ? AND curso_id = ?";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idProfesor);
        stmt.setInt(2, idCurso);
        stmt.setInt(3, idProfesor);
        stmt.setInt(4, idCurso);
        
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_CURSO_PROFESORES();
            outputArea.append("Asignación actualizada correctamente.\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la asignación.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al actualizar la asignación en curso_profesores: " + e.getMessage());
    }
}


private void eliminarCursoProfesores(int idProfesor, int idCurso) {
    String sql = "DELETE FROM curso_profesores WHERE profesor_id = ? AND curso_id = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idProfesor);
        stmt.setInt(2, idCurso);

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_CURSO_PROFESORES();  // Refrescar los datos en la interfaz
            outputArea.append("Asignación en curso_profesores eliminada correctamente.\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la asignación a eliminar en curso_profesores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al eliminar la asignación en curso_profesores: " + e.getMessage());
    }
}

private boolean existeCursoProfesores(int idProfesor, int idCurso) {
    String sql = "SELECT COUNT(*) FROM curso_profesores WHERE profesor_id = ? AND curso_id = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idProfesor);
        stmt.setInt(2, idCurso);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al validar asignación en curso_profesores: " + e.getMessage());
    }
    return false;
}


private boolean existeCursoProfesor(int idProfesor, int idCurso) {
    String sql = "SELECT COUNT(*) FROM curso_profesor WHERE profesor_id = ? AND curso_id = ?";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idProfesor);
        stmt.setInt(2, idCurso);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException e) {
        mostrarError("Error al validar asignación en curso_profesor: " + e.getMessage());
    }
    return false;
}

private void inscribirCursoProfesor(int idProfesor, int idCurso) {
    String sql = "INSERT INTO curso_profesores (profesor_id, curso_id) VALUES (?, ?)";
    
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idProfesor);
        stmt.setInt(2, idCurso);
        
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            ConexionBD.mostrarDatosBD_CURSO_PROFESORES();
            outputArea.append("Profesor asignado al curso correctamente.\n");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo asignar al profesor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        mostrarError("Error al asignar profesor al curso: " + e.getMessage());
    }
}


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InscripcionApp().setVisible(true));
    }
}
