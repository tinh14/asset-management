/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.DepreciationModel;

/**
 *
 * @author tinhlam
 */
public interface DepreciationDAO {

    public List<DepreciationModel> findAll();

    public List<DepreciationModel> findById(int id);

    public List<DepreciationModel> findByAccountingPeriodName(String name);

    public int create(Connection connection, DepreciationModel model) throws SQLException;
}
