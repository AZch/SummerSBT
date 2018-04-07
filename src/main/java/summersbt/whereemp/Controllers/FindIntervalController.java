package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import summersbt.whereemp.BroadcastData.DataRequest;
import summersbt.whereemp.BroadcastData.DataResult;
import summersbt.whereemp.Objects.Employee;

import static summersbt.whereemp.ProcessData.Process.getEmployeeName;
import static summersbt.whereemp.ProcessData.Process.getVectorTime;

@Controller
public class FindIntervalController {
    @PostMapping("/resultInterval")
    public String resultSubmit(@ModelAttribute DataRequest dataRequest, Model model) {
        Employee employee = getEmployeeName(dataRequest.getName());
        DataResult dataResult = new DataResult();
        if (employee != null) {
            dataResult = employee.getProbablyTime(getVectorTime(dataRequest.getHourTimeIn(), dataRequest.getMinTimeIn()),
                    getVectorTime(dataRequest.getHourTimeOut(), dataRequest.getMinTimeOut()));
        } else {
            dataResult.setName("Not found " + dataRequest.getName() + "!");
            dataResult.setNum(0);
            dataResult.setProbably(0);
            dataResult.addAllData("empty");
        }
        model.addAttribute("dataresult", dataResult);
        return "result";
    }
}
