package model;

public class UrgentTask extends Task {

    public UrgentTask(String title) {
        super(title);
    }

    @Override
    public int calculatePriority() {
        return 10;
    }
}
