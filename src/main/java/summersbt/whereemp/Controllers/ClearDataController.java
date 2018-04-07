package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import static summersbt.whereemp.ProcessData.Process.getCountEmp;
import static summersbt.whereemp.WhereempApplication.allEmployee;

@Controller
public class ClearDataController {

    @PostMapping("/indexformclear")
    public String goBack(Model model) {
        if (allEmployee != null)
            allEmployee.clear();
        model.addAttribute("count", String.valueOf(getCountEmp()));
        model.addAttribute("info", "success clear");
        return "index";
    }
}
