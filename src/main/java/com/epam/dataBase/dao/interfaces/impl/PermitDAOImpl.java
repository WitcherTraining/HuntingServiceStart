package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.PermitDAO;
import com.epam.entity.Permit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermitDAOImpl implements PermitDAO {
    private static final String ADD_PERMIT = "INSERT INTO PERMIT (ORDER_DATE, COUNT_ORDERED_ANIMALS, PERMIT_TYPE, " +
            "USER_ID, ANIMAL_ID) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_PERMITS = "SELECT * FROM PERMIT";
    private static final String SELECT_PERMIT_BY_ID = "SELECT ORDER_DATE, COUNT_ORDERED_ANIMALS, PERMIT_TYPE, " +
            "USER_ID, ANIMAL_ID FROM PERMIT WHERE ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public void create(Permit permit) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PERMIT)) {
            java.sql.Date sqlPermitOrderDate = new java.sql.Date(permit.getOrderDate().getTime());
            preparedStatement.setDate(1, sqlPermitOrderDate);
            preparedStatement.setInt(2, permit.getCountOrderedAnimals());
            preparedStatement.setBoolean(3, permit.isPermitType());
            preparedStatement.setInt(4, permit.getUserID());
            preparedStatement.setInt(5, permit.getAnimalID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToPermit(Permit permit, ResultSet resultSet) throws SQLException {
        permit.setOrderDate(resultSet.getDate("orderDate"));
        permit.setCountOrderedAnimals(resultSet.getInt("countOrderedAnimals"));
        permit.setPermitType(resultSet.getBoolean("permitType"));
        permit.setUserID(resultSet.getInt("userID"));
        permit.setAnimalID(resultSet.getInt("animalID"));
    }

    @Override
    public List<Permit> getAll() throws SQLException, ConnectionPoolException {
        List<Permit> permits = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PERMITS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Permit permit = new Permit();
                setParametersToPermit(permit, resultSet);
                permits.add(permit);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return permits;
    }

    @Override
    public Permit getByID(int id) throws SQLException, ConnectionPoolException {
        Permit permit = new Permit();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PERMIT_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            setParametersToPermit(permit, resultSet);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    @Override
    public void update(int id, Permit permit) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }
}
