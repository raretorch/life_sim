package windowJV.AppInterface;

import javax.swing.*;

public class editHandler implements Runnable {

  public int restart = 0;
  public JTextArea resetArea;
  
  public editHandler(JTextArea resetArea) {
    this.resetArea = resetArea;
  }
  
  @Override
  public void run() {
    while (true) {
      restart = interfaceController.restarting;
      if (restart == 1) {
        interfaceController.resetArea.setText("Need a restart the simulation!");
      } else if (restart == 0) {
        interfaceController.resetArea.setText("");
      } else {
        interfaceController.resetArea.setText("Wrong data in the simulation!");
      }
      try {
        Thread.sleep(1000/60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
