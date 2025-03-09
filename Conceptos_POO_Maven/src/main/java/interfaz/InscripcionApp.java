package interfaz;

import modelo.CursoProfesor;
import BaseDatos.ConexionBD;
import DAO.CursoDAO;
import DAO.CursoProfesorDAO;
import DAO.DAOFactory;
import DAO.EstudianteDAO;
import DAO.FacultadDAO;
import DAO.InscripcionDAO;
import DAO.PersonaDAO;
import DAO.ProfesorDAO;
import DAO.ProgramaDAO;
import modelo.Inscripcion;
import modelo.Profesor;
import modelo.Programa;
import modelo.Persona;
import modelo.Facultad;
import modelo.Estudiante;
import modelo.Curso;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicios.CursoProfesorService;
import servicios.CursoProfesoresService;
import servicios.CursoService;
import servicios.CursosInscritosService;
import servicios.EstudianteService;
import servicios.FacultadService;
import servicios.InscripcionService;
import servicios.PersonaService;
import servicios.ProfesorService;
import servicios.ProgramaService;

public class InscripcionApp extends JFrame {
private final PersonaService personaService = new PersonaService();
private final ProfesorService profesorService = new ProfesorService();
private final EstudianteService estudianteService = new EstudianteService();
private final InscripcionService inscripcionService = new InscripcionService();
private final CursoProfesorService cursoProfesorService = new CursoProfesorService();
private final CursoService cursoService = new CursoService();
private final ProgramaService programaService = new ProgramaService();
private final FacultadService facultadService = new FacultadService();
private final CursosInscritosService cursosInscritosService = new CursosInscritosService();
private final CursoProfesoresService cursoProfesoresService = new CursoProfesoresService();
                           

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

        btnGuardarPersona.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idPersona.getText().trim());
                personaService.guardarPersona(id, nombresPersona.getText(), apellidosPersona.getText(), emailPersona.getText());
                
                outputArea.append("Persona guardada: " + id + "\n");
                ConexionBD.mostrarDatosBD_PERSONA();
            } catch (NumberFormatException ex) {
                mostrarError("Error: El ID debe ser un número entero.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar la persona: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Persona:")); panel.add(idPersona);
        panel.add(new JLabel("Nombres:")); panel.add(nombresPersona);
        panel.add(new JLabel("Apellidos:")); panel.add(apellidosPersona);
        panel.add(new JLabel("Email:")); panel.add(emailPersona);
        panel.add(btnGuardarPersona);
        panel.add(new JScrollPane(outputArea)); // Agrega el área de salida

        return panel;
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
            if (!personaService.existePersona(idPersonaVal)) {
                JOptionPane.showMessageDialog(panel, "Error: La persona no está registrada en la tabla PERSONA.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Inscribir en inscripciones_personas
            inscribirPersona(idPersonaVal);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Error: El ID debe ser un número entero.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(InscripcionApp.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            boolean eliminada = personaService.eliminarPersona(idPersona);
            if (eliminada) {
                outputArea.append("Persona eliminada correctamente de inscripciones.\n");
                ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            } else {
                mostrarError("La persona no está en inscripciones_personas.");
            }
        } catch (SQLException e) {
            mostrarError("Error al eliminar persona: " + e.getMessage());
        }
    }

private void actualizarPersona(int idPersona) {
     try {
            boolean actualizada = personaService.actualizarPersona(idPersona);
            if (actualizada) {
                outputArea.append("Persona actualizada correctamente en inscripciones.\n");
                ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            } else {
                mostrarError("La persona no está registrada en la tabla PERSONA.");
            }
        } catch (SQLException e) {
            mostrarError("Error al actualizar persona: " + e.getMessage());
        }
    }

private void inscribirPersona(int idPersona) {
    try {
            boolean inscrita = personaService.inscribirPersona(idPersona);
            if (inscrita) {
                outputArea.append("Persona agregada a Inscripción correctamente.\n");
                ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            } else {
                mostrarError("No se pudo inscribir a la persona. Verifique si está registrada en la base de datos.");
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
        JTextField tipoContratoProfesor = new JTextField("Catedrático");

        JButton btnGuardarProfesor = new JButton("Guardar Profesor");

        btnGuardarProfesor.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idProfesor.getText().trim());
                profesorService.guardarProfesor(id, nombresProfesor.getText(), apellidosProfesor.getText(), emailProfesor.getText(), tipoContratoProfesor.getText());

                outputArea.append("Profesor guardado: " + id + "\n");
            } catch (NumberFormatException ex) {
                mostrarError("Error: El ID debe ser un número entero.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar el profesor: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Profesor:")); panel.add(idProfesor);
        panel.add(new JLabel("Nombres:")); panel.add(nombresProfesor);
        panel.add(new JLabel("Apellidos:")); panel.add(apellidosProfesor);
        panel.add(new JLabel("Email:")); panel.add(emailProfesor);
        panel.add(new JLabel("Tipo de Contrato:")); panel.add(tipoContratoProfesor);
        panel.add(btnGuardarProfesor);
        panel.add(new JScrollPane(outputArea)); // Agrega el área de salida

        return panel;
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
                    mostrarError("Error: Ningún campo de texto puede estar vacío.");
                    return;
                }

                Programa programa = obtenerProgramaPorID(idProgramaVal);
                if (programa == null) {
                    mostrarError("Error: No existe un programa con ese ID en la base de datos.");
                    return;
                }

                estudianteService.guardarEstudiante(id, codigo, nombres, apellidos, email, promedio, programa, activo);
                outputArea.append("Estudiante guardado: " + id + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID, código y promedio deben ser valores numéricos.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar el estudiante: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Estudiante:"));
        panel.add(idEstudiante);
        panel.add(new JLabel("Código Estudiantil:"));
        panel.add(codigoEstudiante);
        panel.add(new JLabel("Nombres:"));
        panel.add(nombresEstudiante);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosEstudiante);
        panel.add(new JLabel("Email:"));
        panel.add(emailEstudiante);
        panel.add(new JLabel("Promedio:"));
        panel.add(promedioEstudiante);
        panel.add(new JLabel("ID Programa(Defecto):"));
        panel.add(idPrograma);
        panel.add(new JLabel("Activo:"));
        panel.add(activoEstudiante);
        panel.add(btnGuardarEstudiante);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }



