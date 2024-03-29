package servlets;

import engine.IorEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserShareRequestAcceptServlet", urlPatterns = {"/userShareRequests/accept"})

public class AcceptUserShareRequests extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");

        String requesterEmail = request.getParameter("requesterEmail");
        String receiverEmail = request.getParameter("receiverEmail");
        try {
            IorEngine.acceptUserShareRequest(receiverEmail, requesterEmail);
        } catch (Throwable throwable) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
