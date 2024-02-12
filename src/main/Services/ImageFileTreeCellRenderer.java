package main.Services;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import Resources.Constants;

//Custom cell renderer to display a text box with each image file
class ImageFileTreeCellRenderer extends DefaultTreeCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
            if (userObject instanceof File) {
                File file = (File) userObject;
                if (isImageFile(file)) {
                    // Display a preview icon for image files
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    icon = new ImageIcon(icon.getImage().getScaledInstance(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT, Image.SCALE_SMOOTH));
      
                    // Create a JLabel to display the icon and text
                    JLabel label = new JLabel();
                    label.setIcon(icon);
                    label.setText(file.getName());

                    // Create a JPanel to add some spacing
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(label, BorderLayout.CENTER);
                    panel.setBorder(BorderFactory.createEmptyBorder(Constants.IMAGE_SPACING, 0, Constants.IMAGE_SPACING, 0)); // Add spacing

                    return panel;
                }
            }
        }
        return component;
    }
	private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }
}
