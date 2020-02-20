package mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/students")
    public String studentPage(){
        return "student";
    }

    @RequestMapping("/groups")
    public String groupPage(){
        return "group";
    }

    @RequestMapping("/teachers")
    public String teacherPage(){
        return "teacher";
    }
}