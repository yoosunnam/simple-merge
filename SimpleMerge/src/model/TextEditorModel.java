package model;
/**
* TextEditorModel.java
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextEditorModel {
	private File file;
	private Mode mode;

	private String originalFileContent;
	private String fileContentBuffer;
	private boolean dirty;

	private ArrayList<String> alignedFileContentBufferList;		/* for viewing purpose */
	private ArrayList<String> fileContentBufferList;			/* for saving purpose */
	private ArrayList<Integer> diffIndices;
	private ArrayList<int[]> blocks;
	private int traverseIndex;									/* index of blocks */

	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private BufferedWriter bw;

	public TextEditorModel() {
		file = null;
		mode = Mode.VIEW;

		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;

		fileContentBufferList = null;

		fr = null;
		fw = null;
		br = null;
		bw = null;
	}

	/**
	 * opens the file in the corresponding file path and sets the file of PanelInfo.
	 * @return true if successfully loaded file, false if failed to load files
	 */
	public boolean load(String filePath) {
		if (file != null) {
			// PanelView에서 얘가 return false하면, save 먼저 하고 다시 시도하게 해야 함
			if (dirty) {
				System.out.println("The file has been modified. Please save the file before opening another one.");
				return false;
			}

			closeFile();
		}

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			originalFileContent = new String();
			String s = br.readLine();

			if (s != null)
				originalFileContent += s;
			while ((s = br.readLine()) != null) {
				originalFileContent += "\r\n";
				originalFileContent += s;
			}

			file = new File(filePath);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
				}
		}

		fileContentBuffer = new String(originalFileContent);
		dirty = false;
		return true;
	}

	/**
	 * saves the file
	 * @return true if success, false if failure.
	 */
	public boolean save() {
		if (file == null) {
			System.out.println("No file exists.");
			return false;
		}
		String filePath = getFilePath();

		return this.saveAs(filePath);
	}

	/**
	 * save the file w/ different file name
	 * @param newFilePath
	 * @return true if success, false if failure.
	 */
	public boolean saveAs(String newFilePath) {
		System.out.println("save as");
		newFilePath = this.concatFileExtension(newFilePath);
		
		try {
			file = new File(newFilePath);

			fw = new FileWriter(newFilePath);
			bw = new BufferedWriter(fw);
			
			bw.write(fileContentBuffer);

			System.out.println("Saved");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to save file.");
			return false;
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
				}
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
				}
		}

		dirty = false;
		originalFileContent = new String(fileContentBuffer);

		return true;
	}

	/**
	 * operation when user closes the file
	 */
	public void closeFile() {
		file = null;
		mode = Mode.VIEW;

		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;

		fileContentBufferList = null;
	}

	/**
	 * Switch the mode to another mode
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
		case VIEW:
			dirty = false;
			break;
		case EDIT:
			break;
		case COMPARE:
			dirty = false;
			break;
		default:
			break;
		}
	}
	
	/**
	 * convert ArrayList<String> to String when saving contents from Compare Mode
	 */
	public void fileContentBufferToString() {
		fileContentBuffer = new String();

		fileContentBuffer += fileContentBufferList.get(0);
		for (int i = 1; i < fileContentBufferList.size(); i++) {
			fileContentBuffer += "\r\n";
			fileContentBuffer += fileContentBufferList.get(i);
		}

	}

	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * 
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return file != null;
	}
	
	/** 
	 * resets the modification in file.
	 */
	public void resetToOriginal() {
		this.fileContentBuffer = new String(originalFileContent);
		this.dirty = false;
	}
	
	/* Private Functions */
	private String concatFileExtension(String filePath) {
		String extension = new String();
		int ptr = file.getName().lastIndexOf('.');
		if (ptr != -1)
			extension = file.getName().substring(ptr);
		
		if (filePath.lastIndexOf('.') == -1) { 
			filePath += extension;
			System.out.println(filePath);
		}
		return filePath;
		
	}

	/* Getter & Setter */
	public File getFile() {
		return this.file;
	}
	
	public String getFilePath() {
		if (file == null)
			return "";
		else
			return file.getPath();
	}

	/**
	 * @return the file name of current file.
	 * returns null if no file is open
	 */
	public String getFileName() {
		if (file == null)
			return "";
		else
			return file.getName();
	}
	
	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.mode;
	}
	
	public String getOriginalFileContent() {
		return this.originalFileContent;
	}

	public String getFileContentBuffer() {
		return this.fileContentBuffer;
	}

	/**
	 * set the file content as a copy of 'fileContent'
	 * @param fileContent
	 */
	public void setFileContentBuffer(String fileContent) {
		fileContentBuffer = new String(fileContent);
	}
	
	public boolean isUpdated() {
		return dirty;
	}

	public void setUpdated(boolean flag) {
		this.dirty = flag;
	}

	public ArrayList<String> getAlignedFileContentBufferList() {
		return alignedFileContentBufferList;
	}
	
	public void setAlignedFileContentBufferList(ArrayList<String> alignedFileContentBufferList) {
		this.alignedFileContentBufferList = alignedFileContentBufferList;
	}

	/**
	 * call when entering compare mode
	 * @return parsed file content buffer
	 */
	public ArrayList<String> getFileContentBufferStringToList() {

		String[] fcArray = fileContentBuffer.split("\\r?\\n", -1);
		this.fileContentBufferList = new ArrayList<String>(Arrays.asList(fcArray));

		return this.fileContentBufferList;
	}
	
	public ArrayList<String> getFileContentBufferList() {
		return this.fileContentBufferList;
	}
	
	public void setFileContentBufferList(ArrayList<String> fileContentBufferList) {
		this.fileContentBufferList = fileContentBufferList;
	}
	
	public void setDiffIndices(ArrayList<Integer> diffIndices) {
		this.diffIndices = diffIndices;
	}

	public void setBlocks(ArrayList<int[]> blocks) {
		this.blocks = blocks;
	}
	
	public void setTraverseIndex(int traverseIndex) {
		this.traverseIndex = traverseIndex;
	}

	public ArrayList<Integer> getDiffIndices() {
		return diffIndices;
	}

	public ArrayList<int[]> getBlocks() {
		return blocks;
	}
	
	/**
	 * @return current block indices. returns null if no block is selected
	 */
	public int[] getCurrentBlock() {
		if (traverseIndex < 0 || traverseIndex > this.blocks.size())
			return null;
		else
			return this.blocks.get(traverseIndex);
	}
	
	public int getTraverseIndex() {
		return traverseIndex;
	}
}
