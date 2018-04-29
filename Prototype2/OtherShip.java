import java.lang.Math.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

public class OtherShip extends Spaceship{
    boolean hasBeenCaptured = false;
    public MyShip motherShip;
    public static LinkedDS<OtherShip> freeShips;
    public static LinkedDS<OtherShip> capturedShips;
    //GradientPaint blueFade;

    public OtherShip(Pair[] vertices, MyShip motherShip){
        super(vertices);
        freeShips.append(this);
        this.motherShip = motherShip;
        //fade = new GradientPaint(0, 0, new Color(0, 0, 255), 0, (int)verticalHeight, new Color(0, 0, 50));
        setGradient();
    }

    protected void setGradient(){
        Pair front = getFront();
        //Pair center = getCentroid();
        fade = new GradientPaint((int)front.x, (int)front.y, new Color(0, 0, 255), (int)center.x, (int)center.y, new Color(0, 0, 50));
    }

    public void captured(){
        hasBeenCaptured = true;
        //parentShip = captor;
        capturedShips.append(this);
        freeShips.remove1(this);
        if(capturedShips.length() == 1){
            attach(motherShip);
        }
        else{
            attach(capturedShips.end.num);
        }
    }

    public void attach(Spaceship leader){
        List<Pair> verticesList = findVertices(leader.spaceship);
        Pair[] vertices = new Pair[verticesList.size()];
        Pair translation = new Pair(leader.getCentroid().x - leader.getFront().x, leader.getCentroid().y - leader.getFront().y);

        for(Pair vertex : vertices){
            vertex = vertex.add(translation);
        }
        super.createPath(vertices);
    }

    public Spaceship findLeader(){
        if(!hasBeenCaptured){
            return null;
        }
        if(capturedShips.length() == 1){
            return motherShip;
        }
        return capturedShips.get(this).previous.num;
    }

   @Override
    public void rotate(double theta, double time){
        if(!hasBeenCaptured){
            return;
        }

        Pair anchor = getFront(); //The front of the ship will be our anchor
        //Pair parentAnchor = capturedShips.get(this).parent.getFront();//Whew!
        Pair parentAnchor = findLeader().getFront();
        Pair centroid = getCentroid();
        double slope = findSlope(anchor, parentAnchor);
        if(isOnLine(centroid, anchor, slope)){
            return;
        }

        AffineTransform transformation = new AffineTransform();
        theta = theta*Math.PI/180;

        boolean frontLocation = anchor.y < centroid.y;
        boolean isBelow = isBelowLine(centroid, anchor, slope);

        //counter-clockwise rotation
        if(frontLocation == isBelow){
            transformation.rotate(theta*time, anchor.x, anchor.y);
        }
        //This final 'if' statement isn't necessary...can be amended later once we're certain things are working

        //clockwise rotation
        if(frontLocation != isBelow){
            transformation.rotate(-theta*time, anchor.x, anchor.y);
        }
        spaceship.transform(transformation);
   }

   public double findSlope(Pair point1, Pair point2){
        return ((point2.y - point1.y) / (point2.x - point1.x));
   }

   public boolean isOnLine(Pair testPoint, Pair linePoint, double slope){
        if(testPoint.y == slope * (testPoint.x - linePoint.x) + linePoint.y){
            return true;
        }
        return false;
   }

   public boolean isBelowLine(Pair testPoint, Pair linePoint, double slope){
        double lineY = slope * (testPoint.x - linePoint.x) + linePoint.y;
        if(linePoint.y > testPoint.y){
            return true;
        }
        return false;
   }
}