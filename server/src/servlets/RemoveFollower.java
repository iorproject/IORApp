package servlets;

import engine.IorEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveFollowerServlet", urlPatterns = {"/removeFollower/remove"})

public class RemoveFollower extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String requesterEmail = request.getParameter("requesterEmail");
        String recieverEmail = request.getParameter("recieverEmail");
        try {
           IorEngine.removeUserFriendship(requesterEmail,recieverEmail);
           //remove signed User from friend's access Permission
        } catch (Throwable throwable) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
