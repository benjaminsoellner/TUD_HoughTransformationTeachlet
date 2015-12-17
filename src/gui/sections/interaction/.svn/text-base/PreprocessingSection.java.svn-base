package gui.sections.interaction;

import gui.GuiManager;
import gui.hough.ImagePanel;
import ij.ImagePlus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import preprocessing.PreProcessingConstants;
import preprocessing.PreProcessingSettings;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class PreprocessingSection extends InteractionSection implements
		PreProcessingConstants, MouseListener {
	
	private static final long serialVersionUID = -6111752152247650138L;

	private final int sliderStartValue = 100;

	private JPanel jPanelGreyscaleButtons;
	private JPanel jPanelThresholdLevel;
	private JLabel jLabelOriginalImage;
	private JLabel jLabelThresholdImage;
	private JRadioButton jRadioButtonNoEdgeDetection;
	private JRadioButton jRadioButtonLaplace;
	private JRadioButton jRadioButtonCannyDeriche;
	private JPanel jPanelEdgeDetection;
	private JRadioButton jRadioButtonSobel;
	private ButtonGroup buttonGroupEdgeDetection;
	private JLabel jLabelEdgeDetectionImage;
	private JLabel jLabelGreyscaleImage;
	private JPanel jPanelThresholdImage;
	private JPanel jPanelEdgeDetectionImage;
	private JPanel jPanelGreyscaleImage;
	private JPanel jPanelOriginalImage;
	private JLabel jLabelSliderValue;
	private JCheckBox jCheckBoxThresholdAuto;
	private JSlider jSliderThresholdLevel;
	private JRadioButton jRadioButtonMean;
	private JRadioButton jRadioButtonB;
	private JRadioButton jRadioButtonG;
	private JRadioButton jRadioButtonR;
	private ButtonGroup buttonGroupRGB;

	public PreprocessingSection(GuiManager guiManager) {
		super(guiManager);
		initGUI();
	}
	
	public PreprocessingSection() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.getGuiManager().getToolkit().updateToolkit();
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1, 1.0, 0.1, 1.0, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7, 75};
			thisLayout.columnWeights = new double[] {1.0, 1.0, 1.0};
			thisLayout.columnWidths = new int[] {7, 7, 7};
			this.setLayout(thisLayout);
