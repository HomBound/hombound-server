package HOMBOUND.Models;

import HOMBOUND.Models.enums.HomBoundStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class HomBoundRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private HomBoundUser requestedBy;

    @ManyToOne
    private HomBoundUser acceptedBy;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "homBoundRequest")
    private List<Item> requestedItems;

    @NotNull
    private String store;
    @NotNull
    private String status;
    @CreationTimestamp
    private Date createdAt;
    private Date acceptedAt;
    private Date completedAt;
    private String receiptImgUrl;
    private String paymentSentImgUrl;

    public HomBoundRequest() {
    }

    public HomBoundRequest(Long id, HomBoundUser requestedBy, HomBoundUser acceptedBy, List<Item> requestedItems, String store, String status, Date createdAt, Date acceptedAt, Date completedAt, String receiptImgUrl, String paymentSentImgUrl) {
        this.id = id;
        this.requestedBy = requestedBy;
        this.acceptedBy = acceptedBy;
        this.requestedItems = requestedItems;
        this.store = store;
        this.status = status;
        this.createdAt = createdAt;
        this.acceptedAt = acceptedAt;
        this.completedAt = completedAt;
        this.receiptImgUrl = receiptImgUrl;
        this.paymentSentImgUrl = paymentSentImgUrl;
    }

    public Long getId() {
        return id;
    }

    public HomBoundUser getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(HomBoundUser requestedBy) {
        this.requestedBy = requestedBy;
    }

    public HomBoundUser getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(HomBoundUser acceptedBy) {
        this.acceptedBy = acceptedBy;
    }



    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getReceiptImgUrl() {
        return receiptImgUrl;
    }

    public void setReceiptImgUrl(String receiptImgUrl) {
        this.receiptImgUrl = receiptImgUrl;
    }

    public String getPaymentSentImgUrl() {
        return paymentSentImgUrl;
    }

    public void setPaymentSentImgUrl(String paymentSentImgUrl) {
        this.paymentSentImgUrl = paymentSentImgUrl;
    }

    public List<Item> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<Item> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
