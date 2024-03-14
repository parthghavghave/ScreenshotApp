package main.Frames;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Resources.Constants;
import main.Panels.DisplayImagePane;
import main.Services.ImageFileTreeCellRenderer;
import main.Services.WordExtractor;
import main.Services.textDataService;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DashboardWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel imagePanel;
	private JTextArea textArea;
	private File selectedFile;
	private String selectedFolder;

	public DashboardWindow() {
		setTitle("Screenshot Dashboard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int dashboardHeight = (int) ((screenSize.height) * (Constants.DASHBOARD_HEIGHT));
		int dashboardWidth = (int) ((screenSize.width) * (Constants.DASHBOARD_WIDTH));

		// Create a JSplitPane to split the window horizontally
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		getContentPane().add(splitPane);

		// Create a JPanel for the file system tree
		JPanel fileSystemPanel = new JPanel(new BorderLayout());
		fileSystemPanel.setPreferredSize(
				new Dimension((int) (dashboardWidth * Constants.FILESYSTEM_PANEL_WIDTH), dashboardHeight)); // Set
																											// preferred
																											// size
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

		// Add a button against the root directory
		JButton rootButton = new JButton("Extract to Word");

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
				if (selectedNode != null && selectedNode.getParent() != null 
			            && selectedNode.getParent().toString().equals("ScreenSnip")) {
					selectedFolder = selectedNode.toString();
				}
				if (selectedNode != null && selectedNode.getUserObject() instanceof File) {
					selectedFile = (File) selectedNode.getUserObject();
					if (isImageFile(selectedFile)) {
						DisplayImagePane.displayImage(selectedFile, imagePanel, textArea, dashboardWidth,
								dashboardHeight);
					}
				}
			}
		});

		rootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String baseFolderPath = Constants.SCREENSHOTS_FOLDER + File.separator + selectedFolder;
                WordExtractor.folderToWordDoc(baseFolderPath);
            }
        });
		
		fileSystemPanel.add(rootButton, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!textArea.getText().isEmpty())
					textDataService.saveText(selectedFile, textArea.getText());
			}
		});

		// Set frame properties
		pack();
		setSize(dashboardWidth, dashboardHeight);
		setLocation(150, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildTree(File directory, DefaultMutableTreeNode parent) {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (!file.getName().toLowerCase().endsWith(".properties")) {
					DefaultMutableTreeNode node;
					if (file.isDirectory()) {
						node = new DefaultMutableTreeNode(file.getName());
						buildTree(file, node);
					} else {
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
