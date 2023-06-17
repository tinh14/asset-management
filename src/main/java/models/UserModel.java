/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;

/**
 *
 * @author tinhlam
 */
public class UserModel extends PersonModel {

    public UserModel() {
    }
    
    public UserModel(int id){
        super(id);
    }
    
    public UserModel(@JsonProperty("id") int id, 
            @JsonProperty("lastName") String lastName, 
            @JsonProperty("firstName") String firstName, 
            @JsonProperty("dateOfBirth") Date dateOfBirth, 
            @JsonProperty("address") String address, 
            @JsonProperty("avatar") String avatar, 
            @JsonProperty("departmentId") int departmentId,
            @JsonProperty("accountUsername") String username) {
        super(id, lastName, firstName, dateOfBirth, address, avatar, new DepartmentModel(departmentId), new AccountModel(username));
    }
  
}
