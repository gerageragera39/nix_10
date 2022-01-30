package ua.com.alevel.persistence.listener;

import org.apache.commons.lang3.StringUtils;
import ua.com.alevel.persistence.entity.users.Personal;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.joda.time.LocalDate;

public class AgeByBirthDayGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateAge(Personal personal) {
        if (StringUtils.isBlank(personal.getBirthDay())) {
            personal.setAge(0);
            return;
        }

        LocalDate nowDate = LocalDate.now();
        String birthDay = personal.getBirthDay();
        String[] dates = birthDay.split("-");
        int[] datesInteger = {Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])};

        int age = nowDate.getYear() - datesInteger[0];
        if (nowDate.getMonthOfYear() < datesInteger[1]) {
            age--;
        } else if (nowDate.getMonthOfYear() == datesInteger[1]) {
            if (nowDate.getDayOfMonth() < datesInteger[2]) {
                age--;
            }
        }
        personal.setAge(age);
    }
}
