package com.area.server.components.user.repository;

import com.area.server.components.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application user repository.
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    /**
     * Find by username application user.
     *
     * @param username the username
     * @return the application user
     */
    ApplicationUser findByUsername(String username);
}

