public interface Movement{
    public void move(double time);
    public void accelerate(double time);
    public void decelerate(double time);
    //public void shot();
    //public void collide();
    public void rotate(double theta, double time);
}
