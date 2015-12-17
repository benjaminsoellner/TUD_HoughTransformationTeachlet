package gui.sections.navigation;

import gui.GuiManager;
import gui.InteractionSectionListener;
import gui.sections.Section;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * The NavigationSection contains elements to navigate between the sections,
 * like the previous- and next- button.
 *
 */
public class NavigationSection extends Section implements ActionListener, InteractionSectionListener{
	private static final long serialVersionUID = 1L;
	private JButton nextButton;
	private JButton prevButton;
	
	/**
	 * The constructor adds a previous- and a next button to the section.
	 * @param guiManager 
	 */
	public NavigationSection(GuiManager guiManager) {
		super(guiManager);
		this.getGuiManager().getInteractionSectionSwitch().addInteractionSectionListener(this);
		
		this.nextButton = new JButton("Weiter");
		this.prevButton = new JButton("Zurueck");
		this.nextButton.addActionListener(this);
		this.prevButton.addActionListener(this);
		this.checkButtonsEnabled(0,0);

		this.setLayout(new FlowLayout(FlowLayout.CENTER));		
		this.add(prevButton);		
		this.add(nextButton);
	}

	/**
	 * Switch to the previous- or next section when the associated button is clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.nextButton)
			this.getGuiManager().getInteractionSectionSwitch().switchToNextSection();
		else if (e.getSource() == this.prevButton)
			this.getGuiManager().getInteractionSectionSwitch().switchToPreviousSection();
	}

	/**
	 * When the section has switched this method calls the method checkButtonsEnabled.
	 */
	public void sectionSwitched(int from, int to) {
		this.checkButtonsEnabled(from,to);
	}
	
	/**
	 * In the first section the prev.-button has to be disabled. Same goes for the next button 
	 * in the last section. This method checks both cases.
	 */
	protected void checkButtonsEnabled(int from, int to) {
		this.prevButton.setEnabled(to!=0);
		this.nextButton.setEnabled(to!=5);
		if(this.getParent()!=null){
			this.getParent().repaint();
		}
	}
}
