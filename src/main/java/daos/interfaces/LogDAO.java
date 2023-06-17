/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.LogModel;

/**
 *
 * @author tinhlam
 */
public interface LogDAO {

    public List<LogModel> findAll();

    public List<LogModel> findById(int id);

    public List<LogModel> findByRecordId(String id);

    public int create(Connection connection, LogModel log) throws SQLException;
}
