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

    private final static String FINDALL ="SELECT * from administrador";
    private final static String FINBYID ="SELECT * from administrador WHERE id_a=?";
    private final static String INSERT ="INSERT INTO administrador (id_a,nombre_admin,contraseña_admin,correo_admin,dni) VALUES (?,?,?,?,?)";
    private final static String UPDATE ="UPDATE administrador SET nombre_admin=?, contraseña_admin=? WHERE id_a=?";
    private final static String DELETE="DELETE from administrador where id_a=?";
    private final static String SELECT_BY_USERNAME_OR_EMAIL = "SELECT * FROM administrador WHERE nombre_admin = ? OR correo_admin = ?";
    private Connection conn;
    private static AdminDAO instance = null;

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
        Admin result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYUSERNAMEANDPASSWORD)) {
            pst.setString(1, id);
            pst.setString(2, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Admin();
                    result.setId_admin(res.getInt("id_a"));
                    result.setUsername(res.getString("nombre_admin"));
                    result.setPassword(res.getString("contraseña_admin"));
                    result.setDNI(res.getString("dni"));
                    result.setEmail(res.getString("correo_admin"));
                }
            }
        }
        return result;
    }

    @Override
    public Admin save(Admin entity) throws SQLException {
        Admin result= new Admin();
        if(entity!=null){
            //insert
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId_admin());
                pst.setString(2, entity.getUsername());
                pst.setString(3, entity.getPassword());
                pst.setString(4, entity.getEmail());
                pst.setString(5, entity.getDNI());

                // Verificar si ya existe un administrador con el mismo usuario o correo electrónico
                try (PreparedStatement pstSelect = this.conn.prepareStatement(SELECT_BY_USERNAME_OR_EMAIL)) {
                    pstSelect.setString(1, entity.getUsername());
                    pstSelect.setString(2, entity.getEmail());
                    ResultSet rs = pstSelect.executeQuery();
                    if (rs.next()) {
                        // Ya existe un administrador con el mismo usuario o correo electrónico
                        return null;
                    }
                }

                // No se encontró un administrador con el mismo usuario o correo electrónico, se procede a insertar el registro
                pst.executeUpdate();
            }
        }else{
            //update
            try (PreparedStatement pst=this.conn.prepareStatement(UPDATE)){
                pst.setString(1, entity.getUsername());
                pst.setString(2,entity.getPassword());
                pst.setString(3,entity.getDNI());
                pst.executeUpdate();
            }
        }
        return result;
    }

    @Override
    public void delete(Admin entity) throws SQLException {
        if(entity!=null){
            try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
                pst.setString(1,entity.getDNI());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {

    }
}
