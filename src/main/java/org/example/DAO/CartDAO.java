package org.example.DAO;

import org.example.Connections.ConnectionMySQL;
import org.example.DOMAIN.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CartDAO que tiene las consultas y los metodos que llaman los controladores
 */

public class CartDAO implements DAO<cart>{

    private final static String FINDALL = "SELECT * FROM carrito";;
    private final static String FINDBYID = "SELECT * FROM carrito WHERE id_usuario=? OR id_producto=?";
    private final static String INSERT = "INSERT INTO carrito (id_usuario, id_producto,fecha_compra,nombreProducto, cantidad, precio) VALUES (?, ?, ?, ?, ?,?)";
    private final static String UPDATE = "UPDATE carrito SET cantidad=?, precio=? WHERE nombreProducto=?";
    private final static String DELETE = "DELETE FROM carrito WHERE nombreProducto=?";
    private static final String DELETEALL = "DELETE FROM carrito";

    private static CartDAO instance = null;

    private Connection conn;

    public CartDAO(Connection conn){
        this.conn=conn;
    }

    public CartDAO() {
        this.conn= ConnectionMySQL.getConnect();
    }

    public static CartDAO getInstance() {
        if (instance == null) {
            instance = new CartDAO();
        }
        return instance;
    }


    @Override
    public List<cart> findAll() throws SQLException {
        List<cart> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    cart c = new cart();
                    c.setId_user(res.getInt("id_usuario"));
                    c.setId_product(res.getInt("id_producto"));
                    c.setBuyDate(res.getDate("fecha_compra"));
                    c.setProductName(res.getString("nombreProducto"));
                    c.setCant(res.getInt("cantidad"));
                    c.setPrice(res.getDouble("precio"));
                    result.add(c);
                }
            }
        }
        return result;
    }

    @Override
    public cart findById(String id) throws SQLException {
        cart result=null;
        try (PreparedStatement pst=this.conn.prepareStatement(FINDBYID)){
            pst.setString(1,id);
            pst.setString(2,id);
            try (ResultSet res= pst.executeQuery()){
                if(res.next()){
                    result.setProductName(res.getString("nombreProducto"));
                    result.setBuyDate(res.getDate("fecha_compra"));
                    result.setCant(res.getInt("cantidad"));
                    result.setPrice(res.getDouble("precio"));
                }

            }
        }
        return result;
    }

    public cart findByProductAndUser(int productId, int userId) throws SQLException {
        cart result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new cart();
                    result.setId_user(res.getInt("id_usuario"));
                    result.setId_product(res.getInt("id_producto"));
                    result.setBuyDate(res.getDate("fecha_compra"));
                    result.setProductName(res.getString("nombreProducto"));
                    result.setCant(res.getInt("cantidad"));
                    result.setPrice(res.getDouble("precio"));
                }
            }
        }
        return result;
    }

    @Override
    public cart save(cart entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId_user());
            pst.setInt(2, entity.getId_product());
            pst.setDate(3, new java.sql.Date(entity.getBuyDate().getTime()));
            pst.setString(4, entity.getProductName());
            pst.setInt(5, entity.getCant());
            pst.setDouble(6, entity.getPrice());
            pst.executeUpdate();
        }
        return entity;
    }

    public cart Update(cart entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
            pst.setInt(1, entity.getCant());
            pst.setDouble(2, entity.getPrice());
            pst.setString(3, entity.getProductName());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public void delete(cart entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
            pst.setString(1, entity.getProductName());
            pst.executeUpdate();
        }
    }

    public void deleteALL() throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEALL)) {
            pst.executeUpdate();
        }
    }


    @Override
    public void close() throws Exception {

    }
}
