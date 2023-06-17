/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.SupplierModel;

/**
 *
 * @author tinhlam
 */
public class SupplierMapper implements RowMapper<SupplierModel> {
    @Override
    public SupplierModel mapRow(ResultSet res) {
        SupplierModel supplier = null;
        try {
            supplier = new SupplierModel();
            supplier.setId(res.getInt("id"));
            supplier.setName(res.getString("name"));
            supplier.setAddress(res.getString("address"));
            supplier.setPhoneNumber(res.getString("phoneNumber"));
        } catch (SQLException ex) {
            Logger.getLogger(SupplierMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return supplier;
    }

}
