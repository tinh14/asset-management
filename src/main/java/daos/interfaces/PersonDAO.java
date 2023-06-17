/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.PersonModel;

/**
 *
 * @author tinhlam
 */
public interface PersonDAO {

    public List<PersonModel> findById(int id);

    public List<PersonModel> findByName(String name);
    
    public List<PersonModel> findByAccountUsername(String username);
    
    public int create(Connection connection, PersonModel person) throws SQLException;

    public void update(Connection connection, PersonModel person) throws SQLException;

}
