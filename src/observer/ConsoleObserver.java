package observer;

import model.Task;

public class ConsoleObserver implements TaskObserver {

    @Override
    public void onTaskAdded(Task task) {
        System.out.println("Task added: " + task.getTitle());
    }

    @Override
    public void onTaskUpdated(Task task) {
        System.out.println("Task updated: " + task.getTitle());
    }

    @Override
    public void onTaskCompleted(Task task) {
        System.out.println("Task completed: " + task.getTitle());
    }
}
