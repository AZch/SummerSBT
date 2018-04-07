package summersbt.whereemp.Objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import static summersbt.whereemp.ProcessData.Process.getTimeInMinutes;

/*
Строка данных о сотруднике
Содержит: даты работы, время входа, время выхода,
          время работы, кабинет входа и выхода
 */
public class TableRow {
    public static final String ALIAS_TABLE_NAME = "r";
    public static final String TABLE_NAME = "tableRow";

    public static final String ALIAS_ID_TABLEROW_COLUMN = "tableRow_id";
    public static final String DATEWORK_COLUMN = "dateWork";
    public static final String IN_COLUMN = "in";
    public static final String OUT_COLUMN = "out";
    public static final String TIMEWORK_COLUMN = "timeWork";
    public static final String ROOMIN_COLUMN = "roomIn";
    public static final String ROOMOUT_COLUMN = "roomOut";

    private Date dateWork = new Date(); // дата работы
    private Vector<Integer> in = new Vector<>(); // время входа (0 - часы, 1 - минуты)
    private Vector<Integer> out = new Vector<>(); // время выхода (0 - часы, 1 - минуты)
    private Vector<Integer> timeWork = new Vector<>(); // время работы (0 - часы, 1 - минуты)
    private String roomIn = ""; // кабинет входа
    private String roomOut = ""; // кабинет выхода

    /**
     * Получение даты из строки
     * @param date строка с датой
     * @return полученная дата
     */
    public Date makeDate(String date) {
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date newDate;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return newDate;
    }

    /**
     * Разбивает полученное время на строку
     * @param time строка где находится время
     * @return массив строк с чисами и минутами
     */
    public String[] splitTime(String time) {
        String[] twoPart = time.split(" ");
        if (twoPart.length > 1)
            twoPart = twoPart[1].split(":");
        else
            twoPart = time.split(":");
        return twoPart;
    }

    public String makeStrData() {
        String res;
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        res = "(" + format.format(dateWork) + "; " +
                getIn() + "; " + getOut() + "; " +
                roomIn + "; " + roomOut + "; " +
                getTimeWork() + ")\n" + "";
        return res;
    }

    // получить временной промежуток работы
    public int getTimeWorkMin() {
        if (getInMin() < getOutMin()) {
            return getOutMin() - getInMin();
        } else
            return 24 * 60 - getInMin() + getOutMin();
    }

    // сеттеры и геттеры
    public boolean setDateWork(String date) {
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        try {
            this.dateWork = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Date getDateWork() {
        return dateWork;
    }

    public void setIn(String in) {
        try {
            String[] twoPartIn = splitTime(in);
            this.in.add(Integer.parseInt(twoPartIn[0]));
            this.in.add(Integer.parseInt(twoPartIn[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIn() {
        String str = String.valueOf(in.get(0));
        str += ":";
        str += String.valueOf(in.get(1));
        return str;
    }

    public Vector<Integer> getInVector() {
        return in;
    }

    public int getInMin() {
        return getTimeInMinutes(in);
    }

    public void setOut(String out) {
        try {
            String[] twoPartOut = splitTime(out);
            this.out.add(Integer.parseInt(twoPartOut[0]));
            this.out.add(Integer.parseInt(twoPartOut[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOut() {
        String str = String.valueOf(out.get(0));
        str += ":";
        str += String.valueOf(out.get(1));
        return str;
    }

    public int getOutMin() {
        return getTimeInMinutes(out);
    }

    public Vector<Integer> getOutVector() {
        Vector<Integer> res = new Vector<>();
        if (out.get(0) < in.get(0))
            res.add(out.get(0) + 24);
        else
            res.add(out.get(0));
        res.add(out.get(1));
        return res;
    }

    public void setRoomIn(String roomIn) {
        this.roomIn = roomIn;
    }

    public String getRoomIn() {
        return roomIn;
    }

    public void setRoomOut(String roomOut) {
        this.roomOut = roomOut;
    }

    public String getRoomOut() {
        return roomOut;
    }

    public void setTimeWork(String timeWork) {
        try {
            String[] twoPartTimeWork = splitTime(timeWork);
            this.timeWork.add(Integer.parseInt(twoPartTimeWork[0]));
            this.timeWork.add(Integer.parseInt(twoPartTimeWork[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTimeWork() {
        String str = String.valueOf(timeWork.get(0));
        str += ":";
        str += String.valueOf(timeWork.get(1));
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TableRow objTableRow = (TableRow) obj;
        if (dateWork != objTableRow.getDateWork() || !in.equals(objTableRow.getInVector()) ||
                !out.equals(objTableRow.getOutVector()) || getTimeWorkMin() != objTableRow.getTimeWorkMin() ||
                !roomIn.equals(objTableRow.getRoomIn()) || !roomOut.equals(objTableRow.getRoomOut()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        return "TableRow[dateWork=" + format.format(dateWork) + ", in=" + getIn() + ", out=" + getOut() +
                ", roomIn=" + roomIn + ", roomOut=" + roomOut + ", timeWork=" + getTimeWork() + "]";
    }
}
