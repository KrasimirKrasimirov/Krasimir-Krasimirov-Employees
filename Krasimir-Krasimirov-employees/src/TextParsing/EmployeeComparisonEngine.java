package TextParsing;

import Models.ComparisonResultInfo;
import Models.Employee;
import Models.TimeWorked;
import TextParsing.Interfaces.IComparisonEngine;
import TextParsing.Interfaces.IProjectsMapParser;

import java.util.*;

public class EmployeeComparisonEngine implements IComparisonEngine {
    private static final int DATE_TO_DAYS_CONVERTER = 24 * 60 * 60 * 1000;

    protected ComparisonResultInfo comparisonResultInfo;

    public EmployeeComparisonEngine() {
        comparisonResultInfo = new ComparisonResultInfo();
    }

    public ComparisonResultInfo run(String filePath) {
        IProjectsMapParser parser = new TextFileToMapParser();
        HashMap<Integer, ArrayList<Employee>> projectsHashMap = parser.getProjectMapFromFile(filePath);
        for (Map.Entry<Integer, ArrayList<Employee>> project : projectsHashMap.entrySet()) {
            ArrayList<Employee> employeesInProject = project.getValue();

            calcAndFillEmpHours(project.getKey(), employeesInProject);
        }

        return comparisonResultInfo;
    }

    private void calcAndFillEmpHours(int projectID, ArrayList<Employee> employeesInProject) {
        for (int i = 0; i < employeesInProject.size(); i++) {
            Employee emp1 = employeesInProject.get(i);
            for (int j = i + 1; j < employeesInProject.size(); j++) {
                Employee emp2 = employeesInProject.get(j);

                int timeWorkedTogether = getOverlapTimeFromDates(emp1.getTimeWorked(), emp2.getTimeWorked());
                updateResultInfo(emp1, emp2, projectID, timeWorkedTogether);
            }
        }
    }

    private void updateResultInfo(Employee emp1, Employee emp2, int projectID, int timeWorkedTogether) {
        if (comparisonResultInfo.daysWorked < timeWorkedTogether) {
            comparisonResultInfo.updateResultInfo(emp1.getEmpID(), emp2.getEmpID(), projectID, timeWorkedTogether);
        }
    }

    private int getOverlapTimeFromDates(TimeWorked firstEmpTimeWorked, TimeWorked secondEmpTimeWorked) {
        Date startA = firstEmpTimeWorked.getDateFrom();
        Date startB = secondEmpTimeWorked.getDateFrom();
        Date endA = firstEmpTimeWorked.getDateTo();
        Date endB = secondEmpTimeWorked.getDateTo();

        boolean checkOverlappingDates = Math.max(startA.getTime(), startB.getTime()) < Math.min(endA.getTime(), endB.getTime());
        if (!checkOverlappingDates)
            return 0;

        // get overlapping time
        Long overlap = Math.min(endA.getTime(), endB.getTime()) - Math.max(startA.getTime(), startB.getTime());
        // get overlapping days
        overlap /= DATE_TO_DAYS_CONVERTER;

        return overlap.intValue();
    }
}