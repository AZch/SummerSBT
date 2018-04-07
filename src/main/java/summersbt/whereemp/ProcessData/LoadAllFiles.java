package summersbt.whereemp.ProcessData;

import summersbt.whereemp.Objects.Employee;

import java.io.File;
import java.util.Vector;

/*
Загрузка всех данных работников из файлов
разрешение файлов xlsx
 */
public class LoadAllFiles {
    /**
     * Рекурсивная загрузка всех файлов из директории которые имеют расширения xlsx
     * @param folder папка для загрузки
     * @param employees уже имеющиеся работники
     * @return полученное новое количество работников
     */
    public Vector<Employee> processFilesFromFolder(File folder, Vector<Employee> employees) {
        if (employees == null) // если на данный момент в общем нет работников, то выделяем память под массив работников
            employees = new Vector<>();
        ParseExcel parseExcel = new ParseExcel(); // объект который рабзбирет excel файл и позволяет получить работника
        File[] folderEntries = folder.listFiles(); // всё внутренее содержимое папки
        for (File entry : folderEntries) {
            if (entry.isDirectory()) { // если внутри встретили папку, то рекурсивно обрабатываем её
                employees = processFilesFromFolder(entry, employees);
                continue;
            } else {
                if (Process.getFileExtension(entry).equals("xlsx")) { // проверка расширения файла
                    Employee employee = parseExcel.ReadExcel(entry.toString(), employees);
                    if (!checkEmp(employee, employees)) // проверка объекта на новизну (если такой объект уже был, то он автоматически дополняется)
                        employees.add(employee);
                }
            }
        }
        return employees;
    }

    /**
     * Проверка нахождения одного объекта в массиве другого объекта
     * @param employee объект для проверки
     * @param allEmployee массив для проверки
     * @return true - объект принадлежит массиву, иначе false
     */
    public static boolean checkEmp(Employee employee, Vector<Employee> allEmployee) {
        for (Employee oneEmployee : allEmployee) {
            if (employee.getName().equals(oneEmployee.getName()) && employee.getNum() == oneEmployee.getNum()) {
                return true;
            }
        }
        return false;
    }
}
