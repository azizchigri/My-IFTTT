package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.actions.repository.DbMapRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Weather service.
 */
@Service
public class WeatherService {
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
     * We temperature reached boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean We_temperature_reached(ActionsConfig actionsConfig) {

        DbMap cityDb = DbMap.dbToMap(actionsConfig.getParameters()).get("city");
        String city = cityDb.getValue();
        DbMap temperatureDb = DbMap.dbToMap(actionsConfig.getParameters()).get("temperature");
        Double temperature = Double.parseDouble(temperatureDb.getValue());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=";
        url += city.replace(' ', '+') + "&APPID=" + "8b1ca471f403bb70f50b7c4d69458f97";
        RestTemplate rest = new RestTemplate();
        try {
        JSONObject object = new JSONObject(rest.getForObject(url, String.class));
        JSONObject weather = ((JSONObject)object.get("main"));

        Double cityTemperature = Double.parseDouble(weather.get("temp").toString()) - 273.15;
        if (cityTemperature.compareTo(temperature) >= 0) {
            //Execute Reaction
            applicationActionsConfigRepository.delete(actionsConfig);
            System.out.println("Temperature Reached, Reaction executed");
            applicationActionsConfigRepository.delete(actionsConfig);
            return true;
        }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * We bellow temperature boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean We_bellow_temperature(ActionsConfig actionsConfig) {

        DbMap cityDb = DbMap.dbToMap(actionsConfig.getParameters()).get("city");
        String city = cityDb.getValue();
        DbMap temperatureDb = DbMap.dbToMap(actionsConfig.getParameters()).get("temperature");
        Double temperature = Double.parseDouble(temperatureDb.getValue());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=";
        url += city.replace(' ', '+') + "&APPID=" + "8b1ca471f403bb70f50b7c4d69458f97";
        RestTemplate rest = new RestTemplate();
        try {
            JSONObject object = new JSONObject(rest.getForObject(url, String.class));
            JSONObject weather = ((JSONObject)object.get("main"));

            Double cityTemperature = Double.parseDouble(weather.get("temp").toString()) - 273.15;
            if (cityTemperature.compareTo(temperature) < 0) {
                //Execute Reaction
                applicationActionsConfigRepository.delete(actionsConfig);
                System.out.println("Temperature Not reached, Reaction executed");
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
