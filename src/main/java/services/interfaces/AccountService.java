/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import javax.enterprise.inject.Default;
import models.AccountModel;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
@Default
public interface AccountService {
    public List<AccountModel> findAll();
    public List<AccountModel> findByUsername(String username);
    public List<AccountModel> findByUsernameWithWildcard(String username);
    public List<AccountModel> findByUsernameAndPassword(String username, String password);
    public ResponseMessage create(AccountModel account);
    public ResponseMessage update(AccountModel account);
}
