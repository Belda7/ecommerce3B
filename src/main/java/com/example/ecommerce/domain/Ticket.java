package com.example.ecommerce.domain;

import java.util.Date;
import java.util.Objects;

// TICKET
public class Ticket {

    public enum TicketStatus {OPEN, IN_PROGRESS, CLOSED};

    private int idTicket;
    private TicketStatus status;
    private Date initialDate;
    private String content;

    public Ticket() { }

    public Ticket(TicketBuilder ticketBuilder) {
        setIdTicket(ticketBuilder.idTicket);
        setStatus(ticketBuilder.status);
        setInitialDate(ticketBuilder.initialDate);
        setContent(ticketBuilder.content);
    }


    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return getIdTicket() == ticket.getIdTicket() && getInitialDate().equals(ticket.getInitialDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdTicket());
    }

    public static class TicketBuilder {
        private int idTicket;
        private TicketStatus status;
        private Date initialDate;
        private String content;

        public TicketBuilder idTicket(int idTicket) {
            this.idTicket = idTicket;
            return this;
        }

        public TicketBuilder status(TicketStatus status) {
            this.status = status;
            return this;
        }

        public TicketBuilder initialDate(Date initialDate) {
            this.initialDate = initialDate;
            return this;
        }

        public TicketBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
