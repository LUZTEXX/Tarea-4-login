package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class EstilosUI {
    public static final Color FONDO = new Color(241, 245, 249);
    public static final Color TARJETA = Color.WHITE;
    public static final Color PRIMARIO = new Color(37, 99, 235);
    public static final Color SECUNDARIO = new Color(71, 85, 105);
    public static final Color PELIGRO = new Color(220, 38, 38);
    public static final Color EXITO = new Color(22, 163, 74);
    public static final Color TEXTO = new Color(15, 23, 42);
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FUENTE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_INPUT = new Font("Segoe UI", Font.PLAIN, 14);

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(FUENTE_INPUT);
        campo.setForeground(TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(203, 213, 225), 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
    }

    public static JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}