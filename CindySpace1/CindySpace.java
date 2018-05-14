import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;



import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;



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

class Ship extends Sphere{
  Pair acceleration = new Pair(0.0,0.0);
  double angle = 0.0;
  double rotation = 0.0;
  double radius = 20.0;
  int[] xs;
  int[] ys;


  public Ship (int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);




  }

  public Ship(){
    super();
  }
  public void rotateShape(){
    AffineTransform at = AffineTransform.getRotateInstance(angle,position.x,position.y);
    myShape = at.createTransformedShape (myShape);
  }
  public void update(World w, double time){

    velocity = velocity.add(acceleration.times(time));
    position = position.add(velocity.times(time));
    angle = angle + rotation * time;


    updateShape();

    rotateShape();



  }



  public void updateShape(){
    xs = new int[]{(int)position.x,(int)(position.x+radius),(int)(position.x+radius),(int)(position.x-radius),(int)(position.x-radius)};

    ys = new int[]{(int)(position.y-radius),(int)position.y,(int)(position.y+radius),(int)(position.y+radius),(int)position.y};

    Polygon poly = new Polygon(xs,ys,5);
    AffineTransform at = AffineTransform.getRotateInstance(0,position.x,position.y);
    myShape = at.createTransformedShape (poly);

  }


  public void drawShape(Graphics2D g2D){
    g2D.setPaint(Color.BLUE);
    g2D.fill(myShape);
    // g2D.drawShapePolygon(myShape.xpoints,myShape.ypoints,5);

  }



}

class MyShip extends Ship{
  public MyShip (int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    velocity = new Pair(0.0,0.0);

    // diff = initDiff;
    // margin = initMargin;
    // width = initWidth;
    // height = initHeight;
    // Random rand = new Random();

    // the postion of a sphere is its center
    acceleration=new Pair(1.0,1.0);
    position = new Pair(0.5*width + margin, height+margin-30.0);
  }

  public void update(World w, double time){
    velocity = velocity.add(acceleration.times(time));
    position = position.add(velocity.times(time));
    angle = angle + rotation * time;


    updateShape();

    rotateShape();



  }

  public void roundScreen(){
    if ((position.x<= margin)||(position.x>= margin+width)){

      position.x = 2*(margin + width/2) - position.x;
    }

    if((position.y <=margin)||(position.y >= margin + height)) {
      position.y = 2*(margin + height/2) - position.y;


      // Pair screenCenter = new Pair(margin + width/2, margin + height/2);
      // position = position.reflect(screenCenter);

    }
  }

  public void updateShape(){
    xs = new int[]{(int)position.x,(int)(position.x+radius),(int)(position.x+radius),(int)(position.x-radius),(int)(position.x-radius)};

    ys = new int[]{(int)(position.y-radius),(int)position.y,(int)(position.y+radius),(int)(position.y+radius),(int)position.y};

    roundScreen();
    Polygon poly = new Polygon(xs,ys,5);
    AffineTransform at = AffineTransform.getRotateInstance(0,position.x,position.y);
    myShape = at.createTransformedShape (poly);

  }

}
class Sphere{
  static int height;
  static int width;
  static int margin;
  static int diff;
  Pair position;
  Pair velocity;
  Pair acceleration;
  double radius;
  Shape myShape;
  Random rand = new Random();

  public Sphere(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;

    // the postion of a sphere is its center

  }

  public Sphere(){

  }
  public void update(World w, double time){
    position = position.add(velocity.times(time));
  }
  public void drawShape(Graphics2D g2D){

  }

  public void updateShape(){

  }

