package com.epam.dataBase.dao.interfaces;

import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.entity.User;

import java.sql.SQLException;

public interface UserDAO extends BaseDAO<User> {

    void changePassword(String password, int id) throws SQLException, ConnectionPoolException;

    void changeUserRole(String role, int id) throws SQLException;
}
