package summersbt.whereemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import summersbt.whereemp.BroadcastData.DataRequest;
import summersbt.whereemp.BroadcastData.DataResult;
import summersbt.whereemp.Objects.Employee;

import static summersbt.whereemp.ProcessData.Process.getEmployeeName;
import static summersbt.whereemp.ProcessData.Process.getEmployeeNum;

@Controller
public class CheckEmpController {
    @PostMapping("/resulcheck")
    public String resultSubmit(@ModelAttribute DataRequest dataRequest, Model model) {
        Employee employee = getEmployeeName(dataRequest.getName());
        DataResult dataResult = new DataResult();
        if (employee != null) {
            dataResult.setAllData(employee.makeStringData());
            dataResult.setNum(employee.getNum());
            dataResult.setName(employee.getName() + "(name field find)");
        } else {
            employee = getEmployeeNum((int) dataRequest.getNum());
            if (employee != null) {

                dataResult.setAllData(employee.makeStringData());
                dataResult.setNum(employee.getNum());
                dataResult.setName(employee.getName() + "(num field find)");
            } else {
                dataResult.setName("Not found " + dataRequest.getName() + "!");
                dataResult.setNum(0);
                dataResult.setProbably(0);
                dataResult.addAllData("empty");
            }
        }
        model.addAttribute("dataresult", dataResult);
        return "resultcheck";
    }
}
