import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Math.*;

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

  public void draw(Graphics g){

    g.setColor(Color.white);
    int iVertex;

    for (iVertex = 0 ;iVertex < NumVertices ;iVertex ++ ) {

    xs[iVertex] = (int)(position.x + Math.cos(angles[iVertex])*dists[iVertex]);
    ys[iVertex] = (int)(position.y + Math.sin(angles[iVertex])*dists[iVertex]);
}
    g.drawPolygon(xs,ys,NumVertices);
  }
 


}
