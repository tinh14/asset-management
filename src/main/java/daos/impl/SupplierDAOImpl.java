/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.SupplierDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.SupplierMapper;
import models.SupplierModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class SupplierDAOImpl implements SupplierDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<SupplierModel> findAll() {
        String sql = "select * from Supplier";
        return genericDAO.executeQuery(sql, new SupplierMapper());
    }

    @Override
    public List<SupplierModel> findById(int id) {
        String sql = "select * from Supplier where id = ?";
        return genericDAO.executeQuery(sql, new SupplierMapper(), id);
    }

    @Override
    public List<SupplierModel> findByName(String name) {
        String sql = "select * from Supplier where name like ?";
        return genericDAO.executeQuery(sql, new SupplierMapper(), "%" + name + "%");
    }

    @Override
    public int create(Connection connection, SupplierModel supplier) throws SQLException {
        String sql = "insert into Supplier values(?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber());
    }

    @Override
    public void update(Connection connection, SupplierModel supplier) throws SQLException {
        String sql = "update Supplier set name = ?, address = ?, phoneNumber = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber(), supplier.getId());
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        String sql = "delete from Supplier where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

}
