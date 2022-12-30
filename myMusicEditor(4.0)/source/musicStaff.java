import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class musicStaff extends JComponent{
    Graphics g;
    int firstLine;
    int number;
    String name;

    LinkedList<Integer> linez = new LinkedList<Integer>();
    LinkedList<addons> additions = new LinkedList<addons>();
    
    public LinkedList<addons> getAdditions() {
        return additions;
    }

    musicStaff(){
    }

    public void editLinez() {
        linez.add(this.firstLine - 10);
        linez.add(this.firstLine);
        for (int i = 1; i < 5; i++) {
            linez.add(this.firstLine + 10*i);
        }
    }

    musicStaff(int beginning) {
        this.firstLine = beginning;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}