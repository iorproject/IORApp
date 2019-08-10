package servlets;

import com.google.gson.Gson;
import engine.IorEngine;
import main.java.DB.Entities.CompanyLogo;
import main.java.DB.Entities.Receipt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CompanyReceiptsByUserServlet", urlPatterns = {"/companyReceiptsByUser"})

public class CompanyReceiptsByUserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String email = request.getParameter("email");
        String company = request.getParameter("company");

        List<Receipt> receipts = IorEngine.getCompanyReceipts(email, company);

        String resp = new Gson().toJson(receipts);
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