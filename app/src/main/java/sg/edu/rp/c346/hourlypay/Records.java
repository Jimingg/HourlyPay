package sg.edu.rp.c346.hourlypay;

import java.util.Calendar;

public class Records {

    private String date;
    private String desc;
    private String start;
    private String end;
    private String hour;
    private String pay;
    private String breaks;


    public Records(String desc, String date, String start, String end,String breaks, String hour, String pay) {
        this.date = date;
        this.desc = desc;
        this.start = start;
        this.end = end;
        this.hour = hour;
        this.pay = pay;
        this.breaks = breaks;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getHour() {
        return hour;
    }

    public String getPay() {
        return pay;
    }

    public String getBreaks() {
        return breaks;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    @Override
    public String toString() {
        return "Records{" +
                "date='" + date + '\'' +
                ", desc='" + desc + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", hour='" + hour + '\'' +
                ", pay='" + pay + '\'' +
                ", breaks='" + breaks + '\'' +
                '}';
    }
}

