/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.InvoiceModel;

/**
 *
 * @author tinhlam
 */
public interface InvoiceService {
    public List<InvoiceModel> findAll();
    public List<InvoiceModel> findById(String id);
    public List<InvoiceModel> findByIdWithWildcard(String id);
    public ResponseMessage create(InvoiceModel invoice);
    public ResponseMessage update(InvoiceModel invoice);
}
