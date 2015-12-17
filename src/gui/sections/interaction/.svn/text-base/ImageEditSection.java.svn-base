package gui.sections.interaction;

import gui.GuiManager;
import gui.hough.HoughSpaceImageEdit;
import gui.hough.ImagePanel;
import gui.hough.ImagePanelToolbarDecoration;
import gui.hough.ImageSpaceWithLines;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseMotionListener;

/**
 * This Class contains the section for the "Bild bearbeiten"-topic
 */
public class ImageEditSection extends InteractionSection {

	private static final long serialVersionUID = -3868591206010372392L;

	private Panel imagePanel;

	private ImagePanel imagespace;

	private ImagePanel houghspace;

	/**
	 * The constructor
	 * 
	 * @param guiManager
	 */
	public ImageEditSection(GuiManager guiManager) {
		super(guiManager);
		setLayout(new BorderLayout());

		imagespace = new ImageSpaceWithLines(guiManager.getToolkit().getImagePlus());
		houghspace = new HoughSpaceImageEdit(guiManager.getToolkit().getImagePlus());

		imagespace.addMouseMotionListener((MouseMotionListener) houghspace);
		houghspace.addMouseMotionListener((MouseMotionListener) imagespace);

		imagePanel = new Panel(new GridLayout(1, 2));
		imagePanel.add(new ImagePanelToolbarDecoration(imagespace, true,
				guiManager.getWorkingPath(), true));

		imagePanel.add(new ImagePanelToolbarDecoration(houghspace, false,
				guiManager.getWorkingPath(), false));

		add(imagePanel, BorderLayout.CENTER);

	}

}
