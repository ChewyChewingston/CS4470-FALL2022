import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class musicView extends JComponent implements KeyListener {
    Graphics g;
    Image trebleClefImage;
    Image commonTimeImage;

    LinkedList<musicStaff> staves = new LinkedList<musicStaff>();
    LinkedList<addons> allAdds = new LinkedList<addons>();

    addons delTarget;
    boolean highlightAllNotes;
    String text;

    public int getStavesSize() {
        return staves.size();
    }
    public int getAddsSize() {
        return allAdds.size();
    }

    public void printAdds() {
        int i = 1;
        for (addons a : allAdds) {
            System.out.println("COORDINATES OF " + i + ": " + a.x + ", " + a.y);
        }
    }
    
    public void setDeleteTarget(addons a) {
        this.delTarget = a;
    }

    /*
     * set which staff a notation belongs to
     */
    public void setAddStaff(addons a) {
        for(musicStaff staff : staves) {
            if (((staff.firstLine-10) <= a.circleCenterY) && (a.circleCenterY <= (staff.firstLine + 50))) {
                if (a.m != null) {
                    a.m.remove(a);
                }
                a.setStaff(staff);
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
            s.name = "Staff " + (i+1);
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
            if ((a.pitch.startsWith("A5")) || (a.pitch.startsWith("B5"))) {
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 10), a.circleCenterX+10, (a.m.firstLine - 10));
            } else if ((a.pitch.startsWith("C4")) || (a.pitch.startsWith("B3"))) { 
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine + 50), a.circleCenterX+10, (a.m.firstLine + 50));
            } else if ((a.pitch.startsWith("D6")) || (a.pitch.startsWith("C6"))) {
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 20), a.circleCenterX+10, (a.m.firstLine - 20));
                g.drawLine(a.circleCenterX - 10, (a.m.firstLine - 10), a.circleCenterX+10, (a.m.firstLine - 10));
            } else if ((a.pitch.startsWith("G3")) || (a.pitch.startsWith("A3"))) {
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
                    }
                }
            }
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

    @Override
    public void repaint() {
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
    }
    @Override
    public void keyReleased(KeyEvent e) {        
    }
}