package gui.hough;

import gui.ImageIconRegistry;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * This class is for the descriptive tooltips on top of the last three sections.
 */
public class ImagePanelToolbarDecoration extends JPanel {


	private static final long serialVersionUID = -752188473816041277L;
	private JToggleButton brubber;
	private JToggleButton bpaint;

	/**
	 * Standard constructor - creates the labels and buttons for the toolbar
	 * @param imagePanel
	 * @param paintbuttons
	 * @param workingPath
	 * @param paintMouse
	 */
	public ImagePanelToolbarDecoration(final ImagePanel imagePanel,boolean paintbuttons,String workingPath, boolean paintMouse) {
		super();
		setLayout(new BorderLayout());
		JToolBar imagetoolbar = new JToolBar();
		JButton b1to1;
	
			
			
		b1to1 = new JButton("",ImageIconRegistry.getInstance().getIcon("zoom1to1"));
		b1to1.setToolTipText("Zoom 100%");
		JButton bfit = new JButton("",ImageIconRegistry.getInstance().getIcon("zoomfit"));
		bfit.setToolTipText("Einpassen");
		
		JLabel lmousenavi=new JLabel("",ImageIconRegistry.getInstance().getIcon("mousenavi"), 0);
		lmousenavi.setToolTipText("Mausfunktionen");
		
		JLabel lnomousenavi=new JLabel("",ImageIconRegistry.getInstance().getIcon("nomousenavi"), 0);
		lmousenavi.setToolTipText("Mausfunktionen");
		
		JLabel lmove=new JLabel("",ImageIconRegistry.getInstance().getIcon("move"), 0);
		lmove.setToolTipText("Bewegen");
		
		JLabel lzoom=new JLabel("",ImageIconRegistry.getInstance().getIcon("zoom"),0);
		lzoom.setToolTipText("Zoomen");
		
		if (paintMouse) imagetoolbar.add(lmousenavi);
		else imagetoolbar.add(lnomousenavi);
		//imagetoolbar.add(lmove);
		//imagetoolbar.add(lzoom);
		imagetoolbar.add(b1to1);
		imagetoolbar.add(bfit);	
		
		imagetoolbar.setFloatable(false);


		
		if(paintbuttons){
			JButton bclear = new JButton("",ImageIconRegistry.getInstance().getIcon("clear"));
			bclear.setToolTipText("Löschen");
			brubber = new JToggleButton("",ImageIconRegistry.getInstance().getIcon("rubber"));
			brubber.setToolTipText("Radieren");
			bpaint = new JToggleButton("",ImageIconRegistry.getInstance().getIcon("paint"));
			bpaint.setToolTipText("Zeichnen");
			bpaint.setSelected(true);
			imagePanel.setPaintmode(PaintMode.Paint);
			imagetoolbar.add(bpaint);
			imagetoolbar.add(brubber);
			imagetoolbar.add(bclear);
			
			bclear.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					imagePanel.clearImage();
					imagePanel.fireImageChanged();
					
				}
				
			});			
			brubber.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()!=brubber) return;
					imagePanel.setPaintmode(!brubber.isSelected()?PaintMode.Paint:PaintMode.Rubber);
					bpaint.setSelected(!brubber.isSelected());
					
				}
				
			});			
			bpaint.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()!=bpaint) return;
					imagePanel.setPaintmode(bpaint.isSelected()?PaintMode.Paint:PaintMode.Rubber);
					brubber.setSelected(!bpaint.isSelected());
					
				}
				
			});			
		}

		add(imagetoolbar,BorderLayout.NORTH);
		add(imagePanel,BorderLayout.CENTER);
		
		b1to1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.resetZoom();
				imagePanel.fireImageChanged();
				
			}
			
		});
		bfit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.fitImage();
				imagePanel.fireImageChanged();
				
			}
			
		});
	
		
	
	
	
	}
	

}
