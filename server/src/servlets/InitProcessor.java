package servlets;

import EmailProcessor.Processor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitProcessor extends HttpServlet {

    public void init(ServletConfig config) throws ServletException{
        Processor emailProcessor = new Processor();
        emailProcessor.Run();
    }
}
