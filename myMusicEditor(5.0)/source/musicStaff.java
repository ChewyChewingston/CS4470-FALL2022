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
    public int getNumber() {
        return this.number;
    }
    public int getFirstLine() {
        return this.firstLine;
    }
    public void setNumber(int value) {
        this.number = value;
    }
    public void addAddition(addons a){
        additions.add(a);
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
    public void sortStaff() {
        for (int i = 0; i < additions.size(); i++) { 
            for (int j = i + 1; j < additions.size(); j++) {
                addons temp;
                if (additions.get(j).getCCX() < additions.get(i).getCCX()) {
                    temp = additions.get(i);
                    additions.set(i, additions.get(j));
                    additions.set(j, temp);                    
                }
            }
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