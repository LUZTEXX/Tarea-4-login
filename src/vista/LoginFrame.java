package vista;

import dao.DAOFactory;
import dao.IUsuarioDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private IUsuarioDAO usuarioDAO;

    public LoginFrame() {
        usuarioDAO = DAOFactory.crearUsuarioDAO();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Inicio de Sesión");
        setSize(420, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstilosUI.FONDO);
        setLayout(new GridBagLayout());
    }

    private void inicializarComponentes() {
        JPanel card = new JPanel();
        card.setBackground(EstilosUI.TARJETA);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 35, 30, 35));
        card.setPreferredSize(new Dimension(340, 370));

        JLabel lblTitulo = new JLabel("LOGIN");
        lblTitulo.setFont(EstilosUI.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosUI.PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Ingresa tus credenciales para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(EstilosUI.SECUNDARIO);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Nombre de Usuario");
        lblUser.setFont(EstilosUI.FUENTE_LABEL);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(270, 38));
        EstilosUI.estilizarCampo(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(EstilosUI.FUENTE_LABEL);
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtContrasena = new JPasswordField();
        txtContrasena.setMaximumSize(new Dimension(270, 38));
        EstilosUI.estilizarCampo(txtContrasena);

        JButton btnEntrar = EstilosUI.crearBoton("Entrar", EstilosUI.PRIMARIO);
        btnEntrar.setMaximumSize(new Dimension(270, 40));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistrarse = new JButton("¿No tienes cuenta? Registrarse");
        btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegistrarse.setForeground(EstilosUI.PRIMARIO);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnEntrar.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> {
            new RegistroFrame(this, null).setVisible(true);
        });

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(5));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(25));
        card.add(lblUser);
        card.add(Box.createVerticalStrut(5));
        card.add(txtUsuario);
        card.add(Box.createVerticalStrut(15));
        card.add(lblPass);
        card.add(Box.createVerticalStrut(5));
        card.add(txtContrasena);
        card.add(Box.createVerticalStrut(25));
        card.add(btnEntrar);
        card.add(Box.createVerticalStrut(12));
        card.add(btnRegistrarse);

        add(card);
    }

    private void iniciarSesion() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "debe ingresar su usuario y contraseña, si no está registrado debe registrarse",
                "Campos requeridos",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Usuario usuarioAutenticado = usuarioDAO.validarLogin(user, pass);
        if (usuarioAutenticado != null) {
            this.dispose();
            new PrincipalFrame(usuarioAutenticado).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Usuario o contraseña incorrectos. Si no está registrado debe registrarse.",
                "Error de acceso",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
