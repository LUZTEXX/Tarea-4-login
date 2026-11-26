package dao;

public class DAOFactory {
    public static IUsuarioDAO crearUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}