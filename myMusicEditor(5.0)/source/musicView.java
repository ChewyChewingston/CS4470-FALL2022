import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class musicView extends JComponent implements KeyListener {
    Graphics g;
    Image trebleClefImage;
    Image commonTimeImage;

    LinkedList<musicStaff> staves = new LinkedList<musicStaff>();
    LinkedList<addons> allAdds = new LinkedList<addons>();
    LinkedList<animCoordinate> animPointList = new LinkedList<animCoordinate>();
    ArrayList<Point2D> strokeCoordinates = new ArrayList<Point2D>();

    addons delTarget;
    animCoordinate currentNote;
    Point2D currentCoordinates;
    boolean highlightAllNotes;
    boolean playMode;
    Timer timer;
    String text;

    int testingX;
    int testingY;

    public int getStavesSize() {
        return staves.size();
    }
    public int getAddsSize() {
        return allAdds.size();
    }
    public void addCoordinate(int x, int y){
        Point2D newCoord = new Point2D.Double(x, y);
        this.strokeCoordinates.add(newCoord);
    }
    public void clearCoordinates() {
        this.strokeCoordinates.clear();
    }
    public void printAdds() {
        int i = 1;
        for (addons a : allAdds) {
            System.out.println("COORDINATES OF " + i + ": " + a.x + ", " + a.y);
        }
    }

    public void drawStroke(Graphics g) {
        g.setColor(Color.BLUE);
        if (this.strokeCoordinates.size() > 1) {
            for (int i = 0; i < (this.strokeCoordinates.size()-1); i++) {
                Point2D point1 = this.strokeCoordinates.get(i);
                Point2D point2 = this.strokeCoordinates.get(i+1);
                g.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
            }
        }
    }
    public void setDeleteTarget(addons a) {
        this.delTarget = a;
    }

    /**
     * creates a sorted list of all x coordinates of notes and rests,
     * as well as which staff they're associated with.
     * This will be used for where the dots will jump/dance.
     */
    public void sortList() {
        for(musicStaff staff : staves) {
            staff.sortStaff();
            //System.out.println(staff.additions.size());
            for (addons a : staff.additions) {
                animCoordinate myAM = new animCoordinate(a.getCCX(),
                                                staff.getFirstLine(),
                                                a.getDuration());
                animPointList.add(myAM);
            }
        }
    }

    /*
     * set which staff a notation belongs to
     */
    public void setAddStaff(addons a) {
        for(musicStaff staff : staves) {
            if (((staff.firstLine-25) <= a.circleCenterY) && (a.circleCenterY <= (staff.firstLine + 75))) {
                if (a.m != null) {
                    a.m.remove(a);
                }
                a.setStaff(staff);
                staff.addAddition(a);
            }
        }
    }
    public void addAddition(addons a) {
        this.allAdds.add(a);
    }
    public void removeAddition(addons a) {
        this.allAdds.remove(a);
    }
    
    /*
     * snaps notation and determines pitch
     */
    public void lineSnapping(addons a, int input) {
        String end= "";
        if (a instanceof note) {
            if (a.pitch.endsWith("b")) {
                end = "b";
            } else if (a.pitch.endsWith("#")) {
                end = "#";
            }
        }
        if (input < 2.5) {
            a.setY(a.m.firstLine - 25 - a.yoffset);
            a.setCCY(a.y+a.yoffset);
            if (a.a != null) {
                a.a.y = a.circleCenterY-a.a.height;
            }
            a.pitch = "D6";
        } else if ((input >= 2.5) && (input < 7.5)) {
            a.setY(a.m.firstLine - 20 - a.yoffset);
            a.pitch = "C6";
        } else if ((input >= 7.5) && (input < 12.5)) {
            a.setY(a.m.firstLine - 15 - a.yoffset);
            a.pitch = "B5";
        } else if ((input >= 12.5) && (input < 17.5)) {
            a.setY(a.m.firstLine - 10 - a.yoffset);
            a.pitch = "A5";
        } else if ((input >= 17.5) && (input < 22.5)) {
            a.setY(a.m.firstLine - 5 - a.yoffset);
            a.pitch = "G5";
        } else if ((input >= 22.5) && (input < 27.5)) {
            a.setY(a.m.firstLine - a.yoffset);
            a.pitch = "F5";
        } else if ((input >= 27.5) && (input < 32.5)) {
            a.setY(a.m.firstLine + 5 - a.yoffset);
            a.pitch = "E5";
        } else if ((input >= 32.5) && (input < 37.5)) {
            a.setY(a.m.firstLine + 10 - a.yoffset);
            a.pitch = "D5";
        }else if ((input >= 37.5) && (input < 42.5)) {
            a.setY(a.m.firstLine + 15 - a.yoffset);
            a.pitch = "C5";
        } else if ((input >= 42.5) && (input < 47.5)) {
            a.setY(a.m.firstLine + 20 - a.yoffset);
            a.pitch = "B4";
        } else if ((input >= 47.5) && (input < 52.5)) {
            a.setY(a.m.firstLine + 25 - a.yoffset);
            a.pitch = "A4";
        } else if ((input >= 52.5) && (input < 57.5)) {
            a.setY(a.m.firstLine + 30 - a.yoffset);
            a.pitch = "G4";
        } else if ((input >= 57.5) && (input < 62.5)) {
            a.setY(a.m.firstLine + 35 - a.yoffset);
            a.pitch = "F4";
        } else if ((input >= 62.5) && (input < 67.5)) {
            a.setY(a.m.firstLine + 40 - a.yoffset);
            a.pitch = "E4";
        } else if ((input >= 67.5) && (input < 72.5)) {
            a.setY(a.m.firstLine + 45 - a.yoffset);
            a.pitch = "D4";
        }  else if ((input >= 72.5) && (input < 77.5)) {
            a.setY(a.m.firstLine + 50 - a.yoffset);
            a.pitch = "C4";
        }  else if ((input >= 77.5) && (input < 82.5)) {
            a.setY(a.m.firstLine + 55 - a.yoffset);
            a.pitch = "B3";
        }  else if ((input >= 82.5) && (input < 87.5)) {
            a.setY(a.m.firstLine + 60 - a.yoffset);
            a.pitch = "A3";
        } else if (input >= 87.5) {
            a.setY(a.m.firstLine + 65 - a.yoffset);
            a.setCCY(a.y+a.yoffset);
            if (a.a != null) {
                a.a.y = a.circleCenterY-a.a.height;
            }
            a.pitch = "G3";
        }

        if(!(a instanceof note)) {
            a.pitch = "Not a note.";
        } else {
            a.pitch += end;
        }
    }
    protected Image loadImage(String path){
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        trebleClefImage = loadImage("/trebleClef.png");
        commonTimeImage = loadImage("/commonTime.png");

        super.paintComponent(g);
        g.setColor(Color.black);

        int i = 0;
        for(musicStaff s : staves) {
            drawStaff(g, 30 + 100*i);
            s.firstLine = 30 + 100*i;
            s.editLinez();
            s.setNumber(i);
            i++;
        }
        checkAllHighlights(g);

        if (delTarget instanceof accidental) {
            g.setColor(Color.RED);
            g.drawRect(delTarget.x, delTarget.y, delTarget.width, delTarget.height);
            g.drawImage(delTarget.i, delTarget.x, delTarget.y, null);
        }

        for(addons a : this.allAdds) {
            if (a.equals(delTarget)) {
                g.setColor(Color.RED);
                g.drawRect(a.x, a.y, a.width, a.height);

                if (a instanceof note) {
                    if (a.a != null) {
                        g.setColor(Color.RED);
                        g.drawRect(a.a.x, a.a.y, a.a.width, a.a.height);
                    }
                }
            }
            
            g.setColor(Color.BLACK);
            if (((a.pitch.startsWith("A5")) || (a.pitch.startsWith("B5"))) || (a.restLedger() == 2)) {
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 10), a.circleCenterX+10, (a.m.firstLine - 10));
            } else if (((a.pitch.startsWith("C4")) || (a.pitch.startsWith("B3"))) || (a.restLedger() == 3)) { 
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine + 50), a.circleCenterX+10, (a.m.firstLine + 50));
            } else if (((a.pitch.startsWith("D6")) || (a.pitch.startsWith("C6"))) || (a.restLedger() == 1)) {
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 20), a.circleCenterX+10, (a.m.firstLine - 20));
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 10), a.circleCenterX+10, (a.m.firstLine - 10));
            } else if (((a.pitch.startsWith("G3")) || (a.pitch.startsWith("A3"))) || (a.restLedger() == 4)) {
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine + 60), a.circleCenterX+10, (a.m.firstLine + 60));
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine + 50), a.circleCenterX+10, (a.m.firstLine + 50));
            }
            
            if (a instanceof note) {
                if (a.a != null) {
                    g.drawImage(a.a.i, a.a.x, a.a.y, null);
                }
            }
            g.drawImage(a.i, a.x, a.y, null);
        }
        drawStroke(g); 

        //if this

        // g.setColor(Color.MAGENTA);
        // g.fillOval(testingX, testingY, 10, 10);

        if (this.currentNote != null) {
            try {
                g.setColor(Color.MAGENTA);
                g.fillOval((int) this.currentNote.getThisX(), (int) this.currentNote.getThisY(), 10, 10);
            } catch (Exception e) {
                
            }
        }
    }

    public void checkAllHighlights(Graphics g) {
        if(highlightAllNotes) {
            for(addons a : this.allAdds) {
                if (a instanceof note) {
                    g.setColor(Color.RED);
                    g.drawRect(a.x, a.y, a.width, a.height);
                    g.drawImage(a.i, a.x, a.y, null);
                }                
            }
        }
    }
    public void checkAccidentalBound(int x, int y, accidental acc) {
        for(addons a : this.allAdds) {
            if (a instanceof note){
                if ((a.x < x) && ((a.x + a.width) > x)) {
                    if ((a.y < y) && ((a.y + a.height) > y)) {
                        if (a.a != null) {
                            a.a = null;
                            a.pitch = a.pitch.substring(0, (a.pitch.length() -1));
                        }
                        a.setAccidental(acc);
                        acc.n = (note) a;
                        this.text = "Pitch has been changed to " + a.pitch;
                        break;
                    }
                }
            }
        }
        if (acc.n == null) {
            this.text = "Accidental has not been assigned to note.";
        }
    }
    public void detectChord(note n) {
        for(addons a : this.allAdds) {
            if (a instanceof note) {
                if ((!(a.equals(n))) && (a.m.equals(n.m))) {
                    if (((a.x - 5) < n.circleCenterX) && ((a.x + a.width + 5) > n.circleCenterX)) {
                        n.setX(a.x);
                        n.setCCX(a.circleCenterX);
                    }
                }
            }
        }
    }
    public addons recognize(Result r) {
        Point2D start = this.strokeCoordinates.get(0);
        String name = r.getName();
        addons result;
        int x = (int) start.getX();
        int y = (int) start.getY();

        if (name.equals("circle")) {
            result = new note("wholeNote.png", x, y);
        }else if (name.equals("half note")) {
            result = new note("halfNote.png", x, y);
        } else if (name.equals("quarter note")) {
            result = new note("quarterNote.png", x, y);
        } else if (name.equals("eighth note")) {
            result = new note("eighthNote.png", x, y);
        } else if (name.equals("sixteenth note")) {
            result = new note("sixteenthNote.png", x, y);
        
        } else if (name.equals("rectangle")) {
            result = new addons("wholeRest.png", x, y);
        } else if (name.equals("half rest")) {
            result = new addons("halfRest.png", x, y);
        } else if (name.equals("right curly brace")) {
            result = new addons("quarterRest.png", x, y);
        } else if (name.equals("eighth rest")) {
            result = new addons("eighthRest.png", x, y);
        } else if (name.equals("sixteenth rest")) {
            result = new addons("sixteenthRest.png", x, y);
        }

        else if (name.equals("flat")) {
            result = new accidental("flat.png", x, y);
        } else if (name.equals("star")) {
            result = new accidental("sharp.png", x, y);
        } else if (name.equals("No match")) {
            result = null;
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public void repaint() {
        if (this.playMode) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(500, 200);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);                
            } catch (Exception ex) {
                System.out.println("Error!");
            }        
        }
    }
    protected void drawStaff(Graphics g, int start){
        g.drawRect(30, start, 1000, 40);

        for (int j = 0; j<4; j++) {
            g.drawLine(30, start + 10*j, 1030, start + 10*j);
        }
        for (int i = 1; i<4; i++) {
            g.drawLine(250*i, start, 250*i, start + 40);
        }
        g.drawImage(trebleClefImage, 30, start-20, null);
        g.drawImage(commonTimeImage, 80, start-8, null);        
    }
    public musicView(){        
        addKeyListener(this);
        setFocusable(true);
        this.playMode =false;
    }

    @Override
    public void keyTyped(KeyEvent e) {       
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == (KeyEvent.VK_DELETE)){
            if (delTarget != null) {
                if (delTarget instanceof accidental) {
                    for (addons a : this.allAdds) {
                        if (a.a == delTarget) {
                            a.a = null;
                            a.pitch = a.pitch.substring(0, (a.pitch.length() -1));
                        }
                    }
                } else {
                    allAdds.remove(delTarget);
                    allAdds.remove(delTarget);
                }
            }
            
            repaint();

            try {
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);                
            } catch (Exception ex) {
                System.out.println("Error!");
            }
        }

        if (e.getKeyCode() == (KeyEvent.VK_D)){
            
        }

        if ((e.getKeyCode() == KeyEvent.VK_C) && ( KeyEvent.CTRL_DOWN_MASK != 0)) {
            addons a; 
            if ((delTarget != null) && (!(delTarget instanceof accidental))) {
                if (delTarget instanceof note) {
                    a = new note(delTarget.path, delTarget.x+5, delTarget.y+5);
                } else {
                    a = new addons(delTarget.path, delTarget.x+5, delTarget.y+5);
                } 
                this.addAddition(a);
                this.setAddStaff(a);
                this.lineSnapping(a, a.circleCenterY - (a.m.firstLine - 25));
                delTarget = a;
            }
            repaint();
            try {
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);                
            } catch (Exception ex) {
                System.out.println("Error!");
            }            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {        
    }
}