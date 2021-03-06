import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Keyboard extends JPanel implements KeyListener{
    //public static final int WIDTH = 1024;
    //public static final int HEIGHT = 768;
    //public static final int FPS = 60;
    World world;
    boolean key1=false;
    boolean key2=false;
    boolean key3=false;
    boolean key4=false;

class Runner implements Runnable{
	public void run()
	{
	    while(true){
		world.update(1.0 / (double)FPS);
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

    if (c=='w'){
		  world.MyShip.velocity.x=0.0;
		  world.MyShip.velocity.y=-200.0;
	    key1=true;
	    }
	  if (c=='s'){
	    int i=0;
		  world.MyShip.velocity.x=0.0;
		  world.MyShip.velocity.y=200.0;
	    key3=true;
	    }
	  if (c=='a'){
		  world.MyShip.velocity.x=-200.0;
		  world.MyShip.velocity.y=0.0;
	   key4=true;
	}
	if (c=='d'){
		world.MyShip.velocity.x=200.0;
		world.MyShip.velocity.y=0.0;
	  key2=true;

	}
	if ((key1==true)&&(key2==true)){
		world.MyShip.velocity.x=200.0;
		world.MyShip.velocity.y=-200.0;
	}
	if ((key3==true)&&(key2==true)){
		world.MyShip.velocity.x=200.0;
		world.MyShip.velocity.y=200.0;
	}
	if ((key4==true)&&(key1==true)){
		world.MyShip.velocity.x=-200.0;
		world.MyShip.velocity.y=-200.0;
	}
	if ((key4==true)&&(key3==true)){
		world.MyShip.velocity.x=-200.0;
		world.MyShip.velocity.y=200.0;
  }
}
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();

      	if ((c=='w')||(c=='s')||(c=='a')||(c=='d')){
	      key4=false;
	      key1=false;
	      key2=false;
	      key3=false;
	      int i=0;
		    world.MyShip.velocity.x=0.0;
		    world.MyShip.velocity.y=0.0;
	}

    }

    public void keyTyped(KeyEvent e) {
	char c = e.getKeyChar();
	System.out.println("You typed: " + c);
    }
     public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Keyboard(){
	world = new World(WIDTH, HEIGHT, 50);
	addKeyListener(this);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
	mainThread.start();
    }


public static void main(String[] args){     //needs to be added into main class
	JFrame frame = new JFrame("Space Adventure");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Keyboard mainInstance = new Keyboard();
	frame.setContentPane(mainInstance);
	frame.pack();
	frame.setVisible(true);
    }

  }
