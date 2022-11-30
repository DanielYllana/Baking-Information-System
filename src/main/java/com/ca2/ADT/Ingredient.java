package com.ca2.ADT;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class Ingredient  implements Comparable <Ingredient>{

    private String name;
    private String desc;
    private Double cals;



    public Ingredient(String _name, String _desc, Double _cals) {
        this.name = _name;
        this.desc = _desc;
        this.cals = _cals;
    }


    public Ingredient(Node node) {
        Element e = (Element) node;

        this.name = e.getElementsByTagName("Name").item(0).getTextContent();
        this.desc = e.getElementsByTagName("Desc").item(0).getTextContent();
        this.cals = Double.parseDouble(e.getElementsByTagName("Cals").item(0).getTextContent());
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

    @Override
    public int compareTo(Ingredient o) {
        //ordered acordign to calories
        //returns negative if this < < o , 0 for this==o , positive this > o
        return cals.compareTo(o.cals);
    }
}
