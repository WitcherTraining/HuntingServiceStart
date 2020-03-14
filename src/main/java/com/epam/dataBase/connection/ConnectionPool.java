package com.epam.dataBase.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private static ConnectionPool instance;
    private Properties properties = getProperties("ConnectionPool.properties");
    private final int maxConnections = Integer.parseInt(properties.getProperty("db.poolsize"));
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(maxConnections);

    private ConnectionPool() {
        DBResourceManager dbResourseManager = DBResourceManager.getInstance();
        this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourseManager.getValue(DBParameter.DB_URL);
        this.user = dbResourseManager.getValue(DBParameter.DB_USER);
        this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        Connection connection;

        try {
            Class.forName(driverName);

            while (connectionQueue.size() < maxConnections){
                connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);
        }
    }

    private Properties getProperties(String configurationFile){
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(configurationFile);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e);
        }
        return properties;
    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error closing the connection.", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {	con.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool.");
        }
        try {	rs.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ResultSet isn't closed.");
        }
        try {	st.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement isn't closed.");
        }
    }

    public void closeConnection(Connection con, Statement st) {
        try {	con.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool.");
        }
        try {	st.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement isn't closed.");
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }
    }
}
