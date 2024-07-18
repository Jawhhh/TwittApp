package by.jawh.eventsforalltopics.events;

import java.time.LocalDate;

public class UserRegisteredEvent {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate bornDate;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(Long id,
                               String firstname,
                               String lastname,
                               String email,
                               LocalDate bornDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.bornDate = bornDate;

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
