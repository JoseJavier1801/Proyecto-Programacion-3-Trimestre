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

/**
 * ProductsDAO que tiene las consultas y los metodos que llaman los controladores
 */
public class ProductsDAO implements DAO<Products> {
    /**
     * Querys que utilizaran los metodos de ProductsDAO
     */
    private final static String FINDALL = "SELECT * FROM productos";
    private final static String FINDBYADMINID = "SELECT * FROM productos WHERE id_admin=?";
    private final static String FINDBYID = "SELECT * FROM productos WHERE id_p=?";
    private final static String FINDID = "SELECT id_p FROM productos WHERE nombre_producto=?";
    private final static String FINDBYNAME = "SELECT * FROM productos WHERE nombre_producto=?";
    private final static String INSERT = "INSERT INTO productos (id_p, nombre_producto, descripcion, stock, precio, id_admin) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE productos SET precio=?, stock=? WHERE nombre_producto=?";
    private final static String DELETE = "DELETE FROM productos WHERE nombre_producto=?";
    private static final String UPDATESTOCK = "UPDATE productos SET stock=? WHERE nombre_producto=?";


    private Connection conn; //establecer conexion a la base de datos
    private AdminDAO adminDAO; //inizializa el adminDAO para el id Del administrador
    private static ProductsDAO instance = null;

    /**
     * Constructor que inicializa la conexion a la base de datos
     */
    private ProductsDAO() {
        this.conn = ConnectionMySQL.getConnect();
        if (this.conn == null) {
            throw new RuntimeException("Unable to establish connection to the database.");
        }
        this.adminDAO = AdminDAO.getInstance();
    }

    public static ProductsDAO getInstance() {
        if (instance == null) {
            instance = new ProductsDAO();
        }
        return instance;
    }

    /**
     * metodo que encuentra todos los productos
     * @return
     * @throws SQLException
     */
    @Override
    public List<Products> findAll() throws SQLException {
        List<Products> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Products p = new Products();
                    p.setId(res.getInt("id_p"));
                    p.setName(res.getString("nombre_producto"));
                    p.setDescription(res.getString("descripcion"));
                    p.setStock(res.getInt("stock"));
                    p.setPrice(res.getDouble("precio"));
                    Admin admin = new Admin();
                    admin.setId(res.getInt("id_admin"));
                    p.setId_admin(admin);
                    result.add(p);
                }
            }
        }
        return result;
    }

    public List<Products> findByAdminId(int id) throws SQLException {
        List<Products> productsList = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FINDBYADMINID)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id_p"));
                product.setName(rs.getString("nombre_producto"));
                product.setDescription(rs.getString("descripcion"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getDouble("precio"));
                int adminId = rs.getInt("id_admin");
                AdminDAO adminDAO = AdminDAO.getInstance();
                Admin admin = adminDAO.findById(String.valueOf(adminId));
                product.setId_admin(admin);
                productsList.add(product);
            }
        }

        return productsList;
    }

    /**
     * encuentra los productos por id
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Products findById(String id) throws SQLException {
        Products result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Products();
                    result.setId(res.getInt("id_p"));
                    result.setName(res.getString("nombre_producto"));
                    result.setDescription(res.getString("descripcion"));
                    result.setStock(res.getInt("stock"));
                    result.setPrice(res.getDouble("precio"));
                    // Obtener el administrador del producto
                    int adminId = res.getInt("id_admin");
                    AdminDAO ADAO = AdminDAO.getInstance();
                    Admin admin = ADAO.findById(String.valueOf(adminId));
                    result.setId_admin(admin);
                }
            }
        }
        return result;
    }

    /**
     * encuentra los productos por nombre
     * @param name del producto a buscar
     * @return la informacion de ese producto
     * @throws SQLException
     */

    public Products findByName(String name) throws SQLException {
        Products result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Products();
                    result.setName(res.getString("nombre_producto"));
                    result.setDescription(res.getString("descripcion"));
                    result.setStock(res.getInt("stock"));
                    result.setPrice(res.getDouble("precio"));
                    // Obtener el objeto Admin correspondiente
                    Admin admin = new Admin();
                    admin.setId(res.getInt("id_admin"));
                    result.setId_admin(admin);
                }
            }
        }
        return result;
    }

    /**
     * metodo que busca el ide del producto por nombre
     * @param name el nombre del producto
     * @return el id del producto
     * @throws SQLException
     */
    public int findId(String name) throws SQLException {
        int result = 0;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDID)) {
            pst.setString(1, name);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result=res.getInt("id_p");
                }
            }
        }
        return result;
    }

    /**
     * metodo save que guarda los datos de los producto
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Products save(Products entity) throws SQLException {
        if (entity == null) {
            return null;
        }
        // Comprobar si el producto ya existe en la base de datos
        Products existingProduct = findByName(entity.getName());
        if (existingProduct == null) {
            // Insertar nuevo registro
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getDescription());
                pst.setInt(4, entity.getStock());
                pst.setDouble(5, entity.getPrice());
                pst.setInt(6, entity.getId_admin().getId());
                pst.executeUpdate();
            }
        } else {
            // Actualizar registro existente
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setDouble(1, entity.getPrice());
                pst.setInt(2, entity.getStock());
                pst.setString(3, entity.getName());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    /**
     * metodo delete que elimina un producto
     * @param entity recibe el nombre del producto a eliminar
     * @throws SQLException
     */
    @Override
    public void delete(Products entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getName());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {

    }

    /**
     * metodo que actualiza unicamente el stock de los productos
     * @param entity recibe el producto a actualizar
     * @return el producto actualizado
     * @throws SQLException
     */
    public Products UpdateStock(Products entity) throws SQLException {
        Products result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(UPDATESTOCK)) {
            pst.setInt(1, entity.getStock());
            pst.setString(2, entity.getName());

            pst.executeUpdate();
            result = entity;

        }
        return result;
    }
}



