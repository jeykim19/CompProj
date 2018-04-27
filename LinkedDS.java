//package oc;

public class LinkedDS<E> implements GenericOrderedCollection<E>{

  // your code here
  private Node<E> end;
  public  LinkedDS(){
    end = null;
  }
  public void append(E toAppend){
    Node toAdd = new Node(toAppend);
    toAdd.previous = end;
    end = toAdd;
  }
  public E peek(){
    if (end!= null){
        return end.num;

      } else{
        return null;
      }
    }


  public E pop(){
    E toReturn = null;
    if (end!= null){
      toReturn = end.num;
      end = end.previous;
    }
    return toReturn;
  }

  public String toString(){
    String allInts = "";
    Node n = end;
    while (n != null){
      allInts = n.num + " " + allInts;
      n = n.previous;


    }
    return allInts;

  }
  public int length(){
    int intsLength = 0;
    Node n = end;
    while(n != null){
      intsLength ++;
      n = n.previous;
    }
    return intsLength;


  }

  public void remove(int index){

    if ((index<length())&&(index>-1)){
      int iIndex = 1;
      Node removed = end;
      while(iIndex != length() - 1-index){
      removed = removed.previous;
      iIndex += 1;


      }
      removed.previous = removed.previous.previous;

    } else {
      throw new java.lang.Error("No node indexed");

    }




  }




 }


 class Node<E>{
 E num;
 Node previous;
 public Node(E num){
   this.num = num;
 }
 }
