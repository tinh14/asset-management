/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AccountModel;
import models.ActionModel;
import models.AuthorizationModel;
import models.ResourceModel;

/**
 *
 * @author tinhlam
 */
public class AuthorizationMapper implements RowMapper<AuthorizationModel> {

    @Override
    public AuthorizationModel mapRow(ResultSet res) {
        AuthorizationModel authorization = null;
        try {
            authorization = new AuthorizationModel();
            authorization.setAccount(new AccountModel(res.getString("accountUsername")));
            authorization.setAction(new ActionModel(res.getString("actionId")));
            authorization.getAction().setResource(new ResourceModel(res.getString("resourceId")));
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizationMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authorization;
    }

}
