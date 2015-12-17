package gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * In this class the images for the imagePanelToolbarDecorator can be registered.
 * It is a Singleton.
 */
public class ImageIconRegistry {

	private static ImageIconRegistry instance;
	
	Map<String,ImageIcon> imageIcons;
	
	/**
	 * Private constructor
	 */
	private ImageIconRegistry() {
		imageIcons=new HashMap<String,ImageIcon>();
	}
	
	/**
	 * Returns the instance and creates one if it was null before
	 * @return instance
	 */
	public static ImageIconRegistry getInstance(){
		if(instance==null){
			instance=new ImageIconRegistry();
		}
		return instance;
	}
	
	/**
	 * Add an icon to the Map of imageIcons
	 * @param name
	 * @param i
	 */
	public void addIcon(String name,ImageIcon i){
		imageIcons.put(name, i);
	}
	
	/**
	 * Get an imageIcon from the Map
	 * @param name
	 * @return ImageIcon
	 */
	public ImageIcon getIcon(String name){
		return imageIcons.get(name);
	}

}
