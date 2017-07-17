package fr.pizzeria.util;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/**
 * @author etbourreau
 * JComponent's extension to create a background on a JFrame
 */
public class ImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
