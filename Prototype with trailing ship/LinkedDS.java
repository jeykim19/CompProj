//package oc;

public class LinkedDS<E> implements GenericOrderedCollection<E>{

  // your code here
  public Node<E> end;
  public int length = 0;
  public  LinkedDS(){
    end = null;
  }
  public void append(E toAppend){
    Node<E> toAdd = new Node<E> (toAppend);
    toAdd.previous = end;
    end = toAdd;
    length += 1;
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
      length -= 1;
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
    return length;
    // int intsLength = 0;
    // Node n = end;
    // while(n != null){
    //   intsLength ++;
    //   n = n.previous;
    // }
    // return intsLength;


  }
  public void remove1 (E element){
    if (length()==0){
      System.out.println("This list is empty.");
    }

    int counter=0;
    int signal=0;
    Node <E> n=end; //Node<E> n= end;
    Node <E> p=end; //Node<E> p=end;
    while ((signal==0)&&(n!=null)){
	if (n.num == element){  //replace value with element in actual one
	    if (counter>0){
		while ((counter-1)>0){
		  p=p.previous; //replace nextSpace with previous
		  counter=counter-1;
	      }
	      p.previous=p.previous.previous;
	      length=length-1;
	      signal=1;
	  }
	    else if (counter==0){
	      end=end.previous;
	      signal=1;
	      length=length-1;
	  }
	    else if (counter<0){
	      System.out.println("Error");
	      signal=1;
	  }
      }
      else if (n.num!= element){
	  n=n.previous;
	  counter=counter+1;
      }
    }

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
      length -= 1;


    } else {
      throw new java.lang.Error("No node indexed");

    }
  }
  public Node<E> get (E element){
    Node<E> n=end;
    while (n!=null){
      if (n.num==element){
        return n;
      }
    }
    return null;
  }





 }


 class Node<E>{
 E num;
 Node previous;
 public Node(E num){
   this.num = num;
 }
 }
