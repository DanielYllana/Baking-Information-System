package com.ca2;

import com.ca2.ADT.RecipesManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DataManager {


    public static void load(String path, RecipesManager manager) {

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            manager.loadData(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void save(File file, RecipesManager manager) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Data");

            root.appendChild(manager.saveIngredients(doc));
            root.appendChild(manager.saveBakedGoods(doc));

            doc.appendChild(root);
            DOMSource source = new DOMSource(doc);

            Result result = new StreamResult(file);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
            System.out.println("Writing xml data completed");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
