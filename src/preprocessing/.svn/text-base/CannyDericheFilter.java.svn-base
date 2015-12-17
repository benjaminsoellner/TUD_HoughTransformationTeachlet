package preprocessing;
import java.util.ArrayList;

import ij.*;
import ij.process.*;
import ij.plugin.filter.RankFilters;


/** Class containing some static methods for Edge Detection, using median filter,
 * Canny-Deriche filtering, double tresholding, hysteresis tresholding. These are 
 * meant to be implemented in another plugin, hence the basic functionality. 
 * Methods based on the plugins deriche_ and hysteresis_
 * @author 	thomas.boudier@snv.jussieu.fr
 * @author 	Joris FA Meys
 * @created	4/2/2009
 * @version 1.0
 * */
public class CannyDericheFilter {
	
	static final int BYTE=0, SHORT=1, FLOAT=2, OTHER=3;
	static private boolean typed = false;
	static private int type;
	
	/**Edge detection by subsequent application of a median filter,
	 * a Canny-Deriche filter double tresholding and hysteresis.
	 * This method works especially well for detection of area edges
	 * in pictures with a high granularity, like confocal pics at high
	 * magnification. Good results were obtained with radius = 5,
	 * alpha = 0.5, upper = 100, lower = 50
	 * 
	 * @param ip		ImageProcessor
	 * @param radius	radius for the median filter
	 * @param alpha		alpha setting for Canny-Deriche filter
	 * @param upper		Upper treshold for double tresholding
	 * @param lower		lower treshold for double tresholding
	 * @return 	ImageProcessor with 16bit greyscale filtered image
	 * */
	public static ImageProcessor areaEdge(ImageProcessor ip, 
											double radius, 
											float alpha, 
											float upper, 
											float lower){
		int type = getType(ip); 
		if (type==OTHER){
			IJ.error("area detection","No action taken. Greyscale or pseudocolored images required!");
			return ip;
		}
		ImageProcessor ipcopy=getDeriche(ip, alpha, radius);
		ipcopy = trin(ipcopy, upper, lower);
		ipcopy = hyst(ipcopy);
		return ipcopy;
	}
	
	/**Canny-Deriche filtering without previous median filter.
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @return	ImageProcessor with 16bit greyscale filtered image.
	 * */
	public static ImageProcessor getDeriche(ImageProcessor ip, float alpha){
		return getDeriche(ip, alpha,0);
		
	}
	
	/**Canny-Deriche filtering with previous median filter.
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @param 	radius	radius for the median filter
	 * @return	ImageProcessor with 16bit greyscale filtered image.
	 * */
	
	public static ImageProcessor getDeriche(ImageProcessor ip, float alpha, double radius){
		int type = getType(ip);
		if (type==OTHER){
			IJ.error("Deriche","No action taken. Greyscale or pseudocolored image required!");
			return ip;
		}
		ArrayList<double[]> arrays = null;
		ImageProcessor ip2= ip.duplicate();
		RankFilters filter = new RankFilters();
		if (radius > 0)filter.rank(ip2, radius, RankFilters.MEDIAN);
		
		arrays = dericheCalc(ip2,alpha);
		double[] norm = arrays.get(0);
		double[] angle = arrays.get(1);
		FloatProcessor normfp = new FloatProcessor(ip2.getWidth(), ip2.getHeight(), norm);
		normfp.resetMinAndMax();
		FloatProcessor anglefp = new FloatProcessor(ip2.getWidth(), ip2.getHeight(), angle);
		anglefp.resetMinAndMax();
		ip2 = nonMaximalSuppression(normfp, anglefp);
		return ip2;
	}
	
	/**Canny-Deriche filtering without previous median filter. Angle output
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @return	FloatProcessor with angle image
	 * */
	
	public static FloatProcessor getDericheAngle(ImageProcessor ip, float alpha){
		return getDericheAngle(ip, alpha,0);
	}

	/**Canny-Deriche filtering with previous median filter.
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @param 	radius	radius for the median filter
	 * @return	FloatProcessor with angle image
	 * */

