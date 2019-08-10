package servlets;

import engine.IorEngine;

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
        try {
            IorEngine.sendUserShareRequest(receiverEmail, requesterEmail);
        } catch (Throwable throwable) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
