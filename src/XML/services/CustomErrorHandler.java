package XML.services;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class CustomErrorHandler implements ErrorHandler {

    private final Logger logger = Logger.getLogger(CustomErrorHandler.class);

    @Override
    public void warning(SAXParseException exception) {
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) {
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) {
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
//        System.out.println("Рядок " + exception.getLineNumber() + ":");
//        System.out.println(exception.getMessage());
    }
}
