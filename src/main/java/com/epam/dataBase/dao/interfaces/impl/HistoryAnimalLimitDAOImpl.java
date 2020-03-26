package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.BaseDAO;
import com.epam.entity.Animal;
import com.epam.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HistoryAnimalLimitDAOImpl implements BaseDAO {
    private static final String ADD_GLOBAL_LIMIT_HISTORY = "INSERT INTO HISTORY_ALL_ANIMAL_LIMIT " +
            " (YEAR, ALL_LIMIT, ANIMAL_ID) VALUES (?,?,?)";

    private ConnectionPool connectionPool;
    private Connection connection;

    public void setGlobalLimitHistory(int year, int limit, Animal animal) throws SQLException, ConnectionPoolException{
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GLOBAL_LIMIT_HISTORY)){
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(1, animal.getId());
        }
    }

    @Override
    public void create(Entity object) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getAll() throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity getByID(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(int id, Entity object) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }
}
