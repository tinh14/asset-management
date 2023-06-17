/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.InvoiceDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.InvoiceMapper;
import models.InvoiceModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class InvoiceDAOImpl implements InvoiceDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<InvoiceModel> findAll() {
        String sql = "select * from Invoice";
        return genericDAO.executeQuery(sql, new InvoiceMapper());
    }

    @Override
    public List<InvoiceModel> findById(String id) {
        String sql = "select * from Invoice where id = ?";
        return genericDAO.executeQuery(sql, new InvoiceMapper(), id);
    }

    @Override
    public List<InvoiceModel> findByIdWithWildcard(String id) {
        String sql = "select * from Invoice where id like ?";
        return genericDAO.executeQuery(sql, new InvoiceMapper(), "%"+id+"%");
    }

    @Override
    public void deleteByInvoiceNumber(Connection connection, int invoiceNumber) throws SQLException {
        String sql = "delete from Invoice where id = ?";
        genericDAO.executeUpdate(connection, sql, invoiceNumber);
    }

    @Override
    public void create(Connection connection, InvoiceModel invoice) throws SQLException {
        String sql = "insert into Invoice values(?, ?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, invoice.getId(), invoice.getNumber(), invoice.getInvoiceDate(), invoice.getSupplier().getId());
    }

    @Override
    public void update(Connection connection, InvoiceModel invoice) throws SQLException {
        String sql = "update Invoice set number = ?, invoiceDate = ?, supplierId = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, invoice.getNumber(), invoice.getInvoiceDate(), invoice.getSupplier().getId(), invoice.getId());
    }

}
