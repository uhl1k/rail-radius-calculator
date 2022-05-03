package cz.uhl1k.railRadiusCalculator.gui;

import cz.uhl1k.railRadiusCalculator.data.Pair;
import cz.uhl1k.railRadiusCalculator.data.RadiusValuesCalculator;
import cz.uhl1k.railRadiusCalculator.data.WrongAngleException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Main window of this application.
 * @author uhl1k (Roman Janků)
 */
public class MainWindow extends JFrame {
  ResourceBundle bundle = ResourceBundle.getBundle("translations/bundle");

  JTextField angle;
  JComboBox<Integer> speed;

  JRadioButton normalGauge;
  JRadioButton narrowGauge;

  JLabel x;
  JLabel y;

  JButton calculate;

  public MainWindow() {
    buildGui();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle(bundle.getString("name"));
    setResizable(false);
    setSize(new Dimension(350, 500));
    setVisible(true);
  }

  private void buildGui() {
    // setup layout
    setLayout(new GridBagLayout());
    var c = new GridBagConstraints();
    c.insets = new Insets(5, 5, 5, 5);
    c.fill = GridBagConstraints.HORIZONTAL;

    // radio buttons
    normalGauge = new JRadioButton(bundle.getString("normalGauge"));
    normalGauge.addActionListener(e -> {
      speed.removeAllItems();
      RadiusValuesCalculator.getSpeeds(false).stream().sorted().forEach(i -> speed.addItem(i));
    });
    c.gridx = 0;
    c.gridy = 0;
    add(normalGauge, c);

    narrowGauge = new JRadioButton(bundle.getString("narrowGauge"));
    narrowGauge.addActionListener(e -> {
      speed.removeAllItems();
      RadiusValuesCalculator.getSpeeds(true).stream().sorted().forEach(i -> speed.addItem(i));
    });
    c.gridx = 1;
    add(narrowGauge, c);

    ButtonGroup group = new ButtonGroup();
    group.add(normalGauge);
    group.add(narrowGauge);

    // input boxes
    c.gridy = 1;
    c.gridx = 0;
    add(new JLabel(bundle.getString("angle")), c);

    angle = new JTextField();
    c.gridx = 1;
    add(angle, c);

    c.gridy = 2;
    c.gridx = 0;
    add(new JLabel(bundle.getString("speed")), c);

    speed = new JComboBox();
    c.gridx = 1;
    add(speed, c);

    // help image

    // output and button
    x = new JLabel("X:");
    c.gridy = 4;
    c.gridx = 0;
    add(x, c);

    y = new JLabel("Y:");
    c.gridx = 1;
    add(y, c);

    c.gridy = 5;
    c.gridx = 0;
    c.gridwidth = 2;
    calculate = new JButton(bundle.getString("calculate"));
    calculate.addActionListener(e -> {
      Pair pair;
      try {
        pair = RadiusValuesCalculator.getSides(narrowGauge.isSelected(), (Integer) speed.getSelectedItem(), Integer.parseInt(angle.getText()));
      } catch (WrongAngleException ex) {
        x.setText("X:");
        y.setText("Y:");
        return;
      }
      x.setText("X: " + pair.getFirst());
      y.setText("Y: " + pair.getSecond());
    });
    add(calculate, c);
  }
}
