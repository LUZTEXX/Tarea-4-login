package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexion;

private static final String JDBC_URL = "jdbc:mysql://mysql-cd05daf-luisjorgemp007-9885.b.aivencloud.com:21256/defaultdb?sslMode=REQUIRED&serverTimezone=UTC";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_OzRg1ITidThezFTa1Z-";

    private ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "No se pudo conectar a la base de datos:\n" + e.getMessage(),
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static ConexionBD getInstancia() {
        try {
            if (instancia == null || instancia.getConexion() == null || instancia.getConexion().isClosed()) {
                instancia = new ConexionBD();
            }
        } catch (SQLException e) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}