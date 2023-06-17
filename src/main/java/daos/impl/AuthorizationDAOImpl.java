/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AuthorizationDAO;
import javax.enterprise.inject.Default;
import java.util.List;
import javax.inject.Inject;
import mappers.AuthorizationMapper;
import models.AuthorizationModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
@Default
public class AuthorizationDAOImpl implements AuthorizationDAO {

    @Inject
    GenericDAO genericDAO;

    @Override
    public boolean checkPermission(String accountUsername, String resourceId, String actionId) {
        String sql = "select * from Authorization where accountUsername = ? and resourceId = ? and actionId = ?";
        return genericDAO.executeQuery(sql, accountUsername, resourceId, actionId);
    }

    @Override
    public void grantPermission(Connection connection, AuthorizationModel authorization) throws SQLException{
        String sql = "insert into Authorization values (?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, authorization.getAccount().getUsername(), authorization.getAction().getId(), authorization.getAction().getResource().getId());
    }

    @Override
    public void revokePermission(Connection connection, AuthorizationModel authorization) throws SQLException{
        String sql = "delete from Authorization where accountUsername = ? and resourceId = ? and actionId = ?";
        genericDAO.executeUpdate(connection, sql, authorization.getAccount().getUsername(), authorization.getAction().getId(), authorization.getAction().getResource().getId());
    }

    @Override
    public List<AuthorizationModel> getPermission(String accountUsername) {
        String sql = "select * from Authorization where accountUsername = ?";
        return genericDAO.executeQuery(sql, new AuthorizationMapper(), accountUsername);
    }

    @Override
    public void revokeAllPermissionByUsername(Connection connection, String accountUsername) throws SQLException{
        String sql = "delete from Authorization where accountUsername = ?";
        genericDAO.executeUpdate(connection, sql, accountUsername);
    }

}
