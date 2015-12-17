package preprocessing;

import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import imagescience.feature.Laplacian;
import imagescience.image.Image;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class contains the functionality of the preprocessing section
 */
public class PreProcessingImageToolkit implements PreProcessingConstants {

	public static final String DEFAULT_IMAGE = "images/test.png";

	private List<ImagePlus> imageList;

	private PreProcessingSettings settings;



	private String workingPath="";

	/**
	 * standard constructor
	 */
	public PreProcessingImageToolkit() {
		this.imageList = new LinkedList<ImagePlus>();	
		this.initialize();
	}
	
	/**
	 * standard constructor
	 * @param url
	 * @throws FileNotFoundException
	 */
	public PreProcessingImageToolkit(URL url) throws FileNotFoundException {
		this.imageList = new LinkedList<ImagePlus>();
		this.initialize(url);
	}
	
	/**
	 * initialize the preProcessingImage
	 * @param url
	 * @throws FileNotFoundException
	 */
	public void initialize(URL url) throws FileNotFoundException {
		this.imageList.clear();
		if (url.getProtocol().equals("file"))
			try {
				this.imageList.add(IJ.openImage(url.toURI().getPath()));
			} catch (URISyntaxException e) {
				this.imageList.add(IJ.openImage(url.getFile()));
			}
		else
			this.imageList.add(IJ.openImage(url.toString()));
		if (this.imageList.get(0) == null)
			throw new FileNotFoundException(url.toString());
	}
	
	/**
	 * initialize the preProcessingImage
	 */
	public void initialize() {
		try {
			this.initialize(new URL(this.workingPath+PreProcessingImageToolkit.DEFAULT_IMAGE));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Default image '" + PreProcessingImageToolkit.DEFAULT_IMAGE + "' not found.");
			return;
		}
	}

	/**
	 * Makes a grey scaled image of given channel or mean.
	 * 
	 * @param channel
	 *            if GREYSCALEMODE_R, GREYSCALEMODE_G or GREYSCALEMODE_B then
	 *            makes grey scaled of this channel.<br>
	 *            if GREYSCALEMODE_MEAN then makes grey scaled with the mean of
	 *            all channels.
	 * 
	 */
	public ImageProcessor makeGreyScale(ImageProcessor ip, int channel) {
		switch (channel) {
		case GREYSCALEMODE_R:
			ip = ip.toFloat(0, (FloatProcessor) ip.convertToFloat())
					.convertToByte(false);
			break;
		case GREYSCALEMODE_G:
			ip = ip.toFloat(1, (FloatProcessor) ip.convertToFloat())
					.convertToByte(false);
			break;
		case GREYSCALEMODE_B:
			ip = ip.toFloat(2, (FloatProcessor) ip.convertToFloat())
					.convertToByte(false);
			break;
		case GREYSCALEMODE_MEAN:
			ip = ip.convertToByte(false);
			break;
		}
		return ip;
	}

	/**
	 * Finds edges using Sobel operator, Canny-Deriche Filter or Harris Corner
	 * Detector.
	 * 
	 * @param ip
	 * @param mode
	 *            : EDGEMODE_SOBELOPERATOR, EDGEMODE_CANNYDERICHEFILTER (with
	 *            alpha=0.5 recommendation) or EDGEMODE_HARRISCORNERDETECTOR.
	 * @return ImageProcessor with detected edges.
	 */
	public ImageProcessor edgeDetection(ImageProcessor ip, int mode) {
		switch (mode) {
		case EDGEMODE_SOBELOPERATOR:
			ip = ip.duplicate();
			ip.findEdges();
			return ip;
		case EDGEMODE_CANNYDERICHEFILTER:			
			ip = CannyDericheFilter.getDeriche(ip, 0.5F);
			return ip.convertToByte(false);
		case EDGEMODE_LAPLACEFILTER:
			ip = ip.duplicate();
			Laplacian laplacian = new Laplacian();
			Image image = Image.wrap(new ImagePlus(null,ip));
			image = laplacian.run(image, 0.5D);
			return image.imageplus().getProcessor().convertToByte(false);
		case EDGEMODE_NO:
			return ip.duplicate();
		default:
			return null;
		}

	}

	/**
	 * Makes threshold with a given level or makes autoThreshold. If level in
	 * range of 0 - 255 then sets pixels less than or equal to level to 0 and
	 * all other pixels to 255. If level not in range of 0 - 255 then makes
	 * autoThreshold.
	 * 
	 * @param level
	 *            : if between 0 and 255 then makes threshold with this level,<br>
	 *            otherwise makes autoThreshold.
	 */
	public void threshold(ImageProcessor ip, int level) {
		if (level < 0 || level > 255)
			ip.autoThreshold();
		else
			ip.threshold(level);
	}

	/**
	 * @return last image of the imageList
	 */
	public ImagePlus getImagePlus() {
		return this.imageList.get(this.imageList.size() - 1);
	}

	/**
	 * Returns the ImagePlus for the given PreprocessingStep.
	 * 
	 * @param preProcessingStep
	 * @return ImagePlus for the given PreprocessingStep
	 */
	public ImagePlus getImagePlus(int preProcessingStep) {
		if (preProcessingStep >= this.imageList.size())
			return null;
		else
			return this.imageList.get(preProcessingStep);
	}

	/**
	 * Updates ImageProcessors of all Preprocessing-Steps.
	 */
	public void updateToolkit() {
		this.updateToolkit(STEP_GREYSCALE);
		this.updateToolkit(STEP_EDGEDETECTION);
		this.updateToolkit(STEP_THRESHOLD);
	}

	/**
	 * Updates ImageProcessor of the given Preprocessing-Step.
	 * 
	 * @param preProcessingStep
	 */
	public void updateToolkit(int preProcessingStep) {
		ImageProcessor ip;
		switch (preProcessingStep) {
		case STEP_GREYSCALE:
			ip = this.getImagePlus(STEP_ORIGINAL).getProcessor().duplicate();
			ip = this.makeGreyScale(ip, this.settings.getGreyScaleMode());
			this.insertOrUpdate_ImageList(ip, STEP_GREYSCALE);
			break;
		case STEP_EDGEDETECTION:
			ip = this.getImagePlus(STEP_GREYSCALE).getProcessor();
			ip = this.edgeDetection(ip, this.settings.getEdgeDetectionMode());
			this.insertOrUpdate_ImageList(ip, STEP_EDGEDETECTION);
		case STEP_THRESHOLD:
			ip = this.getImagePlus(STEP_EDGEDETECTION).getProcessor()
					.duplicate();
			this.threshold(ip, this.settings.getThresholdLevel());
			this.insertOrUpdate_ImageList(ip, STEP_THRESHOLD);
		default:
			break;
		}
	}

	private void insertOrUpdate_ImageList(ImageProcessor ip,
			int preProcessingStep) {
		if (this.getImagePlus(preProcessingStep) == null)
			this.imageList.add(new ImagePlus(null, ip));
		else
			this.imageList.set(preProcessingStep, new ImagePlus(null, ip));
	}

	public PreProcessingSettings getSettings() {
		return settings;
	}

	public void setSettings(PreProcessingSettings settings) {
		this.settings = settings;
	}
	
	public void setWorkingPath(String workingPath) {
		this.workingPath = workingPath;
	}

}