  public boolean checkDeath(){
    if ((margin - position.x >= radius) ||(position.x - (width+margin) >= radius)
    || (position.y - (height + margin) >= radius)){
      // if ((margin - iAst.position.x >= iAst.radius) ||(iAst.position.x - (width+margin) >= iAst.radius)
      //     || (iAst.position.y - (height + margin) >= iAst.radius)){
      // removingAst = true;
      // // System.out.println("gonna remove");
      // int index = numAsteroids - 1 -i ;
      // // System.out.println(index + "" + numAsteroids);
      //
      // asteroids.remove(index);
      // removingAst = false;
      //
      // // iNode = new Asteroid(width, height, margin, diff);
      // // System.out.println("removed one");
      // numAsteroids = asteroids.length();

      // System.out.println(+numAsteroids);
      return true;

    } else {
      return false;
    }
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

  public Asteroid(){
    // public Asteroid(int initWidth, int initHeight,int initMargin, int initDiff){

    // super(initWidth, initHeight, initMargin, initDiff);
    super();
    position = new Pair(rand.nextDouble()*width + margin,0.0);

    angle = Math.PI/NumAxis*rand.nextInt(NumAxis);
    speed = (double)(diff*100);
    velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);


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
    updateShape();

  }

  public void updateShape(){
    int iVertex;

    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

      xs[iVertex] = (int)(position.x + Math.cos(angles[iVertex])*dists[iVertex]);
      ys[iVertex] = (int)(position.y + Math.sin(angles[iVertex])*dists[iVertex]);
    }

    Polygon poly = new Polygon(xs,ys,7);
    AffineTransform at = AffineTransform.getRotateInstance(0,position.x,position.y);
    myShape = at.createTransformedShape (poly);

  }

  public void update(World w, double time){
    position = position.add(velocity.times(time));
    updateShape();
    // System.out.println("updated shape");


  }
  public void drawShape(Graphics2D g2D){

    g2D.setColor(Color.white);
    g2D.draw(myShape);


    // g2D.drawPolygon(xs,ys,NumVertices);
  }

}

class Debris extends Asteroid{
  public Debris(double x, double y){

    super();
    position=new Pair (x,y);
    angle = Math.PI/5*rand.nextInt(100);
    velocity = new Pair(Math.cos(angle)*speed*2, Math.sin(angle)*speed*2);
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
    updateShape();

  }

}

class Bullet extends Asteroid{
  double radius = 5;
  public Bullet(double x, double y, double a){

    super();
    position=new Pair (x,y);
    angle = a - Math.PI/2;
    velocity = new Pair(Math.cos(angle)*1000/diff, Math.sin(angle)*1000/diff);

    updateShape();

  }
  public void drawShape(Graphics2D g2D){

    g2D.setPaint(Color.red);
    g2D.fill(myShape);

    // g2D.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));

  }

  public void updateShape(){
    myShape = new Ellipse2D.Double(position.x-radius, position.y-radius, radius*2, radius*2);

  }

  public void update(World w, double time){
    position = position.add(velocity.times(time));
    updateShape();
  }



}

class Planet extends Sphere{
  // planet radius should always be less than margin so it doesn't suddely appear in the space
  double radius = 170 ;
  public Planet(){
    super();
    // public Planet(int initWidth, int initHeight,int initMargin, int initDiff){
    //   super(initWidth, initHeight, initMargin, initDiff);
    position = new Pair(rand.nextDouble()*width + margin, -70.0);

    velocity = new Pair(0.0, (double)diff*50);
  }
  public void drawShape(Graphics2D g2D){

    g2D.setPaint(Color.red);
    g2D.fill(myShape);

    // g2D.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));

  }

  public void updateShape(){
    myShape = new Ellipse2D.Double(position.x-radius, position.y-radius, radius*2, radius*2);

  }

  public void update(World w, double time){
    position = position.add(velocity.times(time));
    updateShape();
  }


}
class World{
  int height;
  int width;
  int margin;
  int diff;

