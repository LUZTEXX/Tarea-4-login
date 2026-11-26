package vista;

import dao.DAOFactory;
import dao.IUsuarioDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;

public class PrincipalFrame extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private IUsuarioDAO usuarioDAO;
    private Usuario usuarioActual;
    private List<Usuario> listaUsuarios;

    public PrincipalFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.usuarioDAO = DAOFactory.crearUsuarioDAO();
        configurarVentana();
        inicializarComponentes();
        cargarTablaUsuarios();
    }

    private void configurarVentana() {
        setTitle("Panel Principal - Gestión de Usuarios");
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstilosUI.FONDO);
        setLayout(new BorderLayout(15, 15));
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(EstilosUI.TARJETA);
        panelSuperior.setBorder(new EmptyBorder(18, 25, 18, 25));

        JLabel lblTitulo = new JLabel("Usuarios Registrados");
        lblTitulo.setFont(EstilosUI.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosUI.TEXTO);

        JLabel lblBienvenida = new JLabel("Sesión activa: " + usuarioActual.obtenerResumen());
        lblBienvenida.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblBienvenida.setForeground(EstilosUI.SECUNDARIO);

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblBienvenida, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Apellido", "Teléfono", "Correo electrónico", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaUsuarios.setRowHeight(30);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaUsuarios.getTableHeader().setBackground(EstilosUI.PRIMARIO);
        tablaUsuarios.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(new EmptyBorder(0, 25, 0, 25));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(EstilosUI.FONDO);

        JButton btnNuevo = EstilosUI.crearBoton("NUEVO", EstilosUI.EXITO);
        JButton btnActualizar = EstilosUI.crearBoton("ACTUALIZAR", EstilosUI.PRIMARIO);
        JButton btnEliminar = EstilosUI.crearBoton("ELIMINAR", EstilosUI.PELIGRO);
        JButton btnCerrarSesion = EstilosUI.crearBoton("CERRAR SESIÓN", EstilosUI.SECUNDARIO);

        btnNuevo.addActionListener(e -> new RegistroFrame(this, this).setVisible(true));
        btnActualizar.addActionListener(e -> abrirActualizar());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarTablaUsuarios() {
        modeloTabla.setRowCount(0);
        listaUsuarios = usuarioDAO.listarTodos();
        for (Usuario u : listaUsuarios) {
            Object[] fila = {
                u.getNombre(),
                u.getApellido(),
                u.getTelefono(),
                u.getCorreo(),
                u.getNombreUsuario()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void abrirActualizar() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para actualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario usuarioSeleccionado = listaUsuarios.get(filaSeleccionada);
        new ActualizarUsuarioDialog(this, usuarioSeleccionado).setVisible(true);
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioSeleccionado = listaUsuarios.get(filaSeleccionada);
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al usuario '" + usuarioSeleccionado.getNombreUsuario() + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminar(usuarioSeleccionado.getId())) {
                cargarTablaUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cerrarSesion() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}