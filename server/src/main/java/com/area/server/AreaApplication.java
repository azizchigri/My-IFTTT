package com.area.server;

import com.area.server.components.actions.repository.ApplicationActionsRepository;
import com.area.server.components.reactions.repository.ApplicationReactionsRepository;
import com.area.server.components.services.model.*;
import com.area.server.components.services.repository.ApplicationServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

/**
 * The type Area application.
 */
@SpringBootApplication
@EnableScheduling
public class AreaApplication {

    /**
     * The Actions repository.
     */
    @Autowired
    ApplicationActionsRepository actionsRepository;

    /**
     * The Reactions repository.
     */
    @Autowired
    ApplicationReactionsRepository reactionsRepository;

    /**
     * The Services repo.
     */
    @Autowired
    ApplicationServicesRepository servicesRepo;

    /**
     * B crypt password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AreaApplication.class, args);
    }

    /**
     * Init widgets command line runner.
     *
     * @return the command line runner
     */
    @Bean
    @Transactional
    public CommandLineRunner initWidgets() {
        return args -> {
            saveService(new Twitch());
            saveService(new Reddit());
            saveService(new Gmail());
            saveService(new Spotify());
            saveService(new Timer());
            saveService(new Weather());
            saveService(new Youtube());
        };
    }

    /**
     * Save service services.
     *
     * @param service the service
     * @return the services
     */
    Services saveService(Services service)
    {
        actionsRepository.saveAll(service.getActions());
        reactionsRepository.saveAll(service.getReactions());
        servicesRepo.save(service);
        return service;
    }
}

