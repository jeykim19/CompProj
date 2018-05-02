
//import oc.GenericOrderedCollection;
//import oc.LinkedDS;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Pair{
  public double x;
  public double y;

  public Pair(double initX, double initY){
    x = initX;
    y = initY;
  }

  public Pair add(Pair toAdd){
    return new Pair(x + toAdd.x, y + toAdd.y);
  }

  public Pair divide(double denom){
    return new Pair(x / denom, y / denom);
  }

  public Pair times(double val){
    return new Pair(x * val, y * val);
  }

  public void flipX(){
    x = -x;
  }

  public void flipY(){
    y = -y;
  }
}

class Sphere{
Pair position;
Pair velocity;
double radius;
double dampening;
Color color;
public Sphere()
{
  Random rand = new Random();
  position = new Pair(500.0, 500.0);
  velocity = new Pair((double)(rand.nextInt(1000) - 500), (double)(rand.nextInt(1000) - 500));
  // acceleration = new Pair(0.0, 200.0);
  radius = 25;
  dampening = 1.3;
  color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
}

}


public class CindyTester{
    public static void main(String args[]){
        LinkedDS ds = new LinkedDS();
        // LinkedDS ds = new LinkedDS();

        testDS(ds);
    }
  public static void testDS(LinkedDS ds){
    // public static void testDS(LinkedDS ds){

  // System.out.println(ds.end);
  // ds.end.num = 1;
  // System.out.println(ds.end);
  System.out.println(ds.pop());

  System.out.println(ds.peek());

        System.out.println(ds);
        ds.append(1);
        ds.append(2);
        ds.append(3);
        ds.append(9);
        System.out.println(ds);
        System.out.println(ds.length());
ds.remove(2);
// ds.remove(7);

System.out.println(ds);



        // ds.pop();
        System.out.println(ds.pop());

        System.out.println(ds.peek());
        System.out.println(ds);
        ds.append('s');
        ds.append('y');
        ds.length();
        ds.append(6);
        System.out.println(ds);

        ds.pop();
        ds.pop();
        ds.pop();
        ds.append(7);
        Pair Pair1 = new Pair (1,1);
        ds.append(Pair1);
        // Pair a = ds.end.element;
        System.out.println(ds.end.element);

        System.out.println(ds);
        System.out.println(ds.length());

        ds.append(8);
        System.out.println(ds.get(2));
        System.out.println(ds.get(ds.length()-1));

        //ds.append(9);
        ds.append(3);ds.append(1);ds.append(4);ds.append(1);ds.append(5);ds.append(9);
        ds.append(3);ds.pop();ds.pop();
        System.out.println(ds);
        ds.append(9);
        ds.length();
        System.out.println("here");

        ds.append(3);ds.append(1);ds.append(4);ds.append(1);ds.append(5);
        System.out.println(ds);
        // Sphere Sphere1 = new Sphere();
        // ds.append(Sphere1);

        ds.append(9);
        System.out.println(ds);

        ds.remove(4);
        System.out.println(ds);

        ds.remove(ds.length()-1);

        System.out.println(ds);
        ds.pop();ds.pop();ds.pop();ds.pop();ds.pop();ds.pop();
        System.out.println(ds);
        ds.pop();ds.pop();ds.pop();ds.pop();
        ds.pop();System.out.println(ds);ds.pop();
        System.out.println(ds);
        ds.pop();
    }
}
