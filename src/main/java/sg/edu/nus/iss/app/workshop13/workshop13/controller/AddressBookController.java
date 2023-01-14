package sg.edu.nus.iss.app.workshop13.workshop13.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// import java.lang.ProcessBuilder.Redirect;

// import javax.swing.text.html.parser.Entity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
// import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.workshop13.workshop13.models.Contact;
// import sg.edu.nus.iss.app.workshop13.workshop13.models.Person;
import sg.edu.nus.iss.app.workshop13.workshop13.util.Contacts;
import sg.edu.nus.iss.app.workshop13.workshop13.util.IOUtil;

@Controller
@RequestMapping("/")
public class AddressBookController {

    // testing @Component and @Autowired
    @Autowired
    Contact contact;

    // to access methods that handle data directory
    @Autowired
    Contacts contacts;

    // for saving contacts
    // get the directory to save contacts into
    @Autowired
    ApplicationArguments appArgs;

    @Autowired
    IOUtil ioutil;

    @GetMapping
    public RedirectView localRedirect() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("contact");
        return redirectView;
    }

    @GetMapping("/contact")
    public String form(Model model) {
        // var contact = new Contact();
        model.addAttribute("contact", contact);
        return "form";
    }

    @PostMapping("/contact")
    public String saveContact(@Valid Contact contact,
            BindingResult binding, Model model, HttpServletResponse response) {
        if (binding.hasErrors()) {
            return "form";

        }
        contacts.saveContact(contact, appArgs);
        model.addAttribute("contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "thankyou";
    }

    @GetMapping("/contact/{id}")
    public String showContact(Model model, @PathVariable(name = "id") String id, Contact contact) {
        // Contact contact = new Contact();
        contacts.getContactById(contact, appArgs, id);
        model.addAttribute("condact", contact);
        return "thankyou";
    }

    @GetMapping("/contact/list")
    public String listContacts(Model model) {
        List<Contact> listOfContacts = contacts.listAllContacts(appArgs);
        model.addAttribute("listOfContacts", listOfContacts);
        return "contacts";
    }
}
