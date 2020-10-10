package main.java.XML.services;

import main.java.XML.views.View;
import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class CustomErrorHandler implements ErrorHandler {

    private final Logger logger = Logger.getLogger(CustomErrorHandler.class);
    View view = new View();

    @Override
    public void warning(SAXParseException exception) {
        view.printStackTrace("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) {
        view.printStackTrace("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) {
        view.printStackTrace("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
        logger.error("Рядок " + exception.getLineNumber() + ":" + exception.getMessage());
    }
}
