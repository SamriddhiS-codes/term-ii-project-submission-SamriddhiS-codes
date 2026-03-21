package manager;

import model.Task;
import strategy.SchedulingStrategy;
import observer.TaskObserver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SchedulerManager {

    private List<TaskObserver> observers = new ArrayList<>();
    private PriorityQueue<Task> tasks;
    private SchedulingStrategy strategy;

    public SchedulerManager(SchedulingStrategy strategy) {
        this.strategy = strategy;
        this.tasks = new PriorityQueue<>(Comparator.comparingInt(strategy::calculatePriority).reversed());
    }

    public void addObserver(TaskObserver observer) { observers.add(observer); }
    public void removeObserver(TaskObserver observer) { observers.remove(observer); }

    public void addTask(Task task) {
        tasks.add(task);
        observers.forEach(o -> o.onTaskAdded(task));
    }

    public void updateTask(Task task) {
        tasks.remove(task);   // Remove and reinsert to update priority
        tasks.add(task);
        observers.forEach(o -> o.onTaskUpdated(task));
    }

    public void completeTask(Task task) {
        if (tasks.remove(task)) {
            task.setStatus(Task.Status.COMPLETED);  // Update status
            observers.forEach(o -> o.onTaskCompleted(task));
        }
    }

    public Task getNextTask() {
        return tasks.peek();
    }

    public void switchStrategy(SchedulingStrategy newStrategy) {
        this.strategy = newStrategy;
        List<Task> temp = new ArrayList<>(tasks);
        tasks.clear();
        tasks.addAll(temp);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
