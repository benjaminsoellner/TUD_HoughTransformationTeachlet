package gui.sections.topic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * This class creates a Button for the topicSection
 */
public class TopicButton extends JButton {


	private static final long serialVersionUID = 1L;
	private boolean hover;
	private boolean active = false;
	private String label;

	/**
	 * Return if the Button is active
	 * @return true if the button is active or false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Set the button to active or not active
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
		//repaint();
	}

	/**
	 * Standard constructor
	 * @param label
	 */
	public TopicButton(String label) {
		super();
		this.label=label;
		initMouseHover();
	}

	/**
	 * Highlight a button when the mouse is hovering over it
	 */
	public void initMouseHover() {
		this.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent arg0) {

				super.mouseEntered(arg0);
				if (!active)
					hover = true;
				repaint();
			}

			public void mouseExited(MouseEvent arg0) {
				super.mouseExited(arg0);
				hover = false;
				repaint();
			}

		});
		hover = false;
	}
	
	

	/**
	 * Paint the button
	 */
	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setBackground(getBackground());
		g2d.clearRect(0, 0, getSize().width, getSize().height);

		Color cBG = new Color(190, 210, 220);
		//Color cActive = new Color(25 , 140 , 255);
		Color cStroke = new Color(25, 140, 225);

		int w = getParent().getWidth() / 6;
		int h = getParent().getHeight()-4;
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(w - 22, 0);
		p.addPoint(w, h/2);
		p.addPoint(w - 23, h);
		p.addPoint(0, h);
		if (hover)
			g2d.setColor(new Color(255 , 255 , 255));		
		else if (active)
			g2d.setColor(cStroke);
		else
			g2d.setColor(cBG);
		g2d.fillPolygon(p);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(cStroke);
		g2d.drawPolygon(p);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Verdana",0,9));
		int labelWidth=SwingUtilities.computeStringWidth(g.getFontMetrics(), label);
		
		if(labelWidth>getSize().width-30){
			int charWidth=labelWidth/label.length();
			int nrChars=(getSize().width-30)/charWidth;
			String newLabel=label.substring(0, Math.max(0,Math.min(nrChars,label.length()-1)))+"...";
			int newLabelWidth=SwingUtilities.computeStringWidth(g.getFontMetrics(), newLabel);
			g2d.drawString(newLabel, 
					getSize().width/2-newLabelWidth/2, getSize().height/2+2);
		}else{
			g2d.drawString(label, getSize().width/2-labelWidth/2, getSize().height/2+2);
		}
		
		
	}



}
