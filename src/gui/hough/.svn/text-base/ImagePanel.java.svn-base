package gui.hough;

import ij.ImagePlus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


enum PaintMode{
	Paint,
	Rubber,
	None
}

/**
 * A special Panel for the hough animation
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = -1900056582886371517L;


	protected ImagePlus image;


	protected AffineTransform affineTranform;
	
	private double zoom,tx,ty;
	
	private int lastx=0;
	private int lasty=0;
	
	private int mx,my;
	
	private int button;
	
	
	private PaintMode paintmode=PaintMode.None;


	/**
	 * Standard constructor
	 * @param imp
	 */
	public ImagePanel(final ImagePlus imp) {
		super();
		//setBackground(Color.GR);

		setBorder(BorderFactory.createLineBorder(/*Color.lightGray*/ new Color(170,170,255)/*new Color(190, 210, 220)*/, 2));

		this.image = imp;
		affineTranform=new AffineTransform();
		zoom=1.0;
		tx=0;
		ty=0;
		addMouseListener(new MouseAdapter(){

			@Override
			public void mousePressed(MouseEvent e) {
				button=e.getButton();
				lastx=e.getX();
				lasty=e.getY();
				
			}
			
		});
		addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				Point2D.Double p = new Point2D.Double(mx,my);
				Point2D.Double r = new Point2D.Double();
				Point2D.Double d = new Point2D.Double();
				
				try {
					affineTranform.createInverse().transform(p, r);
				} catch (NoninvertibleTransformException e1) {
					e1.printStackTrace();
				}
				if(e.getWheelRotation()<0){
					zoom+=0.1;

				}else{
					zoom-=0.1;
				}
				setupTransform();
				
					
				affineTranform.transform(r,d);
				tx+=(p.x-d.x);
				ty+=(p.y-d.y);
				review();

				
			}
			
		});
		
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				if(button==MouseEvent.BUTTON3){
					tx-=(lastx-e.getX());
					ty-=(lasty-e.getY());
					lastx=e.getX();
					lasty=e.getY();
					review();
				}
				else if(button==MouseEvent.BUTTON1){
					Point m=new Point(e.getX(),e.getY());
					if(!liesOnImage(m)) return;
					Point p=screenToImage(new Point(e.getX(),e.getY()));
					if(paintmode==PaintMode.Paint){
						image.getProcessor().setValue(255);
					}else if(paintmode==PaintMode.Rubber){
						image.getProcessor().setValue(0);
						
					}else return;
					image.getProcessor().drawPixel(p.x,p.y);
					review();		
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mx=e.getX();
				my=e.getY();			
			}
			
		});
		
		addComponentListener(new ComponentAdapter(){



			@Override
			public void componentResized(ComponentEvent e) {
				fitImage();
				fireImageChanged();
				
			}

			
		});
	}

	
	
	/**
	 * @see JPanel
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);


		//setupTransform();
		
		Graphics2D g2d=(Graphics2D)g;
		g2d.drawImage(image.getImage(),affineTranform,null);
	}
	
	/**
	 * Show and transform the image if a parameter has changed
	 */
	private void setupTransform(){
		zoom=Math.min(10.0, Math.max(0.1, zoom));
		tx=Math.max(-(image.getWidth()*zoom-getSize().width),tx);
		ty=Math.max(-(image.getHeight()*zoom-getSize().height),ty);
		tx=Math.min(0, tx);
		ty=Math.min(0, ty);
		if(image.getWidth()*zoom<getSize().width){
			tx=-(image.getWidth()*zoom-getSize().width)/2;
		}
		if(image.getHeight()*zoom<getSize().height){
			ty=-(image.getHeight()*zoom-getSize().height)/2;
		}
		affineTranform.setTransform(zoom, 0, 0, zoom, tx, ty);		
	}

	/**
	 * Calls setupTransform() and repaint()
	 */
	private void review(){
		setupTransform();
		repaint();
	}
	
	/**
	 * Set the zoom back to 1.0
	 */
	public void resetZoom()
	{
		zoom=1.0;
		review();
	}
	
	/**
	 * Fit the image to the panel
	 */
	public void fitImage()
	{
		tx=0;
		ty=0;
		if(image.getHeight()<image.getWidth()){
			zoom=(double)getSize().width/(double)image.getWidth();
			if(image.getHeight()*zoom>getSize().height){
				zoom=(double)getSize().height/(double)image.getHeight();
			}
		}else{
			zoom=(double)getSize().height/(double)image.getHeight();
			if(image.getWidth()*zoom>getSize().width){
				zoom=(double)getSize().width/(double)image.getWidth();
			}
		}
		zoom=Math.min(1.0, zoom);
	}

	/**
	 * Review if the image has changed.
	 */
	public void fireImageChanged()
	{
		review();
	}
	
	/**
	 * Return if a point lies on the image
	 * @param p
	 * @return true if the point lies on the image ore false otherwise
	 */
	public boolean liesOnImage(Point p)
	{
		Point2D.Double pd = new Point2D.Double(p.x,p.y);
		Point2D.Double r = new Point2D.Double();
		try {
			affineTranform.createInverse().transform(pd, r);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return r.x>=0&&r.x<image.getWidth()&&r.y>=0&&r.y<image.getHeight();
	}
	
	/**
	 * Converts the screen-coordinate to the image-coordinate
	 * @param p - screen coordinate
	 * @return new Point - image coordinate
	 */
	public Point screenToImage(Point p)
	{
		
		Point2D.Double pd = new Point2D.Double(p.x,p.y);
		Point2D.Double r = new Point2D.Double();
		try {
			affineTranform.createInverse().transform(pd, r);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		return new Point((int)r.x,(int)r.y);
	}
	
	/**
	 * Converts the image- coordinate to a screen- coordinate
	 * @param p	- image- coordinate
	 * @return new Point - screen- coordinate
	 */
	public Point imageToScreen(Point p)
	{
		
		Point2D.Double pd = new Point2D.Double(p.x,p.y);
		Point2D.Double r = new Point2D.Double();
		affineTranform.transform(pd, r);
		
		return new Point((int)r.x,(int)r.y);
	}

	/**
	 * Set the image
	 * @param image
	 */
	public void setImage(ImagePlus image) {
		this.image = image;
		this.fireImageChanged();
	}
	
	/**
	 * Returns the zoom factor
	 * @return zoom
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * Set the Paintmode
	 * @param paintmode
	 */
	public void setPaintmode(PaintMode paintmode) {
		this.paintmode = paintmode;
	}
	
	/**
	 * Sets the image color back to black.
	 */
	public void clearImage(){
		image.getProcessor().setColor(Color.BLACK);
		image.getProcessor().fill();
	}

	
}
