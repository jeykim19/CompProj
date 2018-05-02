import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

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

  public Ship(Pair planetPosition){
    super();
    position = planetPosition;

    updateShape();

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

  }



}

class MyShip extends Ship{
  public MyShip (int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    velocity = new Pair(0.0,0.0);



    // the postion of a sphere is its center
    position = new Pair(0.5*width + margin, height+margin-30.0);

    updateShape();

  }

  public void update(World w, double time, char charKeyPressed){
    Pair left = new Pair(-500.0, 0.0);
    Pair right = new Pair(500.0, 0.0);
    Pair down = new Pair(0.0, 500.0);
    Pair up = new Pair(0.0, -500.0);
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


    updateShape();

    rotateShape();



  }

  public void roundScreen(){
    if ((position.x<= margin)||(position.x>= margin+width)){

      position.x = 2*(margin + width/2) - position.x;
    }

    if((position.y <=margin)||(position.y >= margin + height)) {
      position.y = 2*(margin + height/2) - position.y;



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

      return true;

    } else {
      return false;
    }
  }


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

    super();
    position = new Pair(rand.nextDouble()*width + margin,0.0);

    angle = Math.PI/NumAxis*rand.nextInt(NumAxis);
    speed = (double)(diff*100);
    velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);


    int iVertex;
    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

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


  }
  public void drawShape(Graphics2D g2D){

    g2D.setColor(Color.white);
    g2D.draw(myShape);


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

    position = new Pair(rand.nextDouble()*width + margin, -70.0);

    velocity = new Pair(0.0, (double)diff*50);

    updateShape();
  }
  public void drawShape(Graphics2D g2D){

    g2D.setPaint(Color.red);
    g2D.fill(myShape);


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
  int lifeCount = 5;


  int numAsteroids;
  int numBullets;
  int numSpheres;
  int numShips;
  CindyDS<Sphere> asteroids = new CindyDS<Sphere>();
  Planet planet;
  MyShip myShip;
  char charKeyPressed;
  Boolean removingAst = false;
  CindyDS<Sphere> allDebris= new CindyDS<Sphere>();
  CindyDS<Sphere> bullets = new CindyDS<Sphere>();
  CindyDS<Sphere> freeShips = new CindyDS<Sphere>();
  CindyDS<Sphere> capturedShips = new CindyDS<Sphere>();


  public World(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    myShip = new MyShip(width, height, margin, diff);


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




        iClient.update(this,time);

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
      Node<Sphere> iNode = capturedShips.end;

      for (int i = 0; i < numSpheres; i ++){
        Sphere iClient = iNode.client;


        iClient.position = myShip.position.add(new Pair(0.0,myShip.radius*(i+1)*2));

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

        planet = null;

      }
    }

    myShip.update(this,time,charKeyPressed);

  }

  public void updateKey(char charKeyPressed){
    this.charKeyPressed = charKeyPressed;
  }

  public void renewPlanet(){
    planet = new Planet();
    Ship freeShip = new Ship(planet.position);
    freeShip.velocity = planet.velocity;
    freeShips.append(freeShip);

  }

  public void addAsteroid(){
    Asteroid ast = new Asteroid();

    asteroids.append(ast);


  }

  public void shoot(){
    if (charKeyPressed == 'j'){
      Bullet newBullet = new Bullet(myShip.position.x, myShip.position.y, myShip.angle);
      bullets.append(newBullet);


    }

  }

  public void capture(){
    numSpheres = freeShips.length();
    if (numSpheres > 0) {
      Node<Sphere> iNode = freeShips.end;

      for (int i = numSpheres - 1; i > -1; i -- ) {



        Sphere iClient = iNode.client;

        Area myArea = new Area(myShip.myShape);

        Area iArea = new Area(iClient.myShape);
        myArea.intersect(iArea);
        if (!myArea.isEmpty()) {
          capturedShips.append(iNode.client);
          freeShips.remove(i);
          lifeCount += 1;

        }
        iNode = iNode.previous;
      }
    }

  }

  public void checkCollision(){
    numAsteroids = asteroids.length();
    ArrayList<Integer> gonnaCollideAst = new ArrayList<Integer>();
    ArrayList<Integer> gonnaCollideShip = new ArrayList<Integer>();

    if(numAsteroids > 2) {


      Node<Sphere> iNode = asteroids.end;
      for (int i = numAsteroids - 1; i > -1; i --){

        Sphere iAst = iNode.client;





        Node<Sphere> jNode = iNode.previous;

        for (int j = i -1; j > -1; j --){

          Sphere jAst = jNode.client;

          // keep iArea inside the inner loop so it's updated as the inner loop run
          Area iArea = new Area(iAst.myShape);

          Area jArea = new Area(jAst.myShape);
          iArea.intersect(jArea);
          // we want A is false and B is false, which is equivalent to A or B is not true
          if (!((iArea.equals(jArea))|| (iArea.isEmpty()) )) {


            gonnaCollideAst.add(i);
            gonnaCollideAst.add(j);

          }

          jNode = jNode.previous;

        }//end of inside asteorid loop

        numBullets = bullets.length();
        Node<Sphere> kNode = bullets.end;

        if (numBullets > 0) {

          for (int k = numBullets - 1; k > -1; k --){

            Sphere kBul = kNode.client;

            // keep iArea inside the inner loop so it's updated as the inner loop run
            Area iArea = new Area(iAst.myShape);

            Area kArea = new Area(kBul.myShape);
            iArea.intersect(kArea);

            // we want A is false and B is false, which is equivalent to A or B is not true
            if (!iArea.isEmpty()) {
              System.out.println(numAsteroids + " " + i + " " + k);

              System.out.println("gonna collide");

              gonnaCollideAst.add(i);
            }

            kNode = kNode.previous;

          }//end of inside bulllet loop
        }//end of bullet condition

        numShips = capturedShips.length();
        Node<Sphere> lNode = capturedShips.end;

        if (numShips > 0) {

          for (int l = numShips - 1; l > -1; l --){
            System.out.println("ship" + numShips + " " + i + " " + l);

            Sphere lShip = lNode.client;

            // keep iArea inside the inner loop so it's updated as the inner loop run
            Area iArea = new Area(iAst.myShape);

            Area lArea = new Area(lShip.myShape);
            iArea.intersect(lArea);
            // we want A is false and B is false, which is equivalent to A or B is not true
            if (!iArea.isEmpty()) {

              System.out.println(numAsteroids + " " + i + " " + l);

              System.out.println("gonna collide");
              gonnaCollideShip.add(l);

              gonnaCollideAst.add(i);
            }
            lNode = lNode.previous;

          }//end of inside ship loop
        }//end of ship condition

        Area iArea = new Area(iAst.myShape);

        Area myArea = new Area(myShip.myShape);
        iArea.intersect(myArea);
        if (!iArea.isEmpty()) {

          myShip = new MyShip(width, height, margin, diff);
          lifeCount -= 1;

          gonnaCollideAst.add(i);

        }//end of myShip condition



        iNode = iNode.previous;
      }//end of outseid asteroid loop
    }//end of outside asterooid codition





    int[] gonnaCollideAst1 = magicSort(gonnaCollideAst);

    for (int i =  0; i < gonnaCollideAst1.length ; i ++ ) {
      int index = gonnaCollideAst1[gonnaCollideAst1.length - 1 - i];
      Sphere iAsteroid = asteroids.get(index);

      asteroids.remove(index);

      System.out.println("removed" + index);

      for (int j = 0; j < 5 ;j ++ ) {
        Debris ijDebris = new Debris(iAsteroid.position.x,iAsteroid.position.y);
        allDebris.append(ijDebris);
      }
    }//end of removing asteroid loop


    int[] gonnaCollideShip1 = magicSort(gonnaCollideShip);

    for (int i =  0; i < gonnaCollideShip1.length ; i ++ ) {
      int index = gonnaCollideShip1[gonnaCollideShip1.length - 1 - i];
      Sphere iShip = capturedShips.get(index);

      capturedShips.remove(index);



    }//end of removing ship loop





  }// end of checkCollision
  public int[] magicSort(ArrayList<Integer> temAL){
    // temAL -> set -> temAL1 -> temA
    Set<Integer> set = new HashSet<Integer>();

    for (int i =  0; i < temAL.size() ; i ++ ) {
      set.add(temAL.get(i));

    }

    ArrayList<Integer> temAL1 = new ArrayList<Integer>();
    Iterator<Integer> it = set.iterator();
    while(it.hasNext()) {
      int next = it.next();
      temAL1.add(next);
    }

    int[] temA = new int[temAL1.size()];
    for (int i =  0; i < temA.length ; i ++ ) {
      temA[i] = temAL1.get(i).intValue();
    }
    Arrays.sort(temA);
    return temA;

  }





}// end of world

