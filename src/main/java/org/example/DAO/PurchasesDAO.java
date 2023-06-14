package org.example.DAO;

import org.example.Connections.ConnectionMySQL;
import org.example.DOMAIN.cart;
import org.example.DOMAIN.purchases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO implements DAO<purchases>{
    private static final String FIND_ALL = "SELECT * FROM compras";
    private static final String FINDBYID = "SELECT * FROM compras WHERE  id_producto=?";
    private static final String INSERT = "INSERT INTO compras (id_usuario, id_producto, fecha_compra,cantidad, precio) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE compras SET cantidad = ? WHERE id_usuario = ? AND id_producto = ?";
    private static final String DELETE = "DELETE FROM compras WHERE id_producto=?";
    private static final String FINDBYIDDATE ="SELECT * FROM compras WHERE id_usuario = ? AND id_producto = ? AND fecha_compra = ?";
    private static final String DELETEALL = "DELETE FROM compras";
    private static final String FINDBYISERID =  "SELECT * FROM compras WHERE id_usuario = ?";

    private static PurchasesDAO instance = null;
    private Connection conn;

    private PurchasesDAO(Connection conn) {
        this.conn = conn;
    }

    public static PurchasesDAO getInstance() {
        if (instance == null) {
            instance = new PurchasesDAO(ConnectionMySQL.getConnect());
        }
        return instance;
    }

    @Override
    public List<purchases> findAll() throws SQLException {
        List<purchases> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    purchases p = new purchases();
                    p.setId_user(UserDAO.getInstance().findById(String.valueOf(res.getInt("id_usuario"))));
                    p.setId_product(ProductsDAO.getInstance().findById(String.valueOf(res.getInt("id_producto"))));
                    p.setBuyDate(res.getDate("fecha_compra"));
                    p.setCant(res.getInt("cantidad"));
                    p.setPrice(res.getDouble("precio"));
                    result.add(p);
                }
            }
        }
        return result;
    }

    @Override
    public purchases findById(String id) throws SQLException {
        purchases result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            pst.setString(2, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new purchases();
                    result.setId_user(UserDAO.getInstance().findById(String.valueOf(res.getInt("id_usuario"))));
                    result.setId_product(ProductsDAO.getInstance().findById(String.valueOf(res.getInt("id_producto"))));
                    result.setBuyDate(res.getDate("fecha_compra"));
                    result.setCant(res.getInt("cantidad"));
                    result.setPrice(res.getDouble("precio"));
                }
            }
        }
        return result;
    }


    @Override
    public purchases save(purchases entity) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId_user().getId());
            pst.setInt(2, entity.getId_product().getId());
            pst.setDate(3, new java.sql.Date(entity.getBuyDate().getTime()));
            pst.setInt(4, entity.getCant());
            pst.setDouble(5, entity.getPrice());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public void delete(purchases entity) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId_product().getId());
            pst.executeUpdate();
        }
    }


    public void deleteAll() throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETEALL)) {
            pst.executeUpdate();
        }
    }

    @Override
    public void close() throws Exception {
        // Cerrar la conexión u otras tareas de limpieza si es necesario
    }

    public void update(cart existingPurchases) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1,existingPurchases.getCant());
            pst.setInt(2, existingPurchases.getId_user().getId());
            pst.setInt(3, existingPurchases.getId_product().getId());
            pst.executeUpdate();
        }
    }
    public purchases findByProductId(int id, String name) throws SQLException {
        purchases result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new purchases();
                    result.setId_user(UserDAO.getInstance().findById(String.valueOf(res.getInt("id_usuario"))));
                    result.setId_product(ProductsDAO.getInstance().findById(String.valueOf(res.getInt("id_producto"))));
                    result.setBuyDate(res.getDate("fecha_compra"));
                    result.setCant(res.getInt("cantidad"));
                    result.setPrice(res.getDouble("precio"));
                }
            }
        }
        return result;
    }
    public purchases findByUserIdProductIdDate(int userId, int productId, java.sql.Date date) throws SQLException {
        purchases existingCartItem = null;

        try (PreparedStatement pst = conn.prepareStatement(FINDBYIDDATE)) {
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            pst.setDate(3, date);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    existingCartItem = new purchases();
                    existingCartItem.setId_user(UserDAO.getInstance().findById(String.valueOf(rs.getInt("id_usuario"))));
                    existingCartItem.setId_product(ProductsDAO.getInstance().findById(String.valueOf(rs.getInt("id_producto"))));
                    existingCartItem.setBuyDate(rs.getDate("fecha_compra"));
                    existingCartItem.setCant(rs.getInt("cantidad"));
                    existingCartItem.setPrice(rs.getDouble("precio"));
                }
            }
        }
        return existingCartItem;
    }
    public List<purchases> findByUserId(int userId) throws SQLException {
        List<purchases> result = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(FINDBYISERID)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    purchases p = new purchases();
                    p.setId_user(UserDAO.getInstance().findById(String.valueOf(rs.getInt("id_usuario"))));
                    p.setId_product(ProductsDAO.getInstance().findById(String.valueOf(rs.getInt("id_producto"))));
                    p.setBuyDate(rs.getDate("fecha_compra"));
                    p.setCant(rs.getInt("cantidad"));
                    p.setPrice(rs.getDouble("precio"));
                    result.add(p);
                }
            }
        }
        return result;
    }
}
