package com.project.railwayreservation.repository;

import java.util.*;
import com.project.railwayreservation.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserId(Long user_id);

    @Modifying
    @Query(value = "DELETE FROM Ticket t WHERE t.id = :id")
    void deleteUsingId(@Param("id") Long id);

}
