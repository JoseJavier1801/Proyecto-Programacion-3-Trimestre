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
    private final static String FINBYID ="SELECT * from administrador WHERE dni=?";
    private final static String INSERT ="INSERT INTO administrador (id,nombre_admin,contraseña_admin,correo_admin,dni) VALUES (?,?,?,?,?)";
    private final static String UPDATE ="UPDATE administrador SET nombre_admin=?, contraseña_admin=? WHERE dni=?";
    private final static String DELETE="DELETE from administrador where dni=?";
    private Connection conn;

    public AdminDAO(Connection conn){
        this.conn=conn;
    }

    public AdminDAO() {
        this.conn= ConnectionMySQL.getConnect();
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

    @Override
    public Admin save(Admin entity) throws SQLException {
        Admin result= new Admin();
        if(entity!=null){
            //insert
            try(PreparedStatement pst=this.conn.prepareStatement(INSERT)){
                pst.setInt(1,entity.getId_admin());
                pst.setString(2, entity.getUsername());
                pst.setString(3,entity.getPassword());
                pst.setString(4,entity.getEmail());
                pst.setString(5,entity.getDNI());
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
