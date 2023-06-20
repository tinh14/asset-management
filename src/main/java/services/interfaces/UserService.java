/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public interface UserService {

    public List<UserModel> findAll();

    public List<UserModel> findById(int id);

    public List<UserModel> findByName(String name);

    public List<UserModel> findByAccountUsername(String username);

    public ResponseMessage create(UserModel user);

    public ResponseMessage update(UserModel user);
}
