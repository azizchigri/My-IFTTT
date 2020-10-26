package com.area.server.components.reactions.repository;

import com.area.server.components.reactions.model.ReactionsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application reactions configuration repository.
 */
public interface ApplicationReactionsConfigRepository extends JpaRepository<ReactionsConfig, String> {
}
