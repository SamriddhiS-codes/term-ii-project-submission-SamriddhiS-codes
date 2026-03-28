import manager.SchedulerManager;
import model.PersonalTask;
import model.UrgentTask;
import model.WorkTask;
import observer.ConsoleObserver;
import strategy.DeadlineStrategy;
import strategy.FixedPriorityStrategy;
import model.Task;
import persistence.TaskPersistence;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SchedulerManager manager = new SchedulerManager(new FixedPriorityStrategy());
        manager.addObserver(new ConsoleObserver());
        TaskPersistence.loadTasks().forEach(manager::addTask);

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Smart Task Scheduler ---");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Complete Task");
            System.out.println("4. Switch Scheduling Strategy");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Task type (Work/Personal/Urgent): ");
                    String type = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    if (title.trim().isEmpty()) {
                        System.out.println("Title cannot be empty!");
                        break;
                    }

                    Task task = null;

                    if (type.equalsIgnoreCase("Work")) {
                        System.out.print("Project name: ");
                        String project = sc.nextLine();
                        task = new WorkTask(title, project);

                    } else if (type.equalsIgnoreCase("Personal")) {
                        System.out.print("Category: ");
                        String category = sc.nextLine();
                        task = new PersonalTask(title, category);

                        System.out.print("Deadline (YYYY-MM-DD, optional, press Enter to skip): ");
                        String date = sc.nextLine();

                        if (!date.isEmpty()) {
                            try {
                                task.setDeadline(LocalDate.parse(date));
                            } catch (Exception e) {
                                System.out.println("Invalid date format! Deadline not set.");
                            }
                        }

                    } else if (type.equalsIgnoreCase("Urgent")) {
                        task = new UrgentTask(title);
                    }

                    if (task != null) {
                        manager.addTask(task);
                    } else {
                        System.out.println("Invalid task type!");
                    }
                    break;

                case 2:
                    System.out.println("\nAll Tasks:");
                    for (Task t : manager.getAllTasks()) {
                        System.out.println(t.getTitle() + " | Status: " + t.getStatus() +
                                " | Deadline: " + t.getDeadline() +
                                " | Priority: " + t.calculatePriority());
                    }
                    break;

                case 3:
                    System.out.print("Enter task title to complete: ");
                    String completeTitle = sc.nextLine();

                    Task toComplete = manager.getAllTasks().stream()
                            .filter(t -> t.getTitle().equalsIgnoreCase(completeTitle))
                            .findFirst()
                            .orElse(null);

                    if (toComplete != null) {
                        manager.completeTask(toComplete);
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 4:
                    System.out.print("Choose strategy (Fixed/Deadline): ");
                    String strat = sc.nextLine();

                    if (strat.equalsIgnoreCase("Fixed")) {
                        manager.switchStrategy(new FixedPriorityStrategy());
                    } else if (strat.equalsIgnoreCase("Deadline")) {
                        manager.switchStrategy(new DeadlineStrategy());
                    } else {
                        System.out.println("Invalid strategy!");
                        break;
                    }

                    System.out.println("Strategy switched to " + strat);
                    break;

                case 5:
                    TaskPersistence.saveTasks(manager.getAllTasks());
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}