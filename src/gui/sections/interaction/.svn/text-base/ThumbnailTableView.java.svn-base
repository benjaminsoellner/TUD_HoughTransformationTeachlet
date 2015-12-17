package gui.sections.interaction;

import ij.ImagePlus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class shows the ThumbnailTableView
 */
public class ThumbnailTableView extends JTable {

	private static final long serialVersionUID = 1L;
	private Dimension thumbnailSize = new Dimension(128, 128);
	private CellRenderer cellRenderer;

	/**
	 * Standard constructor
	 */
	public ThumbnailTableView() {
		this.setCellSelectionEnabled(true);
		this.setRowSelectionAllowed(false);
		this.setColumnSelectionAllowed(false);
		this.setShowGrid(false);
		this.setModel(new ThumbnailTableView.Model());
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);
		this.cellRenderer = new CellRenderer();
		this.setDefaultRenderer(Object.class, this.cellRenderer);
		this.setTableHeader(null);
		this.setAutoCreateColumnsFromModel(true);
		this.setBackground(Color.white);
		this.invalidate();
	}
	
	/**
	 * Return ThumbnailTableViewModel
	 * @return ThumbnailTableViewModel
	 */
	public ThumbnailTableView.Model getThumbnailTableViewModel() {
		return (ThumbnailTableView.Model) this.getModel();
	}
	
	/**
	 * Set the thumbnail size
	 * @param thumbnailSize
	 */
	public void setThumbnailSize(Dimension thumbnailSize) {
		this.thumbnailSize = thumbnailSize;
		this.invalidate();
	}
	
	/**
	 * Returns the selectedPhotoUrl
	 * @return selectedPhotoUrl or null if it doesn't exist
	 */
	public URL getSelectedPhotoUrl() {
		int c = this.getSelectedColumn();
		int r = this.getSelectedRow();
		if (c != -1 && r != -1)
			return this.getThumbnailTableViewModel().getPhotoUrlAt(r, c);
		else
			return null;
	}
	
	/**
	 * Creates the layout
	 */
	public void doLayout() {
		this.getThumbnailTableViewModel().setColumnCount(
				this.getWidth() / this.thumbnailSize.width
			);
		int rows = this.getModel().getRowCount();
		for (int row = 0; row < rows; row++) {
			int height = this.getThumbnailTableViewModel().getPreferredRowHeight(row);
			if (height > 0) this.setRowHeight(height);
		}
		super.doLayout();
	}
	
	/**
	 * This inner class is an AbstractTableModel
	 */
	protected class Model extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		private List<ImagePlus> thumbnails = null;
		private List<URL> photoUrls = null;
		private boolean available = false;
		private int thumbnailColumns;

		/**
		 * Set the availability to true or false
		 * @param value
		 */
		public void setAvalability(boolean value) {
			boolean changed = (this.available != value);
			this.available = value;
			if (changed) this.fireTableStructureChanged();
		}
		
		/**
		 * Returns if the Model is available or not
		 * @return true if the model is available or false otherwise
		 */
		public boolean isAvailable() {
			return this.available;
		}
		
		/**
		 * Sets the number of columns
		 * @param columns
		 */
		public void setColumnCount(int columns) {
			boolean changed = (columns != this.thumbnailColumns);
			this.thumbnailColumns = (columns < 1 ? 1 : columns); 
			if (changed) this.fireTableStructureChanged();
		}
		
		/**
		 * Set the source
		 * @param thumbnails
		 * @param photoUrls
		 */
		public void setSource(List<ImagePlus> thumbnails, List<URL> photoUrls) {
			this.thumbnails = thumbnails;
			this.photoUrls = photoUrls;
			this.fireTableDataChanged();
		}
		
		/**
		 * returns the number of columns
		 * @return int number of columns
		 */
		public int getColumnCount() {
			if (this.available)
				return this.thumbnailColumns;
			else
				return 1;
		}

		/**
		 * Returns the number of rows
		 * @return int number of rows
		 */
		
		public int getRowCount() {
			if (this.available)
				return (int) Math.ceil((float)this.thumbnails.size() / this.thumbnailColumns);
			else
				return 1;
		}
		
		/**
		 * Returns a value from a row- and column index
		 * @return ImagePlus - value from a row- and column index
		 */
		public ImagePlus getValueAt(int rowIndex, int columnIndex) {
			if (this.available) {
				int index = rowIndex * this.thumbnailColumns + columnIndex;
				if (index > this.thumbnails.size()-1)
					return null;
				else
					return this.thumbnails.get(index);
			} else {
				return null;
			}
		}
		
		/**
		 * Returns a PhotoURL from an Index
		 * @param rowIndex
		 * @param columnIndex
		 * @return URL
		 */
		public URL getPhotoUrlAt(int rowIndex, int columnIndex) {
			if (this.available) {
				int index = rowIndex * this.thumbnailColumns + columnIndex;
				if (index > this.thumbnails.size()-1)
					return null;
				else
					return this.photoUrls.get(index);
			} else {
				return null;
			}
		}
		
		/**
		 * Returns the preferred row height
		 * @param row
		 * @return rowHeight
		 */
		public int getPreferredRowHeight(int row) {
			final ThumbnailTableView owner = ThumbnailTableView.this;
			int columns = this.getColumnCount();
			int height, maxHeight = 0;
			for (int column = 0; column < columns; column++) {
				height = owner.cellRenderer
					.getTableCellRendererComponent(owner, this.getValueAt(row, column), false, false, row, column)
					.getHeight();
				if (height > maxHeight)
					maxHeight = height;
			}
			return maxHeight;
		}
		
	}
	
	/**
	 * The inner class cellRenderer renders and shows the image-cells from the
	 * thumbnailpreview
	 */
	protected class CellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		/**
		 * Preprocesses the thumbnails in the ThumbnailPreview and returns an 
		 * ImagePlus-object containing the thumbnail
		 * @param owner
		 * @param thumbnail
		 * @return ImagePlus
		 */
		private ImagePlus preprocessThumbnail(ThumbnailTableView owner, ImagePlus thumbnail) {
			if (thumbnail != null) {
				Dimension newSize = new Dimension();
				if (thumbnail.getProcessor().getWidth() > owner.thumbnailSize.width) {
					newSize.width  = owner.thumbnailSize.width;
					newSize.height = thumbnail.getProcessor().getHeight()
							* owner.thumbnailSize.width / thumbnail.getProcessor().getWidth();
				}
				if (thumbnail.getProcessor().getHeight() > owner.thumbnailSize.height) {
					newSize.height = owner.thumbnailSize.height;
					newSize.width  = thumbnail.getProcessor().getWidth()
							* owner.thumbnailSize.height / thumbnail.getProcessor().getHeight();
				}
				thumbnail.getProcessor().resize(newSize.width, newSize.height);
			}
			return thumbnail;
		}
		
		/**
		 * Returns the TableCellRenderer
		 */
		public Component getTableCellRendererComponent(JTable table,
				Object o, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel thumbnailLabel = (JLabel) super.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);
			ImagePlus thumbnail = (ImagePlus)o;
			ThumbnailTableView owner = (ThumbnailTableView) table; 
			thumbnailLabel.setHorizontalTextPosition(JLabel.CENTER);
			thumbnailLabel.setVerticalAlignment(JLabel.CENTER);
			thumbnailLabel.setHorizontalAlignment(JLabel.CENTER);
			if (owner.getThumbnailTableViewModel().isAvailable()) {
				ImagePlus thumbnailImage = this.preprocessThumbnail(owner, thumbnail);
				if (thumbnailImage != null) {
					ImageIcon thumbnailIcon = new ImageIcon(thumbnailImage.getImage());
					thumbnailLabel.setIcon(thumbnailIcon);
					thumbnailLabel.setText(null);
					thumbnailLabel.setSize( thumbnailIcon.getIconWidth(), thumbnailIcon.getIconHeight()+thumbnailLabel.getFontMetrics(this.getFont()).getHeight() );
				} else {
					thumbnailLabel.setIcon(null);
					thumbnailLabel.setText(null);
				}
			} else {
				thumbnailLabel.setIcon(null);
				thumbnailLabel.setText("Laden...");
			}		
			return thumbnailLabel;
		}
				
	}
}
