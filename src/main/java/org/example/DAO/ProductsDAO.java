package org.example.DAO;

import org.example.Connections.ConnectionMySQL;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO implements DAO<Products>{
    private final static String FINDALL ="SELECT * from productos";
    private final static String FINBYID ="SELECT * from productos WHERE id_p=?";
    private final static String INSERT ="INSERT INTO productos (id_p,nombre_producto,descripcion,stock,precio,id_admin) VALUES (?,?,?,?,?,?)";
    private final static String UPDATE ="UPDATE productos SET precio=?, stock=? WHERE id_p=?";
    private final static String DELETE="DELETE from productos where id_p=?";
    AdminDAO ADAO = AdminDAO.getInstance();
    private Connection conn;
    private static ProductsDAO instance = null;

    public ProductsDAO() {
        this.conn= ConnectionMySQL.getConnect();
        if (this.conn == null) {
            throw new RuntimeException("Unable to establish connection to the database.");
        }
    }

    public static ProductsDAO getInstance() {
        if (instance == null) {
            instance = new ProductsDAO();
        }
        return instance;
    }

    @Override
    public List<Products> findAll() throws SQLException {
        List<Products> result=new ArrayList();
        AdminDAO adminDao = new AdminDAO(); // Instanciar el DAO de Admin
        try (PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try (ResultSet res= pst.executeQuery()){
                while (res.next()){
                    Products p =new Products();
                    p.setId(res.getInt("id_producto"));
                    p.setName(res.getString("nombre_producto"));
                    p.setDescription(res.getString("descripcion"));
                    p.setStock(res.getInt("stock"));
                    p.setPrice(res.getDouble("precio"));
                    int adminId = res.getInt("id_admin");
                    Admin admin = ADAO.findById(String.valueOf(adminId)); // Obtener el objeto Admin correspondiente al ID
                    p.setId_admin(admin);
                    result.add(p);
                }
            }
        }
        return result;
    }

    @Override
    public Products findById(String id) throws SQLException {
        Products result=null;
        try (PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
            pst.setString(1,id);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setName(res.getString("nombre_producto"));
                    result.setDescription(res.getString("descripcion"));
                    result.setStock(res.getInt("stock"));
                    result.setPrice(res.getDouble("precio"));
                    int adminId = res.getInt("id_admin");
                    Admin admin = ADAO.findById(String.valueOf(adminId));
                    result.setId_admin(admin);
                }
            }
        }
        return result;
    }

    @Override
    public Products save(Products entity) throws SQLException {
        if (entity != null) {
            // Insertar nuevo registro
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getDescription());
                pst.setInt(4, entity.getStock());
                pst.setDouble(5, entity.getPrice());
                pst.setInt(6, entity.getId_admin().getId_admin());
                pst.executeUpdate();
            }
        } else {
            // Actualizar registro existente
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setDouble(1, entity.getPrice());
                pst.setInt(2, entity.getStock());
                pst.setInt(3, entity.getId());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    @Override
    public void delete(Products entity) throws SQLException {
        if(entity!=null){
            try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
                pst.setInt(1,entity.getId());
                pst.executeUpdate();
            }
        }
    }
    @Override
    public void close() throws Exception {

    }
}
