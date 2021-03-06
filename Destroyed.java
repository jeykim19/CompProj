import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

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
  int height;
int width;
int margin;
  Pair position;
  Pair velocity;
  double radius;
  int diff;
  public Sphere(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
        margin = initMargin;
        width = initWidth;
        height = initHeight;
    Random rand = new Random();

    // the postion of a sphere is its center
  position = new Pair(rand.nextDouble()*width + margin,0.0);

  }
  public void update(World w, double time){
    position = position.add(velocity.times(time));
  }

  public void setPosition(Pair p){
    position = p;
  }
  public void setVelocity(Pair v){
    velocity = v;
  }
  // public void setAcceleration(Pair a){
  //   acceleration = a;
  // }




}

class Asteroid extends Sphere {
  int NumAxis = 10;
  int NumVertices = 7;
  double speed;
  double angle;
  double radius = 25;
  double[] angles = new double[NumVertices];
  double[] dists = new double[NumVertices];
  int[] xs = new int [NumVertices];
  int[] ys = new int [NumVertices];

  public Asteroid(int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    Random rand = new Random();
    angle = Math.PI/NumAxis*rand.nextInt(NumAxis);
    speed = (double)(diff*100);
    super.velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);

    // Random rand = new Random();
    int iVertex;
    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {
      // if (iVertex == 0) {
      //   angles[iVertex] = rand.nextDouble()*2*Math.PI;
      // } else{
      //   angles[iVertex] = rand.nextDouble()*(2*Math.PI-angles[iVertex -1])+angles[iVertex -1];
      // }
      angles[iVertex] = 2*Math.PI/NumVertices*iVertex;

      dists[iVertex] = rand.nextDouble()*radius/4 + radius;

  }
}

  public void draw(Graphics g){

    g.setColor(Color.white);
    int iVertex;

    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

    xs[iVertex] = (int)(position.x + Math.cos(angles[iVertex])*dists[iVertex]);
    ys[iVertex] = (int)(position.y + Math.sin(angles[iVertex])*dists[iVertex]);
}
    g.drawPolygon(xs,ys,NumVertices);
  }



}

class Planet extends Sphere{
  double radius = 170;
  public Planet(int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    super.velocity = new Pair(0.0, (double)diff*50);
  }
  public void draw(Graphics g){

    g.setColor(Color.red);
    g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));

  }


}
class Debris extends Asteroid{
    public Debris(int initWidth, int initHeight, int initMargin, int initDiff, double x, double y){
	super (initWidth,initHeight,initMargin,initDiff);
	super.position=new Pair (x,y);
	Random rand=new Random ();
	super. angle = Math.PI/5*rand.nextInt(100);
	super.velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);
	 int iVertex;
    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {
      // if (iVertex == 0) {
      //   angles[iVertex] = rand.nextDouble()*2*Math.PI;
      // } else{
      //   angles[iVertex] = rand.nextDouble()*(2*Math.PI-angles[iVertex -1])+angles[iVertex -1];
      // }
      angles[iVertex] = 2*Math.PI/NumVertices*iVertex;

      dists[iVertex] = (rand.nextDouble()*radius/4+rand.nextDouble()*radius)/2 ;

  }
    }


    public void draw(Graphics g){

    g.setColor(Color.white);
    int iVertex;
    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

    xs[iVertex] = (int)(position.x + Math.cos(angles[iVertex])*dists[iVertex]);
    ys[iVertex] = (int)(position.y + Math.sin(angles[iVertex])*dists[iVertex]);
}


    g.drawPolygon(xs,ys,NumVertices);


}
class World{
  int height;
  int width;
  int margin;
  int diff;
    int pieces;
  int numAsteroids;
  Asteroid asteroids[];
  Debris crushed[];
  Planet planet;

Pair acceleration;

  public World(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    numAsteroids = diff * 5;
    Random rand=new Random(10);
    int pieces=rand.nextInt();
    acceleration = new Pair(0.0, 0.0);

    asteroids  = new Asteroid[numAsteroids];
    crushed= new Debris[5];

    for (int i = 0; i < numAsteroids; i ++)
    {
      asteroids[i] = new Asteroid(width, height, margin, diff);
    }
    for (int i=0; i< 5; i++){
	crushed[i]=new Debris(width, height, margin, diff,0.0,0.0);
    }

    planet = new Planet(width, height, margin, diff);

  }

