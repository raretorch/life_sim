package windowJV;

import java.awt.Color;

import javax.swing.*;

import windowJV.AppInterface.interfaceController;

public class App 
{
    static JFrame frame;

    public static boolean running = true;
    public static boolean showRelations = true;

    public static int winX = 800;
    public static int winY = 500;
    public static int[] count = new int[] {50, 50, 50, 50, 50};
    public static int getCount() {
      int c = 0;
      for (int i = 0; i < count.length; i++) {
        c += count[i];
      }
      return c;
    }
    public static int[] mass = new int[] {10, 10, 10, 10, 10};
    public static int wallDistance = 300;
    public static double wallForce = 10;
    public static double wallAspectRatio = 1.5;
    private static Dot[] dotMeshInstance;
    public static double[][] relations = new double[5][5];

    public static double TIME_MODIFIER = 0.001;
    public static final double GRAV_CONST = 6.67;


    public static void main( String[] args )
    {
        frame = new JFrame("LifeSimulation");
        interfaceController.frame = frame;
        Point point = new Point(winX,winY,mass);
        frame.setContentPane(point);
        point.setSize(winX, winY);
        point.setLayout(null);
        new interfaceController();
        point.start();
        frame.setSize(winX + interfaceController.menuLenght,winY +40);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public static void setDots(int[] digits){
      for (int i = 0; i < count.length; i++) {
        count[i] = digits[i];
      }
    }

    public static void newSim (){
      Dot[] dotsMesh = new Dot[getCount()];
      int f = 0;
      for (int l = 0; l < App.count.length; l++)
      {
         for (int j = 0; j < App.count[l]; j++) {
             dotsMesh[f] = new Dot(Math.random() * winX,Math.random() * winY, f);
             dotsMesh[f].setColor(l);
             dotsMesh[f].setMass(mass[dotsMesh[f].getColor()]);
             f++;
         }
      }
      dotMeshInstance = dotsMesh;

      for (int i = 0; i < getCount(); i++)
      {
         dotMeshInstance[i].setParentMesh(dotMeshInstance);
      }
    }

    public static Dot[] getMesh (){
        return dotMeshInstance;
    }

    public static void setMesh(Dot[] mesh){
        dotMeshInstance = mesh;
    }

    public static void MeshInfo(Dot[] mesh){
      for (int i = 0; i < getCount(); i++)
      {
        System.out.println(mesh[i].getID() + ":" + mesh[i].getCoordinatesDouble()[0] + " - " + mesh[i].getCoordinatesDouble()[1]);
      }
      System.out.println("----------------------------------------------------");
    }
}
