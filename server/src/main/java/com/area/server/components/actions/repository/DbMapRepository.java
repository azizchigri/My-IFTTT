package com.area.server.components.actions.repository;

import com.area.server.components.actions.model.Actions;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.reactions.model.ReactionsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Db map repository.
 */
public interface DbMapRepository extends JpaRepository<DbMap, String> {
}