  int numAsteroids;
  int numBullets;
  int numSpheres;
  CindyDS<Asteroid> asteroids = new CindyDS<Asteroid>();
  Planet planet;
  MyShip myShip;
  //char charKeyPressed;   //delete
  //char charKeyReleased;   //delete
  Boolean removingAst = false;
  CindyDS<Debris> allDebris= new CindyDS<Debris>();
  CindyDS<Bullet> bullets = new CindyDS<Bullet>();
  CindyDS<Sphere> freeShips = new CindyDS<Sphere>();
  CindyDS<Sphere> capturedShips = new CindyDS<Sphere>();


  public World(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    myShip = new MyShip(width, height, margin, diff);

    // capturedShips.append(myShip);

    Asteroid ast = new Asteroid();

    asteroids.append(ast);



    planet = new Planet();


  }

  public void drawSpheres(Graphics2D g2D){

    // should paint planet first to set it in the back
    if (planet != null) {
      planet.drawShape(g2D);

    }
    drawList(asteroids, g2D);
    drawList(allDebris, g2D);
    drawList(bullets, g2D);
    drawList(freeShips,g2D);
    drawList(capturedShips,g2D);






    myShip.drawShape(g2D);


  }

  public void drawList(CindyDS list, Graphics2D g2D){
    numSpheres = list.length();
    if (numSpheres > 0) {
      Node<Sphere> iNode = list.end;
      for (int i = 0; i < numSpheres; i ++){
        Sphere iClient = iNode.client;

        //System.out.println("gonna draw");
        // System.out.println(i +""+numSpheres);
        // System.out.println(iAst.position.x);


        iClient.drawShape(g2D);


        iNode = iNode.previous;
      }
    }
  }

  public void updateList(CindyDS list,double time){
    if (list.end!=null) {
      numSpheres = list.length();
      Node<Sphere> iNode = list.end;
      for (int i = 0; i < numSpheres; i ++){
        Sphere iClient = iNode.client;

        // System.out.println("gonna update");
        // System.out.println(i +""+numSpheres);
        // System.out.println(iAst.position.x);


        iClient.update(this,time);
        // iNode.client.updateShape();

        if (iClient.checkDeath()) {
          int index = numSpheres - 1 -i ;
          removingAst = true;
          list.remove(index);
          removingAst = false;


        }

        iNode = iNode.previous;
      }
    }
  }

  public void updateCapturedShips(double time){
    numSpheres = capturedShips.length();
    if (numSpheres > 0) {
      // Node<Sphere> iNodePre = capturedShips.end.previous;
      Node<Sphere> iNode = capturedShips.end;

      for (int i = 0; i < numSpheres; i ++){
        // Sphere iClinetPre = iNodePre.client;
        Sphere iClient = iNode.client;

        //System.out.println("captured");
        //System.out.println(i +""+numSpheres);
        // System.out.println(iAst.position.x);
        iClient.position = myShip.position.add(new Pair(myShip.radius*(i+1)*6,0.0));
        //System.out.println(iClient.position.x + " " + iClient.position.y);
        //System.out.println(myShip.position.x + " " + myShip.position.y);

        // iClient.velocity = iClinetPre.velocity;
        // iClient.acceleration = iClinetPre.acceleration;

        // iClient.update(this,time);
        iClient.updateShape();






        iNode = iNode.previous;
      }
    }

  }


  public void updateSpheres(double time){

    updateList(asteroids,time);
    updateList(allDebris,time);
    updateList(bullets,time);
    updateList(freeShips,time);
    updateCapturedShips(time);

    if (planet != null) {
      planet.update(this,time);
      if (planet.checkDeath()) {
        // if ((margin - planet.position.x >= planet.radius) ||(planet.position.x - (width+margin) >= planet.radius)
        // || (planet.position.y - (height + margin) >= planet.radius)){
        planet = null;

      }
    }

    myShip.update(this,time); //delete charkeypressed variable
    //System.out.println("updateSpheres" + myShip.position.x + " " + myShip.position.y);


    // for (int i = 0; i < numAsteroids; i ++){
    //   for (int j = i +1 ; i < numAsteroids; i ++){
    // Area areaI = new Area(iNode.myShape);
    // Area areaJ= new Area(asteroids[j].myShape);
    // areaI.intersect(areaJ);
    // if (areaI.isEmpty()== false) {
    //   iNode.collide();
    //   asteroids[j].collide();
    //
    //
    // }
    // }
    //     // System.out.println(charKeyPressed);
    //
  }

