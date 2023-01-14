package sg.edu.nus.iss.app.workshop13.workshop13;

import java.applet.AppletContext;
import java.util.List;

// import javax.swing.Spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import sg.edu.nus.iss.app.workshop13.workshop13.util.IOUtil;

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Workshop13Application.class);

		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		// args = --dataDir=/opt/tmp/data
		// dataDir is the option
		// /opt/tmp/data is the argument
		List<String> optionValues = appArgs.getOptionValues("dataDir");
		if (optionValues != null && !optionValues.isEmpty()) {
			IOUtil.createDir(optionValues.get(0));
			logger.info("directory created: " + optionValues.get(0));
		} else {
			logger.warn("No data directory was provided");
			System.exit(1);
		}

		app.run(args);
	}

}
