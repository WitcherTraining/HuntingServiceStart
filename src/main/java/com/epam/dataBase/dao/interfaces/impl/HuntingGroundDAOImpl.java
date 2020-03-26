package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.HuntingGroundDAO;
import com.epam.entity.Animal;
import com.epam.entity.Entity;
import com.epam.entity.HuntingGround;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HuntingGroundDAOImpl implements HuntingGroundDAO {
    private static final String ADD_HUNTING_GROUND = "INSERT INTO HUNTING_GROUND (NAME, DESCRIPTION, DAILY_PRICE, " +
            "SEASON_PRICE, ORGANIZATION_ID, LANGUAGE_ID) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_HUNTING_GROUNDS = "SELECT * FROM HUNTING_GROUND";
    private static final String SELECT_HUNTING_GROUND_BY_ID = "SELECT NAME, DESCRIPTION, DAILY_PRICE, SEASON_PRICE, " +
            " ORGANIZATION_ID, LANGUAGE_ID FROM HUNTING_GROUND WHERE ID = ?";
    private static final String UPDATE_HUNTING_GROUND = "UPDATE HUNTING_GROUND SET DESCRIPTION = ?, DAILY_PRICE = ?, " +
            " SEASON_PRICE = ?, ORGANIZATION_ID = ? WHERE ID = ?";
    private static final String INSERT_DATA_IN_HISTORY_HUNTING_GROUND_LIMIT = "INSERT INTO HISTORY_HUNTING_GROUND_LIMIT " +
            "(YEAR, HUNTING_GROUND_LIMIT, HUNTING_GROUND_ID, ANIMAL_ID) VALUES (?,?,?,?)";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public void create(HuntingGround huntingGround) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_HUNTING_GROUND)) {
            preparedStatement.setString(1, huntingGround.getName());
            preparedStatement.setString(2, huntingGround.getDescription());
            preparedStatement.setDouble(3, huntingGround.getDailyPrice());
            preparedStatement.setDouble(4, huntingGround.getSeasonPrice());
            preparedStatement.setInt(5, huntingGround.getOrganizationID());
            preparedStatement.setInt(6, huntingGround.getLanguageID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToHuntingGround(HuntingGround huntingGround, ResultSet resultSet) throws SQLException {
        huntingGround.setName(resultSet.getString("name"));
        huntingGround.setDescription(resultSet.getString("description"));
        huntingGround.setDailyPrice(resultSet.getDouble("dailyPrice"));
        huntingGround.setSeasonPrice(resultSet.getDouble("seasonPrice"));
        huntingGround.setOrganizationID(resultSet.getInt("organizationID"));
        huntingGround.setLanguageID(resultSet.getInt("languageID"));
    }

    @Override
    public List<HuntingGround> getAll() throws SQLException, ConnectionPoolException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HUNTING_GROUNDS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HuntingGround huntingGround = new HuntingGround();
                setParametersToHuntingGround(huntingGround, resultSet);
                huntingGrounds.add(huntingGround);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public Entity getByID(int id) throws SQLException, ConnectionPoolException {
        HuntingGround huntingGround = new HuntingGround();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HUNTING_GROUND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            setParametersToHuntingGround(huntingGround, resultSet);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGround;
    }

    @Override
    public void update(int id, HuntingGround huntingGround) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HUNTING_GROUND)) {
            preparedStatement.setString(1, huntingGround.getDescription());
            preparedStatement.setDouble(2, huntingGround.getDailyPrice());
            preparedStatement.setDouble(3, huntingGround.getSeasonPrice());
            preparedStatement.setInt(4, huntingGround.getOrganizationID());
            preparedStatement.setInt(5, id);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void setDataInHistoryHuntingGroundLimit(int year, int limit, HuntingGround huntingGround, Animal animal)
            throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_DATA_IN_HISTORY_HUNTING_GROUND_LIMIT)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, huntingGround.getId());
            preparedStatement.setInt(4, animal.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }
}
