package com.area.server.components.about;

import com.area.server.components.services.repository.ApplicationServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The type About controller.
 */
@RestController
@RequestMapping("/about.json")
public class AboutController {

    /**
     * The Services repository.
     */
    @Autowired
	ApplicationServicesRepository servicesRepository;
	
	private String getClientIp(HttpServletRequest request)
	{
		String ip = "";
		if (request != null) {
			ip = request.getHeader("X-FORWARDED-FOR");
            if (ip  == null || "".equals(ip)) {
            	ip  = request.getRemoteAddr();
            }
        }
		return ip;
	}

    /**
     * Process data about.
     *
     * @param request the request
     * @return the about
     */
    @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody About processData(HttpServletRequest request) {
			About about = new About();
			about.client.host = getClientIp(request);
			about.server.current_time = System.currentTimeMillis() / 1000L;
			about.server.services = servicesRepository.findAll();
	        return about;
	    }
}