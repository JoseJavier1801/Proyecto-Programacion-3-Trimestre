package org.example.DAO;

import org.example.Connections.ConnectionMySQL;
import org.example.DOMAIN.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    private final static String FINDALL ="SELECT * from usuarios";
    private final static String FINBYID ="SELECT * from usuarios WHERE dni=?";
    private final static String INSERT ="INSERT INTO usuarios (id,nombre_usuario,contraseña_usuario,correo_usuario,dni) VALUES (?,?,?,?,?)";
    private final static String UPDATE ="UPDATE usuarios SET nombre_usuario=?, contraseña_usuario=? WHERE dni=?";
    private final static String DELETE="DELETE from usuarios where dni=?";
    private Connection conn;

    public UsuarioDAO(Connection conn){
        this.conn=conn;
    }

    public UsuarioDAO() {
        this.conn= ConnectionMySQL.getConnect();
    }

    @Override
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> result=new ArrayList();
        try (PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try (ResultSet res= pst.executeQuery()){
                while (res.next()){
                    Usuario u =new Usuario();
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
    public Usuario findById(String id) throws SQLException {
        Usuario result=null;
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


    public Usuario save(Usuario entity) throws SQLException {
        Usuario result= new Usuario();
        if(entity!=null){
            //insert
            try(PreparedStatement pst=this.conn.prepareStatement(INSERT)){
                pst.setInt(1,entity.getId_user());
                pst.setString(2, entity.getUsername());
                pst.setString(3,entity.getPassword());
                pst.setString(4,entity.getEmail());
                pst.setString(5,entity.getDNI());
                pst.executeUpdate();
            }
        }else{
            try (PreparedStatement pst=this.conn.prepareStatement(UPDATE)){
                pst.setString(1, entity.getUsername());
                pst.setString(2,entity.getPassword());
                pst.setString(3,entity.getDNI());
                pst.executeUpdate();
            }
        }
        return result;
    }


    public void delete(Usuario entity) throws SQLException {
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
