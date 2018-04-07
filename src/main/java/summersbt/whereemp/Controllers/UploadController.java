package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import summersbt.whereemp.ProcessData.UnArchive;

import static summersbt.whereemp.ProcessData.Process.getCountEmp;
import static summersbt.whereemp.ProcessData.Process.getFileExtension;
import static summersbt.whereemp.WhereempApplication.allEmployee;

@Controller
public class UploadController {
    @RequestMapping(value="/indexformupload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model){
        String info = "success upload";
        if (!file.isEmpty())
            allEmployee = UnArchive.unArchive(file, allEmployee);
        if (!getFileExtension(file.getOriginalFilename()).equals("zip"))
            info = "ERROR: please make sure that the file has the ZIP extension";
        model.addAttribute("count", String.valueOf(getCountEmp()));
        model.addAttribute("info", info);
        return "index";
    }
}
