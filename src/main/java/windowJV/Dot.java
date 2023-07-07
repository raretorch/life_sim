package windowJV;

public class Dot 
{
    private double x,y;
    private int mass;
    private int ID;
    private int color;
    private Dot[] parentMesh;
    private double[][] forceVectors = new double[App.getCount()][2];

    public Dot(double x, double y, int ID){
        this.x = x;
        this.y = y;
        this.ID = ID;
    }

    public void setParentMesh(Dot[] parentMesh)
    {
        this.parentMesh = parentMesh;
    }

    public int getColor(){
      return color;
    }

    public void setColor(int color){
      this.color = color;
    }
    
    public int getID()
    {
      return ID;
    }


    public void setDelta()
    {
        for(int i = 0; i < App.getMesh().length; i++)
        {
          try {
              if (parentMesh[i] == null) {
                try {
                  Thread.sleep(10);
                } catch (InterruptedException ie) {
                  ie.printStackTrace();
                }
                this.setDelta();
                return;
              }
              calculateVector(parentMesh[i]);
          } catch (Exception e) {
            System.out.println("Warning: Fail statement when calculating vector");
            try {
              Thread.sleep(10);
            } catch (InterruptedException ie) {
              ie.printStackTrace();
            }
            this.setDelta();
            return;
          }
        }

        for(int y = 0; y < App.getMesh().length; y++)
        {
            if (y != 0) {
              forceVectors[y][0] += forceVectors[y-1][0];
              forceVectors[y][1] += forceVectors[y-1][1];
            }
        }
        double[] selectedVector = pointSelector(forceVectors[App.getMesh().length-1]);
        x += selectedVector[0] * App.TIME_MODIFIER;
        y += selectedVector[1] * App.TIME_MODIFIER;
    }

    private double[] pointSelector(double[] vector) {
        if (x < App.wallDistance) {
            double floor = Math.abs(x);
            double temp = floor / App.wallDistance;
            temp = Math.pow(temp, App.wallAspectRatio);
            double force = App.wallForce / temp;
            vector[0] += force;
        }
        if (y < App.wallDistance) {
            double floor = Math.abs(y);
            double temp = floor / App.wallDistance;
            temp = Math.pow(temp, App.wallAspectRatio);
            double force = App.wallForce / temp;
            vector[1] += force;
        }
        if ((App.winX - x) < App.wallDistance) {
            double floor = Math.abs(App.winX - x);
            double temp = floor / App.wallDistance;
            temp = Math.pow(temp, App.wallAspectRatio);
            double force = App.wallForce / temp;
            vector[0] -= force;
        }
        if ((App.winY - y) < App.wallDistance) {
            double floor = Math.abs(App.winY - y);
            double temp = floor / App.wallDistance;
            temp = Math.pow(temp, App.wallAspectRatio);
            double force = App.wallForce / temp;
            vector[1] -= force;
        }
        return vector;
    }

    private double calculateForce( double[] vec, int color)
    {
        double distance = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        if (distance == 0) {
            return 0;
        }
        double force = App.GRAV_CONST * mass * mass / (distance * distance);
        if (force > 500){
            force = 500;
        }
        force *= (App.relations[this.color][color] / 5);
        
        return force;
    }
    
    private double[] calculateVector(Dot other)
    {
        int id = other.getID();
        int color = other.getColor();
        double dx = other.x - x;
        double dy = other.y - y;
        forceVectors[id] = new double [] {dx,dy};
        double f = calculateForce(forceVectors[id], color);
        forceVectors[id][0] *= f;
        forceVectors[id][1] *= f;
        return forceVectors[id];
    }

    public int[] getCoordinatesInt()
    {
        return new int[] {(int)x,(int)y};
    }

    public double[] getCoordinatesDouble()
    {
        return new double[] {x,y};
    }

    public int getMass()
    {
        return mass;
    }

    public void setMass(int mass)
    {
        this.mass = mass;
    }
}
