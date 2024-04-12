package sg.nus.Show.Tell.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.Show.Tell.Model.weatherDescr;
import sg.nus.Show.Tell.Service.weatherService;

@Controller
@RequestMapping
public class weatherController {
    
    @Autowired
    private weatherService weatherSvc;

    @GetMapping("/details")
    public ModelAndView search(@RequestParam String city) {
        
        List<weatherDescr> res = weatherSvc.cityWeather(city);

        ModelAndView mav = new ModelAndView("details");
        mav.addObject("city", city);
        mav.addObject("details", res);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }
}
