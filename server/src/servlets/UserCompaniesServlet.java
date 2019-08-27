package servlets;

import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.Entities.CompanyLogo;
import servletsUtils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserCompaniesServlet", urlPatterns = {"/userCompanies"})

public class UserCompaniesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {
            List<CompanyLogo> companies = IorEngine.getUserCompanies();

            String resp = new Gson().toJson(companies);
            try (PrintWriter out = response.getWriter()) {
                out.println(resp);
                out.flush();
            }
        }
        catch (Throwable t) {
            response.setStatus(Constants.DB_ERROR_CODE);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }


}