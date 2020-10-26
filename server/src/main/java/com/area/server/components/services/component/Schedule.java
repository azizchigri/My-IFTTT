package com.area.server.components.services.component;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.services.service.CallServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Schedule.
 */
@Component
public class Schedule {

    /**
     * The Application actions config repository.
     */
    @Autowired
    ApplicationActionsConfigRepository applicationActionsConfigRepository;

    /**
     * The Call services.
     */
    @Autowired
    CallServices callServices;

    /**
     * Refresh actions.
     */
    @Scheduled(fixedRate = 30000)
    public void refreshActions() {
        List<ActionsConfig> list = applicationActionsConfigRepository.findAll();
        for (ActionsConfig action : list) {
            callServices.callAction(action);
        }
    }
}
