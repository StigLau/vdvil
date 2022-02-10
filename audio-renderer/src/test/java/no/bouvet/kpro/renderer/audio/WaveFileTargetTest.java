package no.bouvet.kpro.renderer.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WaveFileTargetTest extends AudioTargetTest {
	private File file;

	@BeforeEach
	public void setUp() throws Exception {
		file = File.createTempFile("kprotest", "tmp");
		target = new WaveFileTarget(file);
	}

	@Override
	@AfterEach
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

        String md5 = DigestUtils.md5Hex(Files.readAllBytes(Paths.get(file.toURI())));
		assertEquals("f54fd889d8255823527e15cef4419189", md5);
	}
}
