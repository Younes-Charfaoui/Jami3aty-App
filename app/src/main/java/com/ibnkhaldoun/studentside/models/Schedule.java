package com.ibnkhaldoun.studentside.models;


import com.ibnkhaldoun.studentside.enums.Day;
import com.ibnkhaldoun.studentside.enums.Levels;

import java.util.List;

public class Schedule {
    private Day dayOfSchedule;
    private List<ScheduleItem> scheduleItemList;
    private Levels promo;

    public Schedule(Day dayOfSchedule, List<ScheduleItem> scheduleItemList, Levels promo) {
        this.dayOfSchedule = dayOfSchedule;
        this.scheduleItemList = scheduleItemList;
        this.promo = promo;
    }

    public Day getDayOfSchedule() {
        return dayOfSchedule;
    }

    public List<ScheduleItem> getScheduleItemList() {
        return scheduleItemList;
    }

    public Levels getPromo() {
        return promo;
    }
}
