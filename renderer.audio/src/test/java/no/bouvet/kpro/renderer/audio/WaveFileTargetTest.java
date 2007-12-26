package no.bouvet.kpro.renderer.audio;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WaveFileTargetTest extends AudioTargetTest {
	private File file;

	@Before
	public void setUp() throws Exception {
		file = File.createTempFile("kprotest", "tmp");
		target = new WaveFileTarget(file);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		if (file != null)
			file.delete();
		file = null;
	}

	@Test
	public void testClose() throws Exception {
		writeSamples();
		target.close();
		target = null;

		String md5 = MD5.get(file);
		assertEquals("f54fd889d8255823527e15cef4419189", md5);
	}
}