	public static FloatProcessor getDericheAngle(ImageProcessor ip, float alpha, double radius){
		int type = getType(ip);
		if (type==OTHER){
			IJ.error("Deriche","No action taken. Greyscale or pseudocolored image required");
			return (FloatProcessor) ip;
		}
		ImageProcessor ip2= ip.duplicate();
		RankFilters filter = new RankFilters();
		if (radius > 0)filter.rank(ip2, radius, RankFilters.MEDIAN);

		double[] angle = dericheCalc(ip2,alpha).get(1);
		FloatProcessor anglefp = new FloatProcessor(ip2.getWidth(), ip2.getHeight(), angle);
		anglefp.resetMinAndMax();
		return anglefp;
	}
	/**Canny-Deriche filtering without previous median filter. Norm output
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @return	FloatProcessor with Norm output
	 * */
	public static FloatProcessor getDericheNorm(ImageProcessor ip, float alpha){
		return getDericheNorm(ip, alpha,0);
	}
	
	/**Canny-Deriche filtering with previous median filter.
	 * @param	ip		ImageProcessor
	 * @param	alpha	alpha value for Deriche filter
	 * @param 	radius	radius for the median filter
	 * @return	FloatProcessor with Norm output
	 * */
	
	public static FloatProcessor getDericheNorm(ImageProcessor ip, float alpha, double radius){
		int type = getType(ip); 
		if (type==OTHER){
			IJ.error("Deriche","greyscale or pseudocolored images required");
			return (FloatProcessor) ip;
		}
		ImageProcessor ip2= ip.duplicate();
		RankFilters filter = new RankFilters();
		if (radius > 0)filter.rank(ip2, radius, RankFilters.MEDIAN);
		
		double[] norm = dericheCalc(ip2,alpha).get(0);
		FloatProcessor normfp = new FloatProcessor(ip2.getWidth(), ip2.getHeight(), norm);
		normfp.resetMinAndMax();
		return normfp;
	}
	
	/**
	 *  Suppression of non local-maxima
	 *
	 *@param  grad  the norm gradient image
	 *@param  ang   the angle gradient image
	 *@return       the image with non local-maxima suppressed
	 */
	
//-------------------------TRESHOLDING METHODS---------------------------------------//	
	/**
	 *  Double thresholding
	 *
	 *@param  ima  original image
	 *@param  T1   high threshold
	 *@param  T2   low threshold
	 *@return      "trinarised" image
	 */
	public static ImageProcessor trin(ImageProcessor ima, float T1, float T2) {
		int la = ima.getWidth();
		int ha = ima.getHeight();
		ByteProcessor res = new ByteProcessor(la, ha);
		float pix;

		for (int x = 0; x < la; x++) {
			for (int y = 0; y < ha; y++) {
				pix = ima.getPixelValue(x, y);
				if (pix >= T1) {
					res.putPixel(x, y, 255);
				} else if (pix >= T2) {
					res.putPixel(x, y, 128);
				}
			}
		}
		return res;
	}


