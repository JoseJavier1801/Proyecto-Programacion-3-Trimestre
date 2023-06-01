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

public class CartDAO implements DAO<cart> {

    private static final String FIND_ALL = "SELECT * FROM carrito";
    private static final String FINDBYID = "SELECT * FROM carrito WHERE id_usuario=? OR id_producto=?";
    private static final String INSERT = "INSERT INTO carrito (id_usuario, id_producto, fecha_compra,cantidad, precio) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE carrito SET cantidad=?, precio=? WHERE nombreProducto=?";
    private static final String DELETE = "DELETE FROM carrito WHERE id_producto=?";
    private static final String DELETEALL = "DELETE FROM carrito";

    private static CartDAO instance = null;
    private Connection conn;

    private CartDAO(Connection conn) {
        this.conn = conn;
    }

    public static CartDAO getInstance() {
        if (instance == null) {
            instance = new CartDAO(ConnectionMySQL.getConnect());
        }
        return instance;
    }

    @Override
    public List<cart> findAll() throws SQLException {
        List<cart> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    cart c = new cart();
                    c.setId_user(UserDAO.getInstance().findById(String.valueOf(res.getInt("id_usuario"))));
                    c.setId_product(ProductsDAO.getInstance().findById(String.valueOf(res.getInt("id_producto"))));
                    c.setBuyDate(res.getDate("fecha_compra"));
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
        cart result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            pst.setString(2, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new cart();
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
    public cart save(cart entity) throws SQLException {
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
    public void delete(cart entity) throws SQLException {
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
}
