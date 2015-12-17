package houghtransform;
import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Houghtransformation (HT) for lines. 
 * 
 * @author matze
 *
 */
public class HoughLines {

	//hough accumulator
	private int[][] houghSpace;
	
	//nr of bins in alpha and d direction
	private int nrAlphaBins,nrDBins;
	
	//the image to perform
	private ImagePlus imp;
	
	//LUTs
	private double[] cosLUT;
	private double[] sinLUT;
	
	//half image dimensions
	private int hwidth,hheight;
	
	// image diagonal
	private double dRange;
	
	//d - distance line to origin, varies between -dmax .. dmax
	// origin of image is the center of image !!!
	private double dmax;
	
	//temporal variables of accumulateHoughLine() for efficiency
	private double x_,y_,d;
	private int dBucket;

	private boolean stop;


	
	
	/**
	 * Constructor.
	 * Initialize HT for the image imp. The quantification of houghspace/ 
	 * count of vote buckets/bins is given by alphaBins, dBins.  
	 * @param imp Image to perform HT on, should be an binary image (at least 8-bit).
	 * @param alphaBins Number of bins in alpha direction.
	 * @param dBins Number of bins in d direction.
	 */
	public HoughLines(ImagePlus imp,int alphaBins,int dBins){
		stop=false;
		nrDBins=dBins;
		nrAlphaBins=alphaBins;
		this.imp=imp;
		
		
		//create houghspace
		houghSpace=new int[nrAlphaBins][nrDBins];
		
		//create cos/sin LUTs
		createCosSinLUT();
		
		
		preCalculations();

	}
	
	
	/**
	 * Some calculations before HT starts.
	 */
	private void preCalculations()
	{
		hwidth=imp.getWidth()/2;
		hheight=imp.getHeight()/2;

		
		dRange=Math.sqrt(Math.pow(imp.getWidth(),2)+Math.pow(imp.getHeight(), 2));
		
		dmax=dRange/2.0;	
	}
	
	/**
	 * Build a look up table for sin and cos function for efficiency.
	 */
	private void createCosSinLUT()
	{
		// size of bin in alpha direction
		double alphaStep=Math.PI/nrAlphaBins,alpha;
		
		cosLUT=new double[nrAlphaBins];
		sinLUT=new double[nrAlphaBins];
		for(int a=0;a<nrAlphaBins;a++)
		{
			alpha=a*alphaStep;
			cosLUT[a]=Math.cos(alpha);
			sinLUT[a]=Math.sin(alpha);
		}	
	}
	
	/**
	 * Returns a list of all non black pixel positions.
	 * @return List of point.
	 */
	public List<Point> getNonBlackPixels(){
		List<Point> l=new ArrayList<Point>();
		imp.updateImage();
		for(int y=0;y<imp.getHeight();y++)
		{

			for(int x=0;x<imp.getWidth();x++)
			{
				if(imp.getPixel(x,y)[0]!=0)//pixel non-black?
				{
					l.add(new Point(x,y));
				}
			}
		}
		return l;
	}
	
	/**
	 * Fills the accumulator array,
	 */
	public void fillHoughSpace()
	{
		//for every non-black pixel accumulate a Hough curve d=x*cos(alpha)+y*sin(alpha) 
		for(int y=0;y<imp.getHeight();y++)
		{
			if (y % 10 == 0)
				IJ.showProgress(y, imp.getHeight());
			if(stop){
				stop=false;
				return;
			}
			for(int x=0;x<imp.getWidth();x++)
			{
				if(imp.getPixel(x,y)[0]!=0)//pixel non-black?
				{
					accumulateHoughCurve(x,y);
				}
			}
		}
		
		
	}
	/**
	 * Stops filling the accumulator array, because computation
	 * can be expensive. Useful to stop threads running this algorithm.
	 */
	public void stop(){
		stop=true;
	}
	
