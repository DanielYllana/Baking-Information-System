package com.ca2.ADT;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> , Serializable, Comparable<T>  {



    @Override
    public Iterator<T> iterator() {
        return new Iterador<T>(first);
    }

    @Override
    public int compareTo(T o) {
        return 0;
    }



    protected class Iterador<T> implements Iterator<T> {
        private Node current;
        public Iterador(Node n) {current=n; }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public T next() {
            Node<T> temp= current;
            current=temp.next;
            return temp.element;
        }
    }
    protected   class Node<T> implements  Serializable , Comparable<T>{
        T element;
        Node next;

        Node() {
            next = null;
        }

        Node(T el, Node ne) {
            element = el;
            next = ne;
        }

        @Override
        public int compareTo(T o) {
            return 0 ;
        }
    }

    protected int totalelem = 0;
    protected Node<T> first = null;
    protected Node<T> last;

    public LinkedList() {
        totalelem = 0;
        first = null;
        last = null;
    }

    public LinkedList(LinkedList<T> other) {
        Copia(other);
    }

    public void Copia(LinkedList<T> other) {
        if (other.empty()) {
            first = last = null;
            totalelem = 0;
        } else {
            Node  act = other.first; // recorre la cola original
            Node  ant = new Node<T>((T) act.element, null); // último nodo copiado
            first = ant;
            while (act.next != null) {
                act = act.next;
                ant.next = new Node(act.element,null);
                ant = ant.next;
            }
            last = ant;
            totalelem = other.totalelem;
        }
    }

    public <T extends Comparable<T>>  void  push(T ele) {
        Node novo = new Node(ele, null);
        if (last != null)//just in case the list is empty
        {
            last.next = novo;
        }
        last = novo;
        if (first == null)//again the list could be empty
        {
            first = novo;

        }
        ++totalelem;
    }
    public boolean empty()
    {
        return totalelem==0;
    }
    public T front()
    {
        if(empty())
        {
            throw new RuntimeException("cant  access front since list is empty");

        }
       else
           return first.element;

    }
    public void popfront()
    {

        if(empty())
        {
            throw new RuntimeException("tried to pop an empty list");
        }
        else
        {
            Node<T> todel = first;
            first= first.next;
            //todel= null;
            //Orphan objects delete themselves

            --totalelem;
        }
    }
    public int size()
    {return totalelem;}
    public boolean contains (T elem)
    {
        Node actual = first;
        while(actual!=null)
        {
            T element = (T) actual.element;
            if(element.equals(elem))
                return true;
            else
            actual= actual.next;
        }
            return false;


    }

    public Node<T> search (T elem)
    {
        Node actual = first;
        while(actual!=null)
        {

            if(actual.element==elem)
                return actual;
            actual= actual.next;
        }
        return null;

    }
    public void foreach()//lamba expresion??
    {
        Node actual = first;
        while(actual!=null)
        {

            System.out.println(actual.element);

            actual= actual.next;
        }

    }
    public void showAll()
    {
        Node actual = first;
        while(actual!=null)
        {

          System.out.println(actual.element);
            actual= actual.next;
        }


    }
    public int GetSize() {return  totalelem;}
    public void RemoveItem(T elem )
    {
        Node previos = first;
        boolean found = false;
        Node aux= first;
        if(previos.element==elem)//special case the jewel you remove is the first in the list
        {
            first= previos.next;//now javas trash collector should delete elem since nothing references it
            elem=null;
            totalelem--;
        }
        else {
            while (previos.next != null && !found) {

                if (previos.next.element == elem)
                {   found = true;}
                else {
                    aux = previos;
                    previos = previos.next;
                }
            }

                if (previos.next==null) //borro el ultimo
                {
                    last = aux;
                    aux.next= null;
                }
                else
                {
                    previos.next = previos.next.next; //now javas trash collector should delete elem since nothing references it
                    elem = null;
                }
                totalelem--;


        }

    }
    public <T extends Comparable<T>> void Sortedinsert  ( T elem  )// de menor a mayor
    {
        int i = 0;
        boolean sorted = false;
        Node<T> actual =  (Node<T> ) first;
        Node<T> previous = null;

        while ( i < totalelem && !sorted && actual!=null)
        {
            if(actual.element.compareTo(elem)>=0)
            {
                //insert
                Node<T> novo = new Node<T> (elem , null);
                Node<T> aux = new Node<>(elem, novo);
                    if(previous==null)
                    {
                        this.first = aux.next  ;//es que el casting no va

                        novo.next= actual;
                    }
                    else {
                        previous.next= novo;
                        novo.next = actual;
                    }
                ++totalelem;
                sorted= true;
            }
            else
                previous= actual;
                actual= actual.next;
        }
        if (this.empty() || !sorted) {
            this.push(elem);
        }

    }
    public <T extends Comparable<T>> T Get (int i)
    {
        T elem = null;
        Node<T> a = (Node<T>)first;
        for (int n =0; n<i; n++)
        {
            a=a.next;
        }
        return a.element;
    }
    public <T extends Comparable<T>>T BinarySearch (T elem)
    {

        int mid = totalelem/2;
        int hihg = totalelem-1;
        int low = 0;
        T element = (T) elem;
        T actual;
        while (low<=hihg)
        {   mid = (low + hihg)/2;
            actual = Get(mid);
            if (actual.compareTo((element))==0)
            {return actual;}
            if (actual.compareTo(element)>0)
            {hihg= mid-1;}
            else low = mid+1;


        }
        return null;
    }
    public <T extends Comparable<T>> LinkedList<T> BinarySearchAll (T elem)
    {
        LinkedList<T> results = new LinkedList<T>();
        LinkedList<T> tosearch=new LinkedList<T>( );
        tosearch.Copia((LinkedList<T>) this);
        T found = tosearch.BinarySearch(elem);
        while (found!=null)
        {
            tosearch.RemoveItem(found);
            results.push(found);
            found = tosearch.BinarySearch(elem);
        }
        return results;

    }
}