  public void renewPlanet(){
    planet = new Planet();
    Ship freeShip = new Ship();
    freeShip.position = planet.position;
    freeShip.velocity = planet.velocity;
    freeShips.append(freeShip);

  }

  public void addAsteroid(){
    Asteroid ast = new Asteroid();

    asteroids.append(ast);


    // System.out.println("add");
  }

  /*public void shoot(){      //delete full method
    if (charKeyPressed == 'j'){
      Bullet newBullet = new Bullet(myShip.position.x, myShip.position.y, myShip.angle);
      bullets.append(newBullet);
    }
    if (charKeyReleased=='j'){

    }

  }*/

  public void capture(){
    numSpheres = freeShips.length();
if (numSpheres > 0) {
  Node<Sphere> iNode = freeShips.end;

  for (int i = 0; i < numSpheres; i ++ ) {
    int iIndex = numSpheres- 1 -i ;
    //System.out.println(iIndex + " " + numSpheres);


    Sphere iClient = iNode.client;
    // System.out.println(iClient.position.x + " " + iClient.position.y);

    Area myArea = new Area(myShip.myShape);

    Area iArea = new Area(iClient.myShape);
    myArea.intersect(iArea);
    if (!myArea.isEmpty()) {
      capturedShips.append(iNode.client);
      freeShips.remove(iIndex);
      //System.out.println("catch");

    }
    iNode = iNode.previous;
  }
}

  }

  public void checkCollision(){
    numAsteroids = asteroids.length();
    ArrayList<Integer> gonnaCollide = new ArrayList<Integer>();

    if (numAsteroids > 2) {

      // Don't start with the end because the end might have been just added withouut its shpaed being updated
      Node<Asteroid> iNode = asteroids.end.previous;
      for (int i = 1; i < numAsteroids; i ++){
        int iIndex = numAsteroids - 1 -i ;

        Asteroid iAst = iNode.client;


        // System.out.println(iAst.position.x);
        // System.out.println(iAst.myShape);


        Node<Asteroid> jNode = asteroids.end.previous;

        for (int j = 1; j < numAsteroids; j ++){
          int jIndex = numAsteroids - 1 -j ;

          Asteroid jAst = jNode.client;

          // keep iArea inside the inner loop so it's updated as the inner loop run
          Area iArea = new Area(iAst.myShape);

          Area jArea = new Area(jAst.myShape);
          iArea.intersect(jArea);
          // System.out.println(iArea.isEmpty() + " "+ (iArea.equals(jArea)) );
          // System.out.println(numAsteroids + " " + iIndex + " " + jIndex);
          // we want A is false and B is false, which is equivalent to A or B is not true
          if (!((iArea.equals(jArea))|| (iArea.isEmpty()) )) {
            // System.out.println("gonna collide");

            gonnaCollide.add(iIndex);
            gonnaCollide.add(jIndex);

          }

          jNode = jNode.previous;

        }

        numBullets = bullets.length();
        Node<Bullet> kNode = bullets.end;

        if ((numBullets > 1)&&(bullets.end!=null)) {

          for (int k = 1; k < numBullets; k ++){
            int kIndex = numBullets - 1 -k ;

            Asteroid kAst = kNode.client;

            // keep iArea inside the inner loop so it's updated as the inner loop run
            Area iArea = new Area(iAst.myShape);

            Area kArea = new Area(kAst.myShape);
            iArea.intersect(kArea);
            // System.out.println(iArea.isEmpty() + " "+ (iArea.equals(kArea)) );
            // System.out.println(numAsteroids + " " + iIndex + " " + kIndex);
            // we want A is false and B is false, which is equivalent to A or B is not true
            if (!((iArea.equals(kArea))|| (iArea.isEmpty()) )) {
              // System.out.println("gonna collide");

              gonnaCollide.add(iIndex);
            }
            kNode = kNode.previous;

          }

        }


        iNode = iNode.previous;
      }
    }
    Set<Integer> set = new HashSet<Integer>();

    for (int i =  0; i < gonnaCollide.size() ; i ++ ) {
      set.add(gonnaCollide.get(i));

    }

    ArrayList<Integer> gonnaCollide1 = new ArrayList<Integer>();
    Iterator<Integer> it = set.iterator();
    while(it.hasNext()) {
      int next = it.next();
      gonnaCollide1.add(next);
    }

    int[] gonnaCollide2 = new int[gonnaCollide1.size()];
    for (int i =  0; i < gonnaCollide2.length ; i ++ ) {
      gonnaCollide2[i] = gonnaCollide1.get(i).intValue();
    }
    Arrays.sort(gonnaCollide2);

    for (int i =  0; i < gonnaCollide2.length ; i ++ ) {
      int index = gonnaCollide2[gonnaCollide2.length - 1 - i];
      Asteroid iAsteroid = asteroids.get(index);

      asteroids.remove(index);
      // System.out.println(gonnaCollide.toString());
      // System.out.println(gonnaCollide1.toString());
      // System.out.println(Arrays.toString(gonnaCollide2));
      //
      // System.out.println("removed" + index);

      for (int j = 0; j < 5 ;j ++ ) {
        Debris ijDebris = new Debris(iAsteroid.position.x,iAsteroid.position.y);
        allDebris.append(ijDebris);
      }

    }



  }


}

