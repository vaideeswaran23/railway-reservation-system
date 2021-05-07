package com.project.railwayreservation.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private Train train;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

    public Ticket() {
    }

    public Ticket(User user, Train train, Payment payment, Passenger passenger) {
        this.user = user;
        this.train = train;
        this.payment = payment;
        this.passenger = passenger;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getId(), ticket.getId()) && Objects.equals(getUser(), ticket.getUser()) && Objects.equals(getTrain(), ticket.getTrain()) && Objects.equals(getPayment(), ticket.getPayment()) && Objects.equals(getPassenger(), ticket.getPassenger());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getTrain(), getPayment(), getPassenger());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + user +
                ", train=" + train +
                ", payment=" + payment +
                ", passenger=" + passenger +
                '}';
    }
}
