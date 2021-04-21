package models;

public class MyCalendar {

    //カレンダーの年
    private int year;
    //カレンダーの月
    private int month;
    //カレンダーの日付を保持する配列
    private String[][] data;

    /*setter & getter*/

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public String[][] getData() {
        return data;
    }
    public void setData(String[][] data) {
        this.data = data;
    }
}
