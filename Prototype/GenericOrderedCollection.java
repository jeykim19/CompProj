public interface GenericOrderedCollection<E>{
    public void append(E toAppend);
    public E peek();
    public E pop();
    public void remove(E element);
    public void remove(int index);
    public String toString();
    public int length();
    public boolean isEmpty();
    public Node<E> get(E element);
}
