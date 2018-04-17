import java.lang.Math;
import java.awt.Graphics;
import java.awt.geom;
import java.util.List;

public class Spaceship implements Movement{
    //public Pair[] vertices;
    public Path2D.Double spaceship;
    //public Pair top;
    //public Pair bottom;
    public double verticalHeight;
    public Pair center;
    Pair velocity;
    Pair acceleration;
    public static final double maxVelocity;

    public Spaceship(Pair[] vertices){
        //this.vertices = vertices;
        createPath(vertices);
        //this.top = vertices[0];
        //this.bottom = vertices[2];
        this.verticalHeight = Math.abs(top.y - bottom.y);
        this.center = getCentroid();
        this.velocity = new Pair<>(0.0, 0.0);
        this.acceleration = new Pair<>(0.0, 0.0);
    }

    private void createPath(Pair[] vertices){
        spaceship = new Path2D.Double(Path2D.Double.WIND_EVEN_ODD, vertices.length);
        spaceship.moveTo(vertices[0].x, vertices[0].y);

        for(int index = 1; index < vertices.length; index++){
            spaceship.lineTo(vertices[index].x, vertices[index].y);
        }
        spaceship.closePath();
    }

    public void setCentroid(){
        center = findCentroid(spaceship);
    }

    public void getCentroid(){
        setCentroid();
        return this.center;
    }

    public void draw(Graphics2D g, Color color){
        GradientPaint redFade = new GradientPaint(0, 0, new Color(255, 0, 0), 0, verticalHeight, new Color(50, 0, 0));
        g.setColor(redFade);

        g.draw(spaceship);
    }

    public void move(double time){
        /*
        for(vertex : vertices){
            vertex = vertex.add(velocity.times(time));
        }
        */
        AffineTransform transformation = new AffineTransform();
        transformation.translate(velocity.times(time).x, velocity.times(time).y);
        spaceship.transform(transformation);
    }

    public void setVelocity(Pair vel){
        this.velocity = vel;
    }

    public void setAcceleration(Pair accel){
        this.acceleration = accel;
    }

    public void accelerate(double time){
        velocity = velocity.add(acceleration.times(time));
        velocity.x = Math.max(maxVolecity, velocity.x);
        velocity.y = Math.max(maxVolecity, velocity.y);
    }

    public void decelerate(double time){
        velocity = velocity.add(acceleration.times(-time));
        velocity.x = Math.min(0, velocity.x);
        velocity.y = Math.min(0, velocity.y);
    }

    public void rotate(double theta, double time){
        //Theta - how many radians you rotate per second
        AffineTransform transformation = new AffineTransform();
        //convert to radians
        theta = theta*Math.pi/180;
        Pair centroid = findCentroid(findVertices(spaceship));
        transformation.rotate(theta*time, centroid.x, centroid.y);
    }

    public List<Pair> findVertices(Path2D path){
        ArrayList<Pair> points = new ArrayList<Pair>;
        ArrayList<double[]> pathIteratorPoints = new ArrayList<double[]>
        double[] coordinates = new double[6];

        for(PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()){
            int type = pi.currentSegment(coordinates);
            double[] toAdd = {type, coordinates[0], coordinates[1]};
            pathIteratorPoints.add(toAdd);
        }

        int size = pathIteratorPoints.size();

        for(int i = 0; i < size; i++){
            double[] currentPoint = pathIteratorPoints.get(i);
            if(currentPoint[0] == SEG_MOVETO){
                points.add(new Pair(currentPoint[1], currentPoint[2]));
            }
        }

        return points;
    }

    public double findArea(List<Pair> pointList){
        //This method used the Shoelace Theorem to find the area of a polygon.
        double xySum = 0;
        double yxSum = 0;
        int size = pointList.size();

        for(int i = 0; i < size; i++){
            xySum += (pointList.get(i).x)*(pointList.get((i+1)%size).y);
            yxSum += (pointList.get(i).y)*(pointList.get((i+1)%size).x);
        }

        double toReturn = (Math.abs(xySum - yxSum)) / 2;

        return toReturn;
    }

    public Pair findCentroid(List<Pair> pointList){
        double sumX = 0;
        double sumY = 0;
        double centroidX;
        double centroidY;
        int size = pointList.size();

        for(int i  = 0; i < size, i++){
            double summand = pointList.get(i).x * pointList.get((i+1)%size).y - pointList.get(i).y * pointList.get((i+1)%size).x)
            sumX += (pointList.get(i).x + pointList.get((i+1)%size).x)*summand;
            sumY += (pointList.get(i).y + pointList.get((i+1)%size).y)*summand;
        }

        centroidX = sumX / (6*findArea(pointList));
        centroidY = sumY / (6*findArea(pointList));

        return new Pair(centroidX, centroidY);
    }

}