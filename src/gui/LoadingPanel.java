package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LoadingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private double angle;
    private double extent;

    private double angleDelta = -1;
    private double extentDelta = -5;

    private boolean flip = false;

    public LoadingPanel() {
        setBackground(new Color(60, 63, 65));
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += angleDelta;
                extent += extentDelta;
                if (Math.abs(extent) % 360.0 == 0) {
                    angle = angle - extent;
                    flip = !flip;
                    if (flip) {
                        extent = 360.0;
                    } else {
                        extent = 0.0;
                    }
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int diameter = Math.min(getWidth(), getHeight()) / 2; 
        int x = (getWidth() - diameter) / 2; 
        int y = (getHeight() - diameter) / 2;

        Arc2D.Double arc = new Arc2D.Double(x, y, diameter, diameter, angle, extent, Arc2D.OPEN);
        BasicStroke stroke = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND); 
        g2d.setStroke(stroke);
        g2d.setColor(Color.WHITE);
        g2d.draw(arc);
        g2d.dispose();
    }
}
