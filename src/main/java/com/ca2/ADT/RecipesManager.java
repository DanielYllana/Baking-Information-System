package com.ca2.ADT;

import com.ca2.Controllers.ShowGoodsController;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecipesManager {

    // Quick lookup for baked good name to object
    HashTable<String, BakedGood> bakedGoodsTable;

    // Quick lookup for ingredient name to object;
    HashTable<String, Ingredient> ingredientTable;

    // Upper table has baked good name as key and another table as value
    // the value table contains Ingredient name as key and quantity as value
    HashTable<String, HashTable<String, Double>> recipesTable;


    public RecipesManager() {
        this.bakedGoodsTable = new HashTable<String, BakedGood>();
        this.ingredientTable = new HashTable<String, Ingredient>();

        this.recipesTable = new HashTable<String, HashTable<String, Double>>();
    }


    public void reset() {
        this.bakedGoodsTable = new HashTable<String, BakedGood>();
        this.ingredientTable = new HashTable<String, Ingredient>();

        this.recipesTable = new HashTable<String, HashTable<String, Double>>();
    }



    public void loadData(Document doc) {
        this.reset();


        NodeList nodeListIngredients = doc.getElementsByTagName("Ingredients");
        this.loadIngredients(nodeListIngredients);

        NodeList nodeListBakedGoods = doc.getElementsByTagName("BakedGoods");
        this.loadBakedGoods(nodeListBakedGoods);
    }

    public void loadIngredients(NodeList nodeList) {
        NodeList nl = ((Element) nodeList.item(0)).getElementsByTagName("Ingredient");
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            Ingredient ing = new Ingredient(node);
            this.ingredientTable.insert(ing.getName(), ing);
        }
    }


    public Element saveIngredients(Document doc) {
        Element root = doc.createElement("Ingredients");

        HashTable<String, Ingredient>.Iterator<String, Ingredient> iter =
                this.ingredientTable.begin();

        while(!iter.equals(this.ingredientTable.end())) {
            root.appendChild(iter.value().save(doc));
        }
        return root;
    }


    public Element saveBakedGoods(Document doc) {
        Element root = doc.createElement("BakedGoods");

        HashTable<String, BakedGood>.Iterator<String, BakedGood> iter =
                this.bakedGoodsTable.begin();

        while(!iter.equals(this.bakedGoodsTable.end())) {
            root.appendChild(iter.value().save(doc));
        }
        return root;
    }



    private HashTable<String, Double> loadIngredientListTable(NodeList nodeList) {

        HashTable<String, Double> ingredientListTable = new HashTable<>();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node ingNode = nodeList.item(j);
            Element e = (Element) ingNode;

            String ingName = e.getElementsByTagName("Name").item(0).getTextContent();
            Double ingAmount = Double.parseDouble(e.getElementsByTagName("Amount").item(0).getTextContent());

            if (this.ingredientTable.exists(ingName)) {
                ingredientListTable.insert(ingName, ingAmount);
            } else {
                System.out.println("Invalid ingredient. Not found in table");
            }
        }
        return ingredientListTable;
    }

    public void loadBakedGoods(NodeList nodeList) {
        NodeList nl = ((Element) nodeList.item(0)).getElementsByTagName("BakedGood");
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            BakedGood good = new BakedGood(node);
            this.bakedGoodsTable.insert(good.getName(), good);

            NodeList ingredientList = ((Element) node).getElementsByTagName("Ingredient");

            HashTable<String, Double> ingredientTable = this.loadIngredientListTable(ingredientList);

            if (ingredientTable.numElems() > 0) {
                this.recipesTable.insert(
                        good.getName(),
                        ingredientTable
                );
            } else {
                System.out.println("No recipe provided for baked good: " + good.getName());
            }


        }
    }

    public void addIngredientsToGrid(GridPane grid, ShowGoodsController controller) {
        HashTable<String, Ingredient>.Iterator<String, Ingredient> iter = this.ingredientTable.begin();

        GridPosition position = new GridPosition(0, 0, 3);
        while(!iter.equals(this.ingredientTable.end())) {

            VBox box = iter.value().createTile(controller);
            grid.add(box, position.getColumn(), position.getRow());

            iter.next();
            position.next();
        }
    }

    public void addGoodsToGrid(GridPane grid, ShowGoodsController controller) {
        HashTable<String, BakedGood>.Iterator<String, BakedGood> iter = this.bakedGoodsTable.begin();

        GridPosition position = new GridPosition(0, 0, 3);
        while(!iter.equals(this.bakedGoodsTable.end())) {

            VBox box = iter.value().createTile(controller);
            grid.add(box, position.getColumn(), position.getRow());

            iter.next();
            position.next();
        }
    }

    public void delete(BakedGood good) {
        this.bakedGoodsTable.remove(good.getName());
        this.recipesTable.remove(good.getName());
    }

    public void addBakedGood(BakedGood bg) {
        this.bakedGoodsTable.insert(bg.getName(), bg);
    }

    public void addIngredient(Ingredient ing) {
        this.ingredientTable.insert(ing.getName(), ing);
    }

    public void addGoodsToList(ObservableList<BakedGood> listBakedGood) {
        HashTable<String, BakedGood>.Iterator<String, BakedGood> iter = this.bakedGoodsTable.begin();

        while(!iter.equals(this.bakedGoodsTable.end())) {
            listBakedGood.add(iter.value());
            iter.next();
        }
    }

    public void addIngredientsToList(ObservableList<Ingredient> listIngredients) {
        HashTable<String, Ingredient>.Iterator<String, Ingredient> iter = this.ingredientTable.begin();

        while(!iter.equals(this.ingredientTable.end())) {
            listIngredients.add(iter.value());
            iter.next();
        }
    }

    public void addRecipe(BakedGood bg, HashTable<String, Double> ingredientTable) {
        this.recipesTable.insert(bg.getName(), ingredientTable);
    }

    public void extractRecipe(ObservableList<String> listIngredients, BakedGood bg) {
        if (!this.recipesTable.exists(bg.getName())) {
            return;
        }

        HashTable<String, Double> ingredientTable = this.recipesTable.get(bg.getName());
        HashTable<String, Double>.Iterator<String, Double> iter = ingredientTable.begin();

        while(!iter.equals(ingredientTable.end())) {
            listIngredients.add(iter.key() + ": " + iter.value());
            iter.next();
        }

    }
}
