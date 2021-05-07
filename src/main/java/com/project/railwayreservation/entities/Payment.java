package com.project.railwayreservation.entities;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String cardNo;

    @NotBlank
    private String cardName;

    @NotBlank
    private String expiryDate;

    @NotBlank
    private String cvv;

    public Payment() {
    }

    public Payment(String cardNo, String cardName, String expiryDate, String cvv) {
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getId(), payment.getId()) && Objects.equals(getCardNo(), payment.getCardNo()) && Objects.equals(getCardName(), payment.getCardName()) && Objects.equals(getExpiryDate(), payment.getExpiryDate()) && Objects.equals(getCvv(), payment.getCvv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCardNo(), getCardName(), getExpiryDate(), getCvv());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", cardName='" + cardName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
