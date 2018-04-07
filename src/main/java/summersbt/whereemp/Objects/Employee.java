package summersbt.whereemp.Objects;

import summersbt.whereemp.BroadcastData.DataResult;
import summersbt.whereemp.Objects.TableRow;

import java.util.Calendar;
import java.util.Vector;

import static summersbt.whereemp.ProcessData.Process.getTimeInMinutes;

/*
Один сотрудник, который имеет массив объектов в которых содержатся данные о работе сотрудника
Имя сотрудника, его табельный номер и общее время работы
 */
public class Employee {
    public static final String ALIAS_TABLE_NAME = "e";
    public static final String TABLE_NAME = "employee";
    public static final String NAME_COLUMN = "name";
    public static final String NUM_COLUMN = "num";

    private Calendar calendar = Calendar.getInstance(); // календарь для получения дня недели из даты
    private Vector<Vector<TableRow>> allRow = new Vector<>(); // записи таблицы 0 - понедельник, 1 - вторник и тд
    private Vector<Integer> allTimeWork = new Vector<>(); // общее время работы 0 - часы, 1 - минуты, 2 - секунды
    private String name; // имя сотрудника
    private long num; // табельный номер
    private boolean isAddNewData = false; // добавление новых данных

    /*
    Конструктор
        создаём вектора для дней недели
     */
    public Employee() {
        for (int i = 0; i < 7; i++) {
            allRow.add(new Vector<>());
        }
    }

    // добавление строки
    public void addRow(TableRow tableRow) {
        calendar.setTime(tableRow.getDateWork());
        if (!zeroStr(tableRow)) { // проверка строки на корректность
            isAddNewData = true; // указатель того что данные были изменены
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2; // так как понедельнику соответствует 2 и тд до субботы (7)
            if (dayOfWeek == -1) // так как воскресенью соответствует 1
                dayOfWeek = 6;
            if (dayOfWeek >= 0 && dayOfWeek <= 6)
                allRow.get(dayOfWeek).add(tableRow);
            else
                System.out.println("Пропуск дня");
        }
    }

    // добавление времени работы к общему времени работы
    public void addTimeWork(String timeWork) {
        String[] partTime = timeWork.split(":"); // разбиваем строку
        if (partTime.length > 2) { // корректность введённых данных
            if (allTimeWork.size() > 2) { // если уже есть какое то время
                if (allTimeWork.get(2) + Integer.parseInt(partTime[2]) >= 60) {
                    allTimeWork.set(2, allTimeWork.get(2) + Integer.parseInt(partTime[2]) - 60);
                    if (allTimeWork.get(1) + Integer.parseInt(partTime[1]) + 1 >= 60) {
                        allTimeWork.set(1, allTimeWork.get(1) + Integer.parseInt(partTime[1]) - 60);
                        allTimeWork.set(0, allTimeWork.get(0) + Integer.parseInt(partTime[0]) + 1);
                    } else {
                        allTimeWork.set(1, allTimeWork.get(1) + Integer.parseInt(partTime[1]) + 1);
                        allTimeWork.set(0, allTimeWork.get(0) + Integer.parseInt(partTime[0]));
                    }
                } else { // иначе добавляем
                    allTimeWork.set(0, allTimeWork.get(0) + Integer.parseInt(partTime[0]));
                    allTimeWork.set(1, allTimeWork.get(1) + Integer.parseInt(partTime[1]));
                    allTimeWork.set(2, allTimeWork.get(2) + Integer.parseInt(partTime[2]));
                }
            } else { // иначе первый раз добавляем время
                allTimeWork.add(Integer.parseInt(partTime[0]));
                allTimeWork.add(Integer.parseInt(partTime[1]));
                allTimeWork.add(Integer.parseInt(partTime[2]));
            }
        }
    }

    // проверка на пустуб строку
    private boolean zeroStr(TableRow tableRow) {
        // параметры пустой строки
        if (tableRow.makeDate("01.01.2000").toString().equals(tableRow.toString()) &&
                tableRow.getIn().equals("0:0") && tableRow.getOut().equals("0:0") &&
                tableRow.getDateWork().equals("0:0") && tableRow.getRoomIn().equals("") &&
                tableRow.getRoomOut().equals(""))
            return true;
        return false;
    }

    // получить общее количество рабочих дней
    public int getAllDayWork() {
        int allDayWork = 0;
        for (Vector<TableRow> oneDay : allRow)
            allDayWork += oneDay.size();
        return allDayWork;
    }

    // получить вероятность выхода работника на работу в определённый день
    public DataResult getProbablyDay(int day) {
        if (day >= 0 && day <= 6) {
            int allDayWork = getAllDayWork();
            if (allDayWork != 0)
                return makeDataOutput((double) allRow.get(day).size() / allDayWork, day, day);
            else
                return makeDataOutput(0, day, day);
        } else {
            System.out.println("День введён не корректно");
            return makeDataOutput(0, 0, 0);
        }
    }

    // получить вероятность выхода работника на рабо
    public DataResult getProbablyTime(Vector<Integer> startTime, Vector<Integer> endTime) {
        if (endTime.get(0) < startTime.get(0)) { // если часы выхода меньше чем часы входа
            endTime.set(0, endTime.get(0) + 24);
        }

        int countAll = 0; // общее количество рабочего времени
        int countTrueTime = 0; // время когда можно поймать работника
        for (Vector<TableRow> oneDayOfWeek : allRow) {
            for (TableRow oneRow : oneDayOfWeek) {
                countAll++;
                // если начальное время удавлетворяет условию
                if (include(oneRow, getTimeInMinutes(startTime), getTimeInMinutes(endTime)) != 0)
                    countTrueTime++;
                /*if ((oneRow.getInVector().get(0) < startTime.get(0)) ||
                        (oneRow.getInVector().get(0) == startTime.get(0) && oneRow.getInVector().get(1) <= startTime.get(1))) {
                    if ((oneRow.getOutVector().get(0) > endTime.get(0)) ||
                            (oneRow.getOutVector().get(0) == endTime.get(0) && oneRow.getOutVector().get(1) >= endTime.get(1))) {
                        countTrueTime++;
                    }
                }*/
            }
        }
        return makeDataOutput((double) countTrueTime / countAll, getTimeInMinutes(startTime), getTimeInMinutes(endTime));
    }

