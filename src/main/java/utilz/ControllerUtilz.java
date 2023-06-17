/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import constants.Constants;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tinhlam
 */
public class ControllerUtilz {

    public static void sendMessage(ResponseMessage responseMessage, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setStatus(responseMessage.getStatus());
        servletResponse.setContentType(Constants.TEXT_TYPE);
        servletResponse.getWriter().print(responseMessage.getMessage());
        servletResponse.getWriter().close();
    }
}
