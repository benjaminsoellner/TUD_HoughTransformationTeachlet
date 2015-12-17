package gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

/**
 * This class creates and controls the document-elements from the infoSection
 */
public class DocBrowser extends JPanel {

	private static final long serialVersionUID = 2280153124712750375L;

	private JEditorPane editor;

	private JScrollPane scrollPane;

	/**
	 * Standard constructor
	 */
	public DocBrowser() {
		super();
		this.setLayout(new BorderLayout());

		editor = new JEditorPane("text/html","");

		editor.setEditable(false);
		
		scrollPane = new JScrollPane(editor);

		this.add(scrollPane, BorderLayout.CENTER);

	}
	
	/**
	 * Standard constructor
	 * @param url
	 */
	public DocBrowser(String url) {
		super();
		this.setLayout(new BorderLayout());

		try {
			editor = new JEditorPane(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		editor.setEditable(false);
		
		scrollPane = new JScrollPane(editor);
		this.add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * Set the URL for the page
	 * @param url
	 */
	public void setEditorUrl(String url) {
		try {
			editor.setPage(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Set the text for the document
	 * @param text
	 */
	public void setEditorHTML(String text) {


		editor = new JEditorPane();

		EditorKit editorKit = editor.getEditorKitForContentType("text/html");
		Document doc = editorKit.createDefaultDocument();
		editor.setEditorKit(editorKit); 
		editor.setDocument(doc);
		editor.setEditable(false);
		doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
		try {
			editorKit.read(new StringReader(text), doc, 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		scrollPane.setViewportView(editor);


		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				scrollPane.getVerticalScrollBar().setValue(0);
				scrollPane.getHorizontalScrollBar().setValue(0);
			}
		});
	}

}
