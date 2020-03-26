package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.UserDAO;
import com.epam.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String ADD_USER = "INSERT INTO USER (NAME, SURNAME, LOGIN, PASSWORD, EMAIL, PHONE, ROLE_ID) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM USER";
    private static final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = ? WHERE (ID = ?)";
    private static final String CHANGE_ROLE = "UPDATE USER SET ROLE_ID = ? WHERE (ID = ?)";
    private static final String GET_USERS_BY_ID = "SELECT U.ID, U.NAME, U.SURNAME, U.LOGIN, U.PASSWORD, " +
            "U.EMAIL, U.PHONE, R.ROLE FROM USER U, PERMIT P, ROLE R WHERE U.ROLE_ID=R.ID AND U.ID=?";
    private static final String DELETE_USER = "DELETE FROM USER WHERE ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    private void setParametersToUser(User user, ResultSet resultSet) throws SQLException {
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getInt("phone"));
        user.setRoleID(resultSet.getInt("roleID"));
    }

    @Override
    public void create(User user) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getPhone());
            preparedStatement.setInt(7, user.getRoleID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                setParametersToUser(user, resultSet);
                users.add(user);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public User getByID(int id) throws SQLException, ConnectionPoolException {
        User user = new User();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                setParametersToUser(user, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public void update(int id, User user) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(String password, int id) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void changeUserRole(String role, int id) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ROLE)) {
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
