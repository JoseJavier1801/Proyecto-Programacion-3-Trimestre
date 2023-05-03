package org.example.DAO;

import org.example.DOMAIN.Administrador;
import org.example.Connections.ConnectionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements DAO<Administrador> {

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
    public List<Administrador> findAll() throws SQLException {
        List<Administrador> result=new ArrayList();
        try (PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try (ResultSet res= pst.executeQuery()){
                while (res.next()){
                   Administrador a =new Administrador();
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
    public Administrador findById(String id) throws SQLException {
        return null;
    }

    @Override
    public Administrador save(Administrador entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(Administrador entity) throws SQLException {

    }

    @Override
    public void close() throws Exception {

    }
}
