/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.PersonDAO;
import javax.enterprise.inject.Default;
import java.util.List;
import javax.inject.Inject;
import mappers.PersonMapper;
import models.PersonModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
@Default
public class PersonDAOImpl implements PersonDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<PersonModel> findById(int id) {
        String sql = "select * from Person where id = ?";
        return genericDAO.executeQuery(sql, new PersonMapper(), id);
    }

    @Override
    public List<PersonModel> findByName(String name) {
        String sql = "select * from Person where lastName like ? or firstName like ?";
        return genericDAO.executeQuery(sql, new PersonMapper(), "%" + name + "%", "%" + name + "%");
    }
    
    @Override
    public List<PersonModel> findByAccountUsername(String username) {
        String sql = "select * from Person where accountUsername = ?";
        return genericDAO.executeQuery(sql, new PersonMapper(), username);
    }

    @Override
    public int create(Connection connection, PersonModel person) throws SQLException {
        String sql = "insert into Person values (?, ?, ?, ?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, person.getId(),
                person.getLastName(), person.getFirstName(), person.getDateOfBirth(), person.getAddress(),
                person.getAvatar(), person.getDepartment().getId(), person.getAccount().getUsername());
    }

    @Override
    public void update(Connection connection, PersonModel person) throws SQLException {
        String sql = "update Person set lastName = ?, firstName = ?, dateOfBirth = ?, address = ?, avatar = ?, departmentId = ?, accountUsername = ? where id = ?";
        genericDAO.executeUpdate(connection, sql,
                person.getLastName(), person.getFirstName(), person.getDateOfBirth(), person.getAddress(),
                person.getAvatar(), person.getDepartment().getId(), person.getAccount().getUsername(), person.getId());
    }

    

}
