package networking;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DummyCloudService implements CloudService {

	private HashMap<Integer, String> _emulatedStorage;
	private String _secret;
	private boolean _loggedIn;
	
	public DummyCloudService(String secret) {
		_emulatedStorage = new HashMap<Integer, String>(10);
		_secret = secret;
		_loggedIn = false;
	}
	
	@Override
	public boolean connect(String loginCredential) {
		if (loginCredential == _secret) {
			_loggedIn = true;
		}
		else {
			_loggedIn = false;
		}
		return _loggedIn;
	}

	@Override
	public boolean upload(File file) {
		assert _loggedIn == true;
		int id = file.hashCode();
		
		try(FileInputStream fi = new FileInputStream(file);) {
			byte[] content = new byte[(int) file.length()];
			fi.read(content);
			fi.close();
			_emulatedStorage.put(id, content.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean upload(File file, String location) {
		assert _loggedIn == true;
		
		return false;
	}

	@Override
	public byte[] download(int fileID) {
		assert _loggedIn == true;
		String result = _emulatedStorage.get(fileID);
		if (result != null) {
			return result.getBytes();
		}
		return null;
	}

	@Override
	public byte[] download(int fileID, String location) {
		assert _loggedIn == true;
		return null;
	}

	@Override
	public List<Integer> getFileList() {
		assert _loggedIn == true;
		Set<Integer> result = _emulatedStorage.keySet();
		ArrayList<Integer> output = new ArrayList<Integer>(result.size());
		for(int val: result) {
			output.add(val);
		}
		return output;
	}
	
	@Override
	public boolean isLoggedIn() {
		return _loggedIn;
	}
}
