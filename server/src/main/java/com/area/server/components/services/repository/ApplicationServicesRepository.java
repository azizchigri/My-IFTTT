package com.area.server.components.services.repository;

import com.area.server.components.services.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application services repository.
 */
public interface ApplicationServicesRepository extends JpaRepository<Services, String> {
    /**
     * Find by name services.
     *
     * @param serviceName the service name
     * @return the services
     */
    Services findByName(String serviceName);
}