    /**
     * Проверка нахождение интервала в строке
     * @param tableRow строка
     * @param timeIn время входа
     * @param timeOut время выхода
     * @return 0 - не входит интервал, 1 - интервал входит, 2 - интервалы совпадают
     */
    public int include(TableRow tableRow, int timeIn, int timeOut) {
        if (tableRow.getInMin() < tableRow.getOutMin()) {
            if (tableRow.getInMin() < timeIn && timeOut < tableRow.getOutMin()) //tableRow.getOutMin() - tableRow.getInMin() > segment)
                return 1;
            else if (tableRow.getInMin() == timeIn && timeOut == tableRow.getOutMin())//tableRow.getOutMin() - tableRow.getInMin() == segment)
                return 2;
        } else if ((tableRow.getInMin() < timeIn && timeOut < 24 * 60) || (0 < timeIn && timeOut < tableRow.getOutMin())
                || (tableRow.getInMin() < timeIn && timeOut < tableRow.getOutMin()))//(24 * 60 - tableRow.getInMin() + tableRow.getOutMin() > segment))
            return 1;
        else if ((tableRow.getInMin() == timeIn && timeOut == 24 * 60) || (0 == timeIn && timeOut == tableRow.getOutMin())
                || (tableRow.getInMin() == timeIn && timeOut == tableRow.getOutMin()))
            return 2;
        return 0;
    }

    /**
     * Получить минимальную запись по веремени работы
     * @param data запись
     * @return индекс минимальной записи по времени работы
     */
    public int getIndexMin(Vector<TableRow> data) {
        int index = 0, min = Integer.MAX_VALUE;
        for (int i = 0; i < data.size(); i++)
            if (data.get(i).getTimeWorkMin() < min) {
                min = data.get(i).getTimeWorkMin();
                index = i;
            }
        return index;
    }

    // получить наиболее вероятностное время поймать коллегу в заданный промежуток времени
    public DataResult getMaxProbablyTime(int segment) {
        Vector<TableRow> rowSave = new Vector<>();
        Vector<Vector<Integer>> dataTime = new Vector<>();
        int countAllData = 0;
        // добавляем только те сегменты, в которые можно включить интервал
        for (Vector<TableRow> dayOfWeek : allRow)
            for (TableRow oneTime : dayOfWeek) {
                countAllData++;
                if (oneTime.getTimeWorkMin() >= segment)
                    rowSave.add(oneTime);
            }

        while (rowSave.size() > 0) {
            int indexMin = getIndexMin(rowSave); // берем минимальную запись
            Vector<Integer> oneList = new Vector<>(); // добавляем в возможные ответы
            oneList.add(rowSave.get(indexMin).getInMin());
            oneList.add(rowSave.get(indexMin).getOutMin());
            oneList.add(1);
            dataTime.add(oneList);
            rowSave.remove(indexMin); // удаляем из исходного
            // смотрим в какие интервалы войдет полученный интервал и считаем их количество, а так же добавляем и удаляем повторяющиеся интервалы (такие же как минимальный)
            for (int j = 0; j < rowSave.size(); j++) {
                int isInclude = include(rowSave.get(j), oneList.get(0), oneList.get(1));
                if (isInclude != 0)
                    oneList.set(2, oneList.get(2) + 1);
                if (isInclude == 2)
                    rowSave.remove(j);
            }
        }
        // поиск наибольшей вероятности
        int indexMax = 0;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < dataTime.size(); i++)
            if (max < (double) dataTime.get(i).get(2) / countAllData) {
                max = (double) dataTime.get(i).get(2) / countAllData;
                indexMax = i;
            }
        // данные для вывода
        if (dataTime.size() > 0)
            return makeDataOutput(max, dataTime.get(indexMax).get(0), dataTime.get(indexMax).get(1));
        else
            return makeDataOutput(0, 0, 0);
    }

    /**
     * Данные для вывода
     * @param probably вероятность
     * @param minIn время входа
     * @param minOut время выхода
     */
    private DataResult makeDataOutput(double probably, int minIn, int minOut) {
        DataResult dataResult = new DataResult();
        dataResult.setNum(num);
        dataResult.setName(name);
        dataResult.setProbably(probably * 100);
        dataResult.setPrimeTime(minIn, minOut);
        dataResult.setAllData(makeStringData());
        return dataResult;
    }

    /**
     * Получения данных в качестве одной строки
     * @return строка с данными
     */
    public Vector<String> makeStringData() {
        Vector<String> res = new Vector<>();
        res.add("(Date Work; Time in; Time out; Room in; Room out; Time Work)");
        for (Vector<TableRow> dayOfWeek : allRow)
            for (TableRow row : dayOfWeek)
                res.add(row.makeStrData());
        return res;
    }

    // сеттеры и геттеры

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public long getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Employee employee = (Employee) obj;
        if (!name.equals(employee.getName()) || num != employee.getNum())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee[name=" + name + ", num=" + num + "]";
    }
}