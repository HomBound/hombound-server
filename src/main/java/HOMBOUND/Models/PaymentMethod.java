package HOMBOUND.Models;

import HOMBOUND.Models.enums.PaymentPlatform;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private PaymentPlatform platform;
    @NotNull
    private String accountName;
    private String phone;
    private String email;
    private Boolean preferred;

    @JsonIgnore
    @ManyToOne
    private HomBoundUser user;

    public PaymentMethod(Long id, PaymentPlatform platform, String accountName, String phone, String email, Boolean preferred, HomBoundUser user) {
        this.id = id;
        this.platform = platform;
        this.accountName = accountName;
        this.phone = phone;
        this.email = email;
        this.preferred = preferred;
        this.user = user;
    }

    public PaymentMethod() {
    }

    public Long getId() {
        return id;
    }

    public PaymentPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(PaymentPlatform platform) {
        this.platform = platform;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public HomBoundUser getUser() {
        return user;
    }

    public void setUser(HomBoundUser user) {
        this.user = user;
    }
}
