package com.dsl.simulator;

import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class SatopsDslApplication {

	public static void main(String[] args) {

		try {
			// Copy orekit-data.zip from resources to a temporary file
			final java.io.InputStream zipStream = SatopsDslApplication.class.getClassLoader()
					.getResourceAsStream("orekit-data.zip");
			if (zipStream == null) {
				System.err.println("CRITICAL: orekit-data.zip not found in resources!");
				throw new RuntimeException("orekit-data.zip not found");
			}
			final File tempZip = File.createTempFile("orekit-data", ".zip");
			tempZip.deleteOnExit();
			java.nio.file.Files.copy(zipStream, tempZip.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

			final DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
			manager.addProvider(new org.orekit.data.ZipJarCrawler(tempZip));
		} catch (java.io.IOException e) {
			throw new RuntimeException("Failed to load Orekit data", e);
		}

		SpringApplication.run(SatopsDslApplication.class, args);
	}
}