package com.area.server.components.reactions.repository;

import com.area.server.components.reactions.model.Reactions;
import com.area.server.components.services.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application reactions repository.
 */
public interface ApplicationReactionsRepository extends JpaRepository<Reactions, String> {
}