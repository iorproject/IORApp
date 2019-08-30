package servlets;

import EmailProcessor.Processor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

@WebServlet(name = "InitProcessor", urlPatterns = {"/InitProcessor"}, loadOnStartup = 1)
public class InitProcessor extends HttpServlet {

    public void init(ServletConfig config) throws ServletException{
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        try {
        // This block configure the logger with handler and formatter
            SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
            fh = new FileHandler("./log_" +
                     format.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fh);
            fh.setFormatter(new MyFormatter());
        logger.setLevel(Level.FINEST);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        Processor emailProcessor = new Processor();
        emailProcessor.Run();
    }
}


class MyFormatter extends Formatter {

    /* (non-Javadoc)
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getLevel()).append(':');
        sb.append(record.getMessage()).append('\n');
        return sb.toString();
    }
}