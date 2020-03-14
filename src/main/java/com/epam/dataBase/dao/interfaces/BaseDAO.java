package com.epam.dataBase.dao.interfaces;

import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T extends Entity> {

    void create(T object) throws SQLException, ConnectionPoolException;

    List<T> getAll(int id) throws SQLException, ConnectionPoolException;

    List<T> getByID(int idOrder) throws SQLException, ConnectionPoolException;

    void editByID(int id, String query) throws SQLException, ConnectionPoolException;

    void delete(int id) throws SQLException, ConnectionPoolException;
}
