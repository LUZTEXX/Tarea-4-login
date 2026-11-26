package vista;

import dao.DAOFactory;
import dao.IUsuarioDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class RegistroFrame extends JDialog {
    private JTextField txtNombreUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena, txtConfirmarContrasena;
    private IUsuarioDAO usuarioDAO;
    private PrincipalFrame principalFrame;

    public RegistroFrame(Frame parent, PrincipalFrame principalFrame) {
        super(parent, "Registro de Usuario", true);
        this.principalFrame = principalFrame;
        this.usuarioDAO = DAOFactory.crearUsuarioDAO();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setSize(400, 680);
        setLocationRelativeTo(getParent());
        setResizable(true);
        getContentPane().setBackground(EstilosUI.FONDO);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panelForm = new JPanel(new GridLayout(14, 1, 4, 4));
        panelForm.setBackground(EstilosUI.TARJETA);
        panelForm.setBorder(new EmptyBorder(20, 35, 20, 35));

        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosUI.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosUI.PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 5, 0));
        add(lblTitulo, BorderLayout.NORTH);

        txtNombreUsuario = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        txtConfirmarContrasena = new JPasswordField();

        EstilosUI.estilizarCampo(txtNombreUsuario);
        EstilosUI.estilizarCampo(txtNombre);
        EstilosUI.estilizarCampo(txtApellido);
        EstilosUI.estilizarCampo(txtTelefono);
        EstilosUI.estilizarCampo(txtCorreo);
        EstilosUI.estilizarCampo(txtContrasena);
        EstilosUI.estilizarCampo(txtConfirmarContrasena);

        panelForm.add(crearLabel("Nombre de Usuario:"));
        panelForm.add(txtNombreUsuario);
        panelForm.add(crearLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(crearLabel("Apellido:"));
        panelForm.add(txtApellido);
        panelForm.add(crearLabel("Número de Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(crearLabel("Correo Electrónico:"));
        panelForm.add(txtCorreo);
        panelForm.add(crearLabel("Contraseña:"));
        panelForm.add(txtContrasena);
        panelForm.add(crearLabel("Confirmar Contraseña:"));
        panelForm.add(txtConfirmarContrasena);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(EstilosUI.FONDO);

        JButton btnRegistrar = EstilosUI.crearBoton("Registrar", EstilosUI.EXITO);
        JButton btnCancelar = EstilosUI.crearBoton("Cancelar", EstilosUI.SECUNDARIO);

        btnRegistrar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(EstilosUI.FUENTE_LABEL);
        lbl.setForeground(EstilosUI.TEXTO);
        return lbl;
    }

    private void guardarUsuario() {
        String user = txtNombreUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();
        String confirmPass = new String(txtConfirmarContrasena.getPassword()).trim();

        if (user.isEmpty()) {
            mostrarError("Falta completar el campo: Nombre de Usuario");
            txtNombreUsuario.requestFocus();
            return;
        }
        if (nombre.isEmpty()) {
            mostrarError("Falta completar el campo: Nombre");
            txtNombre.requestFocus();
            return;
        }
        if (apellido.isEmpty()) {
            mostrarError("Falta completar el campo: Apellido");
            txtApellido.requestFocus();
            return;
        }
        if (telefono.isEmpty()) {
            mostrarError("Falta completar el campo: Número de Teléfono");
            txtTelefono.requestFocus();
            return;
        }
        if (correo.isEmpty()) {
            mostrarError("Falta completar el campo: Correo Electrónico");
            txtCorreo.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            mostrarError("Falta completar el campo: Contraseña");
            txtContrasena.requestFocus();
            return;
        }
        if (confirmPass.isEmpty()) {
            mostrarError("Falta completar el campo: Confirmar Contraseña");
            txtConfirmarContrasena.requestFocus();
            return;
        }

        if (!pass.equals(confirmPass)) {
            mostrarError("La contraseña y la confirmación de contraseña no coinciden.");
            txtConfirmarContrasena.requestFocus();
            return;
        }

        if (usuarioDAO.existeUsuario(user)) {
            mostrarError("El nombre de usuario '" + user + "' ya está en uso.");
            return;
        }

        Usuario nuevo = new Usuario(0, user, nombre, apellido, telefono, correo, pass);
        if (usuarioDAO.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.", "Registro Completo", JOptionPane.INFORMATION_MESSAGE);
            if (principalFrame != null) {
                principalFrame.cargarTablaUsuarios();
            }
            dispose();
        } else {
            mostrarError("Error al guardar el usuario en la base de datos.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación de Registro", JOptionPane.WARNING_MESSAGE);
    }
}