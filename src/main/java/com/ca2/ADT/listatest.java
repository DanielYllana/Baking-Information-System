package com.ca2.ADT;

public class listatest {
    public static void main(String [] args)
    {
        LinkedList<BakedGood> lb= new LinkedList<BakedGood>();
        LinkedList<Ingredient> li = new LinkedList<Ingredient>();
        Ingredient i = new Ingredient("no3" , "no",12.0 , "caketest.png");
        Ingredient i2 = new Ingredient("no1" , "no",10.0 , "\\src\\main\\resources\\com\\ca2\\images\\bread.png");
        Ingredient i3 = new Ingredient("no2" , "no",11.0 , "\\src\\main\\resources\\com\\ca2\\images\\bread.png");
        BakedGood b = new BakedGood("a","desc ", "path","\\src\\main\\resources\\com\\ca2\\images\\bread.png");
        BakedGood b2 = new BakedGood("b","desc ", "path","\\src\\main\\resources\\com\\ca2\\images\\bread.png");
        BakedGood b3 = new BakedGood("c","desc ", "path","\\src\\main\\resources\\com\\ca2\\images\\bread.png");
        lb.Sortedinsert(b);
        lb.Sortedinsert(b2);
        lb.Sortedinsert(b3);
        li.Sortedinsert(i);
        li.Sortedinsert(i2);
        li.Sortedinsert(i3);
        Ingredient i4 =  li.BinarySearch(i2);
        System.out.println( i4.getName());

    }
}

