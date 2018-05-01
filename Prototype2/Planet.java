import java.awt.Color;
import java.awt.Graphics;

class Planet extends Sphere{
  double radius = 170;
  public Planet(int initWidth, int initHeight,int initMargin, int initDiff){
    super(initWidth, initHeight, initMargin, initDiff);
    super.velocity = new Pair(0.0, (double)diff*50);
  }
  public void draw(Graphics g){

    g.setColor(Color.WHITE);
    g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));

  }
}
