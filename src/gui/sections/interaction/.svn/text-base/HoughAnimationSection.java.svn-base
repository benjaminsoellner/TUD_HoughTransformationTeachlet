package gui.sections.interaction;

import gui.GuiManager;
import gui.ImageIconRegistry;
import gui.hough.HoughSpaceAnimation;
import gui.hough.ImagePanel;
import gui.hough.ImagePanelToolbarDecoration;
import gui.hough.ImageSpaceAnimation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JSlider;

import appLogic.Simulatable;
import appLogic.Simulation;


/**
 * This section is the container for the hough animation.
 * The simulation takes place here.
 */
public class HoughAnimationSection  extends InteractionSection{

	private static final long serialVersionUID = 1297271033714913242L;
	
	private Simulation simulation;

	private ImagePanel imagespace;

	private ImagePanel houghspace;
	
	/**
	 * The Constructor
	 * @param guiManager
	 */
	public HoughAnimationSection(GuiManager guiManager) {
		super(guiManager);
		setLayout(new BorderLayout());
		
		imagespace = new ImageSpaceAnimation(guiManager.getToolkit().getImagePlus());
		houghspace = new HoughSpaceAnimation(guiManager.getToolkit().getImagePlus());
		
		
		Panel imagePanel=new Panel(new GridLayout(1,2));
		String workingPath=guiManager.getWorkingPath();
		imagePanel.add(new ImagePanelToolbarDecoration(imagespace,false,workingPath, true));
		imagePanel.add(new ImagePanelToolbarDecoration(houghspace,false,workingPath, false));
		
		Panel controlPanel=new Panel(new GridLayout(1,2));
		
		Panel leftControlPanel=new Panel(new FlowLayout());
		Panel rightControlPanel=new Panel(new BorderLayout());
		
		controlPanel.add(leftControlPanel);
		controlPanel.add(rightControlPanel);
		
			
			
		JButton resetButton;

		resetButton = new JButton("",ImageIconRegistry.getInstance().getIcon("reset"));
		resetButton.setToolTipText("Zurücksetzen");
		resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.reset();
				
			}
			
		});
		
		JButton pauseButton=new JButton("",ImageIconRegistry.getInstance().getIcon("pause"));
		pauseButton.setToolTipText("Pause");
		pauseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.pause();
				
			}
			
		});
		
		JButton stepButton=new JButton("",ImageIconRegistry.getInstance().getIcon("step"));
		stepButton.setToolTipText("Schritt vorwärts");
		stepButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.step();
				
			}
			
		});	
		
		JButton playButton=new JButton("",ImageIconRegistry.getInstance().getIcon("play"));
		playButton.setToolTipText("Abspielen");
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.play();
				
			}
			
		});
		
		JSlider speedSlider=new JSlider(JSlider.HORIZONTAL);
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(1000);
		speedSlider.setValue(30);
		speedSlider.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				JSlider slider=(JSlider)e.getSource();
				simulation.configure( slider.getValue()/10.0);
			}
			
		});

		
		leftControlPanel.add(resetButton);
		leftControlPanel.add(pauseButton);
		leftControlPanel.add(stepButton);
		
		leftControlPanel.add(playButton);
		rightControlPanel.add(new Label("Geschwindigkeit"),BorderLayout.WEST);
		rightControlPanel.add(speedSlider,BorderLayout.CENTER);
		
		add(imagePanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		
		
		List<Simulatable> simulatables=new ArrayList<Simulatable>();
		simulatables.add((Simulatable)imagespace);
		simulatables.add((Simulatable)houghspace);
		simulation=new Simulation(simulatables);
		simulation.configure(speedSlider.getValue()/10.0);
	}
}
