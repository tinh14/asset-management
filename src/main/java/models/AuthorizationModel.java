/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author tinhlam
 */
public class AuthorizationModel {

    private ActionModel action;
    private AccountModel account;

    public AuthorizationModel() {
    }

    public AuthorizationModel(@JsonProperty("username") String username,
            @JsonProperty("actionId") String actionId,
            @JsonProperty("resourceId") String resourceId) {
        this.action = new ActionModel(actionId);
        this.action.setResource(new ResourceModel(resourceId));
        this.account = new AccountModel(username);
    }

    /**
     * @return the action
     */
    public ActionModel getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(ActionModel action) {
        this.action = action;
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
