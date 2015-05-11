package networking;

import java.io.File;
import java.util.List;

public interface CloudService {

	/**
	 * Opens a connection to the underlying service
	 * Needs to be done before any other function can be successfully called
	 * 
	 * @param loginCredential – the token required by the cloudService
	 * @return true – connection successful, false – connection failed
	 */
	public boolean connect(String loginCredential);
	
	/**
	 * Uploads a file to the default location on the cloud service
	 * 
	 * @param file
	 * @return true – upload successful, false – upload failed
	 */
	public boolean upload(File file);
	
	/**
	 * Uploads the file to the specified location
	 * 
	 * @param file
	 * @param location
	 * @return true – upload successful, false – upload failed
	 */
	public boolean upload(File file, String location);
	
	/**
	 * Downloads the file to the default location tooot toooooot
	 * 
	 * @param fileID
	 * @return Byte representation of downloaded file
	 */
	public byte[] download(int fileID);
	
	/**
	 * Downloads the file to the specified location
	 * @param fileID
	 * @param location
	 * @return Byte representation of downloaded file
	 */
	public byte[] download(int fileID, String location);
	
	/**
	 * Returns all stored files on the cloud service that this application has created
	 * 
	 * @return List of file IDs under the default location
	 */
	public List<Integer> getFileList();
	
	public boolean isLoggedIn();
}
