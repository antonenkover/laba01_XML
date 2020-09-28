package XML.services;

import XML.controllers.Controller;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class CustomErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) {
        Controller controller = new Controller();
        controller.setErrorTextPaneTextUwU("Hello");
        System.out.println("Рядок " + exception.getLineNumber() + ":");
        System.out.println(exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) {
        System.out.println("Рядок " + exception.getLineNumber() + ":");
        System.out.println(exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) {
        System.out.println("Рядок " + exception.getLineNumber() + ":");
        System.out.println(exception.getMessage());
    }
}
