package gui.sections.interaction;

import java.awt.BorderLayout;

import gui.DocBrowser;
import gui.GuiManager;
import gui.InteractionSectionListener;

/**
 * The DocumentationSection contains the descriptional text of the first topic (Information)
 *
 */
public class DocumentationSection extends InteractionSection{

	private static DocBrowser docBrowser;

	/**
	 * The constructor for the DocumentationSection
	 * @param guiManager
	 */
	public DocumentationSection(GuiManager guiManager) {
		super(guiManager);
		
		setLayout(new BorderLayout());
		if(docBrowser==null)
			docBrowser = new DocBrowser(guiManager.getWorkingPath()+"doku/Hough_Info.html");
		
		add(docBrowser);
		
		guiManager.getInteractionSectionSwitch().addInteractionSectionListener(
				new InteractionSectionListener(){

					@Override
					public void sectionSwitched(int from, int to) {

						if(getParent()!=null){
							invalidate();
							getParent().validate();
						}						
		
					}

				});
	}

	private static final long serialVersionUID = 1L;

}
