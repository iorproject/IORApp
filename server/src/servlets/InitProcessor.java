package servlets;

import EmailProcessor.Processor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "InitProcessor", urlPatterns = {"/InitProcessor"}, loadOnStartup = 1)
public class InitProcessor extends HttpServlet {

    public void init(ServletConfig config) throws ServletException{
        Processor emailProcessor = new Processor();
        emailProcessor.Run();
    }
}
