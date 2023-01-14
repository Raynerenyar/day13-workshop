package sg.edu.nus.iss.app.workshop13.workshop13.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import sg.edu.nus.iss.app.workshop13.workshop13.models.Contact;

// @autowired and @component is for spring to instantiate the class into object
// and used the object in other class
// able to reduce the amount of code
@Component("contacts")
public class Contacts {
    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    @Autowired
    IOUtil ioutil;

    // create file with its id as name of file
    // write the contact's field to file
    public void saveContact(Contact contact, ApplicationArguments appArgs) {
        String dataFilename = contact.getId();
        String filePath = getDataDir(appArgs);
        try {
            // FileWriter fw = new FileWriter(filePath + "/" + dataFilename);
            FileOutputStream fos = new FileOutputStream(filePath + "/" + dataFilename);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            // BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(osw);
            pw.println(contact.getName());
            pw.println(contact.getEmail());
            pw.println(contact.getPhone());
            pw.println(contact.getDateOfBirth().toString());
            pw.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void getContactById(Contact contact, ApplicationArguments appArgs, String id) {
        String filePath = getDataDir(appArgs);
        try {
            // FileReader fr = new FileReader(filePath + "/" + id);
            FileInputStream fis = new FileInputStream(filePath + "/" + id);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            contact.setId(id);
            contact.setName(br.readLine());
            contact.setEmail(br.readLine());
            contact.setPhone(br.readLine());
            contact.setDateOfBirth(LocalDate.parse(br.readLine()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Contact info not found");
        }
    }

    // at this point when getDataDir() is called, the appArgs has already gone
    // through
    // validation at the launch of spring app
    private String getDataDir(ApplicationArguments appArgs) {
        Set<String> optionNames = appArgs.getOptionNames();
        String[] optionArr = optionNames.toArray(new String[optionNames.size()]);
        List<String> ListOfdataDir = appArgs.getOptionValues(optionArr[0]);
        if (!ListOfdataDir.isEmpty()) {
            return ioutil.getWorkinDir() + ListOfdataDir.get(0);
        } else {
            return ioutil.getWorkinDir() + "/data";
        }
    }

    public List<Contact> listAllContacts(ApplicationArguments appArgs) {
        File folder = new File(getDataDir(appArgs));
        File[] listOfFiles = folder.listFiles();
        List<Contact> listOfContactDetails = new LinkedList<>();
        for (File file : listOfFiles) {
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                Contact contact = new Contact(br.readLine(), file.getName());
                listOfContactDetails.add(contact);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage());
            }
        }
        return listOfContactDetails;
    }
}
