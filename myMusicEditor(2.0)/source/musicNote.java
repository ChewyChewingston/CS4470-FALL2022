import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;

public class musicNote extends JComponent{
    Graphics g;

    int x;
    int y;
    int width;
    int height;
    int yoffset;
    int xoffset;    
    int circleCenterY;
    int circleCenterX;

    String pitch;
    String duration;
    Image i;    
    musicStaff m;
    boolean isNote; 
    
    public void setX(int i) {
        this.x = i;
    }

    public void setY(int i) {
        this.y = i;
    }

    public String getPitch() {
        return "Pitch: " + this.pitch;
    }

    public void updateCCX() {
        this.circleCenterX = this.x + this.xoffset;
    }

    public void updateCCY() {
        this.circleCenterY = this.y + this.yoffset;
    }

    public void setStaff(musicStaff s) {
        this.m = s;
        s.notez.add(this);
    }

    public Image loadImage(String path){
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public musicNote(String path, int myX, int myY) {
        Image myI = loadImage(path);
        this.i = myI;
        this.x = myX;
        this.y = myY;
        this.isNote = false;
        this.pitch = "Not Set";

        BufferedImage b = (BufferedImage) myI;
        
        this.width = b.getWidth();
        this.height = b.getHeight();        

        if ((path.equals("/images/wholeNote.png")) || (path.equals("/images/wholeRest.png"))) {
            this.duration = "Whole";
        } else if ((path.equals("/images/wholeNote.png")) || (path.equals("/images/halfRest.png"))) {
            this.duration = "Half";
        } else if ((path.equals("/images/quarterNote.png")) || (path.equals("/images/quarterRest.png"))) {
            this.duration = "Quarter";
        } else if ((path.equals("/images/eighthNote.png")) || (path.equals("/images/eighthRest.png"))) {
            this.duration = "Eighth";
        } else {
            this.duration = "Sixteenth";
        }

        if (path.endsWith("Note.png")) {
            this.isNote = true;
            if (this.duration.equals("Whole")) {
                xoffset = 6;
                yoffset = 10;
            } else if (this.duration.equals("Half")) {
                xoffset = 15;
                yoffset = 34;
            } else if (this.duration.equals("Quarter")) {
                xoffset = 7;
                yoffset = 35;
            } else if (this.duration.equals("Eighth")) {
                xoffset = 6;
                yoffset = 10;
            } else {
                xoffset = 6;
                yoffset = 35;
            }
            this.circleCenterY = this.y + this.yoffset;
            this.circleCenterX = this.x + this.xoffset;
        }
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(this.i, this.x, this.y, null);
    }

    public void determinePitch(int input) {
        if (isNote){
            if (input == 0) {
                this.pitch = "A5";
            } else if (input == 5) {
                this.pitch = "G5";
            } else if (input == 10) {
                this.pitch = "F5";
            } else if (input == 15) {
                this.pitch = "E5";
            } else if (input == 20) {
                this.pitch = "D5";
            } else if (input == 25) {
                this.pitch = "C5";
            } else if (input == 30) {
                this.pitch = "B4";
            } else if (input == 35) {
                this.pitch = "A4";
            } else if (input == 40) {
                this.pitch = "G4";
            } else if (input == 45) {
                this.pitch = "F4";
            } else if (input == 50) {
                this.pitch = "E4";
            } else if (input == 55) {
                this.pitch = "D4";
            } else if (input == 60) {
                this.pitch = "C4";
            } else {
                this.pitch = "Not Set";
            }
        } else {
            this.pitch = "Not a note";
        }
    }
}