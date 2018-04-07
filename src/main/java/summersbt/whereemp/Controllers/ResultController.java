package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import summersbt.whereemp.BroadcastData.DataRequest;

import static summersbt.whereemp.ProcessData.Process.getCountEmp;

@Controller
public class ResultController {
    @PostMapping("/find")
    public String findFormThis(Model model) {
        model.addAttribute("datarequest", new DataRequest());
        return "whatfind";
    }

    @PostMapping("/indexformresult")
    public String goBack(Model model) {
        model.addAttribute("count", String.valueOf(getCountEmp()));
        model.addAttribute("info", "success find");
        return "index";
    }
}
