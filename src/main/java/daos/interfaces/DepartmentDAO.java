/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.DepartmentModel;

/**
 *
 * @author tinhlam
 */
public interface DepartmentDAO {
    public List<DepartmentModel> findAll();
    public List<DepartmentModel> findById(int id);
    public List<DepartmentModel> findByName(String name);
    public int create(Connection connection, DepartmentModel department) throws SQLException;
    public void update(Connection connection, DepartmentModel department) throws SQLException;
    public void delete(Connection connection, int id) throws SQLException;
}
