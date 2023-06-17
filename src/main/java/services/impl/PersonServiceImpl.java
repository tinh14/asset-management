/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.PersonDAO;
import java.util.List;
import javax.inject.Inject;
import models.PersonModel;
import services.interfaces.PersonService;

/**
 *
 * @author tinhlam
 */
public class PersonServiceImpl implements PersonService{

    @Inject
    private PersonDAO personDAO;
    
    @Override
    public List<PersonModel> findByAccountUsername(String username) {
        return personDAO.findByAccountUsername(username);
    }
    
}
