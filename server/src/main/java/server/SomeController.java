package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class SomeController {
    /**
     * Placeholder for checkstyle
     * @return placeholder
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        Config config = new Config();
        return config.getRandom().toString();
    }
}