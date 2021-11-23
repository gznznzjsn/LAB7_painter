package by.bsu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

public class MainWindow {
    private final JFrame frame = new JFrame("Draw your own Bundesdienstflagge");
    private final JPanel contentPane = new JPanel(null);
    private final PaintPanel paintPanel = new PaintPanel();
    private final JPanel panel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(panel);
    private final JPanel filePanel = new JPanel(new GridLayout(2,1,0,0));


    private final JPanel radioPanel= new JPanel(new GridLayout(3,1,0,0));


    public MainWindow(){
        setScrollPane();
        setRadioButtons();
        setFilePanel();
        setFrame();
    }
    private void setScrollPane(){
        panel.add(paintPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 20, 300, 300);
    }
    private void setRadioButtons(){
        JRadioButton radioBlack = new JRadioButton("Black",true);
        JRadioButton radioRed = new JRadioButton("Red");
        JRadioButton radioGolden = new JRadioButton("Golden");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioBlack);
        radioGroup.add(radioRed);
        radioGroup.add(radioGolden);

        radioBlack.addActionListener(e -> paintPanel.setColor(Color.decode("#000000")));
        radioRed.addActionListener(e -> paintPanel.setColor(Color.decode("#DD0000")));
        radioGolden.addActionListener(e -> paintPanel.setColor(Color.decode("#FFCE00")));

        radioPanel.setBounds(400,20,100,150);
        radioPanel.setBorder(BorderFactory.createTitledBorder("Current color"));
        radioPanel.add(radioBlack);
        radioPanel.add(radioRed);
        radioPanel.add(radioGolden);
    }

    private void setFilePanel(){
        JButton buttonSave = new JButton("Save");
        JButton buttonLoad = new JButton("Load");

        buttonSave.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser("./resources");
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.PNG","*.*"));
            if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                try {
                    paintPanel.savePicture(fileChooser.getSelectedFile());
                }
                catch ( IOException e ) {
                    System.out.println("Ошибка при нажатии Save, fileChooser виноват");
                }

            }
        });
        buttonLoad.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            if ( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
                try {
                    paintPanel.loadPicture(fileChooser.getSelectedFile());
                }
                catch ( IOException e ) {
                    System.out.println("Ошибка при нажатии Load, fileChooser виноват");
                }

            }
        });

        filePanel.setBounds(400,210,100,100);
        filePanel.add(buttonSave);
        filePanel.add(buttonLoad);
    }

    private void setFrame(){
        contentPane.setPreferredSize(new Dimension(600, 400));
        contentPane.add(scrollPane);
        contentPane.add(radioPanel);
        contentPane.add(filePanel);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
