import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Math.*;

class Debris extends Asteroid{
    public Debris(int initWidth, int initHeight, int initMargin, int initDiff, double x, double y){
	
	super (initWidth,initHeight,initMargin,initDiff);
	super.position=new Pair (x,y);
	Random rand=new Random ();
	super. angle = Math.PI/5*rand.nextInt(100);
	super.velocity = new Pair(Math.cos(angle)*speed, Math.sin(angle)*speed);
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
