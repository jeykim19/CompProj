public class LinkedDS<E> implements GenericOrderedCollection<E> {

    Node<E> start;
    Node<E> end;
    int myCurrentLength;


    public LinkedDS(){
        this.start = null;
        this.end = null;
        this.myCurrentLength = 0;
    }
    public void append(E toAppend){
        Node<E> tmp = new Node<E>(toAppend);
        if(myCurrentLength == 0){
            start = tmp;
            end = tmp;
            myCurrentLength++;
            return;
        }
        end.next = tmp;
        tmp.previous = end;
        end = tmp;

        myCurrentLength++;
    }
    public E peek(){
        if(end == null){
            return null;
        }
        return end.element;
    }
    public E pop(){
        if(end == null){
            return null;
        }
        E element = peek();
        end = end.previous;
        end.next = null;
        myCurrentLength--;
        return element;
    }
    public void remove(E element) {

        if (myCurrentLength == 0) {
            //System.out.println("The list is empty");
            return;
        }

        Node<E> n = end;
        while (n != null) {
            if (n.element == element) {
                eradicate(n);
            }
            n = n.previous;
        }
        myCurrentLength--;
    }

    public void remove(int index){
        //The node at the ith index is the node that is 'length - i-1' nodes in on the tree
        //We want: this.child --> this.parent and this.parent --> this.child
        if(myCurrentLength == 0){
            //System.out.println("The list is empty");
            return;
        }
        if(index >= myCurrentLength || index < 0){
            //System.out.println("This is not a valid index");
            return;
        }
        if(index == myCurrentLength - 1){
            end.previous.next = null;
        }

        int counter = 0;

        Node<E> n = end;
        counter = 0;

        while(counter < myCurrentLength - index - 1){
            counter++;
            n = n.previous;
        }
        //This removes it
        //n.parent = n.parent.parent;
        eradicate(n);

        myCurrentLength--;
    }

    private void eradicate(Node<E> n){
        if(n.previous != null && n.next != null) {
            n.previous.next = n.next;
            n.next.previous = n.previous;
        }
        else if (n.previous == null && n.next != null) {
            n.next.previous = null;
        }
        else if (n.next == null && n.previous != null) {
            n.previous.next = null;
        }
        else{
            start = null;
            end = null;
        }
    }

    public String toString(){
        String sequence = "";
        Node<E> n = end;
        while(n != null){
            sequence = n.element + " " + sequence;
            n = n.previous;
        }
        return sequence;
    }


    public int length(){
        return myCurrentLength;
    }
    public boolean isEmpty(){
        if(myCurrentLength == 0){
            return true;
        }
        return false;
    }

    public Node<E> get(E element){
        Node<E> n  = end;
        while(n != null){
            if(n.element == element){
                return n;
            }
            n = n.previous;
        }
        return null;
    }
}

class Node<E>{
    E element;
    Node<E> previous;
    Node<E> next;

    public Node(E element){
        this.element = element;
        //this.previous = previous;
        //this.next = next;
    }
}


