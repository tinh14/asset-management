/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.DepreciationDAO;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.DepreciationMapper;
import models.DepreciationModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationDAOImpl implements DepreciationDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<DepreciationModel> findAll() {
        String sql = "select * from Depreciation";
        return genericDAO.executeQuery(sql, new DepreciationMapper());
    }

    @Override
    public List<DepreciationModel> findById(int id) {
        String sql = "select * from Depreciation where id = ?";
        return genericDAO.executeQuery(sql, new DepreciationMapper(), id);
    }

//    @Override
//    public List<DepreciationModel> findNearestDepreciationByAccountingPeriodTypeId(String id) {
//        String sql = "select depreciation.id FROM Depreciation\n"
//                + "INNER JOIN accountingperiod\n"
//                + "ON Depreciation.accountingPeriodId = accountingperiod.id\n"
//                + "INNER JOIN accountingperiodtype\n"
//                + "ON accountingperiod.accountingPeriodTypeId = accountingperiodtype.id\n"
//                + "where accountingperiodtype.id = ? and depreciationDate < NOW()\n"
//                + "ORDER BY depreciationDate DESC\n"
//                + "LIMIT 1";
//        return genericDAO.executeQuery(sql, new DepreciationMapper(), id);
//    }
    @Override
    public int create(Connection connection, DepreciationModel model) throws SQLException {
        String sql = "insert into Depreciation values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, model.getDepreciationDate(),
                model.getTotalValue(), model.getPeriodDepreciationExpense(),
                model.getAccumulatedDepreciation(), model.getRemainingValue(),
                model.getDepreciationMethod().getId(), model.getCreator().getId(),
                model.getAccountingPeriod().getId());
    }

    @Override
    public List<DepreciationModel> findByAccountingPeriodName(String name) {
        String sql = "select d.id as id, depreciationDate, totalValue, periodDepreciationExpense, accumulatedDepreciation, remainingValue, depreciationMethodId, creatorId, accountingPeriodId\n"
                + "from Depreciation as d\n"
                + "inner join AccountingPeriod as a on d.accountingPeriodId = a.id\n"
                + "where a.name like ?";
        return genericDAO.executeQuery(sql, new DepreciationMapper(), "%" + name + "%");
    }

}
