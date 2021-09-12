package Models;

public class ComparisonResultInfo {
    public int empID1;
    public int empID2;
    public int projectID;
    public int daysWorked;

    public ComparisonResultInfo() {
        empID1 = 0;
        empID2 = 0;
        projectID = 0;
        daysWorked = 0;
    }

    public void updateResultInfo(int empID1, int empID2, Integer projectID, int timeWorkedTogether) {
        this.empID1 = empID1;
        this.empID2 = empID2;
        this.projectID = projectID;
        this.daysWorked = timeWorkedTogether;
    }
}
