package backend;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterForFiles extends FileFilter {

	private String ext1 = ".jpg";
	private String ext2 = ".png";
	private String description = "Image from type JPG or PNG";
	
	public FilterForFiles() {
		super();
	}

	@Override
	public boolean accept(File file) {
		return (file.isFile() && (file.getName().endsWith(ext1) ||
				file.getName().endsWith(ext2)));
	}

	@Override
	public String getDescription() {
		return ext1 + " or " + ext2 + " - " + description;
	}
	
}