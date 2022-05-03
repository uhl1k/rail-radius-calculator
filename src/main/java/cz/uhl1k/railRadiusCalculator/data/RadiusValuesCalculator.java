package cz.uhl1k.railRadiusCalculator.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiusValuesCalculator {

  private static final Map<Integer, Integer> normalGaugeValues = new HashMap<>();
  private static final Map<Integer, Integer> narrowGaugeValues = new HashMap<>();

  static {
    normalGaugeValues.put(30, 150);
    normalGaugeValues.put(40, 190);
    normalGaugeValues.put(50, 300);
    normalGaugeValues.put(60, 500);
    normalGaugeValues.put(70, 630);
    normalGaugeValues.put(80, 760);
    normalGaugeValues.put(90, 1020);
    normalGaugeValues.put(100, 1200);
    normalGaugeValues.put(110, 1980);
    normalGaugeValues.put(120, 2500);
    normalGaugeValues.put(140, 3500);
    normalGaugeValues.put(160, 4500);

    narrowGaugeValues.put(10, 40);
    narrowGaugeValues.put(15, 50);
    narrowGaugeValues.put(20, 70);
    narrowGaugeValues.put(25, 90);
    narrowGaugeValues.put(30, 110);
    narrowGaugeValues.put(35, 130);
    narrowGaugeValues.put(40, 140);
    narrowGaugeValues.put(45, 170);
    narrowGaugeValues.put(50, 210);
  }

  public static List<Integer> getSpeeds(boolean narrowGauge) {
    return narrowGauge?narrowGaugeValues.keySet().stream().toList():normalGaugeValues.keySet().stream().toList();
  }

  private static int getRadius(boolean narrowGauge, int speed) {
    return narrowGauge?narrowGaugeValues.get(speed):normalGaugeValues.get(speed);
  }

  public static Pair<Long, Long> getSides(boolean narrowGauge, int speed, int angle) throws WrongAngleException {
    double rads = Math.toRadians(angle);
    if (rads < 0) {
      throw new WrongAngleException("Angle must be at least 0 degrees!");
    } else if (rads <= Math.PI/2) {
      return new Pair<>(Math.round(Math.sin(rads) * getRadius(narrowGauge, speed)), Math.round((1-Math.cos(rads)) * getRadius(narrowGauge, speed)));
    } else if (rads <= Math.PI) {
      return new Pair<>(Math.round(Math.sin(rads) * getRadius(narrowGauge, speed)), Math.round(getRadius(narrowGauge, speed) + Math.abs(Math.cos(rads) * getRadius(narrowGauge, speed))));
    } else {
      throw new WrongAngleException("Angle must be smaller than or equal to 180 degrees!");
    }
  }
}
