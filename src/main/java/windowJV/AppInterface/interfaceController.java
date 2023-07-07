package windowJV.AppInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import windowJV.App;

public class interfaceController {

  public static JFrame frame;
  public static int menuLenght = 250;
  public JTextField[] dotsFields = new JTextField[5];
  public JTextField[][] relationsFields = new JTextField[5][5];
  public static JTextArea resetArea;
  public static int restarting = 0;

  JFrame panelParent;

  public interfaceController() {
    this.panelParent = frame; 
    JPanel pan = new JPanel();
    pan.setSize(menuLenght - 10, App.winY + 30);
    pan.setLocation(App.winX + 14, 0);
    pan.setLayout(null);
    panelParent.add(pan);

    resetArea = new JTextArea("Need a restart the simulation!");
    resetArea.setBounds(22, 370, 230, 25);
    resetArea.setBackground(null);
    resetArea.setEditable(false);
    resetArea.setFocusable(false);
    pan.add(resetArea);

    editHandler eh = new editHandler(resetArea);
    Thread t = new Thread(eh);
    t.start();

    JButton pauseButton = new JButton("Pause");
    pauseButton.setBounds(1, 7, 230, 25);
    pauseButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        App.running = !App.running;
        if (App.running) {
          pauseButton.setText("Pause");
        } else {
          pauseButton.setText("Resume");
        }
      } 
    });
    pan.add(pauseButton);

    JButton resetButton = new JButton("Update");
    resetButton.setBounds(1, 35, 115, 25);
    resetButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        restarting = 0;
        int[] digits = new int[dotsFields.length];
        for (int i = 0; i< dotsFields.length; i++) {
          try {
            digits[i] = Integer.parseInt(dotsFields[i].getText());
          } catch (Exception ex) {
            restarting = 2;
            return;
          }
        }
        App.setDots(digits);
        App.newSim();
      }
    });
    pan.add(resetButton);

    JButton randomButton = new JButton("Randomize");
    randomButton.setBounds(120, 35, 110, 25);
    randomButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
          try {
            for (int k = 0; k < 5; k++){
              for (int l = 0; l < 5; l++) {
                App.relations[k][l] = Math.random() * 4 - 2;
                relationsFields[k][l].setText(String.format("%.1f", App.relations[k][l]));
              }
            }
            restarting = 0;
          } catch (Exception ex) {
            restarting = 2;
            return;
          }
      }
    });
    pan.add(randomButton);

    JSlider time = new JSlider(1,1000,500);
    time.setBounds(3, App.winY - 32, 230, 25);
    time.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        App.TIME_MODIFIER = Double.valueOf(time.getValue()) / 100000; 
      }
    });
    pan.add(time);

    JCheckBox check = new JCheckBox("Show relations");
    check.setBounds(10, App.winY - 60, 230, 25);
    check.setSelected(true);
    check.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        App.showRelations = check.isSelected(); 
      }   
    });
    pan.add(check);

    JTextArea textArea = new JTextArea("maked by Raretorch");
    textArea.setBounds(55, App.winY - 8, 230, 25);
    textArea.setBackground(null);
    textArea.setEditable(false);
    textArea.setFocusable(false);
    textArea.setForeground(new Color(150, 150, 150));
    pan.add(textArea);

    
    for (int i = 0; i < 5; i++) {
      JPanel col = new JPanel();
      col.setBounds(8, 74 + i * 60, 5, 46);
      switch (i) {
        case 0:
          col.setBackground(Color.RED);
          break;
        case 1:
          col.setBackground(Color.BLUE);
          break;
        case 2:
          col.setBackground(Color.GREEN);
          break;
        case 3:
          col.setBackground(Color.YELLOW);
          break;
        case 4:
          col.setBackground(Color.pink);
          break;
      }
      pan.add(col);
      dotsFields[i] = new JTextField(String.valueOf(App.count[i]));
      dotsFields[i].setBounds(15, 72 + i * 60, 205, 25);
      dotsFields[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          restarting = 1;
        }
      });

      for (int j = 0; j < 5; j++) {
        JPanel col2 = new JPanel();
        col2.setBounds(19 + j * 41, 98 + i * 60, 33, 3);
        switch (j) {
          case 0:
            col2.setBackground(Color.RED);
            break;
          case 1:
            col2.setBackground(Color.BLUE);
            break;
          case 2:
            col2.setBackground(Color.GREEN);
            break;
          case 3:
            col2.setBackground(Color.YELLOW);
            break;
          case 4:
            col2.setBackground(Color.pink);
            break;
        } 
        pan.add(col2);
        relationsFields[i][j] = new JTextField(String.valueOf(App.relations[i][j]));
        relationsFields[i][j].setBounds(15 + j * 41, 101 + i * 60, 41, 20);
        relationsFields[i][j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              for (int k = 0; k < 5; k++){
                for (int l = 0; l < 5; l++) {
                  App.relations[k][l] = Double.parseDouble(relationsFields[k][l].getText());
                }
              }
              restarting = 0;
            } catch (Exception ex) {
              restarting = 2;
              return;
            }
          }
        });
        pan.add(relationsFields[i][j]);
      }
      pan.add(dotsFields[i]);
    }
  }
}
