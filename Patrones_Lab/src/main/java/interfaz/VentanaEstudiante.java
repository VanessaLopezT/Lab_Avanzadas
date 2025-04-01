package interfaz;

import javax.swing.*;
import java.awt.*;

public class VentanaEstudiante extends JFrame {
    public VentanaEstudiante() {
        setTitle("Ventana Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para los campos de estudiante
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idEstudiante = new JTextField();
        JTextField nombreEstudiante = new JTextField();
        JTextField emailEstudiante = new JTextField();
        JButton btnGuardarEstudiante = new JButton("Guardar Estudiante");

        btnGuardarEstudiante.addActionListener(e -> {
            // Lógica para guardar el estudiante (similar a lo que tienes en la ventana principal)
            System.out.println("Estudiante guardado: " + idEstudiante.getText());
        });

        panel.add(new JLabel("ID Estudiante:"));
        panel.add(idEstudiante);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreEstudiante);
        panel.add(new JLabel("Email:"));
        panel.add(emailEstudiante);
        panel.add(btnGuardarEstudiante);

        add(panel);
    }
}