	/**
	 * Draws one hough curve for a specified image pixel x,y in accumulator array.
	 * @param x  X Coordinate of image pixel.
	 * @param y  Y Coordinate of image pixel.
	 */
	public void accumulateHoughCurve(int x,int y)
	{
		for(int a=0;a<nrAlphaBins;a++)
		{
			x_=x-hwidth;
			y_=y-hheight;
			d=x_*cosLUT[a]+y_*sinLUT[a];
			d=d+dmax;
			dBucket=(int)(d/dRange*nrDBins);
			if(dBucket>=0&&dBucket<nrDBins) houghSpace[a][dBucket]++;
		}
	}
	
	/**
	 * Returns a visual correct result of the houghspace.
	 * @return Houghspace as ImagePlus.
	 */
	public ImagePlus getHoughSpaceImage()
	{
		int[] fpixels = new int[nrAlphaBins * nrDBins];
		for (int a = 0; a < nrAlphaBins; a++) {
			for (int dBucket = 0; dBucket < nrDBins; dBucket++) {
				fpixels[dBucket * nrAlphaBins + a] = houghSpace[a][dBucket];
			}
		}
		FloatProcessor houghFP = new FloatProcessor(nrAlphaBins, nrDBins,fpixels);
		
		ImagePlus houghIP=new ImagePlus(imp.getTitle()+" Houghspace",houghFP); 
		return houghIP;
	}
	
	/**
	 * Find buckit/bin with maximal votes and return count.
	 * @return Count of maximal votes
	 */
	private int getNrMaximalVotes()
	{
		int maxVotes=0;
		for (int a = 0; a < nrAlphaBins; a++) {
			for (int dBucket = 0; dBucket < nrDBins; dBucket++) {
				maxVotes=Math.max(maxVotes, houghSpace[a][dBucket]);
			}
		}
		return maxVotes;
	}
	