	/**
	 *  Hysteresis thresholding
	 *
	 *@param  ima  original image
	 *@return      thresholded image
	 */
	public static ImageProcessor hyst(ImageProcessor ima) {
		int la = ima.getWidth();
		int ha = ima.getHeight();
		ImageProcessor res = ima.duplicate();
		boolean change = true;

		// connection
		while (change) {
			change = false;
			for (int x = 1; x < la - 1; x++) {
				for (int y = 1; y < ha - 1; y++) {
					if (res.getPixelValue(x, y) == 255) {
						if (res.getPixelValue(x + 1, y) == 128) {
							change = true;
							res.putPixelValue(x + 1, y, 255);
						}
						if (res.getPixelValue(x - 1, y) == 128) {
							change = true;
							res.putPixelValue(x - 1, y, 255);
						}
						if (res.getPixelValue(x, y + 1) == 128) {
							change = true;
							res.putPixelValue(x, y + 1, 255);
						}
						if (res.getPixelValue(x, y - 1) == 128) {
							change = true;
							res.putPixelValue(x, y - 1, 255);
						}
						if (res.getPixelValue(x + 1, y + 1) == 128) {
							change = true;
							res.putPixelValue(x + 1, y + 1, 255);
						}
						if (res.getPixelValue(x - 1, y - 1) == 128) {
							change = true;
							res.putPixelValue(x - 1, y - 1, 255);
						}
						if (res.getPixelValue(x - 1, y + 1) == 128) {
							change = true;
							res.putPixelValue(x - 1, y + 1, 255);
						}
						if (res.getPixelValue(x + 1, y - 1) == 128) {
							change = true;
							res.putPixelValue(x + 1, y - 1, 255);
						}
					}
				}
			}
			if (change) {
				for (int x = la - 2; x > 0; x--) {
					for (int y = ha - 2; y > 0; y--) {
						if (res.getPixelValue(x, y) == 255) {
							if (res.getPixelValue(x + 1, y) == 128) {
								change = true;
								res.putPixelValue(x + 1, y, 255);
							}
							if (res.getPixelValue(x - 1, y) == 128) {
								change = true;
								res.putPixelValue(x - 1, y, 255);
							}
							if (res.getPixelValue(x, y + 1) == 128) {
								change = true;
								res.putPixelValue(x, y + 1, 255);
							}
							if (res.getPixelValue(x, y - 1) == 128) {
								change = true;
								res.putPixelValue(x, y - 1, 255);
							}
							if (res.getPixelValue(x + 1, y + 1) == 128) {
								change = true;
								res.putPixelValue(x + 1, y + 1, 255);
							}
							if (res.getPixelValue(x - 1, y - 1) == 128) {
								change = true;
								res.putPixelValue(x - 1, y - 1, 255);
							}
							if (res.getPixelValue(x - 1, y + 1) == 128) {
								change = true;
								res.putPixelValue(x - 1, y + 1, 255);
							}
							if (res.getPixelValue(x + 1, y - 1) == 128) {
								change = true;
								res.putPixelValue(x + 1, y - 1, 255);
							}
						}
					}
				}
			}
		}
		// suppression
		for (int x = 0; x < la; x++) {
			for (int y = 0; y < ha; y++) {
				if (res.getPixelValue(x, y) == 128) {
					res.putPixelValue(x, y, 0);
				}
			}
		}
		return res;
	}

// ----------------------------PRIVATE METHODS-------------------------------- //

