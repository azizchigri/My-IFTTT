package com.area.server.components.actions.repository;

import com.area.server.components.actions.model.Actions;
import com.area.server.components.reactions.model.Reactions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application actions repository.
 */
public interface ApplicationActionsRepository extends JpaRepository<Actions, String> {
}