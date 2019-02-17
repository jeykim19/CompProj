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

public class JustinSpace extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    //World world;
    //Sphere allSpheres[];
    MyShip player;


    class Runner implements Runnable{
        public void run()
        {
            while(true){
                repaint();
                //world.updateSpheres(1.0 / (double)FPS);
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }

        }

    }


    public void keyPressed(KeyEvent e) {
        char c=e.getKeyChar();
        if(c == 'q'){
            player.rotate(-45.0, 1/(double)FPS);
        }
        if(c == 'e'){
            player.rotate(45.0, 1.0/(double)FPS);
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
        System.out.println("You pressed down: " + c);


    }
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();

	/*
	for(int i = 0; i < allSpheres.length; i++){
	    allSpheres[i].acceleration = new Pair(0.0, 0.0);
	}
	*/
        System.out.println("\tYou let go of: " + c);

    }


    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(c == 'q'){
            player.rotate(-45.0, 1/(double)FPS);
        }
        if(c == 'e'){
            player.rotate(45.0, 1.0/(double)FPS);
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
        System.out.println("You typed: " + c);

    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public JustinSpace(){
        //world = new World(WIDTH, HEIGHT, 50);
        //allSpheres = world.spheres;
        //System.out.println("access1");
        Pair[] vertices = {new Pair(230, 200), new Pair(200, 250), new Pair(230, 240), new Pair(260, 250)};
        for(Pair vertex : vertices){ //this loop doesnt work, for some reason
            vertex = vertex.add(new Pair(200, 200));
        }
        player = new MyShip(vertices);
        player.setCentroid();
        /*
        System.out.println(player.findArea(player.findVertices(player.spaceship)));
        for(Pair vertex : vertices){
            System.out.println(vertex.x + " " + vertex.y);
        }
        System.out.println(player.getCentroid().x + " " + player.getCentroid().y);
        */
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
        //System.out.println("access1");
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JustinSpace mainInstance = new JustinSpace();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
        //System.out.println("access main");
    }


    public void paintComponent(Graphics gOri) {
        Graphics2D g = (Graphics2D) gOri;
        super.paintComponent(g);
        //System.out.println("access");

        g.setPaint(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // player.rotate(45, 1/(double)FPS);
        player.move(1.0 / (double)FPS);
        player.draw(g);


        //world.drawSpheres(g);

    }


}
