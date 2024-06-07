package lib.Models;

import java.io.Serializable;

/**
 * Класс координат с полями <b>x</b>, <b>y</b>.
 */
public class Coordinates implements Serializable {
    private double x;
    private int y;
    /**
     *
     * @param x - X координата
     * @param y - Y координата
     */
    public Coordinates(double x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates() {

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    public boolean is_valide() {
        if (y<=622) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        return "(" +x + "; " + y + ")";
    }
}
