import java.awt.*;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
//import java.imageio.ImageIO;

public class myMusicEditor {
    int numStaves;

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

    Image quarterNoteImage;

    final int FPS_MIN = 0;
    final int FPS_MAX = 4;
    final int FPS_INIT = 2;

    public Image loadImage(String path){
        File file = new File(path);
        try {
            Image img = ImageIO.read(file);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    myMusicEditor() {
        //INITIALIZE FRAME AND JPANELS
        numStaves = 4;
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

        f.setSize(600,600);
        f.setTitle("MY MUSIC EDITOR!");

        editorL = new JLabel("My Music Editor: Showing " + numStaves + " Staves", SwingConstants.CENTER);

        contentArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentArea.setViewportView(editorL);

        quarterNoteImage = loadImage("/images/quarterNote.png");


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
        labelTable.put(new Integer(0), new JLabel("Whole"));
        labelTable.put(new Integer(1), new JLabel("Half"));
        labelTable.put(new Integer(2), new JLabel("Quarter"));
        labelTable.put(new Integer(3), new JLabel("Eighth"));
        labelTable.put(new Integer(4), new JLabel("Sixteenth"));

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

        noteB.setSelected(true);

        //STATUS LABEL
        statusL = new JLabel("Status Text");

        //ACTION LISTENER
        NSM.addActionListener(e -> {
            statusL.setText("New Staff");
            numStaves = numStaves + 1;
            if(numStaves > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            }
            editorL.setText("My Music Editor: Showing " + numStaves + " Staves");
        });

        DSM.addActionListener(e -> {
            statusL.setText("Delete Staff");
            numStaves = numStaves - 1;
            if(numStaves > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            } else {
                DSB.setEnabled(false);
                DSM.setEnabled(false);
            }
            editorL.setText("My Music Editor: Showing " + numStaves + " Staves");
        });

        selectB.addActionListener(e -> {
            statusL.setText("Select Mode");
        });

        penB.addActionListener(e -> {
            statusL.setText("Pen Mode");
        });

        NSB.addActionListener(e -> {
            statusL.setText("New Staff");
            numStaves = numStaves + 1;
            if(numStaves > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            }
            editorL.setText("My Music Editor: Showing " + numStaves + " Staves");
        });
        
        DSB.addActionListener(e -> {            
            statusL.setText("Delete Staff");
            numStaves = numStaves - 1;
            if(numStaves > 1){
                DSB.setEnabled(true);
                DSM.setEnabled(true);
            } else {
                DSB.setEnabled(false);
                DSM.setEnabled(false);
            }
            editorL.setText("My Music Editor: Showing " + numStaves + " Staves");
        });
        playB.addActionListener(e -> {
            statusL.setText("Playing Music");
        });
        stopB.addActionListener(e -> {
            statusL.setText("Stopping Music");
        });

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

        g.drawImage(quarterNoteImage, 100, 100, null);

        //FRAME DISPLAY
        f.setMinimumSize(new Dimension(600, 600));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new myMusicEditor();
    }
}