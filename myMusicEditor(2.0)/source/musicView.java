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
    LinkedList<musicNote> allNotes = new LinkedList<musicNote>();
    musicNote delTarget;

    public int getStavesSize() {
        return staves.size();
    }

    public void setDeleteTarget(musicNote n) {
        this.delTarget = n;
    }

    public void printStavesLines(){
        for (musicStaff s : staves) {
            System.out.println(s.name);
            s.printLinez();
        }
    }

    public void printNotes() {
        int i = 1;
        for (musicNote n : allNotes) {
            System.out.println("Note " + i + ": " + n.x + ", " + n.y);
        }
    }

    public void setNoteStaff(musicNote n) {
        for(musicStaff staff : staves) {
            if (((staff.firstLine-10) < n.circleCenterY) && (n.circleCenterY < (staff.firstLine + 50))) {
                if (n.m != null) {
                    n.m.remove(n);
                }
                n.setStaff(staff);
            }
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

        for(musicNote n : this.allNotes) {
            if (n.equals(delTarget)) {
                g.setColor(Color.RED);
                g.drawRect(n.x, n.y, n.width, n.height);
            }
            g.drawImage(n.i, n.x, n.y, null);
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

    public void addNote(musicNote n) {
        this.allNotes.add(n);
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
                allNotes.remove(delTarget);
                allNotes.remove(delTarget);
            }
            repaint();

            try {
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            } catch (Exception ex) {
                System.out.println("Error!");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {        
    }
}