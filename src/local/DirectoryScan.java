package local;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import MessageDigest.BLAKE512;

public class DirectoryScan {
	
	private Queue<File> _directoryQueue;
	private ArrayList<String> _hashList;
	
	public DirectoryScan(String rootDir) {
		_directoryQueue = new LinkedList<File>();
		_hashList = new ArrayList<String>(1);
		_directoryQueue.offer(new File(rootDir));
	}
	
	public ArrayList<String> scanDirectory() {

		while(!_directoryQueue.isEmpty()) {
			File current = _directoryQueue.poll();

			for(File f : current.listFiles()) {
				if(f.isDirectory()) {
				_directoryQueue.offer(f);
				}
				else {
					String hash = makeHash(f);
					_hashList.ensureCapacity(_hashList.size() + 1);
					_hashList.add(hash);
				}
			}
				
		}
		return _hashList;
	}
	
	private String makeHash(File f) {
		try (FileInputStream fi = new FileInputStream(f);) {
			byte[] content = new byte[(int) f.length()];
			fi.read(content);
			fi.close();
			BLAKE512 bl = new BLAKE512();
			bl.update(content);
			byte[] b = bl.digest();
			String hex = (new HexBinaryAdapter()).marshal(b);
			return hex;
			
		} catch (Exception e) {
			return null;
		}
	}
	

	public static void main(String[] args) {
		DirectoryScan d = new DirectoryScan("/home/daniel/Pictures");
		
		long t = System.nanoTime();
		
		List<String> result = d.scanDirectory();
		
		long t2 = System.nanoTime();
		long dt = (t2-t)/1000000000;
		System.out.println("Scanning and hashing took " + dt + " seconds");
		System.out.println(result.size());
	}
}
