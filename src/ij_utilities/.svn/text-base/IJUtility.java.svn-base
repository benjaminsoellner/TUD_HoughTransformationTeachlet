package ij_utilities;

import ij.ImagePlus;

import java.awt.Image;

public class IJUtility {
	
	/**
	 * Creates an proportional scaled instance.
	 * 
	 * @param img
	 *            - ImagePlus which should be scaled
	 * @param size
	 *            - size of the greater side of new Instance
	 * @return scaled ImagePlus-Instance
	 */
	public static ImagePlus getPropScaledInstance(ImagePlus img, int size) {
		int width = 0;
		int height = 0;
		if (img.getWidth() < img.getHeight()) {
			height = size;
			width = Math
					.round(1.0F * height / img.getHeight() * img.getWidth());
		} else {
			width = size;
			height = Math
					.round(1.0F * width / img.getWidth() * img.getHeight());
		}
		return new ImagePlus("", img.getImage().getScaledInstance(width,
				height, Image.SCALE_FAST));

	}
}
