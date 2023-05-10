package org.example.DAO;

import org.example.DOMAIN.Admin;
import org.example.Connections.ConnectionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements DAO<Admin> {
    /**
     * AdminDAO que tiene las consultas y los metodos que llaman los controladores
     */
    private final static String FINDALL ="SELECT * from administrador";
    private final static String FINBYID ="SELECT * from administrador WHERE id_a=?";

    private final static String FINBYNAME ="SELECT * from administrador WHERE nombre_admin=?";
    private final static String INSERT ="INSERT INTO administrador (id_a,nombre_admin,contraseña_admin,correo_admin,dni) VALUES (?,?,?,?,?)";
    private final static String UPDATE ="UPDATE administrador SET nombre_admin=?, contraseña_admin=? WHERE id_a=?";
    private final static String DELETE="DELETE from administrador where nombre_admin=?";
    private final static String SELECT_BY_USERNAME_OR_EMAIL_EXCEPT_CURRENT = "SELECT * FROM administrador WHERE nombre_admin = ? OR correo_admin = ? AND id_a != ?";
    private final static String SELECT_BY_USERNAME_OR_PASSWORD = "SELECT * FROM administrador WHERE nombre_admin = ? OR contraseña_admin = ?";

    private Connection conn;
    private static AdminDAO instance = null;

    public static int adminId;
    public static String adminDNI;
    public static String adminMail;

    public AdminDAO() {
        this.conn= ConnectionMySQL.getConnect();
        if (this.conn == null) {
            throw new RuntimeException("Unable to establish connection to the database.");
        }
    }

    public static AdminDAO getInstance() {
        if (instance == null) {
            instance = new AdminDAO();
        }
        return instance;
    }

    @Override
    public List<Admin> findAll() throws SQLException {
        List<Admin> result=new ArrayList();
        try (PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try (ResultSet res= pst.executeQuery()){
                while (res.next()){
                   Admin a =new Admin();
                    a.setUsername(res.getString("nombre_usuario"));
                    a.setPassword(res.getString("contraseña_usuario"));
                    a.setDNI(res.getString("dni"));
                    a.setEmail(res.getString("correo_usuario"));
                    result.add(a);
                }
            }
        }

        return result;
    }

    @Override
    public Admin findById(String id) throws SQLException {
        Admin result=null;
        try (PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
            pst.setString(1,id);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setUsername(res.getString("nombre_admin"));
                    result.setPassword(res.getString("contraseña_admin"));
                    result.setDNI(res.getString("dni"));
                    result.setEmail(res.getString("correo_admin"));
                }
            }
        }
        return result;
    }

    public Admin findByName(String name) throws SQLException {
        Admin result = new Admin();
        try (PreparedStatement pst=this.conn.prepareStatement(FINBYNAME)){
            pst.setString(1,name);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setUsername(res.getString("nombre_admin"));
                    result.setPassword(res.getString("contraseña_admin"));
                    result.setDNI(res.getString("dni"));
                    result.setEmail(res.getString("correo_admin"));
                } else {
                    result = null;
                }
            }
        }
        return result;
    }

    @Override
    public Admin save(Admin entity) throws SQLException {
        Admin result = null; // inicialice result como null en lugar de como un nuevo objeto Admin
        if (entity != null) {
            if (entity.getId() == 0) { // Si la clave primaria es 0, entonces es una inserción
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId());
                    pst.setString(2, entity.getUsername());
                    pst.setString(3, entity.getPassword());
                    pst.setString(4, entity.getEmail());
                    pst.setString(5, entity.getDNI());
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            // Obtener el ID del registro insertado y crear un objeto Admin con esos datos
                            int id = rs.getInt(1);
                            result = new Admin(id, entity.getUsername(), entity.getPassword(), entity.getEmail(), entity.getDNI());
                        }
                    }
                }
            } else { // De lo contrario, es una actualización
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
                            // Ya existe un administrador con el mismo usuario o correo electrónico
                            return null;
                        }
                    }
                    // No se encontró un administrador con el mismo usuario o correo electrónico, se procede a actualizar el registro
                    pst.executeUpdate();
                    result = entity; // Asignar el objeto actualizado al objeto result que devuelve
                }
            }
        }
        return result;
    }
    @Override
    public void delete(Admin entity) throws SQLException {
        if(entity!=null){
            try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
                pst.setString(1,entity.getUsername());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {

    }

    public Admin findByUsernameAndPassword(String username, String password) throws SQLException {
        Admin result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(SELECT_BY_USERNAME_OR_PASSWORD)) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    String storedPassword = res.getString("contraseña_admin");
                    if (storedPassword.equals(password)) {
                        result = new Admin();
                        result.setId(res.getInt("id_a"));
                        result.setUsername(res.getString("nombre_admin"));
                        result.setPassword(storedPassword);
                        result.setDNI(res.getString("dni"));
                        result.setEmail(res.getString("correo_admin"));
                    }
                }
            }
        }
        return result;
    }

    /**
     * metodos que guarda ciertos datos del administrador para usarlos en otros metodos
     * @return
     */
    public static int getAdminId() {
        return adminId;
    }

}
