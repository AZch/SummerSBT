package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import summersbt.whereemp.BroadcastData.DataRequest;
import summersbt.whereemp.BroadcastData.DataResult;

import static summersbt.whereemp.ProcessData.Process.getCountEmp;
import static summersbt.whereemp.WhereempApplication.allEmployee;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String mainPage(Model model) {
        model.addAttribute("count", String.valueOf(getCountEmp()));
        model.addAttribute("info", "hi :)");
        return "index";
    }

    @PostMapping("/clear")
    public String clear(Model model) {
        DataResult dataResult = new DataResult(); // для перспективного развития
        if (allEmployee == null)
            dataResult.setNum((long) 0);
        else
            dataResult.setNum((long) allEmployee.size());
        model.addAttribute("dataresult", dataResult);
        return "cleardata";
    }

    @PostMapping("/upload")
    public String goUpload() {
        return "upload";
    }

    @RequestMapping("/upload")
    public String provideUploadInfo() {
        return "upload";
    }

    @RequestMapping("/check")
    public String checkEmp(Model model) {
        model.addAttribute("datarequest", new DataRequest());
        return "checkemp";
    }
}
