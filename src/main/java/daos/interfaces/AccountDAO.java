/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AccountModel;

/**
 *
 * @author tinhlam
 */
public interface AccountDAO {

    public List<AccountModel> findAll();

    public List<AccountModel> findByUsername(String username);

    public List<AccountModel> findByUsernameWithWildcard(String username);

    public List<AccountModel> findByUsernameAndPassword(String username, String password);

    public void create(Connection connection, AccountModel account) throws SQLException;
    
    public void update(Connection connection,AccountModel account) throws SQLException;
    
}
