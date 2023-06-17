/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.InvoiceModel;
import models.SupplierModel;

/**
 *
 * @author tinhlam
 */
public class InvoiceMapper implements RowMapper<InvoiceModel> {

    @Override
    public InvoiceModel mapRow(ResultSet res) {
        InvoiceModel invoice = null;
        try {
            invoice = new InvoiceModel();
            invoice.setId(res.getString("id"));
            invoice.setNumber(res.getInt("number"));
            invoice.setInvoiceDate(res.getDate("invoiceDate"));
            invoice.setSupplier(new SupplierModel(res.getInt("supplierId")));
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoice;
    }

}
