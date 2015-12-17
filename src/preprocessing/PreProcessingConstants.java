package preprocessing;

/**
 * This Interface contains the preprocessing constants
 */
public interface PreProcessingConstants {

	public static final int GREYSCALEMODE_R = 0;
	public static final int GREYSCALEMODE_G = 1;
	public static final int GREYSCALEMODE_B = 2;
	public static final int GREYSCALEMODE_MEAN = 3;

	public static final int STEP_ORIGINAL = 0;
	public static final int STEP_GREYSCALE = 1;
	public static final int STEP_EDGEDETECTION = 2;
	public static final int STEP_THRESHOLD = 3;

	public static final int EDGEMODE_SOBELOPERATOR = 0;
	public static final int EDGEMODE_CANNYDERICHEFILTER = 1;
	public static final int EDGEMODE_LAPLACEFILTER = 2;
	public static final int EDGEMODE_NO = 3;
}
