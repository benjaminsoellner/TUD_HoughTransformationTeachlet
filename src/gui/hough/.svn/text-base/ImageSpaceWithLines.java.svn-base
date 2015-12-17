package gui.hough;

import houghtransform.HoughLines;
import ij.ImagePlus;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class ImageSpaceWithLines extends ImagePanel implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	protected HoughLines houghLines;
	protected List<Point> houghPoints;
	protected ImagePlus overlayedImage;

	public ImageSpaceWithLines(ImagePlus imp) {
		super(imp);
		this.houghPoints = new ArrayList<Point>();
		this.houghLines = new HoughLines(imp,256,256);
		this.houghLines.fillHoughSpace();
	}

	public void drawHoughlines(Point highlightHoughPoint){
		this.houghPoints.clear();
		if (highlightHoughPoint != null)
			this.houghPoints.add(highlightHoughPoint);
		fireImageChanged();
	}
	
	public void mouseMoved(MouseEvent e){
		if(e.getSource()==this) return;
		if(e.getSource() instanceof HoughSpaceMaxima || e.getSource() instanceof HoughSpaceImageEdit) {
			ImagePanel ip = (ImagePanel)e.getSource();
			Point ipos = ip.screenToImage(new Point(e.getX(),e.getY()));
			this.drawHoughlines(ipos);
		}
	}
	
	public void mouseDragged(MouseEvent arg0) {
		
	}
	
	protected void paintComponent(Graphics g) {
		this.overlayedImage = this.houghLines.getImageWithHoughLines(houghPoints);
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.drawImage(this.overlayedImage.getImage(), this.affineTranform, null);
	}
	
}
