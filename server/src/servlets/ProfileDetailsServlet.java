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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ProfileInfoServlet", urlPatterns = {"/profileInfo"})

public class ProfileDetailsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String userEmail = request.getParameter("email");
        Map<String, Integer> profileInfo = new HashMap<>();

        try {
             profileInfo.put("partners",IorEngine.getPartnersAmount(userEmail));
            profileInfo.put("followers",IorEngine.getAmountFollowingMyReceipts(userEmail));
            profileInfo.put("reciepts",IorEngine.getReceiptsAmount(userEmail));
            String resp = new Gson().toJson(profileInfo);
            try (PrintWriter out = response.getWriter()) {
                out.println(resp);
                out.flush();
            }

        }

        catch (Throwable throwable)
        {
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
