package main.Services;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Resources.Constants;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

public class DashboardWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel imagePanel;
	private JTextArea textArea;
	private File selectedFile;

	public DashboardWindow() {
		setTitle("Screenshot Dashboard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a JSplitPane to split the window horizontally
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		getContentPane().add(splitPane);

		// Create a JPanel for the file system tree
		JPanel fileSystemPanel = new JPanel(new BorderLayout());
		fileSystemPanel.setPreferredSize(new Dimension(300, 700)); // Set preferred size
		splitPane.setLeftComponent(fileSystemPanel);

		// Create a JTree to represent the folder structure
		File rootDirectory = new File(Constants.SCREENSHOTS_FOLDER);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory.getName());
		buildTree(rootDirectory, root);
		JTree tree = new JTree(new DefaultTreeModel(root));

		// Customize the cell renderer to display a text box with each image file
		tree.setCellRenderer(new ImageFileTreeCellRenderer());

		// Add the JTree to a JScrollPane and add it to the file system panel
		JScrollPane treeScrollPane = new JScrollPane(tree);
		fileSystemPanel.add(treeScrollPane, BorderLayout.CENTER);

		// Create a JPanel for the images
		imagePanel = new JPanel(new BorderLayout());
		splitPane.setRightComponent(imagePanel);

		// Create text area
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// Add tree selection listener to display image when an image file node is
		// selected
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if(!textArea.getText().isEmpty() || !textArea.getText().isBlank()) {
					DisplayImagePane.saveText(selectedFile, textArea.getText());
				}
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode != null && selectedNode.getUserObject() instanceof File) {
					selectedFile = (File) selectedNode.getUserObject();
					if (isImageFile(selectedFile)) {
						DisplayImagePane.displayImage(selectedFile, imagePanel, textArea);
					}
				}
			}
		});

		// Add key listener to text area
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0)) {
                	DisplayImagePane.saveText(selectedFile, textArea.getText());
                }
            }
        });

		// Set the initial divider location
		splitPane.setDividerLocation(300);

		// Set frame properties
		pack();
		setSize(1400, 700);
		setLocation(150, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildTree(File directory, DefaultMutableTreeNode parent) {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				DefaultMutableTreeNode node;
				if (file.isDirectory()) {
					node = new DefaultMutableTreeNode(file.getName());
				} else {
					node = new DefaultMutableTreeNode(file);
				}
				parent.add(node);
				if (file.isDirectory()) {
					buildTree(file, node);
				}
			}
		}
	}

	private boolean isImageFile(File file) {
		String name = file.getName().toLowerCase();
		return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
	}
}
