package com.epam.dataBase.dao.interfaces.impl;

import com.epam.dataBase.connection.ConnectionPool;
import com.epam.dataBase.connection.ConnectionPoolException;
import com.epam.dataBase.dao.interfaces.OrganizationDAO;
import com.epam.entity.HuntingGround;
import com.epam.entity.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAOImpl implements OrganizationDAO {

    private static final String GET_ORGANIZATION_INFO = "SELECT O.NAME, O.DESCRIPTION, O.LOGO, " +
            "HG.NAME, HG.DAILY_PRICE, HG.SEASON_PRICE " +
            "FROM ORGANIZATION O, HUNTING_GROUND HG, LANGUAGE L " +
            "WHERE HG.ORGANIZATION_ID = O.ID AND O.ID = ? AND L.ID = ?";
    private static final String ADD_NEW_ORGANIZATION = "INSERT INTO ORGANIZATION (NAME, DESCRIPTION, LOGO, " +
            "LANGUAGE_ID) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM ORGANIZATION";
    private static final String SELECT_ORGANIZATION_BY_ID = "SELECT NAME, DESCRIPTION, LOGO, LANGUAGE_ID " +
            "FROM ORGANIZATION WHERE ID = ?";
    private static final String EDIT_ORGANIZATION_BY_ID = "UPDATE ORGANIZATION SET NAME = ?, DESCRIPTION = ?, " +
            "LOGO = ? WHERE ID = ?";
    private static final String REMOVE_ORGANIZATION_BY_ID = "DELETE FROM ORGANIZATION WHERE ID = ?";
    private ConnectionPool connectionPool;
    private Connection connection;

    public void getOrganizationInfo(int organizationId, int languageId) throws SQLException, ConnectionPoolException {
        Organization organization = new Organization();
        HuntingGround hg = new HuntingGround();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORGANIZATION_INFO)) {
            preparedStatement.setInt(1, organizationId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            organization.setName(resultSet.getString("name"));
            organization.setDescription(resultSet.getString("description"));
            organization.setLogo(resultSet.getBytes("logo"));
            hg.setName(resultSet.getString("name"));
            hg.setDailyPrice(resultSet.getDouble("dailyPrice"));
            hg.setSeasonPrice(resultSet.getDouble("seasonPrice"));
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void create(Organization organization) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement ps = connection.prepareStatement(ADD_NEW_ORGANIZATION)) {
            ps.setString(1, organization.getName());
            ps.setString(2, organization.getDescription());
            ps.setBytes(3, organization.getLogo());
            ps.setInt(4, organization.getLanguageID());
            ps.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToOrganization(Organization organization, ResultSet rs) throws SQLException {
        organization.setName(rs.getString("name"));
        organization.setDescription(rs.getString("description"));
        organization.setLogo(rs.getBytes("logo"));
        organization.setLanguageID(rs.getInt("languageID"));
    }

    @Override
    public List<Organization> getAll() throws SQLException, ConnectionPoolException {
        List<Organization> organizations = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORGANIZATIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Organization organization = new Organization();
                setParametersToOrganization(organization, resultSet);
                organizations.add(organization);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organizations;
    }

    @Override
    public Organization getByID(int id) throws SQLException, ConnectionPoolException {
        Organization organization = new Organization();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORGANIZATION_BY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            setParametersToOrganization(organization, resultSet);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    @Override
    public void update(int id, Organization organization) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ORGANIZATION_BY_ID)) {
            preparedStatement.setString(1, organization.getName());
            preparedStatement.setString(2, organization.getDescription());
            preparedStatement.setBytes(3, organization.getLogo());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_ORGANIZATION_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
