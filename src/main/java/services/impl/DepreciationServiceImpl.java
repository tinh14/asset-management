/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import constants.Constants;
import daos.interfaces.AccountingPeriodDAO;
import daos.interfaces.AssetDAO;
import daos.interfaces.DepreciationDAO;
import daos.interfaces.DepreciationDetailDAO;
import daos.interfaces.DepreciationMethodDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.DepreciationDetailModel;
import models.DepreciationModel;
import services.interfaces.DepreciationService;
import services.interfaces.UserService;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public class DepreciationServiceImpl implements DepreciationService{

    @Inject
    private DepreciationDAO depreciationDAO;
    
    @Inject
    private DepreciationDetailDAO depreciationDetailDAO;
    
    @Inject
    private AccountingPeriodDAO accoutingPeriodDAO;
    
    @Inject
    private DepreciationMethodDAO depreciationMethodDAO;
    
    @Inject
    private AssetDAO assetService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private TransactionManager transactionManager;
    
    @Override
    public List<DepreciationModel> findAll() {
        List<DepreciationModel> list = depreciationDAO.findAll();
        for (DepreciationModel model : list){
            model.setAccountingPeriod(accoutingPeriodDAO.findById(model.getAccountingPeriod().getId()).get(0));
            model.setCreator(userService.findById(model.getCreator().getId()).get(0));
            model.setDepreciationMethod(depreciationMethodDAO.findById(model.getDepreciationMethod().getId()).get(0));
        }
        return list;
    }

    @Override
    public List<DepreciationModel> findById(int id) {
        List<DepreciationModel> list = depreciationDAO.findAll();
        for (DepreciationModel model : list){
            model.setAccountingPeriod(accoutingPeriodDAO.findById(model.getAccountingPeriod().getId()).get(0));
            model.setCreator(userService.findById(model.getCreator().getId()).get(0));
            model.setDepreciationMethod(depreciationMethodDAO.findById(model.getDepreciationMethod().getId()).get(0));
            
            List<DepreciationDetailModel> depreciationDetailList = depreciationDetailDAO.findByDepreciationId(id);
            model.setDepreciationDetailList(depreciationDetailList);
            
            for (DepreciationDetailModel depreciationDetailModel : depreciationDetailList){
                depreciationDetailModel.setAsset(assetService.findById(depreciationDetailModel.getAsset().getId()).get(0));
            }
        }
        return list;
    }
    
    private ResponseMessage checkDepreciationMethod(String id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (depreciationMethodDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Phương pháp khấu hao không tồn tại");
        }
        return response;
    }
    
    private ResponseMessage checkAccountingPeriod(int id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (accoutingPeriodDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Kỳ kế toán không tồn tại");
        }
        return response;
    }

    @Override
    public ResponseMessage create(DepreciationModel depreciation) {
        ResponseMessage response1 = checkDepreciationMethod(depreciation.getDepreciationMethod().getId());
        ResponseMessage response2 = checkAccountingPeriod(depreciation.getAccountingPeriod().getId());

        if (response1.isError()) {
            if (response2.isError()){
                response1.setMessage(response2.getMessage());
            }
            return response1;
        }
        
        if (response2.isError()){
            return response2;
        }
        
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = depreciationDAO.create(transactionManager.getConnection(), depreciation);
            depreciation.setId(id);
            for (DepreciationDetailModel detail : depreciation.getDepreciationDetailList()) {
                detail.getDepreciation().setId(id);
                depreciationDetailDAO.create(transactionManager.getConnection(), detail);
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public List<DepreciationModel> findByAccountingPeriodName(String name) {
        List<DepreciationModel> list = depreciationDAO.findByAccountingPeriodName(name);
        for (DepreciationModel model : list){
            model.setAccountingPeriod(accoutingPeriodDAO.findById(model.getAccountingPeriod().getId()).get(0));
            model.setCreator(userService.findById(model.getCreator().getId()).get(0));
            model.setDepreciationMethod(depreciationMethodDAO.findById(model.getDepreciationMethod().getId()).get(0));
        }
        return list;
    }
    
}
