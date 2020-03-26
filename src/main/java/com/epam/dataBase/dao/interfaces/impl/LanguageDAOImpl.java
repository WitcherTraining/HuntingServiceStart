package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.LanguageDAO;
import com.epam.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAOImpl implements LanguageDAO {

    private static final String GET_LANGUAGE_ID = "SELECT ID FROM LANGUAGE WHERE LANGUAGE=?";
    private ConnectionPool connectionPool;
    private Connection connection;

    public int getLanguageId(String language) throws SQLException, ConnectionPoolException {
        int idLanguage = 1;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_ID)){
            preparedStatement.setString(1, language);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idLanguage = resultSet.getInt("id");
            }
        }
        return idLanguage;
    }

    @Override
    public void create(Language object) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Language> getAll() throws SQLException, ConnectionPoolException {
        return new ArrayList<>();
    }

    @Override
    public Language getByID(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(int id, Language language) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        throw new UnsupportedOperationException();
    }
}
