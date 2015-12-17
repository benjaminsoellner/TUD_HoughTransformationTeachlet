package gui.hough;

import houghtransform.HoughLines;
import ij.ImagePlus;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import appLogic.Simulatable;
import appLogic.Simulation;

/**
 * The houghSpaceAnimation contains the hough animation panel and it is
 * responsible for the simulation of the hough lines.
 */
public class HoughSpaceAnimation extends ImagePanel implements Simulatable {

	private static final long serialVersionUID = -1285386000523678691L;

	private ImagePlus imageSpace;

	// private Simulation simulation;

	private HoughLines houghlines;

	private List<Point> nonBlackPixels;

	private Iterator<Point> nonBlackPixelsIt;

	/**
	 * Standard constructor
	 * 
	 * @param imp
	 */
	public HoughSpaceAnimation(ImagePlus imp) {
		super(new HoughLines(imp, 256, 256).getHoughSpaceImage());
		imageSpace = imp;
		houghlines = new HoughLines(imp, 256, 256);
		nonBlackPixels = houghlines.getNonBlackPixels();

		nonBlackPixelsIt = nonBlackPixels.iterator();
	}

	/**
	 * Go to the next white pixel if there exists one
	 */
	public void update() {
		if (nonBlackPixelsIt.hasNext()) {
			Point p = nonBlackPixelsIt.next();
			houghlines.accumulateHoughCurve(p.x, p.y);

			image = houghlines.getHoughSpaceImage();
			fireImageChanged();

		}

	}

	/**
	 * @see appLogic.Simulation
	 */
	public void init(Simulation s) {
		// simulation=s;
	}

	/**
	 * @see appLogic.Simulation
	 */
	public void pause() {
	}

	/**
	 * Resets the canvas for the hough lines
	 */
	public void reset() {
		houghlines = new HoughLines(imageSpace, 256, 256);
		image = houghlines.getHoughSpaceImage();
		fireImageChanged();
		nonBlackPixelsIt = nonBlackPixels.iterator();
	}

	/**
	 * @see appLogic.Simulation
	 */
	public void resume() {
	}

	/**
	 * @see appLogic.Simulation
	 */
	public void start() {
	}

}