  public void drawSpheres(Graphics g){

    // should paint planet first to set it in the back

    if (planet != null) {
  planet.draw(g);

}
    for (int i = 0; i < numAsteroids; i++){
      asteroids[i].draw(g);
    }
    for (int i=0; i<5; i++){
	crushed[i].draw(g);
    }



  }

  public void updateSpheres(double time){
      for (int i=0; i<5; i++){
	  crushed[i].update(this,time);
      }

    for (int i = 0; i < numAsteroids; i ++){
      asteroids[i].update(this,time);
      if ((margin - asteroids[i].position.x >= asteroids[i].radius) ||(asteroids[i].position.x - (width+margin) >= asteroids[i].radius)
      || (asteroids[i].position.y - (height + margin) >= asteroids[i].radius)){
        asteroids[i] = new Asteroid(width, height, margin, diff);

      }
    }

    if (planet != null) {
      planet.update(this,time);
      if ((margin - planet.position.x >= planet.radius) ||(planet.position.x - (width+margin) >= planet.radius)
    || (planet.position.y - (height + margin) >= planet.radius)){
      planet = null;

    }
    }


  }

  public void renewPlanet(){
    planet = new Planet(width, height, margin, diff);
  }


// public void updateGravity(char charKeyPressed){
//   Pair leftG = new Pair(-300.0, 0.0);
//   Pair rightG = new Pair(300.0, 0.0);
//   Pair downG = new Pair(0.0, 300.0);
//   Pair upG = new Pair(0.0, -300.0);
//   Pair noG = new Pair(0.0, 0.0);
//   System.out.println(charKeyPressed);
//
//     switch (charKeyPressed){
//
//       case 'w': acceleration = upG;
//
// break;
//       case 's': acceleration = downG;
//       break;
//
//       case 'a': acceleration = leftG;
//       break;
//
//       case 'd': acceleration = rightG;
//       break;
//
//       default: acceleration = noG;
//       break;
//
//   }
//
// }
}
public class Destroyed extends JPanel{
// public class CindySpace extends JPanel implements KeyListener{
  public static final int WIDTH = 1024 ;
  public static final int HEIGHT = 768 ;
  public static final int MARGIN = 200;
  public static final int FPS = 60;
  public int diff = 1;
  World world;
  char charKeyPressed;
  class Runner implements Runnable {
    public void run()
    {
      while(true){
        // world.updateGravity(charKeyPressed);
        world.updateSpheres(1.0 / (double)FPS);
        // charKeyPressed = 'n';
        repaint();
        try{
          Thread.sleep(1000/FPS);
        }
        catch(InterruptedException e){}
        }

      }

      // Timer timer = new Timer();
      // timer.schedule(world.renewPlanet(),0,10000/diff);

    }
    class KeepPlanetComing implements Runnable{
      public void run()
      {
      while(true){
        world.renewPlanet();
        // charKeyPressed = 'n';
        repaint();
        try{
          Thread.sleep(30000/diff);
        }
        catch(InterruptedException e){}
        }
    }
  }


//     public void keyPressed(KeyEvent e) {
//       char c=e.getKeyChar();
//       System.out.println("You pressed down: " + c);
// charKeyPressed = c;
//     }
//     public void keyReleased(KeyEvent e) {
//       char c=e.getKeyChar();
//       System.out.println("\tYou let go of: " + c);
//     }
//
//
//     public void keyTyped(KeyEvent e) {
//       char c = e.getKeyChar();
//       System.out.println("You typed: " + c);
//     }
//     public void addNotify() {
//       super.addNotify();
//       requestFocus();
//     }

    public Destroyed(){
      world = new World(WIDTH, HEIGHT, MARGIN,diff);
      // addKeyListener(this);
      this.setPreferredSize(new Dimension(WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN));
      Thread mainThread = new Thread(new Runner());
      Thread planetThread = new Thread(new KeepPlanetComing());
      mainThread.start();
      planetThread.start();
    }

    public static void main(String[] args){
      JFrame frame = new JFrame("Physics!!!");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Destroyed mainInstance = new Destroyed();
      frame.setContentPane(mainInstance);
      frame.pack();
      frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.setColor(Color.BLACK);
      g.fillRect(0, 0, WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN);

      world.drawSpheres(g);

      g.setColor(Color.blue);
      g.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
      g.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
      g.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
      g.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);

    }


  }
}
