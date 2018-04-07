package summersbt.whereemp.ProcessData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import summersbt.whereemp.Objects.Employee;
import summersbt.whereemp.Objects.TableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class ParseExcel {

    /**
     * Класс который читает excel
     * @param nameFile имя файла для чтения
     * @param allEmployee уже имеющиеся файлы
     * @return полученный объект из файла
     */
    public static Employee ReadExcel(String nameFile, Vector<Employee> allEmployee) {
        Employee employee = new Employee();
        try {
            FileInputStream inputStream = new FileInputStream(new File(nameFile));
            return load(employee, allEmployee, inputStream, getNameFile(nameFile));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Класс который читает excel
     * @param file файла для чтения
     * @param allEmployee уже имеющиеся файлы
     * @return полученный объект из файла
     */
    public static Employee ReadExcel(File file, Vector<Employee> allEmployee) {
        Employee employee = new Employee();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return load(employee, allEmployee, inputStream, getNameFile(file.getName()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Читает сам ексель из потока
     * @param employee кого читаем
     * @param allEmployee все остальные работнкик
     * @param inputStream поток для чтения
     * @param name имя работника
     * @return полученный работник
     */
    private static Employee load(Employee employee, Vector<Employee> allEmployee, FileInputStream inputStream, String name) {
        try {
            employee.setName(name);

            // книга
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // лист
            XSSFSheet sheet = workbook.getSheetAt(0);
            // итератор по листам
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext()) {
                // итератор по строкам
                Iterator<Row> rowIterator = sheetIterator.next().iterator();
                boolean startRead = false; // начало чтение файла (встретили имя в файле, которое савпадает с именем файла)
                boolean startReadTableRow = false; // начали читать данные о сотруднике (встретиили дату)
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next(); // получили строку
                    // итератор по столбцам
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int countCell = 0; // подсчёт ячеек в строке
                    TableRow tableRow = new TableRow(); // строка данных о сотруднике
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next(); // получили ячейку
                        CellType cellType = cell.getCellTypeEnum(); // получили тип ячейки

                        if (!startRead && cell.getStringCellValue().equals(name)) // встретили имя в файле, которое савпадает с именем файла
                            startRead = true;

                        switch (cellType) {
                            case BLANK:
                                break;
                            case STRING:
                                // концовка данных в файле (имя встретили и читали данные одновременно)
                                if (startReadTableRow && name.equals(cell.getStringCellValue()))
                                    startRead = false;
                                // получаем общее время работы из файла и добавляем его, а также возвращяем полученный объект
                                if (!startRead && startReadTableRow && countCell == 2) {
                                    employee.addTimeWork(cell.getStringCellValue());
                                    return employee;
                                }
                                if (startRead && !startReadTableRow && isDate(cell.getStringCellValue())) // если ещё не читаем записи и дошли до даты
                                    startReadTableRow = true;
                                else if (startRead && !startReadTableRow && countCell == 2) { // если ещё не читаем записи и дошли до табельного номера
                                    employee.setNum(Long.parseLong(cell.getStringCellValue()));
                                    employee = checkNameNum(employee, allEmployee);
                                }
                                if (startRead && startReadTableRow) // читаем записи
                                    switch (countCell) {
                                        case 0: // дата работы
                                            tableRow.setDateWork(cell.getStringCellValue());
                                            break;
                                        case 1: // время входа
                                            tableRow.setIn(cell.getStringCellValue());
                                            break;
                                        case 2: // время выхода
                                            tableRow.setOut(cell.getStringCellValue());
                                            break;
                                        case 3: // кабинет входа
                                            tableRow.setRoomIn(cell.getStringCellValue());
                                            break;
                                        case 4: // кабинет выхода
                                            tableRow.setRoomOut(cell.getStringCellValue());
                                            break;
                                        case 5: // время работы
                                            tableRow.setTimeWork(cell.getStringCellValue());
                                            employee.addRow(tableRow);
                                            break;
                                    }
                                countCell++;
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * Проверка объекта на нахождение его в массиве объектов и возвращение его
     * @param employee объект для поиска
     * @param allEmployee массив объектов в котором ищем
     * @return найденный объект (если не нашли, то возвращяем его же)
     */
    public static Employee checkNameNum(Employee employee, Vector<Employee> allEmployee) {
        if (allEmployee == null)
            return employee;
        for (Employee oneEmployee : allEmployee) {
            if (employee.getName().equals(oneEmployee.getName()) && employee.getNum() == oneEmployee.getNum()) {
                return oneEmployee;
            }
        }
        return employee;
    }

    /**
     * Проверка на правильность введённой даты
     * @param date дата для проверки
     * @return результат проверки
     */
    public static boolean isDate(String date) {
        String[] splitDate = date.split("\\.");
        if (splitDate.length > 2)
            return true;
        else
            return false;
    }

    /**
     * Получение имени файла без расширения
     * @param file полный путь файла
     * @return имя файла без расширенияя
     */
    public static String getNameFile(String file) {
        return file.subSequence(file.lastIndexOf('\\') + 1, file.lastIndexOf('.')).toString();
    }
}
