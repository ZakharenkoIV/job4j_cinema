package ru.job4j.cinema.model.dao;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface TicketDAO {
    Collection<Ticket> getAllTickets();

    Ticket createTicket(Ticket ticket);

    Collection<Ticket> findAllTicketByAccountId(int accountId);

}
