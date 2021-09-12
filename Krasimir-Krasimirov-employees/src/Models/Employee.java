package Models;

import java.util.Date;

public class Employee {
    private int EmpID;
    private TimeWorked TimeWorked;

    public Employee(int empId, Date dateFrom, Date dateTo) {
        this.TimeWorked = new TimeWorked(dateFrom, dateTo);
        this.EmpID = empId;
    }

    public int getEmpID() {
        return this.EmpID;
    }

    public TimeWorked getTimeWorked() {
        return this.TimeWorked;
    }
}