private JPanel crearPanelInscripcion() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField idCurso = new JTextField("912");
        JTextField idEstudiante = new JTextField("1123498175");
        JTextField año = new JTextField("1997");
        JTextField semestre = new JTextField("2");
        JButton btnGuardarInscripcion = new JButton("Guardar Inscripción");

        btnGuardarInscripcion.addActionListener(e -> {
            try {
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());
                int añoVal = Integer.parseInt(año.getText().trim());
                int semestreVal = Integer.parseInt(semestre.getText().trim());

                if (semestreVal < 1 || semestreVal > 2) {
                    mostrarError("Error: El semestre debe ser 1 o 2.");
                    return;
                }

                Curso curso = obtenerCursoPorID(idCursoVal);
                if (curso == null) {
                    mostrarError("Error: No existe un curso con ese ID en la base de datos.");
                    return;
                }

                Estudiante estudiante = obtenerEstudiantePorID(idEstudianteVal);
                if (estudiante == null) {
                    mostrarError("Error: No existe un estudiante con ese ID en la base de datos.");
                    return;
                }

                inscripcionService.guardarInscripcion(curso, añoVal, semestreVal, estudiante);
                outputArea.append("Inscripción guardada: Curso " + curso.getID() + " - Estudiante " + estudiante.getID() + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID, año y semestre deben ser números enteros.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar la inscripción: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("ID Curso(O Dejelo por defecto):"));
        panel.add(idCurso);
        panel.add(new JLabel("ID Estudiante(O Dejelo por defecto):"));
        panel.add(idEstudiante);
        panel.add(new JLabel("Año:"));
        panel.add(año);
        panel.add(new JLabel("Semestre:"));
        panel.add(semestre);
        panel.add(btnGuardarInscripcion);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }


private JPanel crearPanelCursoProfesor() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField idCurso = new JTextField("912");
        JTextField idProfesor = new JTextField("5789");
        JTextField anio = new JTextField("2024");
        JTextField semestre = new JTextField("2");
        JButton btnGuardarCursoProfesor = new JButton("Asignar Profesor");

        btnGuardarCursoProfesor.addActionListener(e -> {
            try {
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
                int anioVal = Integer.parseInt(anio.getText().trim());
                int semestreVal = Integer.parseInt(semestre.getText().trim());

                if (semestreVal < 1 || semestreVal > 2) {
                    mostrarError("Error: El semestre debe ser 1 o 2.");
                    return;
                }

                Curso curso = obtenerCursoPorID(idCursoVal);
                if (curso == null) {
                    mostrarError("Error: No existe un curso con ese ID en la base de datos.");
                    return;
                }

                Profesor profesor = obtenerProfesorPorID(idProfesorVal);
                if (profesor == null) {
                    mostrarError("Error: No existe un profesor con ese ID en la base de datos.");
                    return;
                }

                cursoProfesorService.asignarProfesorACurso(curso, anioVal, semestreVal, profesor);
                outputArea.append("Asignación guardada: Curso " + curso.getID() + " - Profesor " + profesor.getID() + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID, año y semestre deben ser números enteros.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar la asignación: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("ID Curso (O déjelo por defecto):"));
        panel.add(idCurso);
        panel.add(new JLabel("ID Profesor (O déjelo por defecto):"));
        panel.add(idProfesor);
        panel.add(new JLabel("Año:"));
        panel.add(anio);
        panel.add(new JLabel("Semestre:"));
        panel.add(semestre);
        panel.add(btnGuardarCursoProfesor);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }


private JPanel crearPanelCurso() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idCurso = new JTextField("4411");
        JTextField nombreCurso = new JTextField("Matemáticas");
        JTextField idPrograma = new JTextField("603");
        JCheckBox activoCurso = new JCheckBox("Activo", true);
        JButton btnGuardarCurso = new JButton("Guardar Curso");

        btnGuardarCurso.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idCurso.getText().trim());
                String nombre = nombreCurso.getText().trim();
                int idProgramaVal = Integer.parseInt(idPrograma.getText().trim());
                boolean activo = activoCurso.isSelected();

                if (nombre.isEmpty()) {
                    mostrarError("Error: El nombre del curso no puede estar vacío.");
                    return;
                }

                Programa programa = obtenerProgramaPorID(idProgramaVal);
                if (programa == null) {
                    mostrarError("Error: No existe un programa con ese ID en la base de datos.");
                    return;
                }

                cursoService.guardarCurso(id, nombre, programa, activo);
                outputArea.append("Curso guardado: " + id + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar el curso: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Curso:"));
        panel.add(idCurso);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreCurso);
        panel.add(new JLabel("ID Programa (O Déjelo por defecto):"));
        panel.add(idPrograma);
        panel.add(new JLabel("Activo:"));
        panel.add(activoCurso);
        panel.add(btnGuardarCurso);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }

private JPanel crearPanelPrograma() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JTextField idPrograma = new JTextField("1188");
        JTextField nombrePrograma = new JTextField("Ingeniería de Software");
        JTextField duracionPrograma = new JTextField("8");
        JTextField idFacultad = new JTextField("1234");
        JButton btnGuardarPrograma = new JButton("Guardar Programa");

        btnGuardarPrograma.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idPrograma.getText().trim());
                String nombre = nombrePrograma.getText().trim();
                double duracion = Double.parseDouble(duracionPrograma.getText().trim());
                int idFacultadVal = Integer.parseInt(idFacultad.getText().trim());
                Date fechaRegistro = new Date();

                if (nombre.isEmpty()) {
                    mostrarError("Error: El nombre no puede estar vacío.");
                    return;
                }

                Facultad facultad = obtenerFacultadPorID(idFacultadVal);
                if (facultad == null) {
                    mostrarError("Error: No existe una facultad con ese ID en la base de datos.");
                    return;
                }

                programaService.guardarPrograma(id, nombre, duracion, fechaRegistro, facultad);
                outputArea.append("Programa guardado: " + id + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID y la duración deben ser números.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar el programa: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Programa:"));
        panel.add(idPrograma);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombrePrograma);
        panel.add(new JLabel("Duración (semestres):"));
        panel.add(duracionPrograma);
        panel.add(new JLabel("ID Facultad (O Déjelo por defecto):"));
        panel.add(idFacultad);
        panel.add(btnGuardarPrograma);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }

