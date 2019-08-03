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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/registerUser"})
public class RegisterUserServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String email = request.getParameter("email");
        String accessToken = request.getParameter("access_token");
        String refreshToken = request.getParameter("refresh_token");
        String registerDate = request.getParameter("registerDate");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(registerDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        IorEngine.registerUser(new User(email, accessToken, refreshToken, date));


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);




    }


}
