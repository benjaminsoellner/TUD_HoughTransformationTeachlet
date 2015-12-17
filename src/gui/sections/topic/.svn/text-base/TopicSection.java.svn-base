package gui.sections.topic;

import gui.GuiManager;
import gui.InteractionSectionListener;
import gui.sections.Section;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This class contains all topicButtons. The buttons are there to 
 * navigate directly to a certain section
 */
public class TopicSection extends Section implements InteractionSectionListener {

	private static final long serialVersionUID = 1L;
	private List<TopicButton> topicList;	
	
	/**
	 * The constructor
	 * @param guiManager
	 */
	public TopicSection(GuiManager guiManager) {
		super(guiManager);
		//interactionSectionSwitch has to be initialized before topicSection
		this.getGuiManager().getInteractionSectionSwitch().addInteractionSectionListener(this);
		Dimension dMin = new Dimension(100,50);
		Dimension dMax = new Dimension(1000,50);
		
		//this.setBackground(Color.WHITE);	
		this.setMinimumSize(dMin);
		
		this.setMaximumSize(dMax);
		
		//String TBegin = "<html><body><div height='60' width='90' align='center' margin='10'>";
		//String TEnd = "</div></body></html>";

		
		TopicButton TInformation = new TopicButton("Infos" ); 
		TopicButton TLaden = new TopicButton("Bild laden"  );		
		TopicButton TVerarbeiten = new TopicButton("Bild vorverarbeiten"  ); 
		TopicButton TBearbeiten = new TopicButton("Bild bearbeiten"  ); 
		TopicButton TTransform = new TopicButton("Hough Transformation " ); 
		TopicButton TLinien = new TopicButton("Hough Linien"  ); 
		
				
		topicList = new ArrayList<TopicButton>();
		topicList.add(TInformation);
		topicList.add(TLaden);
		topicList.add(TVerarbeiten);
		topicList.add(TBearbeiten);
		topicList.add(TTransform);
		topicList.add(TLinien);
		
		if (topicList.size()>0) topicList.get(0).setActive(true);
		
		Iterator<TopicButton> topicIter= topicList.iterator();
		while (topicIter.hasNext()){
			TopicButton topic = topicIter.next();
			this.add(topic);			
			topic.addActionListener(new ActionListener(){



				@Override
				public void actionPerformed(ActionEvent e) {
					TopicButton actTopic = (TopicButton)e.getSource();
					Container parent = actTopic.getParent();
					List<Component> topicComponents= Arrays.asList(parent.getComponents());
					int topicIndex = topicComponents.indexOf(actTopic);
										
					for (TopicButton tLabel : topicList){
						if(!tLabel.equals(actTopic))
						tLabel.setActive(false);
					}
					
					actTopic.setActive(true);
					getGuiManager().switchToInteractionSection(topicIndex);
					repaint();
				}
			});
		}

		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
						
	}

	/**
	 * This method is called when a section switched from a certain topic 
	 * to a certain topic
	 */
	public void sectionSwitched(int from, int to) {
		if ((from>=0)&&(to>=0)){
			for (TopicButton tl: topicList){
				tl.setActive(false);
			}
			
			topicList.get(to).setActive(true);
			
		}		
	}
	
}
