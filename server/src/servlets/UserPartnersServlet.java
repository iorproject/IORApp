package servlets;


import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.Entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserPartnersServlet", urlPatterns = {"/userPartners"})

public class UserPartnersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String email = request.getParameter("email");
        Map<String, List<User>> result = new HashMap<>();


        try {
            result.put("partners",IorEngine.getUserPartners(email));
           result.put("followers", IorEngine.getFollowers(email));
            result.put("requestusers", IorEngine.getMemberShipRequestUsers(email));
            String resp = new Gson().toJson(result);
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
