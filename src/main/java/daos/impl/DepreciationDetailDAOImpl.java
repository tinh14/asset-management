/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.DepreciationDetailDAO;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.DepreciationDetailMapper;
import models.AccountingPeriodModel;
import models.DepreciationDetailModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationDetailDAOImpl implements DepreciationDetailDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<DepreciationDetailModel> findByDepreciationId(int id) {
        String sql = "select * from DepreciationDetail \n"
                + "inner join asset on DepreciationDetail.assetId = asset.id\n"
                + "where depreciationId = ?";
        return genericDAO.executeQuery(sql, new DepreciationDetailMapper(), id);
    }

    @Override
    public List<DepreciationDetailModel> findByAccountingPeriod(AccountingPeriodModel model) {
//        String sql = "SELECT assetId, "
//                + "sum((itd.quantity * itd.price) + ((itd.quantity * itd.price) * vat / 100)) as totalValue, "
//                + "(sum((itd.quantity * itd.price) + (itd.quantity * itd.price * vat / 100)) / assettype.depreciationPeriod) / (12 / ?) as periodDepreciationExpense\n"
//                + "FROM inventoryTransaction it \n"
//                + "INNER JOIN inventoryTransactionDetail itd \n"
//                + "ON it.id = itd.inventoryTransactionId\n"
//                + "INNER JOIN asset\n"
//                + "ON itd.assetId = asset.id\n"
//                + "INNER JOIN assetType\n"
//                + "ON asset.assetTypeId = assetType.id\n"
//                + "WHERE receiptDate <= ? \n"
//                + "GROUP BY assetId";
        String sql = "SELECT\n"
                + "  assetId,\n"
                + "  image,\n"
                + "  name,\n"
                + "  depreciationPeriod,\n"
                + "  percentageDepreciation,\n"
                + "  totalValue,\n"
                + "  periodDepreciationExpense,\n"
                + "  accumulatedDepreciation,\n"
                + "  (totalValue - accumulatedDepreciation) AS remainingValue\n"
                + "FROM (\n"
                + "  SELECT\n"
                + "    assetId,\n"
                + "    image,\n"
                + "    name,\n"
                + "    depreciationPeriod,\n"
                + "    percentageDepreciation,\n"
                + "    totalValue,\n"
                + "    periodDepreciationExpense,\n"
                + "    COALESCE(\n"
                + "      (\n"
                + "        SELECT SUM(accumulatedDepreciation)\n"
                + "        FROM depreciationdetail\n"
                + "        WHERE assetId = subquery.assetId\n"
                + "          AND depreciationId = (\n"
                + "            SELECT Depreciation.id\n"
                + "            FROM Depreciation\n"
                + "            INNER JOIN accountingperiod ON Depreciation.accountingPeriodId = accountingperiod.id\n"
                + "            INNER JOIN accountingperiodtype ON accountingperiod.accountingPeriodTypeId = accountingperiodtype.id\n"
                + "            WHERE accountingperiodtype.id = ? AND depreciationDate < NOW()\n"
                + "            ORDER BY depreciationDate DESC\n"
                + "            LIMIT 1\n"
                + "          )\n"
                + "      ), 0\n"
                + "    ) + periodDepreciationExpense AS accumulatedDepreciation\n"
                + "  FROM (\n"
                + "    SELECT\n"
                + "      assetId,\n"
                + "      SUM(itd.quantity * itd.price + itd.quantity * itd.price * itd.vat / 100) AS totalValue,\n"
                + "      (SUM((itd.quantity * itd.price) + (itd.quantity * itd.price * vat / 100)) / assettype.depreciationPeriod) / (12 / ?) AS periodDepreciationExpense,\n"
                + "      assetType.depreciationPeriod AS depreciationPeriod,\n"
                + "      asset.image as image,\n"
                + "      asset.name as name,\n"
                + "      asset.percentageDepreciation as percentageDepreciation\n"
                + "    FROM\n"
                + "      inventoryTransaction it\n"
                + "      INNER JOIN inventoryTransactionDetail itd ON it.id = itd.inventoryTransactionId\n"
                + "      INNER JOIN asset ON itd.assetId = asset.id\n"
                + "      INNER JOIN assetType ON asset.assetTypeId = assetType.id\n"
                + "    WHERE\n"
                + "      receiptDate <= ?\n"
                + "    GROUP BY\n"
                + "      assetId\n"
                + "  ) AS subquery\n"
                + ") AS subquery2\n"
                + "where (totalValue - accumulatedDepreciation) >= (totalValue * percentageDepreciation / 100)";
        return genericDAO.executeQuery(sql, new DepreciationDetailMapper(), model.getAccountingPeriodType().getId(), model.getAccountingPeriodType().getNumberOfMonths(), model.getEndDate());
    }

//    @Override
//    public List<DepreciationDetailModel> findByDepreciationIdAndAssetId(int depreciationId, int assetId) {
//        String sql = "select accumulatedDepreciation from depreciationdetail\n"
//                + "where depreciationId = ? and assetId = ?";
//        return genericDAO.executeQuery(sql, new DepreciationDetailMapper(), depreciationId, assetId);
//    }
    @Override
    public void create(Connection connection, DepreciationDetailModel model) throws SQLException {
        String sql = "insert into DepreciationDetail values (?, ?, ?, ?, ?, ?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, model.getDepreciation().getId(), model.getAsset().getId(),
                model.getDepreciationPeriod(), model.getPercentageDepreciation(), model.getTotalValue(),
                model.getPeriodDepreciationExpense(), model.getAccumulatedDepreciation(), model.getRemainingValue());
    }
}
