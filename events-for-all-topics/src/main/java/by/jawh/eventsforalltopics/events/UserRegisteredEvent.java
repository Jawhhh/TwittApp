package by.jawh.eventsforalltopics.events;

import java.time.LocalDate;
import java.util.List;

public class UserRegisteredEvent {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private LocalDate bornDate;

    public UserRegisteredEvent(Long id) {
        this.id = id;
    }

    public UserRegisteredEvent(Long id,
                               String firstname,
                               String lastname,
                               String username,
                               LocalDate bornDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
