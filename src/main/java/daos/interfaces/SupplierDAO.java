/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.SupplierModel;

/**
 *
 * @author tinhlam
 */
public interface SupplierDAO {

    public List<SupplierModel> findAll();

    public List<SupplierModel> findById(int id);

    public List<SupplierModel> findByName(String name);

    public int create(Connection connection, SupplierModel supplier) throws SQLException;

    public void update(Connection connection, SupplierModel supplier) throws SQLException;

    public void delete(Connection connection, int id) throws SQLException;

}
