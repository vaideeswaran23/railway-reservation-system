package com.project.railwayreservation.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.railwayreservation.entities.Passenger;
import com.project.railwayreservation.entities.Ticket;
import com.project.railwayreservation.entities.Train;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class PdfExporter {

    private Ticket ticket;

    public PdfExporter(Ticket ticket) {
        this.ticket = ticket;
    }

    private void writeTrainTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.lightGray);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Train Number", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Train Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Departure Time", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Arrival Time", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fare", font));
        table.addCell(cell);
    }

    private void writePassengerTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.lightGray);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Age", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Mobile", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Berth Preference", font));
        table.addCell(cell);
    }

    private void writeTrainTableData(PdfPTable table) {
        Train train = ticket.getTrain();
        table.addCell(String.valueOf(train.getTrainNumber()));
        table.addCell(train.getTrainName());
        table.addCell(train.getDate());
        table.addCell(train.getDepartureTime());
        table.addCell(train.getArrivalTime());
        table.addCell(String.valueOf(train.getFare()));
    }

    private void writePassengerTableData(PdfPTable table) {
        Passenger passenger = ticket.getPassenger();
        table.addCell(passenger.getName());
        table.addCell(String.valueOf(passenger.getAge()));
        table.addCell(passenger.getGender());
        table.addCell(passenger.getEmail());
        table.addCell(passenger.getMobile());
        table.addCell(passenger.getBerth());
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Booked Train", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTrainTableHeader(table);
        writeTrainTableData(table);

        document.add(table);

        Paragraph p2 = new Paragraph("Paasenger details", font);
        p2.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p2);

        PdfPTable table2 = new PdfPTable(6);
        table2.setWidthPercentage(100f);
        table2.setWidths(new float[] {3.5f, 1.5f, 1.5f, 4.5f, 3.5f, 1.5f});
        table2.setSpacingBefore(10);

        writePassengerTableHeader(table2);
        writePassengerTableData(table2);

        document.add(table2);

        document.close();

    }

}
