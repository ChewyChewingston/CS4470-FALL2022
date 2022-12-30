import javax.swing.*;
import java.awt.geom.*;

public class animCoordinate extends JComponent{
    Point2D drawingCoordinate;
    String duration;
    boolean reachedBottom;
    int delay;
    int originalHeight;

    @Override
    public String toString() {
        String returnStr = "Coordinates: " + this.getThisX() + ", " + this.getThisY();
        return returnStr;
    }

    public animCoordinate(int x, int y, String duration) {
        this.drawingCoordinate = new Point2D.Double(x, y-20);
        this.originalHeight = y;
        this.duration = duration;
        this.reachedBottom = false;
    }

    public animCoordinate() {
        this.drawingCoordinate = null;
        this.duration = null;
    }

    public Point2D getThisLocation(){
        return this.drawingCoordinate;
    }

    public int getThisX() {
        double data = this.drawingCoordinate.getX();
        int value = (int) data;
        return value;
    }
    public int getThisY() {
        double data = this.drawingCoordinate.getY();
        int value = (int) data;
        return value;
    }

    public int getOrigHeight() {
        return this.originalHeight;
    }

    public void setThisX(int value) {
        this.drawingCoordinate.setLocation(value, this.getThisY());
    }
    public void setThisY(int value) {
        this.drawingCoordinate.setLocation(this.getThisX(), value);
    }

    public void incrementX(int value) {
        this.drawingCoordinate.setLocation(this.getThisX()+value, this.getThisY());
    }
    public void incrementY(int value) {
        this.drawingCoordinate.setLocation(this.getThisX(), this.getThisY()-value);
    }
    public void decrementY(int value) {
        this.drawingCoordinate.setLocation(this.getThisX(), this.getThisY()+value);
    }

    public String getDuration(){
        return this.duration;
    }

    /**
     * This function returns how much a ball moves
     * per frame
     */
    public int getStep() {
        if (this.duration == "Whole") {
            return 1;
        } else if (this.duration == "Half") {
            return 2;
        } else if (this.duration == "Quarter") {
            return 4;
        } else if (this.duration == "Sixteenth") {
            return 8;
        } else {
            return 16;
        }
    }
}