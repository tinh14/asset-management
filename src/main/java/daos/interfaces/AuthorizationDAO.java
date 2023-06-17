/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AuthorizationModel;

/**
 *
 * @author tinhlam
 */
public interface AuthorizationDAO {

    public List<AuthorizationModel> getPermission(String accountUsername);

    public boolean checkPermission(String accountUsername, String resourceId, String actionId);

    public void grantPermission(Connection connection, AuthorizationModel authorization) throws SQLException;

    public void revokePermission(Connection connection, AuthorizationModel authorization) throws SQLException;

    public void revokeAllPermissionByUsername(Connection connection, String accountUsername) throws SQLException;
}
