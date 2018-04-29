//package oc;

public class LinkedDS<E> implements GenericOrderedCollection<E>{

  // your code here
  public Node<E> end;
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
        return end.client;

      } else{
        return null;
      }
    }


  public E pop(){
    E toReturn = null;
    if (end!= null){
      toReturn = end.client;
      end = end.previous;
    }
    return toReturn;
  }

  public String toString(){
    String allInts = "";
    Node n = end;
    while (n != null){
      allInts = n.client + " " + allInts;
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
      int iIndex = length()-1;
      Node afterRemoved = end;
      if (iIndex == index) {
        end = end.previous;

      }else{
        while(iIndex != index + 1){
        afterRemoved = afterRemoved.previous;
        iIndex -= 1;


        }
        afterRemoved.previous = afterRemoved.previous.previous;

      }
      }
       else {
      throw new java.lang.Error("No node indexed");

    }




  }
  public void remove(E element){
    if (length()==0){
      System.out.println("This list is empty.");
    }

    int counter=0;
    Node <E> n=end; //Node<E> n= end;
    Node <E> p=end; //Node<E> p=end;
    while (n!=null){
      counter=counter+1;

      if (n.element == element){  //replace value with element in actual one
	  if ((counter-1)>0){
	      while ((count-1)>0){
		  p=p.previous; //replace nextSpace with previous
		  counter=counter-1;
	      }
	      p=p.previous.previous;
	  }
	  else if ((counter-1)==0){
	      end=end.previous;
	  }
      }
      n=n.previous;
    }
  }






 }


 class Node<E>{
 E client;
 Node previous;
 public Node(E client){
   this.client = client;
 }
 }
