/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.AuthorizationModel;

/**
 *
 * @author tinhlam
 */
public interface AuthorizationService {
    public boolean checkPermission(String accountUsername, String resourceCode, String actionCode);
    public List<AuthorizationModel> getPermission(String accountUsername);
    public ResponseMessage update(List<AuthorizationModel> authorizations);
}
