package servlets;

import engine.IorEngine;
import main.java.DB.Entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserShareRequestSendServlet", urlPatterns = {"/userShareRequests/send"})

public class SendUserShareRequests extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String requesterEmail = request.getParameter("requesterEmail");
        String receiverEmail = request.getParameter("receiverEmail");
        String msg;
        try {
            User temp = IorEngine.getUserInfo(receiverEmail);
            if (IorEngine.getUserInfo(receiverEmail) == null)
            {
                response.setStatus(501);
            }
            else
            {
                IorEngine.sendUserShareRequest(receiverEmail, requesterEmail);
                response.setStatus(502);
            }

        } catch (Throwable throwable) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}