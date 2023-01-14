package sg.edu.nus.iss.app.workshop13.workshop13.models;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import sg.edu.nus.iss.app.workshop13.workshop13.annotations.IntegerValidation;

// testing @Component and @Autowired
@Component("contact")
public class Contact {

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, max = 64, message = "Min 3 characters and max 64 characters")
    private String name;

    @Email
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Cannot be empty")
    @Size(min = 7, message = "At least 7 digits")
    // @IntegerValidation // custom validation
    private String phone;

    @Positive(message = "\"-\" not allowed")
    @NotNull(message = "Only numbers allowed")
    private Integer phoneNumInt;

    @Past(message = "Date of birth must not be future")
    @NotNull(message = "Date of Birth is mandatory")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOfBirth;

    @NotNull(message = "User's age cannot be null")
    @Min(value = 10, message = "Must be above 10 years old")
    @Max(value = 100, message = "Must be below 100 years old")
    private Integer age;

    private Integer numChars = 8;

    private String id;

    // Spring will use this constructor and in turn will generate id when class is
    // instantiated
    public Contact() {
        this.id = this.generateId();
    }

    // this constructor is for passing to model
    public Contact(
            String name,
            String email,
            String phone,
            LocalDate dateOfBirth,
            String id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    // for create new Contact object to list all contacts with id and name only
    public Contact(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumInt() {
        return phoneNumInt;
    }

    public void setPhoneNumInt(Integer phoneNumInt) {
        this.phoneNumInt = phoneNumInt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // this is needed. Without this try catch, the field phoneNumInt will throw
        // error
        // message "Property phone threw exception" on the validation message
        try {
            if (!phone.isEmpty()) {
                this.setPhoneNumInt(Integer.parseInt(phone));
            }
        } catch (NumberFormatException e) {
        }
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth != null) {
            LocalDate currLocalDate = LocalDate.now();
            Integer age = Period.between(dateOfBirth, currLocalDate).getYears();
            this.setAge(age);
        }
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private synchronized String generateId() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numChars; i++) {
            // generate number between 0 to 15
            Integer randNum = rand.nextInt();
            sb.append(Integer.toHexString(randNum));
        }

        return sb.toString().substring(0, numChars);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
