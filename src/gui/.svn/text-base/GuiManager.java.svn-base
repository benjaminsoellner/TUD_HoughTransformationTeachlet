package gui;


import gui.sections.Section;
import gui.sections.info.InfoSection;
import gui.sections.interaction.InteractionSectionSwitch;
import gui.sections.navigation.NavigationSection;
import gui.sections.topic.TopicSection;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import preprocessing.PreProcessingImageToolkit;
import appLogic.HoughTransApplet;


/**
 * The GuiManager creates the surface of this applet.
 */
public class GuiManager{
	
	private Applet applet;
	
	private int height=800;
	private int width=600;
	
	private PreProcessingImageToolkit toolkit;
	private Section sTopic;
	private InteractionSectionSwitch interactionSwitch;
	private Section sInfo;
	private Section sNavi;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Standard constructor
	 */
	public GuiManager(Applet applet,PreProcessingImageToolkit toolkit)
	{
		this.applet=applet;
		this.toolkit=toolkit;
	}
	
	/**
	 * Return InteractionSectionSwitch
	 * @return interactionSwitch
	 */
	public InteractionSectionSwitch getInteractionSectionSwitch() {
		return this.interactionSwitch;
	}
	
	/**
	 * Switch to an InteractionSection
	 * @param number
	 */
	public void switchToInteractionSection(int number){
		interactionSwitch.switchToSection(number);
		this.applet.invalidate();
	}
	
	/**
	 * Returns the height
	 * @return height
	 */
	public int getAppletHeight(){
		return height;
	}
	
	/**
	 * Returns the width
	 * @return width
	 */
	public int getAppletWidth(){
		return width;
	}
	
	/**
	 * Returns the PreprocessingToolkit
	 * @return toolkit
	 */
	public PreProcessingImageToolkit getToolkit(){
		return toolkit;
	}
	
	/**
	 * Returns the AppletContext
	 * @return appletContext
	 */
	public AppletContext getAppletContext() {
		return this.applet.getAppletContext();
	}
	
	/**
	 * Creates an output dialog
	 * @param message
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(applet, message);
	}
	
	/**
	 * Initializes the GUI.
	 */
	public void guiInit(){
		// switch to another look and feel than the ugly swing
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(applet, "Could not change swing style.");
		}
		
		applet.setLayout(new MigLayout("", "[grow,fill]", "[60,fill,center]0[grow,fill,center]0[180,fill,center]0[16,fill,center]"));
		//applet.setSize(height,width);
		//applet.setMaximumSize(dMax);
		//applet.setMinimumSize(dMin);
		
		//interactionSectionSwitch has to be initialized before topicSection
		this.interactionSwitch = new InteractionSectionSwitch(this);		
		this.sTopic = new TopicSection(this);
		this.sTopic.setLayout(new GridLayout(1,6));
		this.sInfo = new InfoSection(this);
		this.sNavi = new NavigationSection(this);
		
		this.applet.add(this.sTopic, "grow,wrap");
		this.applet.add(this.interactionSwitch, "grow,wrap");
		this.applet.add(this.sInfo, "grow,wrap");
		this.applet.add(this.sNavi, "grow,wrap");
				
		applet.setBackground(applet.getComponent(0).getBackground());
		this.interactionSwitch.switchToSection(0);


	}
	
	/**
	 * Returns the working path
	 * @return workingpath
	 */
	public String getWorkingPath(){
		return ((HoughTransApplet)applet).getWorkingPath();
	}

	/**
	 * Returns the InfoSection
	 * @return sInfo
	 */
	public InfoSection getInfoSection() {
		return (InfoSection) sInfo;
	}
	
}
