import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;

import java.awt.geom.AffineTransform;


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

  public Pair reflect(Pair point){
    x = 2*point.x - x;
    y = 2*point.y - y;
    return new Pair(x,y);
  }
}

class Ship{
  int height;
int width;
int margin;
  Pair position;
  Pair velocity = new Pair(0.0,0.0);
  Pair acceleration = new Pair(0.0,0.0);
  double angle = 0.0;
  double rotation = 0.0;
  double radius = 20.0;
  int diff;
  Shape shipShape;
int[] xs;
int[] ys;


  public Ship(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
        margin = initMargin;
        width = initWidth;
        height = initHeight;
    Random rand = new Random();

    // the postion of a sphere is its center
  position = new Pair(0.5*width + margin, height+margin-30.0);

  this.updateShape();


  }
  public void rotate(){
    AffineTransform at = AffineTransform.getRotateInstance(angle,position.x,position.y);
    shipShape = at.createTransformedShape (shipShape);
  }
  public void update(World w, double time, char charKeyPressed){
    Pair left = new Pair(-200.0, 0.0);
    Pair right = new Pair(200.0, 0.0);
    Pair down = new Pair(0.0, 200.0);
    Pair up = new Pair(0.0, -200.0);
    Pair noAcc = new Pair(0.0, 0.0);
    double leftTurn = -Math.PI/1;
    double rightTurn = Math.PI/1;

      switch (charKeyPressed){

        case 'w': acceleration = up;

  break;
        case 's': acceleration = down;
        break;

        case 'a': acceleration = left;
        break;

        case 'd': acceleration = right;
        break;

        case 'q': rotation = leftTurn;
        break;

        case 'e': rotation = rightTurn;
        break;


        default: acceleration = noAcc;
        rotation = 0.0;

        break;

    }

velocity = velocity.add(acceleration.times(time));
    position = position.add(velocity.times(time));
    angle = angle + rotation * time;

this.updateShape();

  this.rotate();



  }

public void updateShape(){
  xs = new int[]{(int)position.x,(int)(position.x+radius),(int)(position.x+radius),(int)(position.x-radius),(int)(position.x-radius)};

  ys = new int[]{(int)(position.y-radius),(int)position.y,(int)(position.y+radius),(int)(position.y+radius),(int)position.y};

roundScreen();
  Polygon poly = new Polygon(xs,ys,5);
  AffineTransform at = AffineTransform.getRotateInstance(0,position.x,position.y);
  shipShape = at.createTransformedShape (poly);

}

public void roundScreen(){
  if ((position.x<= margin)||(position.x>= margin+width)||
  (position.y <=margin)||(position.y >= margin + height)) {
    Pair screenCenter = new Pair(margin + width/2, margin + height/2);
    position = position.reflect(screenCenter);

  }
}
  public void draw(Graphics2D g2D){
    g2D.setPaint(Color.BLUE);
    g2D.fill(shipShape);
    // g2D.drawPolygon(shipShape.xpoints,shipShape.ypoints,5);

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

  // public void setPosition(Pair p){
  //   position = p;
  // }
  // public void setVelocity(Pair v){
  //   velocity = v;
  // }
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

  public void draw(Graphics2D g2D){

    g2D.setColor(Color.white);
    int iVertex;

    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

    xs[iVertex] = (int)(position.x + Math.cos(angles[iVertex])*dists[iVertex]);
    ys[iVertex] = (int)(position.y + Math.sin(angles[iVertex])*dists[iVertex]);
}
    g2D.drawPolygon(xs,ys,NumVertices);
  }



}

class Planet extends Sphere{
  double radius = 170;
  public Planet(int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    super.velocity = new Pair(0.0, (double)diff*50);
  }
  public void draw(Graphics2D g2D){

    g2D.setColor(Color.red);
    g2D.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));

  }


}
class World{
  int height;
  int width;
  int margin;
  int diff;

  int numAsteroids;
  Asteroid asteroids[];
  Planet planet;
  Ship ship;
char charKeyPressed;

  public World(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    numAsteroids = diff * 5;



    asteroids  = new Asteroid[numAsteroids];

    for (int i = 0; i < numAsteroids; i ++)
    {
      asteroids[i] = new Asteroid(width, height, margin, diff);
    }

    planet = new Planet(width, height, margin, diff);

    ship = new Ship(width, height, margin, diff);

  }

  public void drawSpheres(Graphics2D g2D){

    // should paint planet first to set it in the back

    if (planet != null) {
  planet.draw(g2D);

}
    for (int i = 0; i < numAsteroids; i++){
      asteroids[i].draw(g2D);

    }

ship.draw(g2D);


  }

  public void updateSpheres(double time){


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

    ship.update(this,time,charKeyPressed);

    // System.out.println(charKeyPressed);

  }

  public void updateKey(char charKeyPressed){
    this.charKeyPressed = charKeyPressed;
  }

  public void renewPlanet(){
    planet = new Planet(width, height, margin, diff);
  }



}

public class CindySpace extends JPanel implements KeyListener{
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
        world.updateKey(charKeyPressed);
        world.updateSpheres(1.0 / (double)FPS);
        charKeyPressed = 'n';
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
        world.updateKey(charKeyPressed);
        world.renewPlanet();
        repaint();
        try{
          Thread.sleep(30000/diff);
        }
        catch(InterruptedException e){}
        }
    }
  }


    public void keyPressed(KeyEvent e) {
      char c=e.getKeyChar();
      System.out.println("You pressed down: " + c);
charKeyPressed = c;
    }
    public void keyReleased(KeyEvent e) {
      char c=e.getKeyChar();
      System.out.println("\tYou let go of: " + c);
    }


    public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
      System.out.println("You typed: " + c);
    }
    public void addNotify() {
      super.addNotify();
      requestFocus();
    }

    public CindySpace(){
      world = new World(WIDTH, HEIGHT, MARGIN,diff);
      addKeyListener(this);
      this.setPreferredSize(new Dimension(WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN));
      Thread mainThread = new Thread(new Runner());
      Thread planetThread = new Thread(new KeepPlanetComing());
      mainThread.start();
      planetThread.start();
    }

    public static void main(String[] args){
      JFrame frame = new JFrame("Physics!!!");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      CindySpace mainInstance = new CindySpace();
      frame.setContentPane(mainInstance);
      frame.pack();
      frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
      Graphics2D g2D = (Graphics2D) g;
      super.paintComponent(g2D);

      g2D.setColor(Color.BLACK);
      g2D.fillRect(0, 0, WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN);

      world.drawSpheres(g2D);

      g2D.setColor(Color.blue);
      g2D.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
      g2D.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
      g2D.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
      g2D.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);

    }


  }