	/**Static method returning an ArrayList of double[] containing the norm array(index 0)
	 * and the angle array(index 1). 
	 * */
	public static ArrayList<double[]> dericheCalc(ImageProcessor ip, float alphaD) {
		double[] norm_deriche = null;
		double[] angle_deriche = null;

		int nmem;
		float[] nf_grx = null;
		float[] nf_gry = null;
		int[] a1 = null;
		float[] a2 = null;
		float[] a3 = null;
		float[] a4 = null;

		int icolonnes = 0;
		int lignes;
		int colonnes;
		int lig_1;
		int lig_2;
		int lig_3;
		int col_1;
		int col_2;
		int col_3;
		int icol_1;
		int icol_2;
		int i;
		int j;
		float ad1;
		float ad2;
		float an1;
		float an2;
		float an3;
		float an4;
		float an11;

		lignes = ip.getHeight();
		colonnes = ip.getWidth();
		nmem = lignes * colonnes;

		lig_1 = lignes - 1;
		lig_2 = lignes - 2;
		lig_3 = lignes - 3;
		col_1 = colonnes - 1;
		col_2 = colonnes - 2;
		col_3 = colonnes - 3;

		/*
		 *  alloc temporary buffers
		 */
		norm_deriche = new double[nmem];
		angle_deriche = new double[nmem];
		ArrayList<double[]> result = new ArrayList<double[]>();

		nf_grx = new float[nmem];
		nf_gry = new float[nmem];

		a1 = new int[nmem];
		a2 = new float[nmem];
		a3 = new float[nmem];
		a4 = new float[nmem];

		ad1 = (float) -Math.exp(-alphaD);
		ad2 = 0;
		an1 = 1;
		an2 = 0;
		an3 = (float) Math.exp(-alphaD);
		an4 = 0;
		an11 = 1;

		/*
		 *  FIRST STEP:  Y GRADIENT
		 */
		/*
		 *  x-smoothing
		 */
		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				a1[i * colonnes + j] = (int) ip.getPixel(j, i);
				
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes - 1;
			icol_2 = icolonnes - 2;
			a2[icolonnes] = an1 * a1[icolonnes];
			a2[icolonnes + 1] = an1 * a1[icolonnes + 1] +
					an2 * a1[icolonnes] - ad1 * a2[icolonnes];
			for (j = 2; j < colonnes; ++j) {
				a2[icolonnes + j] = an1 * a1[icolonnes + j] + an2 * a1[icol_1 + j] -
						ad1 * a2[icol_1 + j] - ad2 * a2[icol_2 + j];
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes + 1;
			icol_2 = icolonnes + 2;
			a3[icolonnes + col_1] = 0;
			a3[icolonnes + col_2] = an3 * a1[icolonnes + col_1];
			for (j = col_3; j >= 0; --j) {
				a3[icolonnes + j] = an3 * a1[icol_1 + j] + an4 * a1[icol_2 + j] -
						ad1 * a3[icol_1 + j] - ad2 * a3[icol_2 + j];
			}
		}

		icol_1 = lignes * colonnes;

		for (i = 0; i < icol_1; ++i) {
			a2[i] += a3[i];
		}

		/*
		 *  FIRST STEP Y-GRADIENT : y-derivative
		 */
		/*
		 *  columns top - downn
		 */
		for (j = 0; j < colonnes; ++j) {
			a3[j] = 0;
			a3[colonnes + j] = an11 * a2[j] - ad1 * a3[j];
			for (i = 2; i < lignes; ++i) {
				a3[i * colonnes + j] = an11 * a2[(i - 1) * colonnes + j] -
						ad1 * a3[(i - 1) * colonnes + j] - ad2 * a3[(i - 2) * colonnes + j];
			}
		}

		/*
		 *  columns down top
		 */
		for (j = 0; j < colonnes; ++j) {
			a4[lig_1 * colonnes + j] = 0;
			a4[(lig_2 * colonnes) + j] = -an11 * a2[lig_1 * colonnes + j] -
					ad1 * a4[lig_1 * colonnes + j];
			for (i = lig_3; i >= 0; --i) {
				a4[i * colonnes + j] = -an11 * a2[(i + 1) * colonnes + j] -
						ad1 * a4[(i + 1) * colonnes + j] - ad2 * a4[(i + 2) * colonnes + j];
			}
		}

		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			a3[i] += a4[i];
		}

		for (i = 0; i < lignes; ++i) {
			for (j = 0; j < colonnes; ++j) {
				nf_gry[i * colonnes + j] = a3[i * colonnes + j];
			}
		}

