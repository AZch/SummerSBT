package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import summersbt.whereemp.BroadcastData.DataRequest;

import static summersbt.whereemp.ProcessData.Process.getCountEmp;

@Controller
public class WhatFindController {
    @RequestMapping("/findDayOfWeek")
    public String findDayOfWeek(Model model){
        model.addAttribute("datarequest", new DataRequest());
        return "findMostDayOfWeek";
    }

    @RequestMapping("/findInterval")
    public String findInterval(Model model){
        model.addAttribute("datarequest", new DataRequest());
        return "findInterval";
    }

    @RequestMapping("/findMostProbablyTime")
    public String findMostProbablyTime(Model model){
        model.addAttribute("datarequest", new DataRequest());
        return "findMostTime";
    }

    @RequestMapping("/indexfromfind")
    public String handleFileUpload(Model model){
        String info = "back from find";
        model.addAttribute("count", String.valueOf(getCountEmp()));
        model.addAttribute("info", info);
        return "index";
    }
}
