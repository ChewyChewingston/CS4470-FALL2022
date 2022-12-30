import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.*;

public class myMusicEditor {
    DollarRecognizer dr;
    Graphics g;

    JFrame f;
    JPanel toolBar;
    JPanel radioBox;
    JPanel radioWrapper;
    JPanel subFrame1;
    JPanel subFrame2;
    JPanel subFrame3;
    JPanel subFrame4;
    JPanel subFrame5;

    JLabel statusL;
    JLabel editorL;

    JMenuBar menuBar;
    JMenu File, Edit;
    JMenuItem exitM, NSM, DSM;

    JButton selectB, penB, NSB, DSB, playB, stopB;
    ButtonGroup radioButtons;

    JSlider slide;

    JScrollPane contentArea;
    JViewport vP;
    musicView mv;

    boolean drawMode;
    boolean selectMode;
    int currentIndex;
    
    /**
     * 0: whole
     * 1: Half
     * 2: Quarter
     * 3: Eighth
     * 4: Sixteenth
     */
    int duration;
    /**
     * 0: Note
     * 1: Rest
     * 2: Flat
     * 3: Sharp
     */
    int type;

    final int FPS_MIN = 0;
    final int FPS_MAX = 4;
    final int FPS_INIT = 2;

    protected Image loadImage(String path){
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public addons checkSelected(int x, int y) {
        addons r;
        for (addons a : mv.allAdds) {
            if ((a instanceof note) && (a.a != null)) {
                if ((a.a.x < x) && ((a.a.x + a.a.width) > x)) {
                    if ((a.a.y < y) && ((a.a.y + a.a.height) > y)) {
                        r = a.a;
                        return r;
                    }
                }
            }
            if ((a.x < x) && ((a.x + a.width) > x)) {
                if ((a.y < y) && ((a.y + a.height) > y)) {
                    r = a;
                    return r;
                }
            }
            
        }
        return null;
    }

    myMusicEditor() {
        //INITIALIZE FRAME AND JPANELS
        f = new JFrame();
        contentArea = new JScrollPane();
        toolBar = new JPanel();
        radioBox = new JPanel();
        radioWrapper = new JPanel();
        subFrame1 = new JPanel();
        subFrame2 = new JPanel();
        subFrame3 = new JPanel();
        subFrame4 = new JPanel();
        subFrame5 = new JPanel();

        mv = new musicView();

        f.setSize(600,600);
        f.setTitle("MY MUSIC EDITOR!");

        for (int i = 0; i<4; i++) {
            mv.staves.add(new musicStaff());
        }        

        type = 0;
        duration = 2;

        contentArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mv.setPreferredSize(new Dimension(1100, 30));
        mv.revalidate();
        mv.repaint();
        contentArea.setViewportView(mv);

        //LAYOUT MANAGERS
        f.setLayout(new BorderLayout());
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.PAGE_AXIS));
        radioBox.setLayout(new BoxLayout(radioBox, BoxLayout.PAGE_AXIS));
        radioWrapper.setLayout(new FlowLayout());
        subFrame1.setLayout(new BoxLayout(subFrame1, BoxLayout.X_AXIS));
        subFrame2.setLayout(new BoxLayout(subFrame2, BoxLayout.X_AXIS));
        subFrame3.setLayout(new BoxLayout(subFrame3, BoxLayout.X_AXIS));
        subFrame4.setLayout(new BoxLayout(subFrame4, BoxLayout.X_AXIS));
        subFrame5.setLayout(new FlowLayout());

        //SET MENU BAR
        menuBar = new JMenuBar();
        File = new JMenu("File");
        Edit = new JMenu("Edit");

        exitM = new JMenuItem("Exit");
        NSM = new JMenuItem("New Staff");
        DSM = new JMenuItem("Delete Staff");

        File.add(exitM);
        Edit.add(NSM);
        Edit.add(DSM);