//			setPreferredSize(new Dimension(600, 500));
			this.add(getJPanelOriginalImage(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJPanelGreyscaleImage(), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJPanelEdgeDetectionImage(), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJPanelThresholdImage(), new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJPanelGreyscaleButtons(), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJPanelThresholdLevel(), new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));			
			this.add(getJLabelOriginalImage(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			this.add(getJLabelGreyscaleImage(), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 10, 2, 10), 0, 0));
			this.add(getJLabelEdgeDetectionImage(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 10, 2, 10), 0, 0));
			this.add(getJLabelThresholdImage(), new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 10, 2, 10), 0, 0));
			this.add(getJPanelEdgeDetection(), new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			
			PreProcessingSettings settings = this.getGuiManager().getToolkit().getSettings(); 
			
			switch (settings.getGreyScaleMode()) {
			case PreProcessingSettings.GREYSCALEMODE_R:
				this.getJRadioButtonR().setSelected(true);
				break;
			case PreProcessingSettings.GREYSCALEMODE_G:
				this.getJRadioButtonG().setSelected(true);
				break;
			case PreProcessingSettings.GREYSCALEMODE_B:
				this.getJRadioButtonB().setSelected(true);
				break;
			case PreProcessingSettings.GREYSCALEMODE_MEAN:
				this.getJRadioButtonMean().setSelected(true);
				break;
			default:
				break;
			}
			
			switch (settings.getEdgeDetectionMode()) {
			case PreProcessingSettings.EDGEMODE_SOBELOPERATOR:
				this.getJRadioButtonSobel().setSelected(true);
				break;
			case PreProcessingSettings.EDGEMODE_CANNYDERICHEFILTER:
				this.getJRadioButtonCannyDeriche().setSelected(true);
				break;
			case PreProcessingSettings.EDGEMODE_LAPLACEFILTER:
				this.getJRadioButtonLaplace().setSelected(true);
				break;
			case PreProcessingSettings.EDGEMODE_NO:
				this.getJRadioButtonNoEdgeDetection().setSelected(true);
				break;
			default:
				break;
			}
						
			if (settings.getThresholdLevel() < 0 || settings.getThresholdLevel() > 255) {				
				this.getJCheckBoxThresholdAuto().setSelected(true);
				this.getJSliderThresholdLevel().setEnabled(false);
				this.getJLabelSliderValue().setEnabled(false);
			} else {				
				this.getJCheckBoxThresholdAuto().setSelected(false);
				this.getJSliderThresholdLevel().setEnabled(true);
				this.getJLabelSliderValue().setEnabled(true);
				this.getJLabelSliderValue().setText("Schwellwert: " + settings.getThresholdLevel());
			}
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method should return an instance of this class which does NOT
	 * initialize it's GUI elements. This method is ONLY required by Jigloo if
	 * the superclass of this class is abstract or non-public. It is not needed
	 * in any other situation.
	 */
	public static Object getGUIBuilderInstance() {
		return new PreprocessingSection(Boolean.FALSE);
	}

	/**
	 * This constructor is used by the getGUIBuilderInstance method to provide
	 * an instance of this class which has not had it's GUI elements initialized
	 * (ie, initGUI is not called in this constructor).
	 */
	public PreprocessingSection(Boolean initGUI) {
		super();
	}
	
	private JPanel getJPanelOriginalImage() {
		if(jPanelOriginalImage == null) {
			ImagePlus img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_ORIGINAL);
			if (img == null)
				jPanelOriginalImage = new JPanel();
			else
				jPanelOriginalImage = new ImagePanel(img);
		}
		return jPanelOriginalImage;
	}
	
	private JPanel getJPanelGreyscaleImage() {
		if(jPanelGreyscaleImage == null) {
			ImagePlus img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_GREYSCALE);
			if (img == null)
				jPanelGreyscaleImage = new JPanel();
			else
				jPanelGreyscaleImage = new ImagePanel(img);
		}
		return jPanelGreyscaleImage;
	}
	
	private JPanel getJPanelEdgeDetectionImage() {
		if(jPanelEdgeDetectionImage == null) {
			ImagePlus img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_EDGEDETECTION);
			if (img == null)
				jPanelEdgeDetectionImage = new JPanel();
			else
				jPanelEdgeDetectionImage = new ImagePanel(img);
		}
		return jPanelEdgeDetectionImage;
	}
	
	private JPanel getJPanelThresholdImage() {
		if(jPanelThresholdImage == null) {
			ImagePlus img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_THRESHOLD);
			if (img == null)
				jPanelThresholdImage = new JPanel();
			else
				jPanelThresholdImage = new ImagePanel(img);
		}
		return jPanelThresholdImage;
	}	

	private JPanel getJPanelGreyscaleButtons() {
		if (jPanelGreyscaleButtons == null) {
			jPanelGreyscaleButtons = new JPanel();
			jPanelGreyscaleButtons.setLayout(null);
			jPanelGreyscaleButtons.add(getJRadioButtonB());
			jPanelGreyscaleButtons.add(getJRadioButtonMean());
			jPanelGreyscaleButtons.add(getJRadioButtonG());
			jPanelGreyscaleButtons.add(getJRadioButtonR());
		}
		return jPanelGreyscaleButtons;
	}
	
	private JPanel getJPanelThresholdLevel() {
		if (jPanelThresholdLevel == null) {
			jPanelThresholdLevel = new JPanel();
			jPanelThresholdLevel.setLayout(null);
			jPanelThresholdLevel.add(getJCheckBoxThresholdAuto());
			jPanelThresholdLevel.add(getJLabelSliderValue());
			jPanelThresholdLevel.add(getJSliderThresholdLevel());
		}
		return jPanelThresholdLevel;
	}

	private ButtonGroup getButtonGroupRGB() {
		if (buttonGroupRGB == null) {
			buttonGroupRGB = new ButtonGroup();
		}
		return buttonGroupRGB;
	}
	
	private JRadioButton getJRadioButtonR() {
		if (jRadioButtonR == null) {
			jRadioButtonR = new JRadioButton();
			jRadioButtonR.setText("R");
			jRadioButtonR.setFocusable(false);			
			jRadioButtonR.setBounds(0, 19, 100, 18);
			jRadioButtonR.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonGrayScaleItemStateChanged(evt);
				}
			});
			getButtonGroupRGB().add(jRadioButtonR);
		}
		return jRadioButtonR;
	}

	private JRadioButton getJRadioButtonG() {
		if (jRadioButtonG == null) {
			jRadioButtonG = new JRadioButton();
			jRadioButtonG.setText("G");
			jRadioButtonG.setFocusable(false);
			jRadioButtonG.setBounds(0, 38, 100, 18);
			jRadioButtonG.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonGrayScaleItemStateChanged(evt);
				}
			});
			getButtonGroupRGB().add(jRadioButtonG);
		}
		return jRadioButtonG;
	}

	private JRadioButton getJRadioButtonB() {
		if (jRadioButtonB == null) {
			jRadioButtonB = new JRadioButton();
			jRadioButtonB.setText("B");
			jRadioButtonB.setFocusable(false);
			jRadioButtonB.setBounds(0, 57, 100, 18);
			jRadioButtonB.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonGrayScaleItemStateChanged(evt);
				}
			});
			getButtonGroupRGB().add(jRadioButtonB);
		}
		return jRadioButtonB;
	}

	private JRadioButton getJRadioButtonMean() {
		if (jRadioButtonMean == null) {
			jRadioButtonMean = new JRadioButton();
			jRadioButtonMean.setText("Mittelwert");
			jRadioButtonMean.setFocusable(false);			
			jRadioButtonMean.setBounds(0, 0, 150, 18);
			jRadioButtonMean.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonGrayScaleItemStateChanged(evt);
				}
			});
			getButtonGroupRGB().add(jRadioButtonMean);
		}
		return jRadioButtonMean;
	}

	private JSlider getJSliderThresholdLevel() {
		if (jSliderThresholdLevel == null) {
			jSliderThresholdLevel = new JSlider();
			jSliderThresholdLevel.setMaximum(255);
			jSliderThresholdLevel.setMajorTickSpacing(50);
			jSliderThresholdLevel.setMinorTickSpacing(10);
			jSliderThresholdLevel.setPaintTicks(true);
			jSliderThresholdLevel.setPaintLabels(true);
			jSliderThresholdLevel.setFocusable(false);
			jSliderThresholdLevel.setValue(sliderStartValue);
			jSliderThresholdLevel.setBounds(0, 32, 192, 42);
			jSliderThresholdLevel.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					getJLabelSliderValue().setText("Schwellwert: "+
							String
									.valueOf(((JSlider) e.getSource())
											.getValue()));
				}
			});
			jSliderThresholdLevel.addMouseListener(this);
		}
		return jSliderThresholdLevel;
	}

	private JCheckBox getJCheckBoxThresholdAuto() {
		if (jCheckBoxThresholdAuto == null) {
			jCheckBoxThresholdAuto = new JCheckBox();
			jCheckBoxThresholdAuto.setText("Auto");
			jCheckBoxThresholdAuto.setFocusable(false);			
			jCheckBoxThresholdAuto.setBounds(0, 0, 70, 18);
			jCheckBoxThresholdAuto.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					getJSliderThresholdLevel().setEnabled(
							evt.getStateChange() == ItemEvent.DESELECTED);
					getJLabelSliderValue().setEnabled(
							evt.getStateChange() == ItemEvent.DESELECTED);
				}
			});
			jCheckBoxThresholdAuto.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jCheckBoxItemStateChanged(evt);
				}				
			});
		}
		return jCheckBoxThresholdAuto;
	}

	private JLabel getJLabelSliderValue() {
		if (jLabelSliderValue == null) {
			jLabelSliderValue = new JLabel("");
			jLabelSliderValue.setText("Schwellwert: " + String.valueOf(sliderStartValue));			
			jLabelSliderValue.setBounds(87, 5, 113, 15);
		}
		return jLabelSliderValue;
	}

	private void updateCanvases() {
		this.updateCanvas(STEP_GREYSCALE);
		this.updateCanvas(STEP_EDGEDETECTION);
		this.updateCanvas(STEP_THRESHOLD);
	}

	private void updateCanvas(int preProcessingStep) {
		ImagePlus img;
		switch (preProcessingStep) {
		case STEP_GREYSCALE:
			img = this.getGuiManager().getToolkit().getImagePlus(STEP_GREYSCALE);
			((ImagePanel)jPanelGreyscaleImage).setImage(img);
			break;
		case STEP_EDGEDETECTION:			
			img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_EDGEDETECTION);
			((ImagePanel)jPanelEdgeDetectionImage).setImage(img);
			break;
		case STEP_THRESHOLD:
			img = this.getGuiManager().getToolkit().getImagePlus(
					STEP_THRESHOLD);
			((ImagePanel)jPanelThresholdImage).setImage(img);
			break;
		default:
			break;
		}
	}
	
	private void jRadioButtonGrayScaleItemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			PreProcessingSettings settings = getGuiManager().getToolkit()
					.getSettings();

			if (evt.getSource() == jRadioButtonR)
				settings.setGreyScaleMode(GREYSCALEMODE_R);
			else if (evt.getSource() == jRadioButtonG)
				settings.setGreyScaleMode(GREYSCALEMODE_G);
			else if (evt.getSource() == jRadioButtonB)
				settings.setGreyScaleMode(GREYSCALEMODE_B);
			else if (evt.getSource() == jRadioButtonMean)
				settings.setGreyScaleMode(GREYSCALEMODE_MEAN);

			getGuiManager().getToolkit().updateToolkit();
			this.updateCanvases();
		}		
	}
	
	private void jCheckBoxItemStateChanged(ItemEvent evt) {		
			PreProcessingSettings settings = getGuiManager().getToolkit()
					.getSettings();
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				settings.setThresholdLevel(-1);
			} else {
				settings.setThresholdLevel(getJSliderThresholdLevel().getValue());
			}

			getGuiManager().getToolkit().updateToolkit(STEP_THRESHOLD);
			this.updateCanvas(STEP_THRESHOLD);				
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (getJSliderThresholdLevel().isEnabled()) {
			PreProcessingSettings settings = this.getGuiManager().getToolkit()
					.getSettings();
			settings.setThresholdLevel(((JSlider) evt.getSource()).getValue());
			this.getGuiManager().getToolkit().updateToolkit(STEP_THRESHOLD);
			this.updateCanvas(STEP_THRESHOLD);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}		
	
	private JLabel getJLabelOriginalImage() {
		if(jLabelOriginalImage == null) {
			jLabelOriginalImage = new JLabel();
			jLabelOriginalImage.setText("Original");
			jLabelOriginalImage.setPreferredSize(new java.awt.Dimension(150, 14));
			jLabelOriginalImage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelOriginalImage;
	}
	
	private JLabel getJLabelGreyscaleImage() {
		if(jLabelGreyscaleImage == null) {
			jLabelGreyscaleImage = new JLabel();
			jLabelGreyscaleImage.setText("Grauwertbildung");
			jLabelGreyscaleImage.setPreferredSize(new java.awt.Dimension(150, 14));
			jLabelGreyscaleImage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelGreyscaleImage;
	}
	
	private JLabel getJLabelEdgeDetectionImage() {
		if(jLabelEdgeDetectionImage == null) {
			jLabelEdgeDetectionImage = new JLabel();
			jLabelEdgeDetectionImage.setText("Kantendetektion");
			jLabelEdgeDetectionImage.setPreferredSize(new java.awt.Dimension(150, 14));
			jLabelEdgeDetectionImage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelEdgeDetectionImage;
	}
	
	private JLabel getJLabelThresholdImage() {
		if(jLabelThresholdImage == null) {
			jLabelThresholdImage = new JLabel();
			jLabelThresholdImage.setText("Binarisierung");
			jLabelThresholdImage.setPreferredSize(new java.awt.Dimension(150, 14));
			jLabelThresholdImage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelThresholdImage;
	}
	
	private ButtonGroup getButtonGroupEdgeDetection() {
		if(buttonGroupEdgeDetection == null) {
			buttonGroupEdgeDetection = new ButtonGroup();
		}
		return buttonGroupEdgeDetection;
	}
	
	private JPanel getJPanelEdgeDetection() {
		if(jPanelEdgeDetection == null) {
			jPanelEdgeDetection = new JPanel();
			jPanelEdgeDetection.setLayout(null);
			jPanelEdgeDetection.add(getJRadioButtonSobel());
			jPanelEdgeDetection.add(getJRadioButtonCannyDeriche());
			jPanelEdgeDetection.add(getJRadioButtonLaplace());
			jPanelEdgeDetection.add(getJRadioButtonNoEdgeDetection());
		}
		return jPanelEdgeDetection;
	}
	
	private JRadioButton getJRadioButtonSobel() {
		if(jRadioButtonSobel == null) {
			jRadioButtonSobel = new JRadioButton();
			jRadioButtonSobel.setText("Sobel Operator");
			jRadioButtonSobel.setBounds(0, 0, 200, 18);
			jRadioButtonSobel.setFocusable(false);
			jRadioButtonSobel.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonEdgeDetectionItemStateChanged(evt);
				}
			});
			getButtonGroupEdgeDetection().add(jRadioButtonSobel);
		}
		return jRadioButtonSobel;
	}
		
	private JRadioButton getJRadioButtonCannyDeriche() {
		if(jRadioButtonCannyDeriche == null) {
			jRadioButtonCannyDeriche = new JRadioButton();
			jRadioButtonCannyDeriche.setText("Canny-Deriche Filter");
			jRadioButtonCannyDeriche.setBounds(0, 19, 200, 18);
			jRadioButtonCannyDeriche.setFocusable(false);
			jRadioButtonCannyDeriche.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonEdgeDetectionItemStateChanged(evt);
				}
			});
			getButtonGroupEdgeDetection().add(jRadioButtonCannyDeriche);
		}
		return jRadioButtonCannyDeriche;
	}
	
	private JRadioButton getJRadioButtonLaplace() {
		if(jRadioButtonLaplace == null) {
			jRadioButtonLaplace = new JRadioButton();
			jRadioButtonLaplace.setText("Laplace Filter");
			jRadioButtonLaplace.setBounds(0, 38, 200, 18);
			jRadioButtonLaplace.setFocusable(false);
			jRadioButtonLaplace.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonEdgeDetectionItemStateChanged(evt);
				}
			});
			getButtonGroupEdgeDetection().add(jRadioButtonLaplace);
		}
		return jRadioButtonLaplace;
	}

	private JRadioButton getJRadioButtonNoEdgeDetection() {
		if(jRadioButtonNoEdgeDetection == null) {
			jRadioButtonNoEdgeDetection = new JRadioButton();
			jRadioButtonNoEdgeDetection.setText("Keine");
			jRadioButtonNoEdgeDetection.setBounds(0, 57, 200, 18);
			jRadioButtonNoEdgeDetection.setFocusable(false);
			getButtonGroupEdgeDetection().add(jRadioButtonNoEdgeDetection);
			jRadioButtonNoEdgeDetection.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					jRadioButtonEdgeDetectionItemStateChanged(evt);
				}
			});
		}
		return jRadioButtonNoEdgeDetection;
	}	
	
	private void jRadioButtonEdgeDetectionItemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			PreProcessingSettings settings = getGuiManager().getToolkit()
					.getSettings();

			if (evt.getSource() == jRadioButtonSobel)
				settings.setEdgeDetectionMode(EDGEMODE_SOBELOPERATOR);
			else if (evt.getSource() == jRadioButtonCannyDeriche)
				settings.setEdgeDetectionMode(EDGEMODE_CANNYDERICHEFILTER);
			else if (evt.getSource() == jRadioButtonLaplace)
				settings.setEdgeDetectionMode(EDGEMODE_LAPLACEFILTER);			
			else if (evt.getSource() == jRadioButtonNoEdgeDetection)
				settings.setEdgeDetectionMode(EDGEMODE_NO);

			getGuiManager().getToolkit().updateToolkit();
			this.updateCanvases();
		}	
	}

}
