package vista;

import dao.DAOFactory;
import dao.IUsuarioDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class ActualizarUsuarioDialog extends JDialog {
    private JTextField txtNombreUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private Usuario usuarioEditar;
    private IUsuarioDAO usuarioDAO;
    private PrincipalFrame principalFrame;

    public ActualizarUsuarioDialog(PrincipalFrame parent, Usuario usuario) {
        super(parent, "Actualizar Usuario", true);
        this.principalFrame = parent;
        this.usuarioEditar = usuario;
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
        JLabel lblTitulo = new JLabel("ACTUALIZAR DATOS", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosUI.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosUI.PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 5, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(10, 1, 4, 4));
        panelForm.setBackground(EstilosUI.TARJETA);
        panelForm.setBorder(new EmptyBorder(20, 35, 20, 35));

        txtNombreUsuario = new JTextField(usuarioEditar.getNombreUsuario());
        txtNombre = new JTextField(usuarioEditar.getNombre());
        txtApellido = new JTextField(usuarioEditar.getApellido());
        txtTelefono = new JTextField(usuarioEditar.getTelefono());
        txtCorreo = new JTextField(usuarioEditar.getCorreo());

        EstilosUI.estilizarCampo(txtNombreUsuario);
        EstilosUI.estilizarCampo(txtNombre);
        EstilosUI.estilizarCampo(txtApellido);
        EstilosUI.estilizarCampo(txtTelefono);
        EstilosUI.estilizarCampo(txtCorreo);

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

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(EstilosUI.FONDO);

        JButton btnGuardar = EstilosUI.crearBoton("Guardar Cambios", EstilosUI.PRIMARIO);
        JButton btnCancelar = EstilosUI.crearBoton("Cancelar", EstilosUI.SECUNDARIO);

        btnGuardar.addActionListener(e -> actualizar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(EstilosUI.FUENTE_LABEL);
        return lbl;
    }

    private void actualizar() {
        String user = txtNombreUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (user.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioEditar.setNombreUsuario(user);
        usuarioEditar.setNombre(nombre);
        usuarioEditar.setApellido(apellido);
        usuarioEditar.setTelefono(telefono);
        usuarioEditar.setCorreo(correo);

        if (usuarioDAO.actualizar(usuarioEditar)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            principalFrame.cargarTablaUsuarios();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}