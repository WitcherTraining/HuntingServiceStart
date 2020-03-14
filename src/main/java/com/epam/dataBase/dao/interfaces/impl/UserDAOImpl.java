package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.UserDAO;
import com.epam.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String ADD_USER = "INSERT INTO USER (NAME, SURNAME, LOGIN, PASSWORD, EMAIL, PHONE, ROLE) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = ? WHERE (ID = ?)";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public void changePassword(String password, int id) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.dispose();
        }
    }

    @Override
    public void changeUserRole(String role, int id) throws SQLException {

    }

    @Override
    public void create(User object) throws SQLException, ConnectionPoolException {
        User user = (User) object;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(5, user.getPhone());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<User> getAll(int id) throws SQLException {
        return null;
    }

    @Override
    public List<User> getByID(int idOrder) throws SQLException {
        return null;
    }

    @Override
    public void editByID(int id, String query) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
