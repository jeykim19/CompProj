public class LinkedDS<E> implements GenericOrderedCollection<E>{
    // your code here
    Node<E> end;
    //Node<E> beginning;
    int myCurrentLength;
    public LinkedDS(){
        end = null;
        //beginning = null;
        myCurrentLength = 0;
    }
    public void append(E toAppend){
        end = new Node<E>(toAppend, end, null);
        if (end.parent !=null){
	    end.parent.child=end;
	}
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
        E element = end.element;
        end = end.parent;
        myCurrentLength--;
        return element;
    }
    public void remove(E element) {

        if (myCurrentLength == 0) {
            System.out.println("The list is empty");
            return;
        }

        Node<E> n = end;
        while (n != null) {
            if (n.element == element) {
                n.child.parent = n.parent;
                n.parent.child = n.child;
            }
        }
        myCurrentLength--;
    }

    public void remove(int index){
        //The node at the ith index is the node that is 'length - i-1' nodes in on the tree
        //We want: this.child --> this.parent and this.parent --> this.child
        if(myCurrentLength == 0){
            System.out.println("The list is empty");
            return;
        }
        if(index >= myCurrentLength || index < 0){
            System.out.println("This is not a valid index");
            return;
        }
        if(index == myCurrentLength - 1){
            end = end.parent;
            end.child = null;
            myCurrentLength--;
            return;
        }

        int counter = 0;

        Node<E> n = end;
        counter = 0;

        while(counter < myCurrentLength - index - 1){
            counter++;
            n = n.parent;
        }
        //This removes it
        //n.parent = n.parent.parent;
        n.parent.child = n.child;
        n.child.parent = n.parent;

        myCurrentLength--;
    }
    public String toString(){
        String sequence = "";
        Node<E> n = end;
        while(n != null){
            sequence = n.element + " " + sequence;
            n = n.parent;
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
        }
        return null;
    }
}

class Node<E>{
    E element;
    Node<E> parent;
    Node<E> child;
    public Node(E element, Node<E> parent, Node<E> child){
        this.element = element;
        this.parent = parent;
        this.child = child;
    }
}
