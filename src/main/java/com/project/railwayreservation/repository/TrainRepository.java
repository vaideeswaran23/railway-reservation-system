package com.project.railwayreservation.repository;

import com.project.railwayreservation.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findAllByDepartureLocationAndArrivalLocation(String departureLocation, String arrivalLocation);

}
