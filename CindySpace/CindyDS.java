public class CindyDS<E> implements CindyCollection<E>{

  // your code here
  public Node<E> end;
  public  CindyDS(){
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


public E get(int index){
E target;
  if ((index<length())&&(index>-1)){
    int iIndex = length()-1;
    if (iIndex == index) {
target = end.client;
    }else{
      Node<E> targetNode = end;
      while(iIndex != index){
      targetNode = targetNode.previous;
      iIndex -= 1;


      }
      target = targetNode.client;
    } return target;
    }
     else {
    throw new java.lang.Error("No node indexed");

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
