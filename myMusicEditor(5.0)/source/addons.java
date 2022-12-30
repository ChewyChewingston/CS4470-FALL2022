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
    String path;
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
    public musicStaff getStaff() {
        return this.m;
    }
    public String getDuration() {
        return this.duration;
    }
    public int getCCX() {
        return this.circleCenterX;
    }
    public int getCCY() {
        return this.circleCenterY;
    }

    public void updateCCX() {
        this.circleCenterX = this.x + this.xoffset;
    }
    public void updateCCY() {
        this.circleCenterY = this.y + this.yoffset;
    }
    public void setStaff(musicStaff s) {
        this.m = s;
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

    public void determinePitch(int a) {
    }
    public void setAccidental(accidental a) {
    }
    public void updateAccidental() {
    }
    public void setCircleCenter() {
        this.yoffset = (int) this.height/2;
        this.xoffset = (int) this.width/2;
        this.updateCCX();
        this.updateCCY();
    }
    
    public addons(String path, int myX, int myY) {
        Image myI = loadImage(path);
        this.i = myI;
        this.x = myX;
        this.y = myY;
        this.path = path;
        this.pitch = "Not a note.";

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
        this.setCircleCenter();
    }

    /*
     * 1: require two ledger line above
     * 2: require one ledger lines above
     * 3: require one ledger line below
     * 4: require two ledger lines below
     */
    public int restLedger() {
        int line = this.m.firstLine;
        int input = this.circleCenterY - (line - 25);
        int result = 0;
        if ((input < 2.5) || ((input >= 2.5) && (input < 7.5))) {
            result = 1;
        } else if (((input >= 7.5) && (input < 12.5)) || ((input >= 12.5) && (input < 17.5))) {
            result = 2;
        } else if (((input >= 67.5) && (input < 72.5)) || ((input >= 72.5) && (input < 77.5))) {
            result = 3;
        } else if (((input >= 77.5) && (input < 82.5)) || (input >= 82.5)) {
            result = 4;
        }
        return result;
    }

}
