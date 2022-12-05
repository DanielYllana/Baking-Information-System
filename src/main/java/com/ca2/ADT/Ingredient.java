package com.ca2.ADT;


import com.ca2.MainApplication;
import com.ca2.Controllers.ShowGoodsController;
import com.ca2.Controllers.TileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Ingredient  implements Comparable <Ingredient>{

    private String name;
    private String desc;
    private Double cals;
    private String relativeURL;
    private String url;
    private Image image;



    public Ingredient(String _name, String _desc, Double _cals, String path) {
        this.name = _name;
        this.desc = _desc;
        this.cals = _cals;

        this.relativeURL = path;

        this.url = this.relativeURL;

        try {
            InputStream imageStream = new FileInputStream(this.url);
            this.image = new Image(imageStream);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find image with url " + this.url);
        }
    }


    public Ingredient(Node node) {
        Element e = (Element) node;

        this.name = e.getElementsByTagName("Name").item(0).getTextContent();
        this.desc = e.getElementsByTagName("Desc").item(0).getTextContent();
        this.cals = Double.parseDouble(e.getElementsByTagName("Cals").item(0).getTextContent());

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
        Element descElem = doc.createElement("Desc");
        Element calsElem = doc.createElement("Cals");

        Text nameText = doc.createTextNode(this.name);
        Text descText = doc.createTextNode(this.desc);
        Text calsText = doc.createTextNode(String.valueOf(this.cals));

        nameElem.appendChild(nameText);
        descElem.appendChild(descText);
        calsElem.appendChild(calsText);

        root.appendChild(nameElem);
        root.appendChild(descElem);
        root.appendChild(calsElem);

        return root;
    }


    public String getName() { return this.name; }

    public String getDesc() { return this.desc; }

    public Double getCals() { return this.cals; }

    public Image getImage() { return this.image; }

    @Override
    public int compareTo(Ingredient o) {
        //ordered acordign to calories
        //returns negative if this < < o , 0 for this==o , positive this > o
        return cals.compareTo(o.cals);
    }

    public VBox createTile(ShowGoodsController controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("tile-bakedGood.fxml"));

            VBox box = fxmlLoader.load();
            TileController tileController = fxmlLoader.getController();
            tileController.setData(this, controller);

            return box;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public String getImageURL() {
        return this.url;
    }

    public boolean match(String query, String param2) {
        query = query.toLowerCase();

        if (param2.equals("Name")) {
            return this.name.toLowerCase().contains(query);
        } else if(param2.equals("Description")) {
            return this.desc.toLowerCase().contains(query);
        }
        return false;
    }
}
