package servlets;

import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.Entities.User;
import servletsUtils.Constants;

import javax.jws.soap.SOAPBinding;
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

@WebServlet(name = "UserInfoServlet", urlPatterns = {"/userInfo"})

public class UserInfoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String email = request.getParameter("email");
        User user = IorEngine.getUserInfo(email);
        User responseUser = new User(email, null, null, user.getRegisterDate());

        String resp = new Gson().toJson(responseUser);
        try (PrintWriter out = response.getWriter()) {
            out.println(resp);
            out.flush();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }



}