        menuBar.add(File);
        menuBar.add(Edit);

        //INITIALIZE SLIDER
        slide = new JSlider(JSlider.VERTICAL, FPS_MIN, FPS_MAX, FPS_INIT);
        slide.setMajorTickSpacing(1);
        slide.setPaintTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(0, new JLabel("Whole"));
        labelTable.put(1, new JLabel("Half"));
        labelTable.put(2, new JLabel("Quarter"));
        labelTable.put(3, new JLabel("Eighth"));
        labelTable.put(4, new JLabel("Sixteenth"));
        
        ChangeListener cL = new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = (int)source.getValue();
                    if (value == 0) {
                    } else if (value == 1) {
                    } else if (value == 2) {
                    } else if (value == 3) {
                    } else if (value == 4) {
                    }
                }
            }
        };
        slide.addChangeListener(cL);
        slide.setLabelTable(labelTable);
        slide.setPaintLabels(true);

        //INITIALIZE BUTTONS
        JButton selectB = new JButton("Select");
        JButton penB = new JButton("Pen");
        JButton NSB = new JButton("New Staff");
        JButton DSB = new JButton("Delete Staff");
        JButton playB = new JButton("Play");
        JButton stopB = new JButton("Stop");
        
        DSB.setEnabled(true);

        //INITIALIZE RADIO BUTTONS
        radioButtons = new ButtonGroup();

        JRadioButton noteB = new JRadioButton("Note");
        JRadioButton restB = new JRadioButton("Rest");
        JRadioButton flatB = new JRadioButton("Flat");
        JRadioButton sharpB = new JRadioButton("Sharp");

        radioButtons.add(noteB);
        radioButtons.add(restB);
        radioButtons.add(flatB);
        radioButtons.add(sharpB);

        noteB.addActionListener(e -> {});
        restB.addActionListener(e -> {});
        flatB.addActionListener(e -> {});
        sharpB.addActionListener(e -> {});

        noteB.setSelected(true);

        //STATUS LABEL
        statusL = new JLabel("Status Text");

        //ACTION LISTENER
        NSM.addActionListener(e -> {
            statusL.setText("New Staff");
            mv.staves.add(new musicStaff());

            if(mv.getStavesSize() > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            }
            mv.setPreferredSize(new Dimension(1100, mv.getStavesSize()*105));

            mv.revalidate();
            mv.repaint();
            contentArea.setViewportView(mv);
        });
        DSM.addActionListener(e -> {
            statusL.setText("Delete Staff");
            mv.staves.remove();

            if(mv.getStavesSize() > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            } else {
                DSB.setEnabled(false);
                DSM.setEnabled(false);
            }

            mv.setPreferredSize(new Dimension(1100, mv.getHeight()-105));  

            if (selectMode) {
                selectMode = false;
            }
            drawMode = true;

            mv.revalidate();
            mv.repaint();
            contentArea.setViewportView(mv);
        });
        selectB.addActionListener(e -> {
            statusL.setText("Select Mode");
            drawMode = false;
            selectMode = true;
        });
        penB.addActionListener(e -> {
            statusL.setText("Pen Mode");
            selectMode = false;
            drawMode = true;
        });
        NSB.addActionListener(e -> {
            statusL.setText("New Staff");
            
            selectMode = false;
            drawMode = false;

            mv.staves.add(new musicStaff());

            if(mv.getStavesSize() > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            }
            
            mv.setPreferredSize(new Dimension(1100, mv.getStavesSize()*105));
            mv.revalidate();
            mv.repaint();
            contentArea.setViewportView(mv);
        });
        DSB.addActionListener(e -> {            
            statusL.setText("Delete Staff");
            
            selectMode = false;
            drawMode = false;

            mv.staves.remove();

            if(mv.getStavesSize() > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            } else {
                DSB.setEnabled(false);
                DSM.setEnabled(false);
            }
            
            mv.setPreferredSize(new Dimension(1100, mv.getHeight()-105));  

            mv.revalidate();
            mv.repaint();
            contentArea.setViewportView(mv);
        });
        playB.addActionListener(e -> {
            statusL.setText("Playing Music");
            selectMode = false;
            drawMode = false;
            mv.playMode = true;
            penB.setEnabled(false);
            selectB.setEnabled(false);
            stopB.setEnabled(false);
            mv.delTarget = null;
            
            mv.sortList();
            currentIndex = 0;
            mv.currentNote = mv.animPointList.getFirst();
            mv.timer = new Timer(10, null);
            mv.timer.addActionListener(new ActionListener() {       
                @ Override
                 public void actionPerformed(ActionEvent e) {
                    int step = mv.currentNote.getStep();

                    if (currentIndex >= mv.animPointList.size()) {
                        stopB.setEnabled(true);
                        mv.timer.stop();
                    } else {
                        mv.currentNote = mv.animPointList.get(currentIndex);
                        // If a note's y is greater than the staff width, 
                        // move to next note until find note within staff
                        if(mv.currentNote.getThisY() > 1030) {
                            currentIndex++;
                        }
                    }

                    if(mv.currentNote.reachedBottom) {
                        mv.currentNote.setThisY(mv.currentNote.getThisY()-step);
                        if (mv.currentNote.getThisY() < (mv.currentNote.getOrigHeight()-20)) {
                            if (currentIndex >= mv.animPointList.size()) {
                                stopB.setEnabled(true);
                                mv.timer.stop();
                            } else {
                                currentIndex++;
                            }
                        }
                    } else {
                        mv.currentNote.setThisY(mv.currentNote.getThisY()+step);
                        System.out.println(mv.currentNote.getThisY()+", "+mv.currentNote.getOrigHeight());
                        if (mv.currentNote.getThisY() >= (mv.currentNote.getOrigHeight())) {
                            mv.currentNote.reachedBottom = true;
                        }
                    }
                    mv.repaint();
                 }
            });
            mv.timer.start();

            
            
        });
        stopB.addActionListener(e -> {
            selectMode = false;
            drawMode = false;
            
            penB.setEnabled(true);
            selectB.setEnabled(true);

            statusL.setText("Stopping Music");

            mv.timer.stop();

            try{
                mv.currentNote = null;
            } catch (java.lang.NullPointerException ex) {
                System.out.println("Animation stopped");
            }
            
            mv.revalidate();
            mv.repaint();
            mv.playMode = false;
        });

        MouseAdapter ma = new MouseAdapter() {
            addons selected;
            int xOff;
            int yOff;
            DollarRecognizer dr = new DollarRecognizer();

            public void mousePressed(java.awt.event.MouseEvent e) {
                mv.requestFocusInWindow();
                if (drawMode) {
                    if (selected != null) {
                        selected = null;
                    }
                    mv.addCoordinate(e.getX(), e.getY());
                    contentArea.revalidate();
                    contentArea.repaint();
                } else if (selectMode) {
                    selected = checkSelected(e.getX(), e.getY());
                    mv.setDeleteTarget(selected);

                    if (selected != null) {                        
                        //make sure click remains relative and not always at 0,0
                        xOff = e.getX()-selected.x;
                        yOff = e.getY()-selected.y;

                        //update pitch in text
                        statusL.setText(selected.getPitch());
                    }
                    contentArea.revalidate();
                    contentArea.repaint();
                }
            }
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (drawMode) {
                    mv.addCoordinate(e.getX(), e.getY());
                }
                if (selected != null) {
                    if (selectMode) {
                        if (!(selected instanceof accidental)) {
                            selected.setX(e.getX() - xOff);
                            selected.setY(e.getY() - yOff);
                            mv.setAddStaff(selected);
                        }
                    }
                    selected.updateCCX();
                    selected.updateCCY();

                    if (selected instanceof note){
                        if (selected.m != null) {
                            mv.detectChord((note) selected);
                        }
                    }
                    mv.setAddStaff(selected);
                    if ((selected.m != null) && (!(selected instanceof accidental))) {
                        mv.lineSnapping(selected, selected.circleCenterY - (selected.m.firstLine - 25));
                        statusL.setText(selected.getPitch());
                    }
                }
                contentArea.revalidate();
                contentArea.repaint();
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (drawMode) {
                    Result result = dr.recognize(mv.strokeCoordinates);
                    
                    ArrayList<String> bannedShapes = new ArrayList<>();
                    bannedShapes.add("check");
                    bannedShapes.add("caret");
                    bannedShapes.add("zig-zag");
                    bannedShapes.add("arrow");
                    bannedShapes.add("left square bracket");
                    bannedShapes.add("right square bracket");
                    bannedShapes.add("v");
                    bannedShapes.add("delete");
                    bannedShapes.add("left curly brace");
                    bannedShapes.add("pigtail");

                    if ((result.getMatchedTemplate() == null) || (bannedShapes.contains(result.getName()))) {
                        statusL.setText("No match");                        
                    } else {
                        selected = mv.recognize(result);
                        mv.setAddStaff(selected);
                        
                        if (selected instanceof accidental) {
                            int startX = (int) mv.strokeCoordinates.get(0).getX();
                            int startY = (int) mv.strokeCoordinates.get(0).getY();
                            
                            mv.checkAccidentalBound(startX, startY, (accidental) selected);
                            if (selected.n == null) {
                                selected = null;
                                mv.delTarget = null;
                            }
                        } else {
                            if (selected.m != null) {
                                mv.setDeleteTarget(selected);
                                mv.addAddition(selected);
                            }
                            
                            if (selected instanceof note) {
                                mv.lineSnapping(selected, selected.circleCenterY - (selected.m.firstLine - 25));
                                mv.text = selected.duration + " Note at " + selected.pitch;
                            } else {                                
                                mv.lineSnapping(selected, selected.circleCenterY - (selected.m.firstLine - 25));
                                selected.setY((int) mv.strokeCoordinates.get(0).getY());
                                mv.text = selected.duration + " Rest";
                            }                        
                        }
                        statusL.setText(mv.text);
                    }
                }
                // if (selected != null) {
                //     mv.setAddStaff(selected);
                // }
                mv.clearCoordinates();
                mv.highlightAllNotes = false;   
                contentArea.revalidate();
                contentArea.repaint();                
            }
        };

        contentArea.addMouseListener(ma);
        contentArea.addMouseMotionListener(ma);

        //ADD TO JPANEL
        subFrame1.add(selectB);
        subFrame1.add(penB);

        subFrame2.add(NSB);
        subFrame2.add(DSB);

        subFrame3.add(playB);
        subFrame3.add(stopB);

        subFrame5.add(slide);

        radioBox.add(noteB);
        radioBox.add(restB);
        radioBox.add(flatB);
        radioBox.add(sharpB);
        radioBox.setAlignmentY(Component.TOP_ALIGNMENT);
        radioWrapper.add(radioBox);
        subFrame4.add(radioWrapper);
        subFrame4.add(subFrame5);

        toolBar.add(subFrame1);
        toolBar.add(subFrame2);
        toolBar.add(subFrame3);
        toolBar.add(subFrame4);

        //ADD TO FRAME
        f.setJMenuBar(menuBar);
        f.add(toolBar, BorderLayout.WEST);
        f.add(statusL, BorderLayout.SOUTH);
        statusL.setHorizontalAlignment(JLabel.CENTER);;
        f.add(contentArea, BorderLayout.CENTER);
        f.addKeyListener(mv);
        //FRAME DISPLAY
        f.setMinimumSize(new Dimension(600, 600));
        f.setVisible(true);
        contentArea.requestFocusInWindow();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new myMusicEditor();
    }
}