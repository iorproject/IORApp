package servlets;

import engine.IorEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RejectFriendshipRequestsServlet", urlPatterns = {"/rejectFriendshipRequest/reject"})

public class RejectFriendshipRequests extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String requesterEmail = request.getParameter("useremail");
        String receiverEmail = request.getParameter("friendemail");
        try {
            IorEngine.rejectUserShareRequest(receiverEmail, requesterEmail);
        } catch (Throwable throwable) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }
}
