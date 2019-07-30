package main.java.servlets;

import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.DBHandler;
import main.java.DB.Entities.User;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.servletsUtils.ServletsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddingUserServlet", urlPatterns = {"/registerUser"})
public class AddingUserServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String userId = request.getParameter("email");
        String accessToken = request.getParameter("access_token");
        String refreshToken = request.getParameter("refresh_token");

        IorEngine engine = ServletsUtils.getEngine(getServletContext());
        engine.registerUser(new User(userId, accessToken, refreshToken));


//        try (PrintWriter out = response.getWriter()) {
//            Gson gson = new Gson();
//            String message = gson.toJson("turn played");
//            out.println(message);
//            out.flush();
//        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);




    }


}
