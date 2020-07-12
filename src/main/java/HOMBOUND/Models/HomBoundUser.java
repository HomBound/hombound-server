package HOMBOUND.Models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class HomBoundUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Boolean volunteer;
    @NotNull
    private Boolean active;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "requestedBy")
    private List<HomBoundRequest> homBoundsRequested;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "acceptedBy")
    private List<HomBoundRequest> homBoundsAccepted;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<PaymentMethod> paymentMethods;

    public HomBoundUser() {
    }

    public HomBoundUser(Long id, String firstName, String lastName, String username, String email, String password, Boolean volunteer, Boolean active, String address, String city, String state, Date createdAt, Date updatedAt, List<HomBoundRequest> homBoundsRequested, List<HomBoundRequest> homBoundsAccepted, List<PaymentMethod> paymentMethods) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.volunteer = volunteer;
        this.active = active;
        this.address = address;
        this.city = city;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homBoundsRequested = homBoundsRequested;
        this.homBoundsAccepted = homBoundsAccepted;
        this.paymentMethods = paymentMethods;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public Boolean getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Boolean volunteer) {
        this.volunteer = volunteer;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<HomBoundRequest> getHomBoundsRequested() {
        return homBoundsRequested;
    }

    public void setHomBoundsRequested(List<HomBoundRequest> homBoundsRequested) {
        this.homBoundsRequested = homBoundsRequested;
    }

    public List<HomBoundRequest> getHomBoundsAccepted() {
        return homBoundsAccepted;
    }

    public void setHomBoundsAccepted(List<HomBoundRequest> homBoundsAccepted) {
        this.homBoundsAccepted = homBoundsAccepted;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
