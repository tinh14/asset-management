/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import models.InvoiceModel;

/**
 *
 * @author tinhlam
 */
public interface InvoiceDAO {

    public List<InvoiceModel> findAll();

    public List<InvoiceModel> findById(String id);
    
    public List<InvoiceModel> findByIdWithWildcard(String id);
    
    public void create(Connection connection, InvoiceModel invoice)throws SQLException;

    public void update(Connection connection, InvoiceModel invoice)throws SQLException;

    public void deleteByInvoiceNumber(Connection connection, int invoiceNumber)throws SQLException;

}
