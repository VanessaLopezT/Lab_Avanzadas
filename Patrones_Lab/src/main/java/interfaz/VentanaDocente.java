/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
import javax.swing.*;
import java.awt.*;
import java.awt.*;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author VANESA
 */
public class VentanaDocente extends JFrame{
     public VentanaDocente() {
        setTitle("Ventana Docente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para los campos de docente
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idDocente = new JTextField();
        JTextField nombreDocente = new JTextField();
        JTextField emailDocente = new JTextField();
        JButton btnGuardarDocente = new JButton("Guardar Docente");

        btnGuardarDocente.addActionListener(e -> {
            // Lógica para guardar el docente
            System.out.println("Docente guardado: " + idDocente.getText());
        });

        panel.add(new JLabel("ID Docente:"));
        panel.add(idDocente);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreDocente);
        panel.add(new JLabel("Email:"));
        panel.add(emailDocente);
        panel.add(btnGuardarDocente);

        add(panel);
    }
}