public class CindySpace extends JPanel implements KeyListener{
  public static final int WIDTH = 1024 ;
  public static final int HEIGHT = 768 ;
  public static final int MARGIN = 100;
  public static final int FPS = 60;
  public int diff = 1;
  public static boolean paused=false;
  double diffThreshold = 998;

  World world;
  char charKeyPressed;
  Random rand = new Random();

  class Runner implements Runnable {
    public void run()
    {
      while(true){
        if(paused== false){
          world.updateSpheres(1.0 / (double)FPS);
          world.updateKey(charKeyPressed);
          world.shoot();
          world.capture();

          world.checkCollision();
          incDiff();

          charKeyPressed = 'n';
          if (world.lifeCount==0){
            JOptionPane.showMessageDialog(null, "You don't have any more lives. You lose!");
            System.exit(0);
          }
        }

        repaint();
        try{
          Thread.sleep(1000/FPS);
        }
        catch(InterruptedException e){}
        }

      }

      public void incDiff(){
        double dart = 1000 * rand.nextDouble();
        if (dart > diffThreshold) {
          diff += 1;
          diffThreshold -= 1;
        }
      }


    }

    class KeepPlanetComing implements Runnable{
      public void run()
      {
        while(true){
          if(paused== false){

            world.renewPlanet();
          }
          try{
            Thread.sleep(30000 + (long)(5000*rand.nextDouble()));
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
          if(paused== false){

            if (world.removingAst != true) {
              world.addAsteroid();

            }
          }


          try{
            Thread.sleep(500 + (long)(100*rand.nextDouble()));

          }
          catch(InterruptedException e){
          }
        }
      }
    }




