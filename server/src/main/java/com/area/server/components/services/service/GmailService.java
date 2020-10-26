package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.DbMapRepository;
import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * The type Gmail service.
 */
@Service
public class GmailService {

    private static Long id = 0L;

    /**
     * The Db map repository.
     */
    @Autowired
    DbMapRepository dbMapRepository;

    /**
     * Gm rcv mail boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Gm_rcv_mail(ActionsConfig actionsConfig) {
        boolean res;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        String url = "https://www.googleapis.com/gmail/v1/users/me/messages";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getGmailToken());
        ResponseEntity<String> resp = rest.exchange(
                url + "?labelIds=INBOX", HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        DbMap dateDb = DbMap.dbToMap(actionsConfig.getParameters()).get("date");
        resp = rest.exchange(
                url + "/" + response.getJSONArray("messages").getJSONObject(0).getString("id") + "?format=minimal", HttpMethod.GET, new HttpEntity(headers), String.class);
        response = new JSONObject(resp.getBody());
        Date date = new Date(Long.parseLong(response.getString("internalDate")));
        try {
            res = df.parse(dateDb.getValue()).before(date);
            if (res) {
                dateDb.setValue(df.format(date));
                dbMapRepository.save(dateDb);
            }
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    private String buildMail(String from, String to, String subject, String content) {
        String model = "From: " + from + " \n" +
                "To: " + to + " \n" +
                "Subject: " + subject + " \n" +
                "Date: " + new Date().toString() + " \n" +
                "Message-ID: <" + id.toString() + "@area.machine>\n" +
                "\n" + content;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(model.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gm send mail boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Gm_send_mail(ReactionsConfig reactionsConfig, ApplicationUser user) {
        Map<String, DbMap> params = DbMap.dbToMap(reactionsConfig.getParameters());
        String from = params.get("fromName").getValue() + " <" + params.get("fromMail").getValue() + ">";
        String to = params.get("toName").getValue() + " <" + params.get("toMail").getValue() + ">";
        JSONObject body = new JSONObject("{ \"raw\": " + buildMail(from, to, params.get("subject").getValue(), params.get("content").getValue()) + " }");
        ResponseEntity<String> resp = YoutubeService.postRequest("https://www.googleapis.com/gmail/v1/users/me/messages/send", body, user.getGmailToken());
        return resp.getStatusCode() == HttpStatus.OK;
    }
}
