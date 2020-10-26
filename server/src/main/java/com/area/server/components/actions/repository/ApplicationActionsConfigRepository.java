package com.area.server.components.actions.repository;

import com.area.server.components.actions.model.Actions;
import com.area.server.components.actions.model.ActionsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application actions config repository.
 */
public interface ApplicationActionsConfigRepository extends JpaRepository<ActionsConfig, String> {
}