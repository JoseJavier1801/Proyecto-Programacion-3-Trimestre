package org.example.DAO;

import org.example.Connections.ConnectionMySQL;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO que tiene las consultas y los metodos que llaman los controladores
 */
public class UserDAO implements DAO<User> {
    private final static String FINDALL ="SELECT * from usuarios";
    private final static String FINBYID ="SELECT * from usuarios WHERE id_u=?";
    private final static String FINBYNAME ="SELECT * from usuarios WHERE nombre_usuario=?";
    private final static String INSERT ="INSERT INTO usuarios (id_u,nombre_usuario,contraseña_usuario,correo_usuario,dni) VALUES (?,?,?,?,?)";
    private final static String UPDATE ="UPDATE usuarios SET nombre_usuario=?, contraseña_usuario=? WHERE id_u=?";
    private final static String SELECT_BY_USERNAME_OR_EMAIL_EXCEPT_CURRENT = "SELECT * FROM usuarios WHERE nombre_usuario = ? OR correo_usuario = ? AND id_u != ?";
    private final static String DELETE="DELETE from usuarios where nombre_usuario=?";
    private final static String SELECT_BY_USERNAME_OR_PASSWORD = "SELECT * FROM usuarios WHERE nombre_usuario = ? OR contraseña_usuario = ?";

    /**
     * variables id dni y mail que almacenaran los estos datos del usuario que inicie sesion
     */
    public static int userId;
    public static String userDNI;
    public static String userMail;

    private Connection conn;


    private static UserDAO instance = null;

    public UserDAO(Connection conn){
        this.conn=conn;
    }

    public UserDAO() {
        this.conn= ConnectionMySQL.getConnect();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> result=new ArrayList();
        try (PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try (ResultSet res= pst.executeQuery()){
                while (res.next()){
                    User u =new User();
                    u.setUsername(res.getString("nombre_usuario"));
                    u.setPassword(res.getString("contraseña_usuario"));
                    u.setDNI(res.getString("dni"));
                    u.setEmail(res.getString("correo_usuario"));
                    result.add(u);
                }
            }
        }

        return result;
    }

    @Override
    public User findById(String id) throws SQLException {
        User result=null;
        try (PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
            pst.setString(1,id);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setUsername(res.getString("nombre_usuario"));
                    result.setPassword(res.getString("contraseña_usuario"));
                    result.setDNI(res.getString("dni"));
                    result.setEmail(res.getString("correo_usuario"));
                }

            }
        }
        return result;
    }

    @Override
    public User save(User entity) throws SQLException {
        User result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, entity.getId());
            pst.setString(2, entity.getUsername());
            pst.setString(3, entity.getPassword());
            pst.setString(4, entity.getEmail());
            pst.setString(5, entity.getDNI());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se ha podido insertar el usuario.");
            }
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    // Obtener el ID del registro insertado y crear un objeto User con esos datos
                    int id = rs.getInt(1);
                    result = new User(id, entity.getUsername(), entity.getPassword(), entity.getEmail(), entity.getDNI());
                } else {
                    throw new SQLException("No se ha podido insertar el usuario, no se ha generado una clave única.");
                }
            }
        }
        return result;
    }
    public User Update(User entity) throws SQLException {
        User result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
            pst.setString(1, entity.getUsername());
            pst.setString(2, entity.getPassword());
            pst.setInt(3, entity.getId());
            // Verificar si ya existe un administrador con el mismo usuario o correo electrónico
            try (PreparedStatement pstSelect = this.conn.prepareStatement(SELECT_BY_USERNAME_OR_EMAIL_EXCEPT_CURRENT)) {
                pstSelect.setString(1, entity.getUsername());
                pstSelect.setString(2, entity.getEmail());
                pstSelect.setInt(3, entity.getId());
                ResultSet rs = pstSelect.executeQuery();
                if (rs.next()) {
                    // Ya existe un usuario
                    return null;
                }
            }
            // No se encontró un usuario con los mismos datos se procede a actualizar el registro
            pst.executeUpdate();
            result = entity; // Asignar el objeto actualizado al objeto result que devuelve
        }
        return result;
    }
    public void delete(User entity) throws SQLException {
        if(entity!=null){
            try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
                pst.setString(1,entity.getUsername());
                pst.executeUpdate();
            }
        }
    }

    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        User result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(SELECT_BY_USERNAME_OR_PASSWORD)) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    String storedPassword = res.getString("contraseña_usuario");
                    if (storedPassword.equals(password)) {
                        result = new User();
                        result.setId(res.getInt("id_u"));
                        result.setUsername(res.getString("nombre_usuario"));
                        result.setPassword(storedPassword);
                        result.setDNI(res.getString("dni"));
                        result.setEmail(res.getString("correo_usuario"));
                    }
                }
            }
        }
        return result;
    }


    public User findByName(String name) throws SQLException{
        User result = new User();
        try (PreparedStatement pst=this.conn.prepareStatement(FINBYNAME)){
            pst.setString(1,name);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setUsername(res.getString("nombre_usuario"));
                    result.setPassword(res.getString("contraseña_usuario"));
                    result.setDNI(res.getString("dni"));
                    result.setEmail(res.getString("correo_usuario"));
                } else {
                    result = null;
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }


    public static int getUserId() {
        return userId;
    }
}