public class CindySpace extends JPanel implements KeyListener{
  public static final int WIDTH = 1024 ;
  public static final int HEIGHT = 768 ;
  public static final int MARGIN = 100;
  public static final int FPS = 60;
  public int diff = 1;
  World world;
  boolean key1=false;            //from here
    boolean key2=false;
    boolean key3=false;
    boolean key4=false;
    boolean key5=false;
    int i=0;                  //to here is changed

  class Runner implements Runnable {
    public void run()
    {
      while(true){
        // world.updateGravity(charKeyPressed);
        world.updateSpheres(1.0 / (double)FPS);
        //world.updateKey(charKeyPressed);     //jk
        //world.updateKey1(charKeyReleased); ///jk
        //world.shoot();
        world.capture();

        // System.out.println(world.removingAst);
        world.checkCollision();

      //  charKeyPressed = 'n';   //jk
      //  charKeyPressed = 'm';   //jk
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
          try{
            Thread.sleep(30000/diff);
          }
          catch(InterruptedException e){
          }
        }
      }
    }

    class KeepAsteroidsComing implements Runnable{
      public void run()
      {

        while(true){
          if (world.removingAst != true) {
            world.addAsteroid();

          }


          try{
            Thread.sleep(500/diff);

          }
          catch(InterruptedException e){
          }
        }
      }
    }



