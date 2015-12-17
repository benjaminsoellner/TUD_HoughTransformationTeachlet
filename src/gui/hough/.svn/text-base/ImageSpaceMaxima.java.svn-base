package gui.hough;

import ij.ImagePlus;

import java.awt.Point;

/**
 * This class represents the maxima section for the image space
 */
public class ImageSpaceMaxima extends ImageSpaceWithLines {


	private static final long serialVersionUID = -4191302334680465804L;
	
	private double lastThreshold;

	private boolean lastNonMaximumSuppression;

	/**
	 * Standard constructor
	 * @param imp
	 */
	public ImageSpaceMaxima(ImagePlus imp) {
		super(imp);
		imp.updateImage();
		lastThreshold=0.5;
		lastNonMaximumSuppression=false;
		this.houghPoints = this.houghLines.getMaximaByRelativeIntensity(0.5);
		this.fireImageChanged();	
	}
	
	/**
	 * Updates the threshold and the image with the hough lines
	 * @param threshold
	 * @param nonMaximumSupression
	 * @param highlightMaxima
	 */
	public void updateThreshold(double threshold, boolean nonMaximumSupression, Point highlightMaxima){
		lastThreshold=threshold;
		lastNonMaximumSuppression=nonMaximumSupression;
		this.drawHoughlines(highlightMaxima);
	}
	
	@Override
	public void drawHoughlines(Point highlightHoughPoint) {
		double threshold = this.lastThreshold;
		boolean nonMaximumSupression = this.lastNonMaximumSuppression;
		this.houghPoints.clear();
		this.houghPoints = this.houghLines.getMaximaByRelativeIntensity(threshold);
		if (nonMaximumSupression)
			this.houghPoints = this.houghLines.nonMaximumSupression(
					this.houghPoints,
					this.houghLines.getMaximaImage(this.houghPoints),
					0.01);
		if (highlightHoughPoint != null)
			this.houghPoints.add(highlightHoughPoint);
		this.fireImageChanged();
	}

}