private JPanel crearPanelFacultad() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idFacultad = new JTextField("8271");
        JTextField nombreFacultad = new JTextField("Facultad de Economía");
        JTextField idDecano = new JTextField("7392");
        JButton btnGuardarFacultad = new JButton("Guardar Facultad");

        btnGuardarFacultad.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idFacultad.getText().trim());
                int idDecanoVal = Integer.parseInt(idDecano.getText().trim());
                String nombre = nombreFacultad.getText().trim();

                if (nombre.isEmpty()) {
                    mostrarError("Error: El nombre no puede estar vacío.");
                    return;
                }

                Persona decano = obtenerPersonaPorID(idDecanoVal);
                if (decano == null) {
                    mostrarError("Error: No existe una persona con ese ID en la base de datos.");
                    return;
                }

                facultadService.guardarFacultad(id, nombre, decano);
                outputArea.append("Facultad guardada: " + id + "\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al guardar la facultad: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Ingrese ID Facultad:"));
        panel.add(idFacultad);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreFacultad);
        panel.add(new JLabel("ID Decano (O Déjelo por defecto):"));
        panel.add(idDecano);
        panel.add(btnGuardarFacultad);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }


private JPanel crearPanelCursosInscritos() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idCurso = new JTextField("912");
        JTextField idEstudiante = new JTextField("1123981625");
        JButton btnGuardarInscripcion = new JButton("Guardar Inscripción");
        JButton btnEliminarInscripcion = new JButton("Eliminar Inscripción");
        JButton btnActualizarInscripcion = new JButton("Actualizar Inscripción");

        btnGuardarInscripcion.addActionListener(e -> {
            try {
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

                Inscripcion inscripcion = obtenerInscripcionPorCursoYEstudiante(idCursoVal, idEstudianteVal);
                if (inscripcion == null) {
                    mostrarError("Error: No existe una inscripción para este curso y estudiante.");
                    return;
                }

                cursosInscritosService.inscribirEnCursos(inscripcion);
                outputArea.append("Curso inscrito correctamente.\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al inscribir en cursos: " + ex.getMessage());
            }
        });

        btnEliminarInscripcion.addActionListener(e -> {
            try {
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

                cursosInscritosService.eliminarInscripcion(idCursoVal, idEstudianteVal);
                outputArea.append("Inscripción eliminada correctamente.\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al eliminar la inscripción: " + ex.getMessage());
            }
        });

        btnActualizarInscripcion.addActionListener(e -> {
            try {
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int idEstudianteVal = Integer.parseInt(idEstudiante.getText().trim());

                cursosInscritosService.actualizarInscripcion(idCursoVal, idEstudianteVal, idCursoVal);
                outputArea.append("Inscripción actualizada correctamente.\n");

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al actualizar la inscripción: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("ID Curso:"));
        panel.add(idCurso);
        panel.add(new JLabel("ID Estudiante:"));
        panel.add(idEstudiante);
        panel.add(btnGuardarInscripcion);
        panel.add(btnEliminarInscripcion);
        panel.add(btnActualizarInscripcion);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }

   



private JPanel crearPanelCursoProfesores() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idProfesor = new JTextField("9176");
        JTextField idCurso = new JTextField("912");
        JTextField anio = new JTextField("2024");
        JTextField semestre = new JTextField("2");
        JButton btnGuardarCursoProfesor = new JButton("Guardar Asignación");
        JButton btnEliminarCursoProfesores = new JButton("Eliminar Asignación");
        JButton btnActualizarCursoProfesor = new JButton("Actualizar Asignación");

        btnGuardarCursoProfesor.addActionListener(e -> {
            try {
                int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int anioVal = Integer.parseInt(anio.getText().trim());
                int semestreVal = Integer.parseInt(semestre.getText().trim());

                Profesor profesor = obtenerProfesorPorID(idProfesorVal);
                Curso curso = obtenerCursoPorID(idCursoVal);

                if (profesor == null || curso == null) {
                    mostrarError("Error: El curso o el profesor no existen.");
                    return;
                }

                CursoProfesor nuevaAsignacion = new CursoProfesor(profesor, anioVal, semestreVal, curso);
                if (cursoProfesoresService.inscribirCursoProfesor(nuevaAsignacion)) {
                     ConexionBD.mostrarDatosBD_CURSO_PROFESORES();
                    outputArea.append("Profesor asignado al curso correctamente.\n");
                } else {
                    mostrarError("Error: La asignación ya existe o no se pudo completar.");
                }

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los valores deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al asignar profesor al curso: " + ex.getMessage());
            }
        });

        btnEliminarCursoProfesores.addActionListener(e -> {
            try {
                int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int anioVal = Integer.parseInt(anio.getText().trim());
                int semestreVal = Integer.parseInt(semestre.getText().trim());

                if (cursoProfesoresService.eliminarCursoProfesor(idProfesorVal, idCursoVal, anioVal, semestreVal)) {
                    ConexionBD.mostrarDatosBD_CURSO_PROFESORES();
                    outputArea.append("Asignación eliminada correctamente.\n");
                } else {
                    mostrarError("Error: No se encontró la asignación a eliminar.");
                }

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los valores deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al eliminar la asignación: " + ex.getMessage());
            }
        });

        btnActualizarCursoProfesor.addActionListener(e -> {
            try {
                int idProfesorVal = Integer.parseInt(idProfesor.getText().trim());
                int idCursoVal = Integer.parseInt(idCurso.getText().trim());
                int nuevoAnio = Integer.parseInt(anio.getText().trim());
                int nuevoSemestre = Integer.parseInt(semestre.getText().trim());

                if (cursoProfesoresService.actualizarCursoProfesor(idProfesorVal, idCursoVal, nuevoAnio, nuevoSemestre)) {
                     ConexionBD.mostrarDatosBD_CURSO_PROFESORES();
                    outputArea.append("Asignación actualizada correctamente.\n");
                } else {
                    mostrarError("Error: No existe una asignación para actualizar.");
                }

            } catch (NumberFormatException ex) {
                mostrarError("Error: Los valores deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al actualizar la asignación: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("ID Profesor:"));
        panel.add(idProfesor);
        panel.add(new JLabel("ID Curso:"));
        panel.add(idCurso);
        panel.add(new JLabel("Año:"));
        panel.add(anio);
        panel.add(new JLabel("Semestre:"));
        panel.add(semestre);
        panel.add(btnGuardarCursoProfesor);
        panel.add(btnEliminarCursoProfesores);
        panel.add(btnActualizarCursoProfesor);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }



private Curso obtenerCursoPorID(int idCurso) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idCurso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return DAOFactory.crearCurso(
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
                    return DAOFactory.crearEstudiante(
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
                    return DAOFactory.crearProfesor(
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

private Programa obtenerProgramaPorID(int idPrograma) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM programa WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPrograma);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return DAOFactory.crearPrograma(
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






private Facultad obtenerFacultadPorID(int idFacultad) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idFacultad);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return DAOFactory.crearFacultad(
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





private Persona obtenerPersonaPorID(int idPersona) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM persona WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return DAOFactory.crearPersona(
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





private Inscripcion obtenerInscripcionPorCursoYEstudiante(int cursoID, int estudianteID) {
    try (Connection conexion = ConexionBD.conectar()) {
        String sql = "SELECT * FROM inscripcion WHERE curso_id = ? AND estudiante_id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cursoID);
            pstmt.setInt(2, estudianteID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return DAOFactory.crearInscripcion(
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



    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InscripcionApp().setVisible(true));
    }
}