		/*
		 *  SECOND STEP X-GRADIENT
		 */
		for (i = 0; i < lignes; ++i) {
			for (j = 0; j < colonnes; ++j) {
				a1[i * colonnes + j] = (int) (ip.getPixel(j, i));
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes - 1;
			icol_2 = icolonnes - 2;
			a2[icolonnes] = 0;
			a2[icolonnes + 1] = an11 * a1[icolonnes];
			for (j = 2; j < colonnes; ++j) {
				a2[icolonnes + j] = an11 * a1[icol_1 + j] -
						ad1 * a2[icol_1 + j] - ad2 * a2[icol_2 + j];
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes + 1;
			icol_2 = icolonnes + 2;
			a3[icolonnes + col_1] = 0;
			a3[icolonnes + col_2] = -an11 * a1[icolonnes + col_1];
			for (j = col_3; j >= 0; --j) {
				a3[icolonnes + j] = -an11 * a1[icol_1 + j] -
						ad1 * a3[icol_1 + j] - ad2 * a3[icol_2 + j];
			}
		}
		icol_1 = lignes * colonnes;
		for (i = 0; i < icol_1; ++i) {
			a2[i] += a3[i];
		}

		/*
		 *  on the columns
		 */
		/*
		 *  columns top down
		 */
		for (j = 0; j < colonnes; ++j) {
			a3[j] = an1 * a2[j];
			a3[colonnes + j] = an1 * a2[colonnes + j] + an2 * a2[j]
					 - ad1 * a3[j];
			for (i = 2; i < lignes; ++i) {
				a3[i * colonnes + j] = an1 * a2[i * colonnes + j] + an2 * a2[(i - 1) * colonnes + j] -
						ad1 * a3[(i - 1) * colonnes + j] - ad2 * a3[(i - 2) * colonnes + j];
			}
		}

		/*
		 *  columns down top
		 */
		for (j = 0; j < colonnes; ++j) {
			a4[lig_1 * colonnes + j] = 0;
			a4[lig_2 * colonnes + j] = an3 * a2[lig_1 * colonnes + j] - ad1 * a4[lig_1 * colonnes + j];
			for (i = lig_3; i >= 0; --i) {
				a4[i * colonnes + j] = an3 * a2[(i + 1) * colonnes + j] + an4 * a2[(i + 2) * colonnes + j] -
						ad1 * a4[(i + 1) * colonnes + j] - ad2 * a4[(i + 2) * colonnes + j];
			}
		}

		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			a3[i] += a4[i];
		}

		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				nf_grx[i * colonnes + j] = a3[i * colonnes + j];
			}
		}

		/*
		 *  SECOND STEP X-GRADIENT : the x-gradient is  done
		 */
		/*
		 *  THIRD STEP : NORM
		 */
		/*
		 *  computation of the magnitude and angle
		 */
		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				a2[i * colonnes + j] = nf_gry[i * colonnes + j];
			}
		}
		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			norm_deriche[i] = modul(nf_grx[i], nf_gry[i]);
			angle_deriche[i] = angle(nf_grx[i], nf_gry[i]);
		}
		result.add(norm_deriche);
		result.add(angle_deriche);
		return result;
	}


	/**
	 *  Suppression of non local-maxima
	 *
	 *@param  grad  the norm gradient image
	 *@param  ang   the angle gradient image
	 *@return       the image with non local-maxima suppressed
	 */
	public static ImageProcessor nonMaximalSuppression(ImageProcessor grad, ImageProcessor ang) {
		FloatProcessor res = new FloatProcessor(grad.getWidth(), grad.getHeight());

		int la = grad.getWidth();
		int ha = grad.getHeight();

		float ag;
		float pix1 = 0;
		float pix2 = 0;
		float pix;

		for (int x = 1; x < la - 1; x++) {
			for (int y = 1; y < ha - 1; y++) {
				ag = ang.getPixelValue(x, y);
				if ((ag > -22.5) && (ag <= 22.5)) {
					pix1 = grad.getPixelValue(x + 1, y);
					pix2 = grad.getPixelValue(x - 1, y);
				} else if ((ag > 22.5) && (ag <= 67.5)) {
					pix1 = grad.getPixelValue(x + 1, y - 1);
					pix2 = grad.getPixelValue(x - 1, y + 1);
				} else if (((ag > 67.5) && (ag <= 90)) || ((ag < -67.5) && (ag >= -90))) {
					pix1 = grad.getPixelValue(x, y - 1);
					pix2 = grad.getPixelValue(x, y + 1);
				} else if ((ag < -22.5) && (ag >= -67.5)) {
					pix1 = grad.getPixelValue(x + 1, y + 1);
					pix2 = grad.getPixelValue(x - 1, y - 1);
				}
				pix = grad.getPixelValue(x, y);
				if ((pix >= pix1) && (pix >= pix2)) {
					res.putPixelValue(x, y, pix);
				}
			}
		}
		return res;
	}


	/**
	 *  modul
	 *
	 *@param  dx  derivative in x
	 *@param  dy  derivative in y
	 *@return     norm of gradient
	 */
	public static double modul(float dx, float dy) {
		return (Math.sqrt(dx * dx + dy * dy));
	}


	/**
	 *  angle
	 *
	 *@param  dx  derivative in x
	 *@param  dy  derivative in y
	 *@return     angle of gradient
	 */
	public static double angle(float dx, float dy) {
		return (-Math.toDegrees(Math.atan(dy / dx)));
	}



	
	/**Checks the type of the image
	 * */
    static int getType(ImageProcessor ip) {
        if (!typed){
        	typed = true;
	        if (ip instanceof ByteProcessor & !ip.isColorLut())
	            	type = BYTE;            
	        else if (ip instanceof ShortProcessor)
	                type = SHORT;
	        else if (ip instanceof FloatProcessor)
	                type = FLOAT;
	        else
	                type = OTHER;
        }
        return type;
    }
}
