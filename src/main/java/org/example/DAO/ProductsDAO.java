package org.example.DAO;

import org.example.DOMAIN.Products;

import java.sql.SQLException;
import java.util.List;

public class ProductsDAO implements DAO<Products>{
    @Override
    public List<Products> findAll() throws SQLException {
        return null;
    }

    @Override
    public Products findById(String id) throws SQLException {
        return null;
    }

    @Override
    public Products save(Products entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(Products entity) throws SQLException {

    }
    @Override
    public void close() throws Exception {

    }
}
