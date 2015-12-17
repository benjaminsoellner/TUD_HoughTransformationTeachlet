package appLogic;

import gui.GuiManager;
import gui.ImageIconRegistry;

import java.applet.Applet;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import preprocessing.PreProcessingImageToolkit;
import preprocessing.PreProcessingSettings;

/**
 * This class is the starting point of the application.
 * The main components are initialized here.
 */
public class HoughTransApplet extends Applet {

	private static final long serialVersionUID = 5481840564113012422L;

	/**
	 * Initialize the applet
	 */
	public void init() {

		// load image
		PreProcessingImageToolkit toolkit = null;

		try {
			URL url = new URL(this.getWorkingPath() + "images/test.png");
			toolkit = new PreProcessingImageToolkit(url);
			toolkit.setWorkingPath(getWorkingPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// load icons
		ImageIconRegistry imageReg = ImageIconRegistry.getInstance();

		try {
			imageReg.addIcon("mousenavi", new ImageIcon(new URL(getWorkingPath()+"images/mousenavi.png")));
			imageReg.addIcon("nomousenavi", new ImageIcon(new URL(getWorkingPath()+"images/uselessNavigationHeightEqualityOnePixelGraphicWithExtremeDistractingName.png")));
			imageReg.addIcon("clear", new ImageIcon(new URL(getWorkingPath()+"images/clear.png")));
			imageReg.addIcon("rubber", new ImageIcon(new URL(getWorkingPath()+"images/rubber.png")));
			imageReg.addIcon("paint", new ImageIcon(new URL(getWorkingPath()+"images/paint.png")));
			imageReg.addIcon("zoom1to1", new ImageIcon(new URL(getWorkingPath()+"images/zoom1to1.png")));
			imageReg.addIcon("zoomfit", new ImageIcon(new URL(getWorkingPath()+"images/zoomfit.png")));
			imageReg.addIcon("move", new ImageIcon(new URL(getWorkingPath()+"images/move.png")));
			imageReg.addIcon("zoom", new ImageIcon(new URL(getWorkingPath()+"images/zoom.png")));
			imageReg.addIcon("reset",new ImageIcon(new URL(getWorkingPath()+"images/reset.png")));
			imageReg.addIcon("pause",new ImageIcon(new URL(getWorkingPath()+"images/pause.png")));
			imageReg.addIcon("play",new ImageIcon(new URL(getWorkingPath()+"images/play.png")));
			imageReg.addIcon("step",new ImageIcon(new URL(getWorkingPath()+"images/step.png")));
		} catch (MalformedURLException e) {
			System.err.println("Error loading icons.");
		}

		// preprocessing
		toolkit.setSettings(new PreProcessingSettings());
		toolkit.updateToolkit();

		GuiManager gm = new GuiManager(this, toolkit);
		gm.guiInit();

	}

	/**
	 * Returns a String containing the working path
	 */
	public String getWorkingPath() {
		String file = getDocumentBase().toString().split(".*/")[1];
		String path = getDocumentBase().toString().substring(0,
				getDocumentBase().toString().length() - file.length());
		path = path.replaceAll("localhost", "127.0.0.1");
		return path;
	}

}
