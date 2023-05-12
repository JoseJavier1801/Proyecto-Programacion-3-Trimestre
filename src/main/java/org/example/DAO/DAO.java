package org.example.DAO;

import org.example.DOMAIN.User;

import java.sql.SQLException;
import java.util.List;

/**
 * interfaz DAO que se implementa en las Clases DAOS con metodos genericos
 * @param <T>
 */
public interface DAO<T> extends AutoCloseable{
	List<T> findAll() throws SQLException ;
	T findById(String id) throws SQLException ;

	T save(T entity) throws SQLException ;

    void delete(T entity) throws SQLException ;
}
