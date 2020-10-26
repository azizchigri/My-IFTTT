package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.actions.repository.DbMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Timer service.
 */
@Service
public class TimerService {
    /**
     * The Db map repository.
     */
    @Autowired
    DbMapRepository dbMapRepository;

    /**
     * The Application actions config repository.
     */
    @Autowired
    ApplicationActionsConfigRepository applicationActionsConfigRepository;

    /**
     * Ti timeout boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Ti_timeout(ActionsConfig actionsConfig){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

        try {
            DbMap dateDb = DbMap.dbToMap(actionsConfig.getParameters()).get("date");
            Date timoutDate = df.parse(dateDb.getValue());
            if (new Date().compareTo(timoutDate) > 0) {
                //execute reaction
                System.out.println("Date is Reached, Execute Reaction");
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Ti frequency boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Ti_frequency(ActionsConfig actionsConfig){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        try {
            DbMap dateDb = DbMap.dbToMap(actionsConfig.getParameters()).get("date");
            DbMap frequencyDb = DbMap.dbToMap(actionsConfig.getParameters()).get("frequency");
            Date executeDate = df.parse(dateDb.getValue());
            Date now = new Date();
            if (now.compareTo(executeDate) > 0) {
                Calendar newYearsDay = Calendar.getInstance();
                newYearsDay.add(Calendar.SECOND, Integer.parseInt(frequencyDb.getValue()) * 30);
                dateDb.setValue(df.format(newYearsDay.getTime()));
                dbMapRepository.save(dateDb);
                System.out.println("Date Reached, Reaction executed + Date execution updated");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
