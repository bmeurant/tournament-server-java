package org.resthub.sample.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.ResourceBundle;

@Entity
public class Participant {

    public static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean hasPicture;

    public Participant() {
        super();
    }

    public Participant(String firstname, String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Participant(String firstname, String lastname, String email) {
        this(firstname, lastname);
        this.email = email;
    }

    /**
     * Fake constructor to allow JSON serialization for transient fields
     *
     * @param firstname
     * @param lastname
     * @param email
     */
    public Participant(String firstname, String lastname, String email, String pictureUrl, String pictMin) {
        this(firstname, lastname);
        this.email = email;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public String getPictureUrl() {
        if (this.hasPicture == null) {
            if (this.getPictureFile() != null) {
                this.hasPicture = Boolean.TRUE;
            }
        }

        return (Boolean.TRUE == this.hasPicture) ? "/participant/" + this.id + "/photo" : null;
    }

    public void setPictureUrl(String pictureUrl) {
        return;
    }

    @Transient
    public String getPictMin() {
        if (this.hasPicture == null) {
            if (this.getPictureFile() != null) {
                this.hasPicture = Boolean.TRUE;
            }
        }

        return (Boolean.TRUE == this.hasPicture) ? "/participant/" + this.id + "/min-photo" : null;
    }

    public void setPictMin(String pictMin) {
        return;
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    public File getPictureFile() {
        File file = null;

        String[] formats = {"gif", "GIF", "jpg", "jpeg", "JPG", "JPEG", "png", "PNG"};
        String fileName = CONFIG.getString("photosPath") + "participant/" + this.id;

        for (String format : formats) {
            File tmpFile = new File(fileName + "." + format);
            if (tmpFile.exists()) {
                file = tmpFile;
                break;
            }
        }

        return file;
    }
}
