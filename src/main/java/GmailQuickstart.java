package main.java;
import EmailProcessor.Processor;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import java.util.logging.*;

public class GmailQuickstart {

    private static final String USER = "shikoba21@gmail.com";
    private static Tika tikaParser = new Tika();  // for get the string from byte[]. external jar.
    private static Metadata metadata = new Metadata();  // for get the string from byte[]. external jar.

    public static void main(String[] args) throws Throwable {
        Logger logger = Logger.getLogger("MyLog");
//        FileHandler fh;
//        try {
            // This block configure the logger with handler and formatter
//            SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
//            fh = new FileHandler("C:\\Users\\orbar\\Desktop\\logsIOR\\log_" +
//                     format.format(Calendar.getInstance().getTime()) + ".log");
//            logger.addHandler(fh);
//            fh.setFormatter(new MyFormatter());
            logger.setLevel(Level.FINEST);
//        } catch (SecurityException | IOException e) {
//            e.printStackTrace();
//        }

        // the following statement is used to log any messages

        Processor emailProcessor = new Processor();
        emailProcessor.Run();

    }
}

//class MyFormatter extends Formatter {
//
//    /* (non-Javadoc)
//     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
//     */
//    @Override
//    public String format(LogRecord record) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(record.getLevel()).append(':');
//        sb.append(record.getMessage()).append('\n');
//        return sb.toString();
//    }
//}