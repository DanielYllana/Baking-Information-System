package DataStructures;

import java.io.Serializable;
import java.util.Iterator;

public class Lista<T> implements Iterable<T> , Serializable {
    private T value;


    @Override
    public Iterator<T> iterator() {
        return new Iterador<T>(first);
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
    protected class Node<T> implements  Serializable {
        T element;
        Node next;

        Node() {
            next = null;
        }

        Node(T el, Node ne) {
            element = el;
            next = ne;
        }
    }

    protected int totalelem = 0;
    protected Node<T> first;
    protected Node<T> last;

    public Lista() {
        totalelem = 0;
        first = null;
        last = null;
    }

    public Lista(Lista<T> other) {
        Copia(other);
    }

    public void Copia(Lista<T> other) {

    }

    public void push(T ele) {
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
        if(previos.element==elem)//special case the jewel you remove is the first in the list
        {
            first= previos.next;//now javas trash collector should delete elem since nothing references it
            elem=null;
        }
        else {
            while (previos.next != null && !found) {

                if (previos.next.element == elem)
                {   found = true;}
                else
                previos = previos.next;
            }


                previos.next = previos.next.next; //now javas trash collector should delete elem since nothing references it
                elem = null;


        }

    }
}
