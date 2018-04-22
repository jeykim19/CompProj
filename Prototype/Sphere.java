import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Math.*;
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
