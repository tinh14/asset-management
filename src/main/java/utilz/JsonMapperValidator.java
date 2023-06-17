/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tinhlam
 */
public class JsonMapperValidator {

    private static int MIN = 1;
    private static int MAX = 2147483647;

    private static String checkFieldType(String type, String value, String name) {
        String str = "";
        switch (type) {
            case "int":
                try {
                int val = Integer.parseInt(value);
                if (!(val >= MIN && val <= MAX)) {
                    str = " phải lớn 0 và nhỏ hơn 2147483648";
                }
            } catch (NumberFormatException e) {
                str = " phải lớn 0 và nhỏ hơn 2147483648";
            }
            break;
            case "String":
                if (value.isBlank()) {
                    str = " không được trống";
                } else if (value.length() > 256) {
                    str = " không được quá 256 ký tự";
                }
                break;
            case "Date":
                try {
                LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                str = " không hợp lệ";
            }
            break;
        }
        if (str.equals("")) {
            name = "";
        } else {
            str += "\n";
        }
        return name + str;
    }

    private static String checkFieldTypeInt(String value, String name) {
        String str = "";
        try {
            int val = Integer.parseInt(value);
            if (!(val >= MIN - 1 && val <= MAX)) {
                str = " phải lớn -1 và nhỏ hơn 2147483648";
            }
        } catch (NumberFormatException e) {
            str = " phải lớn -1 và nhỏ hơn 2147483648";
        }
        if (str.equals("")) {
            name = "";
        } else {
            str += "\n";
        }
        return name + str;
    }

    private static void setValidDate(String errorString, JsonNode node, String field) {
        if (errorString.equals("")) {
            String[] dateStrArr = node.get(field).asText().split("/");
            String newDateStr = dateStrArr[2] + "-" + dateStrArr[1] + "-" + dateStrArr[0];
            ((ObjectNode) node).put(field, newDateStr);
        }
    }

    private static int getStatusFromMessage(String message) {
        return message.equals("") ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST;
    }

    public static ResponseMessage assetValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("name").asText(), "Tên tài sản");
        message += checkFieldType("int", node.get("weight").asText(), "Khối lượng");
        message += checkFieldType("int", node.get("price").asText(), "Giá nhập");
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage assetTypeValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("name").asText(), "Tên loại tài sản");
        message += checkFieldType("int", node.get("depreciationPeriod").asText(), "Thời gian trích khấu hao");
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage invoiceValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("id").asText(), "Mã hóa đơn");
        message += checkFieldType("int", node.get("number").asText(), "Số hóa đơn");
        String temp = checkFieldType("Date", node.get("invoiceDate").asText(), "Ngày lập hóa đơn");
        message += temp;
        setValidDate(temp, node, "invoiceDate");
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage inventoryTransactionValidator(JsonNode node) {
        String message = "";
        String temp = checkFieldType("Date", node.get("receiptDate").asText(), "Ngày nhập kho");
        message += temp;
        setValidDate(temp, node, "receiptDate");

        if (node.get("inventoryTransactionDetailList").size() == 0) {
            message += "Danh sách tài sản không được trống";
        } else {
            for (JsonNode itd : node.get("inventoryTransactionDetailList")) {
                message += checkFieldType("int", itd.get("quantity").asText(), "Số lượng tài sản có mã số " + itd.get("assetId"));
                message += checkFieldTypeInt(itd.get("price").asText(), "Đơn giá tài sản có mã số " + itd.get("assetId"));
                message += checkFieldTypeInt(itd.get("VAT").asText(), "VAT tài sản có mã số " + itd.get("assetId"));
            }
        }

        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage assetTransferValidator(JsonNode node) {
        String message = "";
        String temp = checkFieldType("Date", node.get("transferDate").asText(), "Ngày chuyển tài sản");
        message += temp;
        setValidDate(temp, node, "transferDate");

        if (node.get("assetTransferDetailList").size() == 0) {
            message += "Danh sách tài sản không được trống";
        } else {
            for (JsonNode itd : node.get("assetTransferDetailList")) {
                message += checkFieldType("int", itd.get("quantity").asText(), "Số lượng tài sản có mã số " + itd.get("assetId"));
            }
        }
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage assetDisposeValidator(JsonNode node) {
        String message = "";
        String temp = checkFieldType("Date", node.get("disposalDate").asText(), "Ngày hủy");
        message += temp;
        setValidDate(temp, node, "disposalDate");

        if (node.get("assetDisposeDetailList").size() == 0) {
            message += "Danh sách tài sản không được trống";
        } else {
            for (JsonNode itd : node.get("assetDisposeDetailList")) {
                message += checkFieldType("int", itd.get("quantity").asText(), "Số lượng tài sản có mã số " + itd.get("assetId"));
            }
        }
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage departmentValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("name").asText(), "Tên phòng ban");
        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage supplierValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("name").asText(), "Tên nhà cung cấp");
        message += checkFieldType("String", node.get("phoneNumber").asText(), "Số điện thoại");

        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage userValidator(JsonNode node) {
        String message = "";
        String temp = checkFieldType("Date", node.get("dateOfBirth").asText(), "Ngày sinh");
        message += temp;
        setValidDate(temp, node, "dateOfBirth");

        message += checkFieldType("String", node.get("lastName").asText(), "Họ");
        message += checkFieldType("String", node.get("firstName").asText(), "Tên");

        return new ResponseMessage(getStatusFromMessage(message), message);
    }

    public static ResponseMessage accountValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("username").asText(), "Tên tài khoản");
        message += checkFieldType("String", node.get("password").asText(), "Mật khẩu");
        return new ResponseMessage(getStatusFromMessage(message), message);
    }
    
    public static ResponseMessage accountingPeriodValidator(JsonNode node) {
        String message = "";
        message += checkFieldType("String", node.get("name").asText(), "Kỳ kế toán");
        
        String temp = checkFieldType("Date", node.get("startDate").asText(), "Ngày bắt đầu");
        message += temp;
        setValidDate(temp, node, "startDate");
        
        temp = checkFieldType("Date", node.get("endDate").asText(), "Ngày kết thúc");
        message += temp;
        setValidDate(temp, node, "endDate");
        
        return new ResponseMessage(getStatusFromMessage(message), message);
    }
    
    public static ResponseMessage depreciationValidator(JsonNode node) {
        String message = "";
        String temp = checkFieldType("Date", node.get("depreciationDate").asText(), "Ngày lập");
        message += temp;
        setValidDate(temp, node, "depreciationDate");

        if (node.get("depreciationDetailList").size() == 0) {
            message += "Danh sách tài sản không được trống";
        }

        return new ResponseMessage(getStatusFromMessage(message), message);
    }

}
