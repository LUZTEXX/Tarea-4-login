package dao;

import java.util.List;
import modelo.Usuario;

public interface IUsuarioDAO {
    Usuario validarLogin(String nombreUsuario, String contrasena);
    boolean registrar(Usuario usuario);
    List<Usuario> listarTodos();
    boolean actualizar(Usuario usuario);
    boolean eliminar(int id);
    boolean existeUsuario(String nombreUsuario);
}