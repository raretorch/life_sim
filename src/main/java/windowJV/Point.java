package windowJV;

import javax.swing.*;

import java.awt.*;

public class Point extends JPanel
{
   private int x,y;
   private int[] m;

   public Point(int x, int y, int[] m)
   {
      this.x = x;
      this.y = y;
      this.m = m;
      Dot[] dotsMesh = new Dot[App.getCount()];

      int f = 0;
      for (int l = 0; l < App.count.length; l++)
      {
         for (int j = 0; j < App.count[l]; j++) {
             dotsMesh[f] = new Dot(Math.random() * this.x,Math.random() * this.y, f);
             dotsMesh[f].setColor(l);
             dotsMesh[f].setMass(m[l]);
             f++;
         }
      }
      
      if (App.getMesh() == null) {
         App.setMesh(dotsMesh);
      }
      

      for (int i = 0; i < App.getMesh().length; i++)
      {
         App.getMesh()[i].setParentMesh(App.getMesh());
      }
   }

   public void start() {
     SimRun simRun = new SimRun();
     simRun.point = this;
     Thread thread = new Thread(simRun);
     thread.start();
   }

   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      for (int k = 0; k < App.getMesh().length; k++) {
        Dot dot = App.getMesh()[k];
        int[] coords = dot.getCoordinatesInt();
        for (int j = 0; j < App.getMesh().length; j++) {
            if (k != j && App.showRelations) {
                Dot other = App.getMesh()[j];
                int ax = coords[0] + (App.mass[other.getColor()] / 2);
                int ay = coords[1] + (App.mass[other.getColor()] / 2);
                int bx = other.getCoordinatesInt()[0] + (App.mass[other.getColor()] / 2);
                int by = other.getCoordinatesInt()[1] + (App.mass[other.getColor()] / 2);
                double lenght = Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
                if (lenght <= 20 && lenght > 10) {
                    g.setColor(new Color(100, 100, 100));
                    g.drawLine(ax, ay, bx, by);
                }
                if (lenght <= 30 && lenght > 20) {
                    g.setColor(new Color(145, 145, 145));
                    g.drawLine(ax, ay, bx, by);
                }
                if (lenght <= 45 && lenght > 30) {
                    g.setColor(new Color(180, 180, 180));
                    g.drawLine(ax, ay, bx, by);
                }
                if (lenght <= 60 && lenght > 45) {
                    g.setColor(new Color(210, 210, 210));
                    g.drawLine(ax, ay, bx, by);
                }
                if (lenght <= 80 && lenght > 60) {
                    g.setColor(new Color(230, 230, 230));
                    g.drawLine(ax, ay, bx, by);
                }
            }
        }
      }
      for (int i = 0; i < App.getMesh().length; i++) {
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, App.winX + 10, 0);
        g.drawLine(App.winX + 10, 0, App.winX + 10, App.winY + 10);
        g.drawLine(App.winX + 10, App.winY + 10, 0, App.winY + 10);
        g.drawLine(0, App.winY + 10, 0, 0);
        Dot dot = App.getMesh()[i];
        int[] coords = dot.getCoordinatesInt();
        switch (dot.getColor()) {
          case 0:
            g.setColor(Color.RED);
            g.fillOval(coords[0], coords[1], m[dot.getColor()], m[dot.getColor()]);
            break;
          case 1:
            g.setColor(Color.BLUE);
            g.fillOval(coords[0], coords[1], m[dot.getColor()], m[dot.getColor()]);
            break;
          case 2:
            g.setColor(Color.GREEN);
            g.fillOval(coords[0], coords[1], m[dot.getColor()], m[dot.getColor()]);
            break;
          case 3:
            g.setColor(Color.YELLOW);
            g.fillOval(coords[0], coords[1], m[dot.getColor()], m[dot.getColor()]);
            break;
          case 4:
            g.setColor(Color.pink);
            g.fillOval(coords[0], coords[1], m[dot.getColor()], m[dot.getColor()]);
            break;  }
      }
   }
}
