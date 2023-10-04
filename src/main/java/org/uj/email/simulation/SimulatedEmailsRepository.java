package org.uj.email.simulation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.uj.email.simulation.SimulatedEmailsRepository.SimulatedEmail;

public interface SimulatedEmailsRepository extends JpaRepository<SimulatedEmail, String> {

    List<SimulatedEmail> findByReceiverAddress(String receiverEmail);

    @Data
    @Entity
    class SimulatedEmail {
        @Id
        private String id;
        private String emailTitle;
        private String receiverAddress;
        private String emailBody;
    }
}
