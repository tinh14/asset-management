/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mappers.RowMapper;

/**
 *
 * @author tinhlam
 */
public interface GenericDAO {

    static final String HOST = "localhost";
    static final int PORT = 3306;
    static final String DATABASE = "QL_TAISAN";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    static final boolean AUTO_COMMIT = false;

    public void setParameters(PreparedStatement stmt, Object... parameters);

    public <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... parameters);

    public boolean executeQuery(String sql, Object... parameters);

    public void executeUpdate(Connection connection, String sql, Object... parameters) throws SQLException;

    public int executeScalar(Connection connection, String sql, Object... parameters) throws SQLException;
}
