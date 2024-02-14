package main.Services;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Resources.Constants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		fileSystemPanel.setPreferredSize(new Dimension(300, Constants.DASHBOARD_HEIGHT)); // Set preferred size
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

		// Create text area for input text
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// Add tree selection listener to display image when an image file node is
		// selected
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (!textArea.getText().isEmpty() || !textArea.getText().isBlank()) {
					textDataService.saveText(selectedFile, textArea.getText());
					textArea.setText("");
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

		// Set the initial divider location
		splitPane.setDividerLocation(300);
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(!textArea.getText().isEmpty())
            		textDataService.saveText(selectedFile, textArea.getText());
            }
        });

		// Set frame properties
		pack();
		setSize(Constants.DASHBOARD_WIDTH, Constants.DASHBOARD_HEIGHT);
		setLocation(150, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildTree(File directory, DefaultMutableTreeNode parent) {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if(!file.getName().toLowerCase().endsWith(".properties")) {					
					DefaultMutableTreeNode node;
					if (file.isDirectory()) {
						node = new DefaultMutableTreeNode(file.getName());
						buildTree(file, node);
					}
					else {
						node = new DefaultMutableTreeNode(file);
					}
					parent.add(node);
				}
			}
		}
	}

	private boolean isImageFile(File file) {
		String name = file.getName().toLowerCase();
		return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
	}
}
