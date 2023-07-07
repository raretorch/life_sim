package windowJV;

public class SimRun implements Runnable {

public Point point;

  @Override
  public void run() {
      while (true) {
        if (App.running) {
            for (int i = 0; i < App.getMesh().length; i++)
            {
               App.getMesh()[i].setDelta();
            }
        }
        point.repaint();
            
        try {
          Thread.sleep(1000/60);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }
  }
}
