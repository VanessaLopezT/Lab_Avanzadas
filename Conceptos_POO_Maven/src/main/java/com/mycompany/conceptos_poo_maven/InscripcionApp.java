package com.mycompany.conceptos_poo_maven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        //tabbedPane.addTab("Profesores", crearPanelProfesores());
        //tabbedPane.addTab("Inscripciones", crearPanelInscripciones());
        //tabbedPane.addTab("Inscripciones", crearPanelInscripciones());
        
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

    private void guardarInscripcionPersona(JTextField idPersona, JTextField nombresPersona, JTextField apellidosPersona, JTextField emailPersona) {
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
        InscripcionesPersona nuevainscripcionpersona=new InscripcionesPersona();
        nuevainscripcionpersona.inscribir(nuevaPersona);
        try (Connection conexion = ConexionBD.conectar()) {
            nuevaPersona.guardarPersonaBD(conexion);
            nuevainscripcionpersona.inscribir(nuevaPersona);
            outputArea.append("InsrcipcionPersona guardada: " + idPersonaVal + "\n");
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
        } catch (SQLException e) {
            mostrarError("Error al guardar la InsrcipcionPersona: " + e.getMessage());
        }
    } catch (NumberFormatException ex) {
        mostrarError("Datos inválidos. Asegúrate de ingresar valores numéricos para el ID.");
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
    JPanel panel = new JPanel(new GridLayout(5, 2));
    
    JTextField idInscripcion = new JTextField("140");
    JTextField idEstudiante = new JTextField("1123981625");
    JButton btnGuardarInscripcion = new JButton("Guardar Inscripción");

    // Acción para guardar inscripción en cursos_inscritos con validaciones
    btnGuardarInscripcion.addActionListener(e -> {
        try {
            int idInscripcionVal = Integer.parseInt(idInscripcion.getText().trim());
            int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

            // Validar si la inscripción existe
            Inscripcion inscripcion = obtenerInscripcionPorID(idInscripcionVal);
            if (inscripcion == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe una inscripción con ese ID.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar si el estudiante existe
            Estudiante estudiante = obtenerEstudiantePorID(idEstudianteVal);
            if (estudiante == null) {
                JOptionPane.showMessageDialog(panel, "Error: No existe un estudiante con ese ID.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar inscripción en cursos_inscritos
            inscribirEnCursos(inscripcion);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: Los ID deben ser números enteros.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    });

    panel.add(new JLabel("ID Inscripción(O Dejar por defecto):")); panel.add(idInscripcion);
    panel.add(new JLabel("ID Estudiante(O Dejar por defecto):")); panel.add(idEstudiante);
    panel.add(btnGuardarInscripcion);

    return panel;
}

private Inscripcion obtenerInscripcionPorID(int idInscripcion) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM inscripcion WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idInscripcion);
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


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InscripcionApp().setVisible(true));
    }
}
