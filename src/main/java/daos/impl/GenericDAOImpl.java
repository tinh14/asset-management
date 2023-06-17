/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import java.sql.Blob;
import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.RowMapper;
import daos.interfaces.GenericDAO;
import daos.interfaces.TransactionManager;
import java.sql.Statement;
import java.sql.Types;
import javax.inject.Inject;

/**
 *
 * @author tinhlam
 */
@Default
public class GenericDAOImpl implements GenericDAO {

    @Inject
    private TransactionManager transactionManager;

    @Override
    public void setParameters(PreparedStatement stmt, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                int columnIndex = i + 1;
                Object parameter = parameters[i];
                if (parameter instanceof Integer) {
                    stmt.setInt(columnIndex, (Integer) parameter);
                } else if (parameter instanceof String) {
                    stmt.setString(columnIndex, (String) parameter);
                } else if (parameter instanceof Timestamp) {
                    stmt.setTimestamp(columnIndex, (Timestamp) parameter);
                } else if (parameter instanceof Blob) {
                    stmt.setBlob(columnIndex, (Blob) parameter);
                } else if (parameter instanceof Boolean) {
                    stmt.setBoolean(columnIndex, (Boolean) parameter);
                } else if (parameter instanceof Date) {
                    stmt.setDate(columnIndex, (Date) parameter);
                } else if (parameter == null) {
                    stmt.setNull(columnIndex, Types.NULL);
                }
            }
        } catch (SQLException ex) {
        }
    }

    @Override
    public <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... parameters) {
        List<T> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        Connection con;
        try {
            transactionManager.initConnection();
            con = transactionManager.getConnection();
            stmt = con.prepareStatement(sql);
            this.setParameters(stmt, parameters);
            res = stmt.executeQuery();
            while (res.next()) {
                list.add(mapper.mapRow(res));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            transactionManager.closeConnection();
        }
        return list;
    }

    @Override
    public boolean executeQuery(String sql, Object... parameters) {
        boolean result = false;
        ResultSet res = null;
        PreparedStatement stmt = null;
        Connection con;
        try {
            transactionManager.initConnection();
            con = transactionManager.getConnection();
            stmt = con.prepareStatement(sql);
            this.setParameters(stmt, parameters);
            res = stmt.executeQuery();
            if (res.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            transactionManager.closeConnection();
        }
        return result;
    }

    /**
     *
     * @param con
     * @param sql
     * @param parameters
     * @throws java.sql.SQLException
     */
    @Override
    public void executeUpdate(Connection con, String sql, Object... parameters) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            this.setParameters(stmt, parameters);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    @Override
    public int executeScalar(Connection con, String sql, Object... parameters) throws SQLException {
        int result = -1;
        PreparedStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.setParameters(stmt, parameters);
            stmt.executeUpdate();
            res = stmt.getGeneratedKeys();
            if (res.next()) {
                result = res.getInt(1);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }
}
