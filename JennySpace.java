import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math.*;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Color;
//import java.awt.GradientPaint;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;
class World{
    int height;
    int width;
    int margin;
    int diff;

    int numAsteroids;
    Asteroid asteroids[];
    Planet planet;
    Debris crushed[];
    Pair acceleration;

    public World(int initWidth, int initHeight,int initMargin, int initDiff){
        diff = initDiff;
        margin = initMargin;
        width = initWidth;
        height = initHeight;
        numAsteroids = 1; //diff*5
        Random rand=new Random(10);
        int pieces=rand.nextInt();
        crushed=new Debris[5];
        acceleration = new Pair(0.0, 0.0);

        asteroids  = new Asteroid[numAsteroids];

        for (int i = 0; i < numAsteroids; i ++)
        {
            asteroids[i] = new Asteroid(width, height, margin, diff);
        }
        for (int i=0; i< 5; i++){
            crushed[i]=new Debris(width, height, margin, diff,asteroids[0].position.x,asteroids[0].position.y);
        }

        planet = new Planet(width, height, margin, diff);

    }
    // public static int count=20;
    public void drawSpheres(Graphics g){

        // should paint planet first to set it in the back

        if (planet != null) {
            planet.draw(g);

        }

        //if (count>=0){//alter here
        for (int i = 0; i < numAsteroids; i++){
            asteroids[i].draw(g);
        }
        //for (int i=0; i<5; i++){
        //	crushed[i].draw(g);
        // }

    }

    public void drawDebris(Graphics g){
        for (int i=0; i<5; i++){
            crushed[i].draw(g);
        }
    }
    public void updateDebris(double time){
        for (int i=0; i<5; i++){
            crushed[i].update(this,time);
        }
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


    }

