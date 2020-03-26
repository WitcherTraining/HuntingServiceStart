package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.AnimalDAO;
import com.epam.entity.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {
    private static final String ADD_ANIMAL = "INSERT INTO ANIMAL (NAME, TERM_BEGIN, TERM_END, LANGUAGE_ID) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ANIMALS = "SELECT * FROM ANIMAL";
    private static final String SELECT_ANIMAL_BY_ID = "SELECT NAME, TERM_BEGIN, TERM_END " +
            "FROM ANIMAL WHERE ID = ?";
    private static final String UPDATE_ANIMAL_ONLY_NAME = "UPDATE ANIMAL SET NAME = ? WHERE ID = ?";
    private static final String INSERT_DATA_IN_ANIMAL_TO_HUNTING_GROUND = "INSERT INTO ANIMAL_TO_HUNTING_GROUND " +
            "(ANIMAL_ID, HUNTING_GROUND_ID) VALUES (ANIMAL_ID = ?, HUNTING_GROUND_ID = ?)";
    private static final String GET_ALL_ANIMAL_GLOBAL_LIMIT = "SELECT A.ID, HAL.YEAR, A.NAME, HAL.ALL_LIMIT, " +
            "A.TERM_BEGIN, A.TERM_END FROM ANIMAL A, HISTORY_ALL_ANIMAL_LIMIT HAL " +
            "WHERE HAL.ANIMAL_ID = A.ID AND A.ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public void create(Animal animal) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANIMAL)){
            preparedStatement.setString(1,animal.getName());
            preparedStatement.setString(2, animal.getTermBegin());
            preparedStatement.setString(3, animal.getTermEnd());
            preparedStatement.setInt(4, animal.getLanguageID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void setDataAnimalToHuntingGround(int animalID, int huntingGroundID)
            throws SQLException, ConnectionPoolException{
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATA_IN_ANIMAL_TO_HUNTING_GROUND)){
            preparedStatement.setInt(1,animalID);
            preparedStatement.setInt(2, huntingGroundID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToAnimal(Animal animal, ResultSet resultSet) throws SQLException{
        animal.setName(resultSet.getString("name"));
        animal.setTermBegin(resultSet.getString("termBegin"));
        animal.setTermEnd(resultSet.getString("termEnd"));
        animal.setLanguageID(resultSet.getInt("languageID"));
    }

    @Override
    public List<Animal> getAll() throws SQLException, ConnectionPoolException {
        List<Animal> animals = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ANIMALS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Animal animal = new Animal();
                setParametersToAnimal(animal, resultSet);
                animals.add(animal);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animals;
    }

    @Override
    public Animal getByID(int id) throws SQLException, ConnectionPoolException {
        Animal animal = new Animal();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ANIMAL_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            setParametersToAnimal(animal, resultSet);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animal;
    }

    @Override
    public void update(int id, Animal animal) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANIMAL_ONLY_NAME)){
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setInt(2, id);
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