    public void keyPressed(KeyEvent e) {
      char c=e.getKeyChar();
      charKeyPressed = c;
    }
    public void keyReleased(KeyEvent e) {
      char c=e.getKeyChar();
    }


    public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
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
      JFrame frame = new JFrame("Space Adventure");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      String text="Welcome to Space Adventures! Your goal is to rescue the ships on the planets without getting hit by asteroids.You move using w,a,s,d and can shoot the asteroids using j. You are given 5 lives initially but rescuing the other ships on nearby planets will give back 1 life. If you don't rescue them, you will lose a life. When your life counter reaches 0, it's game over. Careful: The asteroids will turn into debris when they are hit/hit each other and while they CANNOT damage you, they CAN serve as distraction. Be viligant and good luck!";

      JTextArea textArea=new JTextArea(text);
      textArea.setColumns(50);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      textArea.setSize(textArea.getPreferredSize().width,1);

      JOptionPane.showMessageDialog(frame, textArea);

      int exit=JOptionPane.showConfirmDialog(null, "Would you like to play?", null,JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

      if (exit!=JOptionPane.YES_OPTION){
        System.exit(1);
      }

      CindySpace mainInstance = new CindySpace();
      frame.setContentPane(mainInstance);
      frame.pack();
      frame.setVisible(true);
      JPanel p=new JPanel();

      JButton pauseButton=new JButton("Pause");
      JButton unpauseButton=new JButton("Resume");
      JButton exitButton=new JButton("Exit");


      exitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          System.exit(1);
        }
      });


      pauseButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          paused=true;
        }
      });
      unpauseButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          paused=false;
          // notify();
        }
      });


      p.add(pauseButton);
      p.add(exitButton);
      p.add(unpauseButton);
      frame.add(p);

    }


    public void paintComponent(Graphics g) {
      Graphics2D g2D = (Graphics2D) g;
      super.paintComponent(g2D);

      g2D.setColor(Color.BLACK);
      g2D.fillRect(0, 0, WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN);

      world.drawSpheres(g2D);

      // this is to cover the death area where non-ships disappear
      g2D.setColor(Color.blue);
      g2D.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
      g2D.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
      g2D.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
      g2D.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);



      g2D.setColor(Color.WHITE);
      g2D.drawString("Lives: "+ String.valueOf(world.lifeCount), 50,50);

    }


  }