    public void renewPlanet(){
        planet = new Planet(width, height, margin, diff);
    }


}
public class JennySpace extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 200;
    public static final int FPS = 60;
    public int diff = 1;
    World world;
    //Sphere allSpheres[];
    MyShip player;


    class Runner implements Runnable{
        public void run()
        {
            while(true){
                world.updateSpheres(1.0/(double)FPS);
                world.updateDebris(1.0 / (double)FPS);
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }

        }

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


    public void keyPressed(KeyEvent e) {
        char c=e.getKeyChar();
        if(c == 'q'){
            player.rotate(45.0, 1.0/(double)FPS);
        }
        if(c == 'e'){
            player.rotate(-45.0, 1.0/(double)FPS);
        }
        if(c == 'w'){
            player.setAcceleration(new Pair(0.0, -200.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
            otherShip1.setAcceleration(new Pair(0.0, -200.0));
            otherShip1.setVelocity(new Pair(0.0, 0.0));
            otherShip1.accelerate(1.0);
            otherShip1.move(1.0 / (double)FPS);
            otherShip2.setAcceleration(new Pair(0.0, -200.0));
            otherShip2.setVelocity(new Pair(0.0, 0.0));
            otherShip2.accelerate(1.0);
            otherShip2.move(1.0 / (double)FPS);
        }
        if(c == 'a'){
            player.setAcceleration(new Pair(-200.0, 0.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
            otherShip1.setAcceleration(new Pair(-200.0, 0.0));
            otherShip1.setVelocity(new Pair(0.0, 0.0));
            otherShip1.accelerate(1.0);
            otherShip1.move(1.0 / (double)FPS);
            otherShip2.setAcceleration(new Pair(-200.0, 0.0));
            otherShip2.setVelocity(new Pair(0.0, 0.0));
            otherShip2.accelerate(1.0);
            otherShip2.move(1.0 / (double)FPS);
        }
        if(c == 's'){
            player.setAcceleration(new Pair(0.0, 200.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
            otherShip1.setAcceleration(new Pair(0.0, 200.0));
            otherShip1.setVelocity(new Pair(0.0, 0.0));
            otherShip1.accelerate(1.0);
            otherShip1.move(1.0 / (double)FPS);
            otherShip2.setAcceleration(new Pair(0.0, 200.0));
            otherShip2.setVelocity(new Pair(0.0, 0.0));
            otherShip2.accelerate(1.0);
            otherShip2.move(1.0 / (double)FPS);
        }
        if(c == 'd'){
            player.setAcceleration(new Pair(200.0, 0.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
            otherShip1.setAcceleration(new Pair(200.0, 0.0));
            otherShip1.setVelocity(new Pair(0.0, 0.0));
            otherShip1.accelerate(1.0);
            otherShip1.move(1.0 / (double)FPS);
            otherShip2.setAcceleration(new Pair(200.0, 0.0));
            otherShip2.setVelocity(new Pair(0.0, 0.0));
            otherShip2.accelerate(1.0);
            otherShip2.move(1.0 / (double)FPS);
        }



    }
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();

	/*
	for(int i = 0; i < allSpheres.length; i++){
	    allSpheres[i].acceleration = new Pair(0.0, 0.0);
	}
	*/

    }


    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(c == 'q'){
            player.rotate(45.0, 1);
        }
        if(c == 'e'){
            player.rotate(-45.0, 1.0/(double)FPS);
        }
        if(c == 'w'){
            player.setAcceleration(new Pair(0.0, -200.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
        }
        if(c == 'a'){
            player.setAcceleration(new Pair(-200.0, 0.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
        }
        if(c == 's'){
            player.setAcceleration(new Pair(0.0, 200.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
        }
        if(c == 'd'){
            player.setAcceleration(new Pair(200.0, 0.0));
            player.setVelocity(new Pair(0.0, 0.0));
            player.accelerate(1.0);
            player.move(1.0 / (double)FPS);
        }


    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public JennySpace(){
        world = new World(WIDTH, HEIGHT, MARGIN, diff);

        //allSpheres = world.spheres;
        //System.out.println("access1");
        Pair[] vertices = {new Pair(230, 200), new Pair(200, 250), new Pair(230, 240), new Pair(260, 250)};

        /*
        for(Pair vertex : vertices){ //this loop doesnt work, for some reason
            vertex = vertex.add(new Pair(200, 200));
        }
        */


        player = new MyShip(vertices);

        player.setCentroid();

        Pair[] vertices1 = new Pair[4];
        Pair[] vertices2 = new Pair[4];
        Pair[] vertices3 = new Pair[4];
        Pair[] vertices4 = new Pair[4];
        for(int i  = 0; i < 4; i++){
            int shipNumber = i;
            switch(i+1){
                case 1: {
                    for (Pair vertex : vertices1) {
                        vertex = vertices[i].add(new Pair(100.0, 100.0));
                    }
                }
                case 2:{
                    for (Pair vertex : vertices1) {
                        vertex = vertices[i].add(new Pair(-100.0, 100.0));
                    }
                }
                case 3:{
                    for (Pair vertex : vertices1) {
                        vertex = vertices[i].add(new Pair(100.0, 1100.0));
                    }
                }
                case 4:{
                    for (Pair vertex : vertices1) {
                        vertex = vertices[i].add(new Pair(-100.0, -100.0));
                    }
                }
            }
        }
        OtherShip otherShip1 = new OtherShip(vertices1, player);
        OtherShip otherShip2 = new OtherShip(vertices2, player);
        OtherShip otherShip3 = new OtherShip(vertices3, player);
        OtherShip otherShip4 = new OtherShip(vertices4, player);

        otherShip1.captured();
        otherShip2.captured();
        /*
        System.out.println(player.findArea(player.findVertices(player.spaceship)));
        for(Pair vertex : vertices){
            System.out.println(vertex.x + " " + vertex.y);
        }
        System.out.println(player.getCentroid().x + " " + player.getCentroid().y);
        */
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH+2*MARGIN,HEIGHT+2*MARGIN));
        Thread mainThread = new Thread(new Runner());
        Thread planetThread=new Thread(new KeepPlanetComing());
        mainThread.start();
        planetThread.start();
        //System.out.println("access1");
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JennySpace mainInstance = new JennySpace();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
        //System.out.println("access main");
    }


    public void paintComponent(Graphics gOri) {

        Crush crush=new Crush();
        Graphics2D g = (Graphics2D) gOri;

        super.paintComponent(g);
        //System.out.println("access");

        g.setPaint(Color.BLACK);
        g.fillRect(0, 0, WIDTH+2*MARGIN, HEIGHT+2*MARGIN);
        world.drawSpheres(g);

        g.setColor(Color.blue);
        g.fillRect(0,0,WIDTH + 2*MARGIN,MARGIN);
        g.fillRect(0,0,MARGIN,HEIGHT + 2*MARGIN);
        g.fillRect(WIDTH + MARGIN,0,MARGIN,HEIGHT + 2*MARGIN);
        g.fillRect(0,HEIGHT + MARGIN,WIDTH + 2*MARGIN,MARGIN);
        player.rotate(45, 1.0/(double)FPS);
        //player.move(1.0 / (double)FPS);
        otherShip1.rotate(45, 1.0 / (double)FPS);
        otherShip2.rotate(45, 1.0 / (double)FPS);

        boolean check;

        if (crush.CrushAS(world.asteroids[0].position,world.asteroids[0].radius,player.findVertices(player.spaceship))==true){
            check=true;
        }
        else{
            check=false;
        }
        if (check==true){
            world.drawDebris(g);
        }

        player.draw(g);


        //world.drawSpheres(g);

    }


}