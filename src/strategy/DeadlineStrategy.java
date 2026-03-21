package strategy;

import model.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DeadlineStrategy implements SchedulingStrategy {

    @Override
    public int calculatePriority(Task task) {
        if (task.getDeadline() == null) {
            return 1;
        }

        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());

        if (daysLeft < 0) {
            return 0;
        }

        int priority = (int) Math.max(1, 10 - daysLeft);
        return priority;
    }
}