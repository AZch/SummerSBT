package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import summersbt.whereemp.BroadcastData.DataRequest;
import summersbt.whereemp.BroadcastData.DataResult;
import summersbt.whereemp.Objects.Employee;

import static summersbt.whereemp.ProcessData.Process.getEmployeeName;

@Controller
public class FindDayOfWeekController {
    @PostMapping("/resultDayOfWeek")
    public String resultSubmit(@ModelAttribute DataRequest dataRequest, Model model) {
        Employee employee = getEmployeeName(dataRequest.getName());
        DataResult dataResult = new DataResult();
        if (employee != null) {
            dataResult = employee.getProbablyDay(dataRequest.getTimeInterval() - 1);
            switch (dataRequest.getTimeInterval()) {
                case 1:
                    dataResult.setPrimeTime("Monday");
                    break;
                case 2:
                    dataResult.setPrimeTime("Tuesday");
                    break;
                case 3:
                    dataResult.setPrimeTime("Wednesday");
                    break;
                case 4:
                    dataResult.setPrimeTime("Thursday");
                    break;
                case 5:
                    dataResult.setPrimeTime("Friday");
                    break;
                case 6:
                    dataResult.setPrimeTime("Saturday");
                    break;
                case 7:
                    dataResult.setPrimeTime("Sunday");
                    break;
                default:
                    dataResult.setPrimeTime("not index day of week!");
                    break;
            }
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
