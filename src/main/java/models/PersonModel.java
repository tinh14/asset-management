/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;

/**
 *
 * @author tinhlam
 */
public class PersonModel {

    private int id;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String address;
    private String avatar;
    private DepartmentModel department;
    private AccountModel account;
    
    public PersonModel() {
    }

    public PersonModel(int id) {
        this.id = id;
    }

    public PersonModel(int id, String lastName, String firstName, Date dateOfBirth, String address, String avatar, DepartmentModel department, AccountModel account) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.avatar = avatar;
        this.department = department;
        this.account = account;
    }

    public void setPerson(PersonModel person) {
        this.setId(person.getId());
        this.setLastName(person.getLastName());
        this.setFirstName(person.getFirstName());
        this.setDateOfBirth(person.getDateOfBirth());
        this.setAddress(person.getAddress());
        this.setAvatar(person.getAvatar());
        this.setDepartment(person.getDepartment());
        this.setAccount(person.getAccount());
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFullName(){
        return lastName + " " + firstName;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the department
     */
    public DepartmentModel getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    /**
     * @return the account
     */
    public AccountModel getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(AccountModel account) {
        this.account = account;
    }

}
