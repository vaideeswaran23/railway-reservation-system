package com.project.railwayreservation.controller;

import com.lowagie.text.DocumentException;
import com.project.railwayreservation.entities.*;
import com.project.railwayreservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TrainController {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/train")
    public String getAllTrains(Model model) {
        List<Train> trainList = trainRepository.findAll();
        model.addAttribute("trains", trainList);
        model.addAttribute("search", new Search("Alabama", "NewYork"));
        return "viewTrains";
    }

    @PostMapping("/search")
    public String searchTrains(Search search, BindingResult result, Model model) {
        List<Train> trainList = trainRepository.findAllByDepartureLocationAndArrivalLocation(search.getDepartureLocation(), search.getArrivalLocation());
        model.addAttribute("trains", trainList);
        model.addAttribute("search", search);
        return "viewTrains";
    }

    @PostMapping("/train")
    public String addTrain(Train train, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("train", train);
            return "addTrain";
        }
        trainRepository.save(train);
        List<Train> trainList = trainRepository.findAll();
        model.addAttribute("trains", trainList);
        model.addAttribute("search", new Search());
        return "viewTrains";
    }

    @GetMapping("/addTrain")
    public String addTrain(Model model) {
        model.addAttribute("train", new Train());
        return "addTrain";
    }

    @GetMapping("/book/{id}")
    public String bookTrain(@PathVariable Long id, Model model) {
        Train train = trainRepository.findById(id).get();
        model.addAttribute("train", train);
        model.addAttribute("passenger", new Passenger());
        return "booking";
    }

    @PostMapping("/payment/{id}")
    public String payment(@PathVariable Long id, Passenger passenger, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("passenger", passenger);
            return "booking";
        }
        Train train = trainRepository.findById(id).get();
        model.addAttribute("train", train);
        passenger = passengerRepository.save(passenger);
        model.addAttribute("passenger", passenger);
        model.addAttribute("payment", new Payment());
        return "payment";
    }

    @PostMapping("/confirm/{id}/{pid}")
    public String confirm(HttpServletResponse response, @PathVariable Long id, @PathVariable Long pid, Payment payment, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("passenger", payment);
            return "payment";
        }
        Train train = trainRepository.findById(id).get();
        Passenger passenger = passengerRepository.findById(pid).get();
        payment = paymentRepository.save(payment);
        List<User> userList = userRepository.findAll();
        Ticket ticket = new Ticket();
        for(User user : userList) {
            if(user.isLoggedIn()) {
                ticket.setUser(user);
                break;
            }
        }
        ticket.setTrain(train);
        ticket.setPassenger(passenger);
        ticket.setPayment(payment);
        ticket = ticketRepository.save(ticket);
        train.setAvailableSeats(train.getAvailableSeats() - 1);
        trainRepository.save(train);
        model.addAttribute("ticket", ticket);
        sendEmail(passenger.getEmail());
        return "confirm";
    }

    private void sendEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Booking confirmation email");
        msg.setText("Thank you for booking \n Your booking has been confirmed, Please access your account to check the booking details");

        javaMailSender.send(msg);
    }

    @GetMapping("/export/{id}")
    public void exportToPDF(HttpServletResponse response, @PathVariable Long id) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Ticket ticket = ticketRepository.findById(id).get();
        PdfExporter exporter = new PdfExporter(ticket);
        exporter.export(response);
    }


    @GetMapping("/myaccount")
    public String myAccount(Model model) throws DocumentException, IOException {
        Long id = null;
        List<User> userList = userRepository.findAll();
        Ticket ticket = new Ticket();
        for(User user : userList) {
            if(user.isLoggedIn()) {
                id = user.getId();
                break;
            }
        }
        List<Ticket> ticketList = ticketRepository.findAllByUserId(id);
        model.addAttribute("tickets", ticketList);
        return "account";
    }

    @GetMapping("/cancel/{id}/{uid}")
    @Transactional
    public String cancel(@PathVariable Long id, @PathVariable Long uid,Model model) {
        ticketRepository.deleteUsingId(id);
        List<Ticket> ticketList = ticketRepository.findAllByUserId(uid);
        model.addAttribute("tickets", ticketList);
        return "account";
    }

}
