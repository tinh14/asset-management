/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.DepartmentDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.DepartmentMapper;
import models.DepartmentModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class DepartmentDAOImpl implements DepartmentDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<DepartmentModel> findAll() {
        String sql = "select * from Department";
        return genericDAO.executeQuery(sql, new DepartmentMapper());
    }

    @Override
    public List<DepartmentModel> findById(int id) {
        String sql = "select * from Department where id = ?";
        return genericDAO.executeQuery(sql, new DepartmentMapper(), id);
    }

    @Override
    public List<DepartmentModel> findByName(String name) {
        String sql = "select * from Department where name like ?";
        return genericDAO.executeQuery(sql, new DepartmentMapper(), "%" + name + "%");
    }

    @Override
    public int create(Connection connection, DepartmentModel department) throws SQLException {
        String sql = "insert into Department values (?, ?)";
        return genericDAO.executeScalar(connection, sql, department.getId(), department.getName());
    }

    @Override
    public void update(Connection connection, DepartmentModel department) throws SQLException {
        String sql = "update Department set name = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, department.getName(), department.getId());
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        String sql = "delete from Department where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

}
