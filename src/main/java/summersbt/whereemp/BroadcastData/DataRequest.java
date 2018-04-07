package summersbt.whereemp.BroadcastData;


import summersbt.whereemp.Constants.Requests;

public class DataRequest {
    private long num; // табельный номер
    private String name; // имя сотрудника
    private int typeRequest = -1; // тип запроса
    private int hourTimeIn; // время входа часы
    private int minTimeIn; // время входа минуты
    private int hourTimeOut; // время выхода часы
    private int minTimeOut; // время выхода минуты
    private int timeInterval; // временной интервал

    public DataRequest() {};

    // сеттеры и геттеры

    public void setNum(long num) {
        this.num = num;
    }

    public long getNum() {
        return num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String request) {
        switch (request) {
            case Requests.DAY_OF_WEEK:
                this.typeRequest = Requests.DAY_OF_WEEK_REQ;
                break;
            case Requests.ONE_TIME:
                this.typeRequest = Requests.ONE_TIME_REQ;
                break;
            case Requests.TWO_TIME:
                this.typeRequest = Requests.TWO_TIME_REQ;
                break;
        }
    }

    public void setHourTimeIn(int hourTimeIn) {
        this.hourTimeIn = hourTimeIn;
    }

    public int getHourTimeIn() {
        return hourTimeIn;
    }

    public void setMinTimeIn(int minTimeIn) {
        this.minTimeIn = minTimeIn;
    }

    public int getMinTimeIn() {
        return minTimeIn;
    }

    public void setHourTimeOut(int hourTimeOut) {
        this.hourTimeOut = hourTimeOut;
    }

    public int getHourTimeOut() {
        return hourTimeOut;
    }

    public void setMinTimeOut(int minTimeOut) {
        this.minTimeOut = minTimeOut;
    }

    public int getMinTimeOut() {
        return minTimeOut;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }
}
