/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.LogAssetDetailModel;
import models.LogDetailModel;

/**
 *
 * @author tinhlam
 */
public interface LogAssetDetailDAO {
    public List<LogAssetDetailModel> findByLogId(int id);
    public void create(Connection connection, LogAssetDetailModel logAssetDetail) throws SQLException;
}