    public void keyPressed(KeyEvent e) {
      char c=e.getKeyChar();

      //System.out.println("pressed");
      //System.out.println("You pressed down: " + c); //from here
      if (c=='w'){
     world.myShip.velocity.x=0.0;
     world.myShip.velocity.y=-200.0;
     key1=true;
     }
   if (c=='s'){
     world.myShip.velocity.x=0.0;
     world.myShip.velocity.y=200.0;
     key3=true;
     }
   if (c=='a'){
     world.myShip.velocity.x=-200.0;
     world.myShip.velocity.y=0.0;
    key4=true;
 }
 if (c=='d'){
   world.myShip.velocity.x=200.0;
   world.myShip.velocity.y=0.0;
   key2=true;
 }
 if (c=='j'){
   if (i!=1){
   Bullet newBullet = new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
   world.bullets.append(newBullet);
   i=1;
 }
   key5=true;
 }
 if (c=='q'){
   world.myShip.rotation=-Math.PI*2;
 }
 if (c=='e'){
   world.myShip.rotation=Math.PI*2;
 }
 if ((key1==true)&&(key2==true)){
   world.myShip.velocity.x=200.0;
   world.myShip.velocity.y=-200.0;
 }
 if ((key3==true)&&(key2==true)){
   world.myShip.velocity.x=200.0;
   world.myShip.velocity.y=200.0;
 }
 if ((key4==true)&&(key1==true)){
   world.myShip.velocity.x=-200.0;
   world.myShip.velocity.y=-200.0;
 }
 if ((key4==true)&&(key3==true)){
   world.myShip.velocity.x=-200.0;
   world.myShip.velocity.y=200.0;
 }
 if ((key1==true)&&(key5==true)){
   world.myShip.velocity.x=0.0;
   world.myShip.velocity.y=-200.0;
   if (i!=1){
   Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
   world.bullets.append(newBullet);
   i=1;
 }
 }
 if ((key2==true)&&(key5==true)){
   world.myShip.velocity.x=200.0;
   world.myShip.velocity.y=0.0;
   if (i!=1){
   Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
   world.bullets.append(newBullet);
   i=1;
 }
 }
 if ((key3==true)&&(key5==true)){
   world.myShip.velocity.x=0.0;
   world.myShip.velocity.y=200.0;
   if (i!=1){
     Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
   world.bullets.append(newBullet);
   i=1;
 }
 }
 if ((key4==true)&&(key5==true)){
   world.myShip.velocity.x=-200.0;
   world.myShip.velocity.y=0.0;
   if (i!=1){
   Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
   world.bullets.append(newBullet);
   i=1;
 }
}
if ((key1==true)&&(key2==true)&&(key5==true)){
  world.myShip.velocity.x=200.0;
  world.myShip.velocity.y=-200.0;
  if (i!=1){
  Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
  world.bullets.append(newBullet);
  i=1;
}
}
if ((key3==true)&&(key2==true)&&(key5==true)){
  world.myShip.velocity.x=200.0;
  world.myShip.velocity.y=200.0;
  if (i!=1){
  Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
  world.bullets.append(newBullet);
  i=1;
}
}
if ((key4==true)&&(key1==true)&&(key5==true)){
  world.myShip.velocity.x=-200.0;
  world.myShip.velocity.y=-200.0;
  if (i!=1){
  Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
  world.bullets.append(newBullet);
  i=1;
}
}
if ((key4==true)&&(key3==true)&&(key5==true)){
  world.myShip.velocity.x=-200.0;
  world.myShip.velocity.y=200.0;
  if (i!=1){
  Bullet newBullet=new Bullet(world.myShip.position.x, world.myShip.position.y, world.myShip.angle);
  world.bullets.append(newBullet);
  i=1;
}
} //till here
}
    public void keyReleased(KeyEvent e) {
      char c=e.getKeyChar(); //from here
        if ((c=='w')||(c=='s')||(c=='a')||(c=='d')){
  	      key4=false;
  	      key1=false;
  	      key2=false;
  	      key3=false;
  		    //world.myShip.velocity.x=0.0;
  		    //world.myShip.velocity.y=0.0;
        }
        if (c=='j'){
          i=0;
          key5=false;
        }
        if (c=='q'){
          world.myShip.rotation=0.0;
        }
        if (c=='e'){
          world.myShip.rotation=0.0;
        }                     //to here
      // System.out.println("You let go of: " + c);
    }


    public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
      // System.out.println("You typed: " + c);
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
      Thread asteroidThread = new Thread(new KeepAsteroidsComing());

      mainThread.start();
      planetThread.start();
      asteroidThread.start();
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

      // g2D.setColor(Color.blue);
      // g2D.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
      // g2D.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
      // g2D.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
      // g2D.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);

    }


  }
