package XML.services;

import XML.models.Department;
import XML.models.Product;
import XML.models.Warehouse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserService {

//    public Warehouse populateXML() {
//        Product tea = new Product("01", "Tea", 1, 10);
//        Product milk = new Product("02", "Milk", 1, 10);
//        Product bread = new Product("03", "Bread", 1, 10);
//        Product mug = new Product("04", "Mug", 1, 10);
//
//        Department dairy = new Department(1, "Dairy");
//        Department bakery = new Department(2, "Bakery");
//        Department homeGoods = new Department(3, "Home goods");
//        Department drinks = new Department(4, "Drinks");
//
//        Warehouse amazonWarehouse = new Warehouse(1, "Amazon");
//
//        dairy.addProduct(milk);
//        bakery.addProduct(bread);
//        homeGoods.addProduct(mug);
//        drinks.addProduct(tea);
//
//        amazonWarehouse.addDepartment(dairy);
//        amazonWarehouse.addDepartment(bakery);
//        amazonWarehouse.addDepartment(homeGoods);
//        amazonWarehouse.addDepartment(drinks);
//        return amazonWarehouse;
//    }

    Document doc = null;

    public String validateXML(String filepath) {
        DocumentBuilder db = null;
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s = null;
        try {
            s = sf.newSchema(new File("storage.xsd"));
        } catch (SAXException e) {
            e.printStackTrace();
        }
        assert s != null;
        Validator validator = s.newValidator();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setSchema(s);
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;
        Source source = new StreamSource(filepath);
        try {
            db.setErrorHandler(new CustomErrorHandler());
            try {
                doc = db.parse(new File(filepath));
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
            validator.validate(source);
            return null;
        } catch (SAXException | IOException ex) {
            return "XML file is invalid because " + ex.getMessage();
        }
    }

    public Warehouse parseXML() {
        assert doc != null;
        return createWarehouse(doc);
    }

    public Warehouse createWarehouse(Document doc) {
        assert doc != null;
        Warehouse warehouse = null;
        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("Storage")) {
            int id = Integer.parseInt(root.getAttribute("id"));
            String name = root.getAttribute("name");
            warehouse = new Warehouse(id, name);
            NodeList listOfDepartments = root.getElementsByTagName("Department");
            for (int i = 0; i < listOfDepartments.getLength(); i++) {
                Element department = (Element) listOfDepartments.item(i);
                String departmentId = department.getAttribute("id");
                String departmentName = department.getAttribute("name");
                Department newDepartment = new Department(Integer.parseInt(departmentId), departmentName);
                warehouse.addDepartment(newDepartment);
                NodeList listOfProducts = department.getElementsByTagName("Product");
                for (int j = 0; j < listOfProducts.getLength(); j++) {
                    Element product = (Element) listOfProducts.item(j);
                    String barcode = product.getAttribute("barcode");
                    String productName = product.getAttribute("name");
                    String productWeight = product.getAttribute("weight");
                    String productPrice = product.getAttribute("price");
                    Product newProduct = new Product(barcode, productName, Integer.parseInt(productWeight), Integer.parseInt(productPrice));
                    newDepartment.addProduct(newProduct);
                }
            }
        }
        return warehouse;
    }

    public void createXML(String filepath, Warehouse warehouse) {
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;
        db.setErrorHandler(new CustomErrorHandler());
        Document doc = db.newDocument();

        Element root = doc.createElement("Storage");
        root.setAttribute("id", String.valueOf(warehouse.getId()));
        root.setAttribute("name", warehouse.getTitle());
        doc.appendChild(root);

        ArrayList<Department> departments = warehouse.getDepartments();
        ArrayList<Product> products;

        for (Department d : departments) {
            Element department = doc.createElement("Department");
            department.setAttribute("id", String.valueOf(d.getId()));
            department.setAttribute("name", d.getName());
            root.appendChild(department);

            products = d.getProducts();

            for (Product p : products) {
                Element product = doc.createElement("Product");
                product.setAttribute("barcode", p.getBarcode());
                product.setAttribute("name", p.getName());
                product.setAttribute("weight", String.valueOf(p.getWeight()));
                product.setAttribute("price", String.valueOf(p.getPrice()));
                department.appendChild(product);
            }
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filepath));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        try {
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
