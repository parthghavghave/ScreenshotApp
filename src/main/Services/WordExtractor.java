package main.Services;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import Resources.Constants;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class WordExtractor {

	public static void folderToWordDoc(String baseFolderPath, String filename) {

		try {
			XWPFDocument document = new XWPFDocument();

			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText(filename);

			List<String> imagePaths = parseFolderForImages(baseFolderPath);
			for (String imagePath : imagePaths) {
				try (FileInputStream fis = new FileInputStream(imagePath)) {
					run.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, imagePath, Units.toEMU(500), Units.toEMU(270));
					File imageFile = new File(imagePath);
					String textAssociated = textDataService.getTxtForImg(imageFile);
					if (!(textAssociated == null)) {
						run.setText(textAssociated);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String outputFile = Constants.WORD_DOC_EXTRACT_PATH + File.separator + filename + ".docx";

			// Write the document to a file
			try (FileOutputStream fos = new FileOutputStream(outputFile)) {
				document.write(fos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occurred while saving the document: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private static List<String> parseFolderForImages(String folderPath) {
		List<String> imagePaths = new ArrayList<>();
		try {
			Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (isImageFile(file)) {
						imagePaths.add(file.toString());
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagePaths;
	}

	private static boolean isImageFile(Path file) {
		String fileName = file.getFileName().toString().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
	}

}
