package org.uj.email.simulation;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.uj.email.simulation.SimulatedEmailsRepository.SimulatedEmail;

@RestController
@Profile("dev")
@RequestMapping("/dev/simulated-emails")
public class EmailSimulationController {
    private final SimulatedEmailsRepository repository;

    public EmailSimulationController(SimulatedEmailsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{receiverAddress}")
    public List<SimulatedEmail> getByAddress(@PathVariable String receiverAddress) {
        return repository.findByReceiverAddress(receiverAddress);

    }


}
