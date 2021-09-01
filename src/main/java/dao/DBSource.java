package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBSource {
    public abstract Connection getConnection() throws SQLException;
    public abstract void closeConnection(Connection conn) throws SQLException;
}
