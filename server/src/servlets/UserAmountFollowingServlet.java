package servlets;

import com.google.gson.Gson;
import engine.IorEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserAmountFollowingServlet", urlPatterns = {"/userAmountFollowing"})

public class UserAmountFollowingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String email = request.getParameter("email");

        try {

            int partners = IorEngine.getAmountFollowingMyReceipts(email);
            String resp = new Gson().toJson(partners);
            try (PrintWriter out = response.getWriter()) {
                out.println(resp);
                out.flush();
            }
        }
        catch (Throwable t) {
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }


}
