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
    public static LinkedDS<OtherShip> freeShips = new LinkedDS<OtherShip>();
    public static LinkedDS<OtherShip> capturedShips = new LinkedDS<OtherShip>();
    //GradientPaint blueFade;

    public OtherShip(Pair[] vertices, MyShip motherShip){
        super(vertices);
        //freeShips.append(this);
        this.motherShip = motherShip;
        //freeShips = new LinkedDS<OtherShip>();
        //capturedShips = new LinkedDS<OtherShip>();
        freeShips.append(this);
        //fade = new GradientPaint(0, 0, new Color(0, 0, 255), 0, (int)verticalHeight, new Color(0, 0, 50));
        setGradient();
    }

    protected void setGradient(){
        Pair front = getFront();
        //Pair center = getCentroid();
        fade = new GradientPaint((int)front.x, (int)front.y, new Color(0, 0, 255), (int)center.x, (int)center.y, new Color(0, 0, 50));
    }

    public void moveCaptured(){
        Pair translation = new Pair(findLeader().getCentroid().x - getFront().x, findLeader().getCentroid().y - getFront().y);
        AffineTransform transformation = new AffineTransform();

        transformation.translate(translation.x, translation.y);
        spaceship.transform(transformation);
    }

    public void captured(){
        hasBeenCaptured = true;
        //parentShip = captor;
        //System.out.println(capturedShips.length());
        capturedShips.append(this);
        //System.out.println(capturedShips.length());
        freeShips.remove(this);
        //Something is SERIOUSLY wrong if the length of captured ships is not positive at this point

        ///System.out.println(freeShips.length());
        if(capturedShips.length() == 1){
            //System.out.println(capturedShips.length());
            attach(motherShip);
        }
        else{
            //System.out.println(capturedShips.length());
            attach((Spaceship)capturedShips.end.previous.element); //EDITED FROM DS
        }


    }


    public void attach(Spaceship leader){
        setCentroid();
        leader.setCentroid();
        List<Pair> verticesList = findVertices(leader.spaceship);
        Pair[] vertices = new Pair[verticesList.size()];
        Pair translation = new Pair(leader.getCentroid().x - leader.getFront().x, leader.getCentroid().y - leader.getFront().y);

        moveCaptured();
    }


    public Spaceship findLeader(){
        if(!hasBeenCaptured){
            return null;
        }
        if(this == capturedShips.start.element){
            return motherShip;
        }
        return (Spaceship)capturedShips.get(this).previous.element; //EDITED FROM DS
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

       AffineTransform transformation = new AffineTransform();
       theta = theta*Math.PI/180;

        if(parentAnchor.x == anchor.x){
            if(centroid.x == anchor.x){
                return;
            }
            if(centroid.x > anchor.x){
                transformation.rotate(-theta*time, anchor.x, anchor.y);
            }
            else{
                transformation.rotate(theta*time, anchor.x, anchor.y);
            }
            return;
        }

        double slope = findSlope(anchor, parentAnchor);
        if(isOnLine(centroid, anchor, slope)){
            return;
        }

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

   public void rotate(double time){
        if(!hasBeenCaptured){
            return;
        }
        Pair parentFront = findLeader().getFront();
        Pair parentCentroid = findLeader().getCentroid();
        Pair childCentroid = getCentroid();

       AffineTransform transformation = new AffineTransform();
       //theta = theta*Math.PI/180;
       double rotationAngle = 300;

       boolean crossProd = crossProduct(parentFront, parentCentroid, childCentroid);
       double theta = theta(parentFront, parentCentroid, childCentroid);
       theta = theta*180/Math.PI;

       if(theta > 175 && theta < 185){
           return;
       }

       if(crossProd){
           //System.out.println("crossProd");
           transformation.rotate((rotationAngle/theta)*time, parentCentroid.x, parentCentroid.y);
       }
       if(!crossProd) {
           //System.out.println("noCrossProd");
           transformation.rotate(-(rotationAngle) * time, parentCentroid.x, parentCentroid.y);
       }
       spaceship.transform(transformation);
   }

   public boolean crossProduct(Pair a, Pair b, Pair c){
        double toReturn = (a.x - b.x)*(c.y - b.y) - (a.y - b.y)*(c.x - b.x);
        return toReturn > 0;
   }

   public double theta(Pair a, Pair b, Pair c){
        Pair vector1 = new Pair(a.x - b.x, a.y - b.y);
        Pair vector2 = new Pair(c.x - b.x, c.y - b.y);
        double dotProduct = dotProduct(vector1, vector2);
        double eucDist = euclideanDistance(a, b)*euclideanDistance(b, c);
        return Math.acos(dotProduct / eucDist);
   }

   public double dotProduct(Pair p1, Pair p2){
        return p1.x*p2.x + p1.y*p2.y;
   }

   public double euclideanDistance(Pair p1, Pair p2){
        return Math.sqrt(Math.pow((p2.y - p1.y), 2) + Math.pow((p2.x - p1.x), 2));
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
        if(lineY > testPoint.y){
            return true;
        }
        return false;
   }
}