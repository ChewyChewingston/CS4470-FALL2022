import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class addons extends JComponent{
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
    accidental a;
    note n;
    boolean isFlat;
    
    public void setX(int i) {
        this.x = i;
    }
    public void setY(int i) {
        this.y = i;
    }
    public void setYOffset(int i) {
        this.yoffset = i;
    }
    public void setCCX(int i) {
        this.circleCenterX = i;
    }
    public void setCCY(int i) {
        this.circleCenterY = i;
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
        s.additions.add(this);
    }

    // public Image loadImage(String path){
    //     URL url = addons.class.getResource(path); 
    //     Image img = Toolkit.getDefaultToolkit().getImage(url);
    //     return img;
    // }

    protected Image loadImage(String path){
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void determinePitch(int a) {
    }
    public void setAccidental(accidental a) {
    }
    public void updateAccidental() {
    }
    
    public addons(String path, int myX, int myY) {
        Image myI = loadImage(path);
        this.i = myI;
        this.x = myX;
        this.y = myY;
        this.pitch = "Not a note.";

        // BufferedImage b = (BufferedImage) myI;
        
        // this.width = b.getWidth();
        // this.height = b.getHeight();        

        if ((path.equals("wholeNote.png")) || (path.equals("wholeRest.png"))) {
            if (path.equals("wholeNote.png")) {
                this.width = 19;
                this.height = 11;
            } else {
                this.width = 20;
                this.height = 10;
            }
            this.duration = "Whole";
        } else if ((path.equals("halfNote.png")) || (path.equals("halfRest.png"))) {
            if (path.equals("halfNote.png")) {
                this.width = 30;
                this.height = 40;
            } else {
                this.width = 20;
                this.height = 10;
            }
            this.duration = "Half";
        } else if ((path.equals("quarterNote.png")) || (path.equals("quarterRest.png"))) {
            if (path.equals("quarterNote.png")) {
                this.width = 13;
                this.height = 40;
            } else {
                this.width = 11;
                this.height = 28;
            }
            this.duration = "Quarter";
        } else if ((path.equals("eighthNote.png")) || (path.equals("eighthRest.png"))) {
            if (path.equals("eighthNote.png")) {
                this.width = 40;
                this.height = 40;
            } else {
                this.width = 10;
                this.height = 20;
            }
            this.duration = "Eighth";
        } else {
            if (path.equals("sixteenthNote.png")) {
                this.width = 22;
                this.height = 40;
            } else {
                this.width = 13;
                this.height = 27;
            }
            this.duration = "Sixteenth";
        }
    }

}
