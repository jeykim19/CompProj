public interface crush()
public void drawCrush()

public class agent
Pair position
Pair velocity
public void draw(Graphics g)
public void update(World w, double time)
//update position based on current position and current velocity by physics//
public void setPosition(Pair p)
//set initial position//
public void setVelocity(Pair p)
///change velocity//

class MyShip extends agent implements crush
double life
// total life//
double ammu
// stock of ammunition//
// get life and ammunition replenished when docking on the planet//
@override
public void draw(Graphics g)
@override
public void update(World w, double time, char charkeyPressed)
// control movement with wasd//
public void showLifeAndAm(Graphic g)
//show life and ammunition in the corner//
public void getLifeAndAmmu()
// this should just be called by pickOtherShip()//
public void pickOtherShip()
//compute the proximity between me and a ship on the planet//


class OtherShip extends agent implements crush
OtherShip next;
@override
public void draw(Graphics g)

class Bullet extends agent
@override
public void draw(Graphics g)

class Asteorid extends agent
@override
public void draw(Graphics g)

class Planet extends agent
@override
public void draw(Graphics g)

class World()
Int height
Int width
Int numAsteroid
Int numSpFact
Int numAmFact
Asteroid asteroids[]
MyShip myShip
OtherShip otherShip[]
Planet planet
public World(int initWidth, int initHeight, int Diff)
//instantiate all the agents according to the difficulty level (i.e. deciding the fields above)//
public void drawAgents(Graphic g)
// this calls all the draw method of all agents//
public void updateAgents(double time)
// this calls all the update method of all agents//

public class Game extends JPanel implements Keylistener
public static final int WIDTH
public static final int HEIGHT
public static final int FPS
public static final long startTime = System.nanoTime()
public static long curTime
public static long totalTime = curTime - startTime
public static int DIFF
//Difficulty level increases over time//
public Game()
// instantiate a World; start the runner thread; add Keylistener; repaint; increase difficulty accordingly//
public static void main(String[] args){
// instantiate a JFrame; instantiate a Game//
public void paintComponent(Graphics g)
// call world to draw all agents//
public void increaseDiff()
// increase difficulty by creating more asteroids and making asteroid moving faster//

class Pair()
//Perform numeric operations on Pair//

public class LinkedList()
// create a linked list of OtherShips trailing behind MyShip//
public static MyShip beginning
public void append(OtherShip othership)
//pick other ships up//
public void pop(OtherShip, othership)
//lose the ship and its trailing ships when clashed//
