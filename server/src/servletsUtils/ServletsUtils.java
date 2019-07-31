package servletsUtils;

import engine.IorEngine;

import javax.servlet.ServletContext;

public class ServletsUtils {

    private static final String IOR_ENGINE_ATTRIBUTE_NAME = "iorEngine";
    private static final Object engineLock = new Object();

    public static IorEngine getEngine(ServletContext servletContext) {

        synchronized (engineLock) {
            if (servletContext.getAttribute(IOR_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(IOR_ENGINE_ATTRIBUTE_NAME, new IorEngine());
            }
        }
        return (IorEngine) servletContext.getAttribute(IOR_ENGINE_ATTRIBUTE_NAME);
    }


}
