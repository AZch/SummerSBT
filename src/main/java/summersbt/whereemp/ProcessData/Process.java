package summersbt.whereemp.ProcessData;

import summersbt.whereemp.Objects.Employee;

import java.io.File;
import java.util.Vector;

import static summersbt.whereemp.WhereempApplication.allEmployee;

public class Process {
    // получиь вектор часов и минут
    public static Vector<Integer> getVectorTime(int hour, int min) {
        Vector<Integer> res = new Vector<>();
        res.add(hour);
        res.add(min);
        return res;
    }

    /**
     * Возвращяет время в минутах (только для типа час:минута)
     * @param time исходное время
     * @return полученное время
     */
    public static int getTimeInMinutes(Vector<Integer> time) {
        if (time.size() > 2)
            return -1;
        return time.get(0) * 60 + time.get(1);
    }

    // получиьт количество сотрудников
    public static int getCountEmp() {
        if (allEmployee  == null)
            return 0;
        else
            return allEmployee.size();
    }

    // сотрудник по имени
    public static Employee getEmployeeName(String name) {
        if (allEmployee == null)
            return null;
        if (name.equals(""))
            return null;
        for (Employee employee : allEmployee) {
            if (name.equals(employee.getName()))
                return employee;
        }
        return null;
    }

    // сотрудника по номеру
    public static Employee getEmployeeNum(int num) {
        if (allEmployee == null)
            return null;
        for (Employee employee : allEmployee) {
            if (num == employee.getNum())
                return employee;
        }
        return null;
    }

    //метод определения расширения файла
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла,
        // то вырезаем все знаки после последней точки в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    //метод определения расширения файла
    public static String getFileExtension(String fileName) {
        // если в имени файла есть точка и она не является первым символом в названии файла,
        // то вырезаем все знаки после последней точки в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    // сравнивает векторы одинаковой длины
    public static boolean checkVect(Vector<Integer> first, Vector<Integer> second) {
        for (int i = 0; i < first.size(); i++)
            if (first.get(i).equals(second.get(i)))
                return false;
        return true;
    }
}
