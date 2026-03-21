package strategy;

import model.Task;

public interface SchedulingStrategy {
    int calculatePriority(Task task);
}