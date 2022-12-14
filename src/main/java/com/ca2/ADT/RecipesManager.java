package com.ca2.ADT;

import com.ca2.Controllers.ShowGoodsController;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class  RecipesManager<T>  {
    T value ;
    // Quick lookup for baked good name to object
    HashTable<String, BakedGood> bakedGoodsTable;

    // Quick lookup for ingredient name to object;
    HashTable<String, Ingredient> ingredientTable;

    // Upper table has baked good name as key and another table as value
    // the value table contains Ingredient name as key and quantity as value
    HashTable<String, HashTable<String, Double>> recipesTable;


    LinkedList<BakedGood> bakedGoodsList;
    LinkedList<Ingredient> ingredientList;


    public RecipesManager() {
        this.bakedGoodsTable = new HashTable<String, BakedGood>();
        this.ingredientTable = new HashTable<String, Ingredient>();

        this.recipesTable = new HashTable<String, HashTable<String, Double>>();

        this.bakedGoodsList = new LinkedList<>();
        this.ingredientList = new LinkedList<>();
    }


    public void reset() {
        this.bakedGoodsTable = new HashTable<String, BakedGood>();
        this.ingredientTable = new HashTable<String, Ingredient>();

        this.recipesTable = new HashTable<String, HashTable<String, Double>>();

        this.bakedGoodsList = new LinkedList<>();
        this.ingredientList = new LinkedList<>();
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
            this.ingredientList.Sortedinsert(ing);
        }
    }


    public Element saveIngredients(Document doc) {
        Element root = doc.createElement("Ingredients");


        for (Ingredient ing: this.ingredientList) {
            root.appendChild(ing.save(doc));
        }

        return root;
    }


    public Element saveBakedGoods(Document doc) {
        Element root = doc.createElement("BakedGoods");

        for (BakedGood bg: this.bakedGoodsList) {
            root.appendChild(bg.save(doc));
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

            this.bakedGoodsList.Sortedinsert(good);

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
        GridPosition position = new GridPosition(0, 0, 3);

        for (Ingredient ing: this.ingredientList) {
            VBox box = ing.createTile(controller);
            grid.add(box, position.getColumn(), position.getRow());
            position.next();
        }
    }

    public void addGoodsToGrid(GridPane grid, ShowGoodsController controller, int search ) {

        if (search != 0 && search != 1) { // default search is alphabetical
            search = 0;
        }

        GridPosition position = new GridPosition(0, 0, 3);
        if(search==1)   // 1 calorias , 0 alfabetico
        {
            for (BakedGood bg : this.bakedGoodsList) {


                HashTable<String, Double> ings = recipesTable.get(bg.getName());
                int totalc = 0;
                if (ings != null) {
                    HashTable<String, Double>.Iterator<String, Double> iter = ings.begin();

                    while (!iter.equals(ings.end())) {
                        Ingredient ing = this.ingredientTable.get(iter.key());
                        totalc += ((ing.getCals()) / 100.0 * iter.value());
                        iter.next();
                    }

                    bg.SetCals(totalc);
                }
            }

            LinkedList<BakedGood> caloriesSorted = sortBakedGoodsCals();

            for (BakedGood bg : caloriesSorted) {
                VBox box = bg.createTile(controller);
                grid.add(box, position.getColumn(), position.getRow());

                position.next();
            }

        }
        else if (search==0) {
            for (BakedGood bg : this.bakedGoodsList)
            {
                VBox box = bg.createTile(controller);
                grid.add(box, position.getColumn(), position.getRow());

                position.next();
            }
        }
    }

    public void delete(BakedGood good) {
        this.bakedGoodsTable.remove(good.getName());
        this.recipesTable.remove(good.getName());

        this.bakedGoodsList.RemoveItem(good);
    }


    public void delete(Ingredient ing) {
        this.ingredientTable.remove(ing.getName());
        this.ingredientList.RemoveItem(ing);

        HashTable<String, HashTable<String, Double>>
                .Iterator<String, HashTable<String, Double>> iter = this.recipesTable.begin();

        while(!iter.equals(this.recipesTable.end())) {
            if (iter.value().exists(ing.getName())) {
                iter.value().remove(ing.getName());
            }
            iter.next();
        }
    }

    public void addBakedGood(BakedGood bg) {
        this.bakedGoodsList.Sortedinsert(bg);
        this.bakedGoodsTable.insert(bg.getName(), bg);
    }

    public void addIngredient(Ingredient ing) {
        this.ingredientList.Sortedinsert(ing);
        this.ingredientTable.insert(ing.getName(), ing);
    }

    public void addGoodsToList(ObservableList<BakedGood> listBakedGood) {
        for (BakedGood bg: this.bakedGoodsList) {
            listBakedGood.add(bg);
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


    public void addIngredientsToGrid(GridPane goodsGrid, ShowGoodsController controller, String param2, String query) {
        GridPosition position = new GridPosition(0, 0, 3);

        for(Ingredient ing: this.ingredientList) {
            if (ing.match(query, param2)) {
                goodsGrid.add(ing.createTile(controller), position.getColumn(), position.getRow());
                position.next();
            }
        }
    }

    public void addGoodsToGrid(GridPane goodsGrid, ShowGoodsController controller, String param2, String query) {
        GridPosition position = new GridPosition(0, 0, 3);

        for(BakedGood bg: this.bakedGoodsList) {
            if (bg.match(query, param2)) {
                goodsGrid.add(bg.createTile(controller), position.getColumn(), position.getRow());
                position.next();
            }
        }
    }

    public Ingredient getIngredient(String t1) {
        return this.ingredientTable.get(t1);
    }
    public LinkedList<BakedGood> sortBakedGoodsCals()
    {
        LinkedList<BakedGood> aux = new LinkedList<>();
        aux.Copia(bakedGoodsList);

        LinkedList<BakedGood> result = new LinkedList<BakedGood>();

        while (result.totalelem != bakedGoodsList.totalelem)
        {
            BakedGood mingood = aux.first.element;
            BakedGood actualgood = aux.first.element;

            for ( int rec = 0; rec < aux.totalelem ; rec++)
            {
                if (actualgood.GetCals() <= mingood.GetCals())
                {
                    mingood=actualgood;
                }

                actualgood = aux.Get(rec);

            }
            result.push(mingood);
            aux.RemoveItem(mingood);
        }
        return result;
    }



}
