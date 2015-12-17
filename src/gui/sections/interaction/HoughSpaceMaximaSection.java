package gui.sections.interaction;

import gui.GuiManager;
import gui.hough.HoughSpaceMaxima;
import gui.hough.ImagePanel;
import gui.hough.ImagePanelToolbarDecoration;
import gui.hough.ImageSpaceMaxima;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This Class contains the Section for the "Hough Linien"-topic.
 */
public class HoughSpaceMaximaSection extends InteractionSection{

	private static final long serialVersionUID = 4430016275124207676L;


	private JSlider thresholdSlider;

	private Checkbox nonMaximumSuppression;


	private ImagePanel imagespace;


	private ImagePanel houghspace;


	private JLabel thresholdLabel;
	
	/**
	 * The constructor
	 * @param guiManager
	 */
	public HoughSpaceMaximaSection(GuiManager guiManager) {
		super(guiManager);
		setLayout(new BorderLayout());
		
		imagespace = new ImageSpaceMaxima(guiManager.getToolkit().getImagePlus());
		houghspace = new HoughSpaceMaxima(guiManager.getToolkit().getImagePlus());
		
		houghspace.addMouseMotionListener((MouseMotionListener) imagespace);
		imagespace.addMouseMotionListener((MouseMotionListener) houghspace);
		
		Panel imagePanel=new Panel(new GridLayout(1,2));
		imagePanel.add(new ImagePanelToolbarDecoration(imagespace,false,guiManager.getWorkingPath(), true));
		imagePanel.add(new ImagePanelToolbarDecoration(houghspace,false,guiManager.getWorkingPath(), false));
		
		Panel controlPanel=new Panel(new GridLayout(1,2));
		
		Panel leftControlPanel=new Panel(new FlowLayout());
		Panel rightControlPanel=new Panel(new BorderLayout());
		
		controlPanel.add(leftControlPanel);
		controlPanel.add(rightControlPanel);
		
		thresholdSlider=new JSlider(JSlider.HORIZONTAL);
		thresholdSlider.setMinimum(0);
		thresholdSlider.setMaximum(255);
		thresholdSlider.setMajorTickSpacing(50);
		thresholdSlider.setMinorTickSpacing(10);
		thresholdSlider.setPaintTicks(true);
		thresholdSlider.setPaintLabels(true);
		thresholdSlider.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				((ImageSpaceMaxima)imagespace)
					.updateThreshold(thresholdSlider.getValue()/255.0,nonMaximumSuppression.getState(),null);
				((HoughSpaceMaxima)houghspace)
					.updateThreshold(thresholdSlider.getValue()/255.0,nonMaximumSuppression.getState());
			}
			
		});
		thresholdLabel=new JLabel("Schwellwert: 128");
		thresholdSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider s=((JSlider)e.getSource());
				thresholdLabel.setText("Schwellwert: "+s.getValue());
				
			}
		});
		thresholdSlider.setValue(128);
		
		rightControlPanel.add(thresholdLabel,BorderLayout.WEST);
		rightControlPanel.add(thresholdSlider,BorderLayout.CENTER);
		
		nonMaximumSuppression=new Checkbox("Non Maximum Unterdrückung");
		nonMaximumSuppression.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				((ImageSpaceMaxima)imagespace)
					.updateThreshold(thresholdSlider.getValue()/255.0,nonMaximumSuppression.getState(),null);
				((HoughSpaceMaxima)houghspace)
					.updateThreshold(thresholdSlider.getValue()/255.0,nonMaximumSuppression.getState());
				
				
			}
			
		});
		
		leftControlPanel.add(nonMaximumSuppression);
		
		add(imagePanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
	}

}
