package com.epam.dataBase.dao.interfaces;

import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T extends Entity> {

    void create(T object) throws SQLException, ConnectionPoolException;

    List<T> getAll() throws SQLException, ConnectionPoolException;

    Entity getByID(int id) throws SQLException, ConnectionPoolException;

    void update(int id, T object) throws SQLException, ConnectionPoolException;

    void delete(int id) throws SQLException, ConnectionPoolException;
}
