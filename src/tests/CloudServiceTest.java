package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import networking.CloudService;
import networking.DummyCloudService;

import org.junit.Before;
import org.junit.Test;

public class CloudServiceTest {

	CloudService _service;
	String _tmpSecret;
	File _testFile;
	
	@Before
	public void setUp() throws Exception {
		_tmpSecret = "dickbutt";
		_service = new DummyCloudService(_tmpSecret);
		_testFile = new File("src/tests/upload_test.txt");
	}

	@Test
	public void testConnect() {
		_service.connect(_tmpSecret);
		assertTrue(_service.isLoggedIn());
	}
	
	@Test
	public void testUnauthorizedAccessProtection() {
		_service.connect("some false secret");
		assertFalse(_service.isLoggedIn());
		
		try {
			_service.upload(_testFile);
			fail("Access was possible before a call to CloudService.connect()");
		} catch (AssertionError e) {
			assertTrue("Access was denied because no call to CloudService.connect() was made", true);
		}
		
		try {
			_service.download(_testFile.hashCode());
			fail("Access was possible before a call to CloudService.connect()");
		} catch (AssertionError e) {
			assertTrue("Access was denied because no call to CloudService.connect() was made", true);
		}
		
		try {
			_service.getFileList();
			fail("Access was possible before a call to CloudService.connect()");
		} catch (AssertionError e) {
			assertTrue("Access was denied because no call to CloudService.connect() was made", true);
		}

	}
	
	@Test
	public void testUpload() {
		_service.connect(_tmpSecret);
		assertTrue(_service.upload(_testFile));
	}

	@Test
	public void testRemoteFileListing() {
		_service.connect(_tmpSecret);
		_service.upload(_testFile);
		List<Integer> list = _service.getFileList();
		assertEquals(1, list.size());
		int id = _testFile.hashCode();
		int sample = list.get(0);
		assertEquals(id, sample);
	}
	
	@Test
	public void testDownload() {
		_service.connect(_tmpSecret);
		_service.upload(_testFile);
		int id = _service.getFileList().get(0);
		assertNotNull(_service.download(id));
	}
}
