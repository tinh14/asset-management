/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AccountDAO;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.AccountMapper;
import models.AccountModel;

/**
 *
 * @author tinhlam
 */
public class AccountDAOImpl implements AccountDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AccountModel> findAll() {
        String sql = "select * from Account";
        return genericDAO.executeQuery(sql, new AccountMapper());
    }

    @Override
    public List<AccountModel> findByUsername(String username) {
        String sql = "select * from Account where username = ?";
        return genericDAO.executeQuery(sql, new AccountMapper(), username);
    }

    @Override
    public List<AccountModel> findByUsernameWithWildcard(String username) {
        String sql = "select * from Account where username like ?";
        return genericDAO.executeQuery(sql, new AccountMapper(), "%" + username + "%");
    }

    @Override
    public List<AccountModel> findByUsernameAndPassword(String username, String password) {
        String sql = "select * from Account where username = ? and password = ?";
        return genericDAO.executeQuery(sql, new AccountMapper(), username, password);
    }

    @Override
    public void create(Connection connection, AccountModel account) throws SQLException {
        String sql = "insert into Account values (?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, account.getUsername(), account.getPassword(), account.getStatus());
    }

    @Override
    public void update(Connection connection, AccountModel account) throws SQLException {
        String sql = "update Account set password = ?, status = ? where username = ?";
        genericDAO.executeUpdate(connection, sql, account.getPassword(), account.getStatus(), account.getUsername());
    }

}
