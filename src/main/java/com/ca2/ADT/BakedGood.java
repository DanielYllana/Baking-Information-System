package com.ca2.ADT;

import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BakedGood implements Comparable<BakedGood>{

    private String name;
    private String origin;
    private String desc;
    private String relativeURL;
    private String url;
    private Image image;


    public BakedGood(String _name, String _origin, String _desc, String path) {
        this.name = _name;
        this.origin = _origin;
        this.desc = _desc;
        this.relativeURL = path;

        this.url = System.getProperty("user.dir") + this.relativeURL;

        try {
            InputStream imageStream = new FileInputStream(this.url);
            this.image = new Image(imageStream);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find image with url " + this.url);
        }
    }


    public BakedGood(Node node) {
        Element e = (Element) node;

        this.name = e.getElementsByTagName("Name").item(0).getTextContent();
        this.origin = e.getElementsByTagName("Origin").item(0).getTextContent();
        this.desc = e.getElementsByTagName("Desc").item(0).getTextContent();
        this.relativeURL = e.getElementsByTagName("url").item(0).getTextContent();

        this.url = System.getProperty("user.dir") + this.relativeURL;

        try {
            InputStream imageStream = new FileInputStream(this.url);
            this.image = new Image(imageStream);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find image with url " + this.url);
        }
    }


    public Element save(Document doc) {
        Element root = doc.createElement("Ingredient");

        Element nameElem = doc.createElement("Name");
        Element originElem = doc.createElement("Origin");
        Element descElem = doc.createElement("Desc");
        Element urlElem = doc.createElement("url");

        Text nameText = doc.createTextNode(this.name);
        Text originText = doc.createTextNode(this.origin);
        Text descText = doc.createTextNode(this.desc);
        Text urlText = doc.createTextNode(this.relativeURL);

        nameElem.appendChild(nameText);
        originElem.appendChild(originText);
        descElem.appendChild(descText);
        urlElem.appendChild(urlText);

        root.appendChild(nameElem);
        root.appendChild(originElem);
        root.appendChild(descElem);
        root.appendChild(urlElem);

        return root;
    }

    public String getName() { return this.name; }

    @Override
    public int compareTo(BakedGood o)// compara el objecto que usa el metodo con el que le pasas
    {//los baked goods van en orden alfabetico por su nombre
    //returns negative if this < < o , 0 for this==o , positive this > o
        return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}
