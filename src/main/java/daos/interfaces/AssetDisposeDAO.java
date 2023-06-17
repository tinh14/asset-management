/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetDisposeModel;

/**
 *
 * @author tinhlam
 */
public interface AssetDisposeDAO {

    public List<AssetDisposeModel> findAll();

    public List<AssetDisposeModel> findById(int id);

    public List<AssetDisposeModel> findByDisposalDepartmentId(int id);
    
    public int create(Connection connection, AssetDisposeModel assetDispose) throws SQLException;

    public void update(Connection connection, AssetDisposeModel assetDispose) throws SQLException;

    public void deleteById(Connection connection, int id) throws SQLException;
}
