package servlets;

import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.Entities.User;
import servletsUtils.ServletsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddingUserServlet", urlPatterns = {"/registerUser"})
public class AddingUserServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String userId = request.getParameter("email");
        String accessToken = request.getParameter("access_token");
        String refreshToken = request.getParameter("refresh_token");

        PrintWriter o = response.getWriter();
        Gson gson = new Gson();

        Map<String, String> user = new HashMap<>();
        user.put("email", userId);
        user.put("token", accessToken);

        String json = gson.toJson(user);
        o.println(json);
        o.flush();

        int x= 5;

        //IorEngine engine = ServletsUtils.getEngine(getServletContext());
        //engine.registerUser(new User(userId, accessToken, refreshToken));

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);




    }


}
