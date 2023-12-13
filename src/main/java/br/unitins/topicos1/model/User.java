package br.unitins.topicos1.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_table")
public class User extends DefaultEntity {

    @Column(unique = true)
    private String username;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    //@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    private Profile profile;

    private String imageName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Phone> phones;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_address")
    private List<Address> addresses;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE)
    private PhysicalPerson physicalPerson;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }
}
