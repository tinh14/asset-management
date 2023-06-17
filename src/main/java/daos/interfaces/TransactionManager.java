/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public interface TransactionManager {
    static final String HOST = "localhost";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    static final int PORT = 3306;
    static final String DATABASE = "QL_TAISAN";
    static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    static final boolean AUTO_COMMIT = false;
    
    public void initConnection();
    public Connection getConnection();
    public void commitAndCloseConnection() throws SQLException;
    public void closeConnection();
}
