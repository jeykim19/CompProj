import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;               //added
import javax.swing.*;             //added
import java.awt.event.*;            //added
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
    velocity = new Pair(0.0,0.0);

    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    Random rand = new Random();

    // the postion of a sphere is its center
    position = new Pair(0.5*width + margin, height+margin-30.0);


  }
  public void rotateShape(){
    AffineTransform at = AffineTransform.getRotateInstance(angle,position.x,position.y);
    myShape = at.createTransformedShape (myShape);
  }
  public void update(World w, double time, char charKeyPressed){
    Pair left = new Pair(-300.0, 0.0);
    Pair right = new Pair(300.0, 0.0);
    Pair down = new Pair(0.0, 300.0);
    Pair up = new Pair(0.0, -300.0);
    Pair noAcc = new Pair(0.0, 0.0);
    double leftTurn = -Math.PI/1;
    double rightTurn = Math.PI/1;
    switch (charKeyPressed){

      case 'w': velocity = up;

      break;
      case 's': velocity = down;
      break;

      case 'a': velocity = left;
      break;

      case 'd': velocity = right;
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

  public void updateShape(){
    xs = new int[]{(int)position.x,(int)(position.x+radius),(int)(position.x+radius),(int)(position.x-radius),(int)(position.x-radius)};

    ys = new int[]{(int)(position.y-radius),(int)position.y,(int)(position.y+radius),(int)(position.y+radius),(int)position.y};

    roundScreen();
    Polygon poly = new Polygon(xs,ys,5);
    AffineTransform at = AffineTransform.getRotateInstance(0,position.x,position.y);
    myShape = at.createTransformedShape (poly);

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
  public void drawShape(Graphics2D g2D){
    g2D.setPaint(Color.BLUE);
    g2D.fill(myShape);
    // g2D.drawShapePolygon(myShape.xpoints,myShape.ypoints,5);

  }



}


class Sphere{
  static int height;
  static int width;
  static int margin;
  static int diff;
  Pair position;
  Pair velocity;
  double radius;
  Shape myShape;
  Random rand = new Random();

  public Sphere(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    //myShape= 
    // the postion of a sphere is its center
    //set up myShape to be not null //SA
  }

  public Sphere(){

  }
  public void update(World w, double time){
    position = position.add(velocity.times(time));
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
    //System.out.println(myShape);
	g2D.draw(myShape); //null pointer here!
    // g2D.drawPolygon(xs,ys,NumVertices);
  }

}

class Debris extends Asteroid{
  public Debris(double x, double y){

    super();
    position=new Pair (x,y);
    angle = Math.PI/5*rand.nextInt(100);
    velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);
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
}

 class Bullet extends Asteroid{
    double radius = 5;
    public Bullet(double x, double y, double a){

      super();
      position=new Pair (x,y);
      angle = a - Math.PI/2;
      velocity = new Pair(Math.cos(angle)*1000/diff, Math.sin(angle)*1000/diff);

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
    g2D.fill(myShape); ///null pointer here!

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
  CindyDS<Asteroid> asteroids = new CindyDS<Asteroid>();
  Planet planet;
  Ship ship;
  char charKeyPressed;
  Boolean removingAst = false;
  CindyDS<Debris> allDebris= new CindyDS<Debris>();
  CindyDS<Bullet> bullets = new CindyDS<Bullet>();

  public static int lifeCount=5;
    
  public World(int initWidth, int initHeight,int initMargin, int initDiff){
    diff = initDiff;
    margin = initMargin;
    width = initWidth;
    height = initHeight;
    ship = new Ship(width, height, margin, diff);

    Asteroid ast = new Asteroid();

    asteroids.append(ast);



    planet = new Planet();


  }

  public void drawSpheres(Graphics2D g2D){
   if (planet != null) {
      planet.drawShape(g2D);

    }
    // should paint planet first to set it in the back
    drawList(asteroids, g2D);
    drawList(allDebris, g2D);
    drawList(bullets, g2D);


    ship.drawShape(g2D);


  }

  public void drawList(CindyDS list, Graphics2D g2D){
    if (list.end!=null) {
      numAsteroids = list.length();
      Node<Asteroid> iNode = list.end;
      for (int i = 0; i < numAsteroids; i ++){
        Asteroid iClient = iNode.client;

        // System.out.println("gonna draw");
        // System.out.println(i +""+numAsteroids);
        // System.out.println(iAst.position.x);


        iClient.drawShape(g2D);


        iNode = iNode.previous;
      }
    }
  }

  public void updateList(CindyDS list,double time){
    if (list.end!=null) {
      numAsteroids = list.length();
      Node<Asteroid> iNode = list.end;
      for (int i = 0; i < numAsteroids; i ++){
        Asteroid iClient = iNode.client;

        // System.out.println("gonna draw");
        // System.out.println(i +""+numAsteroids);
        // System.out.println(iAst.position.x);


        iClient.update(this,time);
        // iNode.client.updateShape();

        if (iClient.checkDeath()) {
          int index = numAsteroids - 1 -i ;
          removingAst = true;
          list.remove(index);
          removingAst = false;


        }

        iNode = iNode.previous;
      }
    }
  }

  public void updateSpheres(double time){

    updateList(asteroids,time);
    updateList(allDebris,time);
    updateList(bullets,time);

    if (planet != null) {
      planet.update(this,time);
      if (planet.checkDeath()) {
        // if ((margin - planet.position.x >= planet.radius) ||(planet.position.x - (width+margin) >= planet.radius)
        // || (planet.position.y - (height + margin) >= planet.radius)){
        planet = null;

      }
    }

    ship.update(this,time,charKeyPressed);


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

  public void updateKey(char charKeyPressed){
    this.charKeyPressed = charKeyPressed;
  }

  public void renewPlanet(){
    planet = new Planet();
  }

  public void addAsteroid(){
    Asteroid ast = new Asteroid();

    asteroids.append(ast);


    // System.out.println("add");
  }

  public void shoot(){
    if (charKeyPressed == 'j'){
    Bullet newBullet = new Bullet(ship.position.x, ship.position.y, ship.angle);
      bullets.append(newBullet);


    }

  }

  public void checkCollision(){
    numAsteroids = asteroids.length();
    ArrayList<Integer> gonnaCollide = new ArrayList<Integer>();

    if ((numAsteroids > 1)&&(asteroids.end!=null)) {

      // Don't start with the end because the end might have been just added withouut its shpaed being updated
      Node<Asteroid> iNode = asteroids.end.previous;
      for (int i = 1; i < numAsteroids; i ++){
        int iIndex = numAsteroids - 1 -i ;

        Asteroid iAst = iNode.client;


        // System.out.println(iAst.position.x);
        // System.out.println(iAst.myShape);

	
        Node<Asteroid> jNode = asteroids.end.previous;
	//check to see if null //SA
	if (jNode!=null){
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
	    //lifeCount=lifeCount+1;
	    lifeCount=lifeCount-1;
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
 
    
    int diff = 2;                 //jk
    int diff1=2;                  //jk
  World world;
  char charKeyPressed;
    Random rand=new Random ();      //jk
    double i=998.0;                    //jk
    public static boolean paused=false;       //jk
    
  class Runner implements Runnable {

    public void run()
    {
	while(true){
        // world.updateGravity(charKeyPressed);
	    if (paused!=true){ //jk
		world.updateKey(charKeyPressed);
		world.shoot();
		world.updateSpheres(1.0 / (double)FPS);
        // System.out.println(world.removingAst);    
        world.checkCollision();
	double j= 0.0+(1000.0-0.0)*rand.nextDouble(); //jk
        if (j>i){                       //jk
	    diff=diff+1;                  //jk
	    i=i-1;                        //jk
	}                                  //jk
	charKeyPressed = 'n';

	    }                          //jk
	   
           repaint();

	   try {
	       Thread.sleep(1000/FPS);
	       }
           catch (InterruptedException e){

        	 }
	
	  
	}
      
    }
     
  }

    class KeepPlanetComing implements Runnable{
      public void run()
      {
        while(true){
	    diff=2;                       //jk
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
      //System.out.println("You pressed down: " + c); //jk
      charKeyPressed = c;
    }
    public void keyReleased(KeyEvent e) {
      char c=e.getKeyChar();
      // System.out.println("\tYou let go of: " + c); //jk
    }


    public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
      //System.out.println("You typed: " + c); //jk
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
	JFrame frame = new JFrame("Space Adventure");           //jk changed name to spaceadventure
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      String text="Welcome to Space Adventures! Your goal is to rescue the ships on the planets without getting hit by asteroids.You move using w,a,s,d and can shoot the asteroids using j. You are given 5 lives initially but rescuing the other ships on nearby planets will give back 1 life. If you don't rescue them, you will lose a life. When your life counter reaches 0, it's game over. Careful: The asteroids will turn into debris when they are hit/hit each other and while they CANNOT damage you, they CAN serve as distraction. Be viligant and good luck!"; //jk
      
      JTextArea textArea=new JTextArea(text);            //jk
      textArea.setColumns(50);                     //jk
      textArea.setLineWrap(true);               //jk
      textArea.setWrapStyleWord(true);          //jk
      textArea.setSize(textArea.getPreferredSize().width,1);      //jk
      
      JOptionPane.showMessageDialog(frame, textArea);     //jk
      
      int exit=JOptionPane.showConfirmDialog(null, "Would you like to play?", null,JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);           //jk

      if (exit!=JOptionPane.YES_OPTION){        //jk
      	  System.exit(1);          //jk
      }                 //jk
       
      CindySpace mainInstance = new CindySpace();
      frame.setContentPane(mainInstance);
      frame.pack();
      frame.setVisible(true);
      JPanel p=new JPanel();                               //jk
     
      JButton pauseButton=new JButton("Pause");           //jk
      JButton unpauseButton=new JButton("Unpause");      //jk
      JButton exitButton=new JButton("Exit");            //jk

      //everything from here
      
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
		   notify();
	       }
	   });
      
      
      p.add(pauseButton);
      p.add(exitButton);
      p.add(unpauseButton);
      frame.add(p);

      //to here
    }

    // public void startTask(){
    //	timer.schedule(new Runner(),0);
    //}


    
    public void paintComponent(Graphics g) {
      Graphics2D g2D = (Graphics2D) g;
      super.paintComponent(g2D);

      g2D.setColor(Color.BLACK);
      g2D.fillRect(0, 0, WIDTH+ 2*MARGIN, HEIGHT+ 2*MARGIN);

      world.drawSpheres(g2D);
      //String lifeCount=new String(world.lifeCount);
    
      g2D.setColor(Color.blue);
      g2D.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
      g2D.setColor(Color.WHITE);           //starting here
      g2D.drawString("Lives: "+ String.valueOf(world.lifeCount), 50,50);
      if (world.lifeCount==0){
	      JOptionPane.showMessageDialog(null, "You don't have any more lives. You lose!");
	      System.exit(1);
	  }
      //to here
      
      // g2D.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
      // g2D.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
      // g2D.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);

    }


  }