	/**
	 * Gets a list of maximum votes in houghspace by relative intensity,
	 * that mean every point in houghspace which votecount is greater
	 * relInt * nr. maximal votes.
	 */
	public List<Point> getMaximaByRelativeIntensity(double relInt)
	{
		relInt=Math.min(1.0,Math.max(relInt, 0.0));
		List<Point> maxima=new ArrayList<Point>();
		int maxVotes=getNrMaximalVotes();
		int voteThreshold=(int)(maxVotes*relInt);
		for (int a = 0; a < nrAlphaBins; a++) {
			for (int d = 0; d < nrDBins; d++) {
				if(houghSpace[a][d]>voteThreshold){
					maxima.add(new Point(a,d));
				}
			}
		}
		return maxima;
	}

	
	/**
	 * Converts the list of maxima points of houghspace 
	 * in a list of parameter alpha and d , describing fitted lines.
	 * @param maxima List of maxima points.
	 * @return List of parameters alpha/d in floating point precision (Point2D.Double).
	 */
	private List<Point2D.Double> convertMaxima(List<Point> maxima){

		List<Point2D.Double> result=new ArrayList<Point2D.Double>();
		
		double alphaStep=Math.PI/nrAlphaBins,alpha,d;
				
		Iterator<Point> it=maxima.iterator();
		int a,dBucket;
		while(it.hasNext()){
			Point p=it.next();
			a=p.x;
			dBucket=p.y;
			alpha=a*alphaStep+alphaStep/2.0;
			d=dBucket*dRange/(double)nrDBins-dmax+dRange/(2.0*nrDBins);	
			result.add(new Point2D.Double(alpha,d));
		}
		return result;
	}
	
	
	/**
	 * Returns an image with marked houspace maxima given by maxima.
	 * @param maxima List of maxima points.
	 * @return Image with maxima pixel.
	 */
	public ImagePlus getMaximaImage(List<Point> maxima){
		
		int[] fpixels = new int[nrAlphaBins * nrDBins];
		
		Iterator<Point> it=maxima.iterator();
		int a,dBucket;
		while(it.hasNext()){
			Point p=it.next();
			a=p.x;
			dBucket=p.y;
			fpixels[dBucket * nrAlphaBins + a] = houghSpace[a][dBucket];
			
		}
		
		FloatProcessor houghFP = new FloatProcessor(nrAlphaBins, nrDBins,fpixels);
		
		ImagePlus houghIP=new ImagePlus(imp.getTitle()+" Maxima",houghFP); 
		return houghIP;
	}
	
	
	/**
	 * Suppress maxima in local neighborhood.
	 * @param maxima List of maxima.
	 * @param maximaImage Image consisting of maxima pixel.
	 * @param maxThreshold Suppress only maxima which where maxThreshold smaller.
	 * @return List of rest of maxima.
	 */
	public List<Point> nonMaximumSupression(List<Point> maxima,ImagePlus maximaImage,double maxThreshold){
		
		List<Point> nonMaxSupMaxima=new ArrayList<Point>(maxima);
		
		FloatProcessor maxIm=((FloatProcessor)maximaImage.getProcessor());
		
		Iterator<Point> it=nonMaxSupMaxima.iterator();

		
		while(it.hasNext()){
			Point p=it.next();
			float v=maxIm.getPixelValue(p.x, p.y);
			if(	!(maxIm.getPixelValue(p.x+1, p.y)+maxThreshold<v&&
					maxIm.getPixelValue(p.x+1, p.y-1)+maxThreshold<v&&
					maxIm.getPixelValue(p.x+1, p.y+1)+maxThreshold<v&&
					maxIm.getPixelValue(p.x-1, p.y)+maxThreshold<v&&
					maxIm.getPixelValue(p.x-1, p.y-1)+maxThreshold<v&&
					maxIm.getPixelValue(p.x-1, p.y+1)+maxThreshold<v&&
					maxIm.getPixelValue(p.x, p.y+1)+maxThreshold<v&&
					maxIm.getPixelValue(p.x, p.y-1)+maxThreshold<v)){

				it.remove();
			}
		}	
		return nonMaxSupMaxima;
	}
	
	
	/**
	 * Reduces the list of maximas to count elements.
	 * @param maxima List of maximas.
	 * @param maximaImage Image consisting of maxima pixel.
	 * @param count Count of maximas remaining.
	 * @return Reduced maxima list.
	 */
	public List<Point> reduceMaxima(List<Point> maxima,ImagePlus maximaImage,int count)
	{
		final FloatProcessor maxIm=((FloatProcessor)maximaImage.getProcessor());
		
		List<Point> reducedMaxima=new ArrayList<Point>(maxima);
		
		Collections.sort(reducedMaxima, new Comparator<Point>(){

			@Override
			public int compare(Point o1, Point o2) {
				
				float v1=maxIm.getPixelValue(o1.x, o1.y);
				float v2=maxIm.getPixelValue(o2.x, o2.y);
				return v1==v2 ? 0 : (v1<v2 ? -1 : +1);
			}
			
		});
		
		if(reducedMaxima.size()>count)
			reducedMaxima.subList(count, reducedMaxima.size()).clear();
		
		return reducedMaxima;
	}
	
	
	/**
	 * Builds an image with the input image in the background
	 * an the fitted red lines specified by the list of maximas. 
	 * @param maxima List of maxima.
	 * @return Image visualizing result of houghtransformation.
	 */
	public ImagePlus getImageWithHoughLines(List<Point> maxima){
		List<Point2D.Double> m=convertMaxima(maxima);
		
		ImageProcessor imgWHL = imp.getProcessor().duplicate().convertToRGB();
			
		Iterator<Point2D.Double> it=m.iterator();
		while(it.hasNext()){
			Point2D.Double p=it.next();
			double alpha=p.x;
			double d=p.y;
			int lx=(int)(hwidth+Math.cos(alpha)*d);
			int ly=(int)(hheight+Math.sin(alpha)*d);
			imgWHL.setColor(Color.red);
			imgWHL.drawLine((int)(lx+Math.cos(alpha-Math.PI/2.0)*dmax),
					(int)(ly+Math.sin(alpha-Math.PI/2.0)*dmax),
					(int)(lx+Math.cos(alpha+Math.PI/2.0)*dmax),
					(int)(ly+Math.sin(alpha+Math.PI/2.0)*dmax));
		}
		return new ImagePlus(imp.getTitle()+" Hough lines",imgWHL);
	}
}
