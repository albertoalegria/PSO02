import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * PSO02
 * Created by alberto from Instituto Tecnologico de Tuxtla Gutierrez ITTG
 * At Instituto de Investigaciones en Matematicas Aplicadas y en Sistemas IIMAS
 * Mexico, DF. 27/06/16, 10:34 AM
 */

public class Utilities {
    public static int getRandomInteger(int rMin, int rMax) {
        return new Random().nextInt((rMax - rMin) + 1) + rMin;
    }

    public static double getRandomDouble(double min, double max) {
        return round(min + (max - min) * new Random().nextDouble(), Constants.ROUND);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}