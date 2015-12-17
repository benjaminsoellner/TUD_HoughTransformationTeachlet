package gui.sections.info;

import gui.DocBrowser;
import gui.GuiManager;
import gui.InteractionSectionListener;
import gui.sections.Section;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * The InfoSection contains the informational text shown in the lower half of
 * the applet. Every topic has its own informational text, which is written in
 * HTML.
 * 
 */
public class InfoSection extends Section {

	private static final long serialVersionUID = -5338957471296683298L;

	private DocBrowser docBrowser;

	private ArrayList<String> docList;

	/**
	 * The InfoSection constructor adds all info-HTML files from the working
	 * path to the ArrayList doclist and calls that one that belongs to the
	 * actual topic.
	 * 
	 * @param guiManager
	 */
	public InfoSection(GuiManager guiManager) {
		super(guiManager);
		
		setLayout(new BorderLayout());
		docBrowser = new DocBrowser(getGuiManager().getWorkingPath()+"doku/");
		add(docBrowser);

		
		
		docList = new ArrayList<String>();
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Info.html");
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Laden.html");
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Vorverarbeitung.html");
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Bearbeitung.html");
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Transformation.html");
		docList.add(getGuiManager().getWorkingPath()+"doku/Hough_Linien.html");
		


		guiManager.getInteractionSectionSwitch().addInteractionSectionListener(
				new InteractionSectionListener() {

					@Override
					public void sectionSwitched(int from, int to) {
						if (to == 0) {
							setVisible(false);
						} else {
							setVisible(true);
							docBrowser.setEditorUrl( docList.get(to) );

						}
						if (getParent() != null) {
							invalidate();
							getParent().validate();
						}

					}

				});
	}
	
	/**
	 * returns the HTML from a URL
	 * @param url
	 * @return String
	 */
	private String getHtml(String url){
		URL url_=null;
		try {
			url_ = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			
		}
		try {

			URLConnection urlConn=url_.openConnection();
			InputStream bis=(InputStream) urlConn.getContent();
			
			byte[] contents = new byte[1024];
			
			int bytesRead=0;
			
			String strFileContents="";

			while( (bytesRead = bis.read(contents)) != -1){

				strFileContents += new String(contents, 0, bytesRead);
			
			}
			
			return strFileContents;

		} catch (IOException e) {
			e.printStackTrace();
		}	
		return "Error loading page "+url+".";
	}

	/**
	 * Returns the documentList
	 * @return docList
	 */
	public ArrayList<String> getDocumentList() {
		return docList;
	}
	
	

}
