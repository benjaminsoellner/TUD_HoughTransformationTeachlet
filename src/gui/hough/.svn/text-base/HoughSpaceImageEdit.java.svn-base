package gui.hough;

import houghtransform.HoughLines;
import ij.ImagePlus;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * This class contains the functionality for the ImageEditSection.
 */
public class HoughSpaceImageEdit extends ImagePanel implements
		MouseMotionListener {

	private static final long serialVersionUID = 7267191950147922654L;

	private ImagePlus imageSpace;

	/**
	 * Standard constructor 
	 * @param imp
	 */
	public HoughSpaceImageEdit(ImagePlus imp) {
		super(imp);
		image = new HoughLines(imp, 256, 256).getHoughSpaceImage();
		imageSpace = imp;

	}

	/**
	 * MouseMoved Listener - draws a point on the HoughSpaceImageEdit- Panel 
	 */
	public void mouseMoved(MouseEvent e) {
		if (e.getSource() == this)
			return;
		if (e.getSource() instanceof ImagePanel) {
			ImagePanel c = (ImagePanel) e.getSource();

			Point m = new Point(e.getX(), e.getY());
			if (!c.liesOnImage(m))
				return;
			Point p = c.screenToImage(m);

			HoughLines hl = new HoughLines(imageSpace, 256, 256);
			hl.accumulateHoughCurve(p.x, p.y);

			image = hl.getHoughSpaceImage();
			fireImageChanged();
		}
	}

	/**
	 * Empty mouseDragged-listener
	 */
	public void mouseDragged(MouseEvent e) {

	}

}
