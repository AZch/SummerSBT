package summersbt.whereemp.BroadcastData;

import java.util.Vector;

public class DataResult {
    private double probably = 0; // вероятность в процентах
    private String name; // имя
    private long num; // табельный номер
    private Vector<String> allData = new Vector<>();; // все данные
    private String primeTime; // лучшее время чтобы поймать коллегу

    // сеттеры и геттеры

    public void setProbably(double probably) {
        this.probably = probably;
    }

    public double getProbably() {
        return probably;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public long getNum() {
        return num;
    }

    public String getPrimeTime() {
        return primeTime;
    }

    /*
      строка с полученным временным промежутком
     */
    public void setPrimeTime(int minIn, int minOut) {
        this.primeTime = String.valueOf(minIn / 60) + ":" + String.valueOf(minIn % 60) + " - " + String.valueOf(minOut / 60) + ":" + String.valueOf(minOut % 60);
    }

    /*
      строка с полученным временным промежутком
     */
    public void setPrimeTime(String time) {
        this.primeTime = time;
    }

    public void setAllData(Vector<String> allData) {
        this.allData = allData;
    }

    public void addAllData(String data) {
        allData.add(data);
    }

    public Vector<String> getAllData() {
        return allData;
    }
}
