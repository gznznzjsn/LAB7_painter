package by.bsu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PaintPanel extends JPanel{
    private BufferedImage paintImage = new BufferedImage(260, 600, BufferedImage.TYPE_4BYTE_ABGR_PRE);
    private final ArrayList<ArrayList<Point>> lines = new ArrayList<>();
    private final ArrayList<Color> colors = new ArrayList<>();
    private Color currentColor =Color.black;
    private int number = 0;

    public PaintPanel(){
        setPreferredSize(new Dimension(260, 600));
        setMouseEvents();
    }

    public void setColor(Color c){
        currentColor = c;
    }

    private void setMouseEvents(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                colors.add(currentColor);
                lines.add(new ArrayList<>());
                lines.get(number).add(e.getPoint());
            }
            @Override
            public void mouseReleased(MouseEvent e){
                number++;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                lines.get(number).add(e.getPoint());
                updatePaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(paintImage,0,0,null);
    }

    public void updatePaint(){
        Graphics2D g = paintImage.createGraphics();
        g.setStroke(new BasicStroke(3));
        Point start;
        for(int i=0;i<lines.size();i++){
            start = lines.get(i).get(0);
            g.setColor(colors.get(i));
            for(Point point:lines.get(i)){
                g.drawLine(start.x, start.y,point.x,point.y );
                start = point;
            }
        }
        g.dispose();
        repaint();
    }

    public void savePicture(File selectedFile) throws IOException {

        ImageIO.write(paintImage,"PNG",selectedFile);
    }

    public void loadPicture(File selectedFile) throws IOException {
        paintImage = ImageIO.read(selectedFile);
        repaint();
    }

}