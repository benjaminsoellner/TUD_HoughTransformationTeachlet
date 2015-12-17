package gui.hough;

import houghtransform.HoughLines;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * This class is an ImagePanel that represents the maxima section for the hough space
 */
public class HoughSpaceMaxima extends ImagePanel implements MouseMotionListener {


	private static final long serialVersionUID = -7746956314427207931L;
		
	private HoughLines houghlines;
	private ImagePlus spacialImage;
	private ImagePlus houghlineImage;

	/**
	 * Standard constructor
	 * @param imp	- an ImagePlus
	 */
	public HoughSpaceMaxima(ImagePlus imp) {
		super(imp);
		imp.updateImage();
		houghlines=new HoughLines(imp,256,256);
		houghlines.fillHoughSpace();
		List<Point> maxima = houghlines.getMaximaByRelativeIntensity(0.5);
		this.image=houghlines.getMaximaImage(maxima);
		fireImageChanged();	
		
	}
	
	/**
	 * updates the pisture if the threshold has changed
	 * @param threshold
	 * @param nonMaximumSupression	
	 */
	public void updateThreshold(double threshold,boolean nonMaximumSupression){
		List<Point> maxima = houghlines.getMaximaByRelativeIntensity(threshold);
		if(nonMaximumSupression) maxima=houghlines.nonMaximumSupression(maxima, houghlines.getMaximaImage(maxima), 0.01);
		image=houghlines.getMaximaImage(maxima);
		fireImageChanged();	
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getSource() == this)
			return;
		if (e.getSource() instanceof ImageSpaceWithLines) {
			ImagePanel c = (ImagePanel) e.getSource();

			Point m = new Point(e.getX(), e.getY());
			if (!c.liesOnImage(m))
				return;
			Point p = c.screenToImage(m);

			HoughLines hl = new HoughLines(this.image, 256, 256);
			hl.accumulateHoughCurve(p.x, p.y);

			this.houghlineImage = hl.getHoughSpaceImage();
			this.fireImageChanged();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		ImageProcessor overlayedImage = this.image.getProcessor().duplicate().convertToRGB();
		overlayedImage.setColor(Color.red);
		if (this.houghlineImage != null) {
			FloatProcessor fp = (FloatProcessor) this.houghlineImage.getProcessor();
			for (int x = 0; x < fp.getWidth(); x++)
				for (int y = 0; y < fp.getHeight(); y++)
					if (fp.getPixelValue(x, y) != 0)
						overlayedImage.drawPixel(x, y);
		}
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.drawImage(overlayedImage.getBufferedImage(), this.affineTranform, null);
	}
}
