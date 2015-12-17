package gui.sections.interaction;

import gui.GuiManager;
import gui.InteractionSectionListener;
import ij.IJ;
import ij.ImagePlus;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

/**
 * This class contains the section for the "Bild laden"-topic
 */
public class ImageLoadSection extends InteractionSection implements
			ActionListener, ListSelectionListener, FocusListener, InteractionSectionListener {
	public static final String SERVERCOMPONENT_CONFIGFILE = "./servercomponent.cfg.txt";
	public static final String BACKEND_SCRIPT  = "backend.php";
	public static final String FRONTEND_SCRIPT = "frontend.txt.php";
	public static final Dimension THUMBNAIL_SIZE = new Dimension(128,128);
	private static final long serialVersionUID = 1L;
	private ExecutorService executor;
	private JTextField imageUrl;
	private ThumbnailTableView thumbnailTable;
	private JButton backendButton;
	private JButton refreshButton;
	
	private String serverComponentURL = null; 
	
	/**
	 * Returns the ServerComponentURL
	 * @return serverComponentURL
	 */
	public String getServerComponentURL() {
		if (serverComponentURL == null) {
			try {
				URL url = new URL(this.getGuiManager().getWorkingPath()
						+ ImageLoadSection.SERVERCOMPONENT_CONFIGFILE);
				URLConnection  conn = url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				this.serverComponentURL = reader.readLine();
			} catch (MalformedURLException e) {
				this.serverComponentURL = "";
			} catch (IOException e) {
				this.serverComponentURL = "";
			}
		}
		return this.serverComponentURL;
	}
	
	/**
	 * The constructor
	 * @param guiManager
	 */
	public ImageLoadSection(GuiManager guiManager) {
		super(guiManager);
		this.setLayout(new MigLayout("", "[100,fill][100,fill]20[fill,grow,center]", "[grow,fill][16,fill]"));
		this.imageUrl       = new JTextField();
		this.backendButton  = new JButton("Neues Bild hochladen");
		this.refreshButton  = new JButton("Aktualisieren");
		this.thumbnailTable = new ThumbnailTableView();
		this.executor = Executors.newSingleThreadExecutor();
		this.backendButton.addActionListener(this);
		this.refreshButton.addActionListener(this);
		this.thumbnailTable.getSelectionModel().addListSelectionListener(this);
		this.imageUrl.addFocusListener(this);
		JScrollPane scrollPane = new JScrollPane(this.thumbnailTable);
		scrollPane.getViewport().setBackground(Color.WHITE);
		this.add(scrollPane,         "grow, span 3, wrap");
		this.add(this.backendButton, "grow");
		this.add(this.refreshButton, "grow");
		this.add(this.imageUrl,      "grow");
		this.getGuiManager().getInteractionSectionSwitch().addInteractionSectionListener(this);
		this.retrieveThumbnailsElements();
	}
	
	/**
	 * Retrieves the elements for the thumbnails.
	 */
	public void retrieveThumbnailsElements() {  
		this.executor.execute( new Thread(new ThumbnailFetcher(), "ThumbnailFetcher") );
	}
	
	/**
	 * Inner class to fetch the thumbnails
	 */
	private class ThumbnailFetcher implements Runnable {
		
		/**
		 * Read a list of URLs from a given URL
		 * @param url
		 * @param urls
		 */
		private void readUrlsFromUrl(URL url, List<URL> urls) {
			try {
				urls.clear();
				URLConnection  urlConn   = url.openConnection();
				BufferedReader urlReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
				String line;
				while ((line = urlReader.readLine()) != null)
					urls.add(new URL(line));
				urlReader.close();
			} catch (IOException e) {
				System.err.println("Couldn't retrieve image list. Connection error when retrieving images.");
			}
		}
		
		/**
		 * Creates the ThumbsFormURLs
		 * @param urls
		 * @param thumbs
		 */
		private void createThumbsFromUrls(List<URL> urls, List<ImagePlus> thumbs) {
			thumbs.clear();
			for (URL url: urls) {
				ImagePlus thumb;
				if (url.getProtocol().equals("file"))
					try {
						thumb = IJ.openImage(url.toURI().getPath());
					} catch (URISyntaxException e) {
						thumb = IJ.openImage(url.getFile());
					}
				else
					thumb = IJ.openImage(url.toString());
				thumbs.add(thumb);
			}
		}

		/**
		 * Run the thumbnailFetcher
		 */
		public void run() {
			final ImageLoadSection owner = ImageLoadSection.this;
			owner.thumbnailTable.getThumbnailTableViewModel().setAvalability(false);
			List<URL> thumbnailUrls = new ArrayList<URL>();
			List<URL> photoUrls     = new ArrayList<URL>();
			List<ImagePlus> thumbnails = new ArrayList<ImagePlus>();
			try {
				String urlBase = owner.getServerComponentURL() + ImageLoadSection.FRONTEND_SCRIPT;
				URL thumbnailsUrl = new URL(urlBase + "?resolution=thumbnails");
				URL photosUrl     = new URL(urlBase + "?resolution=photos");
				this.readUrlsFromUrl(thumbnailsUrl, thumbnailUrls);
				this.readUrlsFromUrl(photosUrl,     photoUrls);
				this.createThumbsFromUrls(thumbnailUrls, thumbnails);
			} catch (MalformedURLException e) {
				System.err.println("Couldn't retrieve image list. Check the URL in " + ImageLoadSection.SERVERCOMPONENT_CONFIGFILE + ".");
			}
			owner.thumbnailTable.setThumbnailSize(ImageLoadSection.THUMBNAIL_SIZE);
			owner.thumbnailTable.getThumbnailTableViewModel().setSource(thumbnails, photoUrls);
			owner.thumbnailTable.getThumbnailTableViewModel().setAvalability(true);
		}
	}
	
	
	
	/**
	 * ActionPerformed-listener
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.backendButton) {
			try {
				AppletContext c = this.getGuiManager().getAppletContext();
				URL url = new URL(this.getServerComponentURL() + ImageLoadSection.BACKEND_SCRIPT);
				c.showDocument(url, "_blank");
			} catch (MalformedURLException e1) {
				System.err.println("Couldn't open backend. Check the ImageLoadSection.BACKEND_URL constant.");
			}
		} else if (e.getSource() == this.refreshButton) {
			this.thumbnailTable.getSelectionModel().clearSelection();
			this.retrieveThumbnailsElements();
		}
		
	}

	/**
	 * ValueChanged-listener
	 */
	public void valueChanged(ListSelectionEvent e) {
		URL url = this.thumbnailTable.getSelectedPhotoUrl();
		if (url != null) this.imageUrl.setText(url.toString());
	}


	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == this.imageUrl)
			this.thumbnailTable.getSelectionModel().clearSelection();
	}

	/**
	 * empty FocusLost-listener
	 */
	public void focusLost(FocusEvent e) { }

	/**
	 * Call this method when the section switched from a certain section number to a 
	 * certain section number
	 */
	public void sectionSwitched(int from, int to) {
		if (from == 1) {
			try {
				this.getGuiManager().getToolkit().initialize(new URL(this.imageUrl.getText()));
			} catch (Exception e) {
				this.getGuiManager().showMessage("Das Bild konnte nicht geladen werden. Das Standardbild wird stattdessen verwendet.");
				this.getGuiManager().getToolkit().initialize();
			}
			this.getGuiManager().getToolkit().updateToolkit();
			this.getGuiManager().getInteractionSectionSwitch().removeInteractionSectionListener(this);
		}
	}
	
}
