package gui.sections.interaction;

import gui.GuiManager;
import gui.InteractionSectionListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.miginfocom.swing.MigLayout;

/**
 * This class controls the switching between the InteractionSections, when a TopicLabel or the 
 * previous/next-button is clicked.
 *
 */
public class InteractionSectionSwitch extends InteractionSection {

	private static final long serialVersionUID = 1L;

	private GuiManager guiManager;
	private int activeSection = 0;
	private Set<InteractionSectionListener> interactionSectionListeners;
	
	/**
	 * Standard constructor
	 * @param guiManager
	 */
	public InteractionSectionSwitch(GuiManager guiManager) {
		this.guiManager=guiManager;
		this.setLayout(new MigLayout("", "[grow,shrink,fill]", "[grow,fill]"));
		this.interactionSectionListeners = new HashSet<InteractionSectionListener>();
	}
	
	/**
	 * This method adds an InteractionSectionListener
	 * @param isl
	 */
	public void addInteractionSectionListener(InteractionSectionListener isl) {
		this.interactionSectionListeners.add(isl);
	}
	
	/**
	 * This method removes an InteractionSectionListener
	 * @param isl
	 */
	public void removeInteractionSectionListener(InteractionSectionListener isl) {
		this.interactionSectionListeners.remove(isl);
	}
	
	/**
	 * This method notifies all interactionSectionListeners that a Section has changed.
	 * @param from	- switch from section -> int value 	
	 * @param to	- switch to section -> int value
	 */
	protected void fireInteractionSectionListenerSectionSwitched(int from, int to) {
		Set<InteractionSectionListener> isls = Collections.synchronizedSet(
				new HashSet<InteractionSectionListener>(this.interactionSectionListeners.size()));
		synchronized (isls) {
			isls.addAll(this.interactionSectionListeners);
			for (InteractionSectionListener isl: isls)
				isl.sectionSwitched(from, to);
		}
	}
	
	/**
	 * Switch to the previous section
	 */
	public void switchToPreviousSection() {
		if (!this.isFirstSection()) this.switchToSection(this.activeSection-1);
	}
	
	/** 
	 * Switch to the next section
	 */
	public void switchToNextSection() {
		if (!this.isLastSection()) this.switchToSection(this.activeSection+1);
	}
	
	/**
	 * Returns the the actual section is the last section
	 * @return true if the actual section is the last or false otherwise
	 */
	public boolean isLastSection() {
		return (this.activeSection == 5);
	}
	
	/**
	 * Returns the the actual section is the first section
	 * @return true if the actual section is the first or false otherwise
	 */
	public boolean isFirstSection() {
		return (this.activeSection == 0);
	}
	
	/**
	 * Switch to a certain section
	 * @param nr
	 */
	public void switchToSection(int nr) {
		this.fireInteractionSectionListenerSectionSwitched(this.activeSection, nr);
		this.removeAll();
		switch(nr)
		{
		case 0:
			add(new DocumentationSection(guiManager));
			break;
		case 1:
			add(new ImageLoadSection(guiManager));
			break;
		case 2:
			add(new PreprocessingSection(guiManager));
			break;
		case 3:
			add(new ImageEditSection(guiManager));
			break;
		case 4:
			add(new HoughAnimationSection(guiManager));
			break;
		case 5:
			add(new HoughSpaceMaximaSection(guiManager));
			break;
		}		
		this.activeSection = nr;
		if(this.getParent()!=null)
			this.getParent().validate();		
	}

}
