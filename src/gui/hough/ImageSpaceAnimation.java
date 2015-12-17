package gui.hough;

import houghtransform.HoughLines;
import ij.ImagePlus;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import appLogic.Simulatable;
import appLogic.Simulation;

/**
 * This class is for the animation of the imagespace during the simulation
 */
public class ImageSpaceAnimation extends ImagePanel implements Simulatable {

	private static final long serialVersionUID = 2788936513776072227L;
	
	private List<Point> nonBlackPixels;

	private Simulation simulation;

	private Iterator<Point> nonBlackPixelsIt;

	private Point lastPixel;

	private ImagePlus imageSpace;


	/**
	 * Standard constructor
	 * @param imp
	 */
	public ImageSpaceAnimation(ImagePlus imp) {
		super(new ImagePlus(imp.getTitle(),imp.getProcessor().duplicate().convertToRGB()));
		imageSpace=imp;
		nonBlackPixels=new HoughLines(imp,256,256).getNonBlackPixels();
		
		nonBlackPixelsIt=nonBlackPixels.iterator();
	}

	/**
	 * Initialize the simulation
	 */
	public void init(Simulation s) {
		simulation=s;
		simulation.configure(nonBlackPixels.size()+1, 30);
	}

	/**
	 * Update the image
	 */
	public void update() {
		
		if(lastPixel!=null){
			image.setColor(new Color(1.0f,0.9f,0.5f));
			image.getProcessor().drawPixel(lastPixel.x, lastPixel.y);
		}
		if(nonBlackPixelsIt.hasNext()){
			lastPixel=nonBlackPixelsIt.next();
			image.setColor(Color.RED);
			image.getProcessor().drawPixel(lastPixel.x, lastPixel.y);

			fireImageChanged();
			
		}
		
	}
	
	/**
	 * @see Simulation
	 */
	public void pause() {
	}

	/**
	 * Reset the image
	 */
	public void reset() {
		image=new ImagePlus(imageSpace.getTitle(),imageSpace.getProcessor().duplicate().convertToRGB());
		fireImageChanged();
		nonBlackPixelsIt=nonBlackPixels.iterator();
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
