/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.LogModel;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public interface LogService {
    public List<LogModel> findAll();
    public List<LogModel> findByLogId(int id);
    public List<LogModel> findByRecordId(String id);
    public ResponseMessage create(HttpServletRequest req, Object oldObject, Object newObject);
}
