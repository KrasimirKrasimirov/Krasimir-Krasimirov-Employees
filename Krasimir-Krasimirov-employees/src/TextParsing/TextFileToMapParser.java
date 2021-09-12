package TextParsing;

import Models.Employee;
import TextParsing.Interfaces.IProjectsMapParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFileToMapParser implements IProjectsMapParser {
    private static final String EMPLOYEE_VALIDATION_EXCEPTION_TEXT = ("Invalid employee ID or project ID parameters, record skipped.");
    private static final String UNSUPPORTED_DATE_EXCEPTION_TEXT = ("Unsupported date format or invalid date, record skipped. -> ");
    private static final String DAY_MONTH_YEAR_PATTERN_TEXT = ("\\b(?<day>\\d{2})([-\\/\\.])(?<month>\\w+)(\\2)(?<year>\\d{2,4})\\b");
    private static final String YEAR_MONTH_DAY_PATTERN_TEXT = ("\\b(?<year>\\d{2,4})([-\\/\\.])(?<month>\\w+)(\\2)(?<day>\\d{2})\\b");
    private static final String EMPTY_DATE_TEXT = ("null");
    private static final String DATE_DAY_TEXT = ("day");
    private static final String DATE_MONTH_TEXT = ("month");
    private static final String DATE_YEAR_TEXT = ("year");
    private static final String SIMPLE_DATE_FORMAT = ("dd.MM.yyyy");

    private List<Integer> employeeIDs = new ArrayList<>();
    private Employee employee;
    private int empId;
    private int projectId;
    private Date dateFrom;
    private Date dateTo;

    public HashMap<Integer, ArrayList<Employee>> getProjectMapFromFile(String filePath) {
        ArrayList<String> fileLines = getLines(filePath);
        HashMap<Integer, ArrayList<Employee>> projectTable = new HashMap<>();
        for (int i = 0; i < fileLines.size(); i++) {
            String[] parts = fileLines.get(i).split(", ");

            try {
                empId = Integer.parseInt(parts[0]);
                projectId = Integer.parseInt(parts[1]);
                dateFrom = parseDate(parts[2]);
                dateTo = parseDate(parts[3]);
                employee = new Employee(empId, dateFrom, dateTo);
            } catch (NumberFormatException e) {
                System.out.println(EMPLOYEE_VALIDATION_EXCEPTION_TEXT);
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (projectTable.containsKey(projectId)) {
                projectTable.get(projectId).add(employee);
            } else {
                ArrayList<Employee> employees = new ArrayList<>();
                employees.add(employee);
                projectTable.put(projectId, employees);
            }

            if (!employeeIDs.contains(empId))
                employeeIDs.add(empId);
        }
        return projectTable;
    }

    private Date parseDate(String date) {
        if (date.toLowerCase().equals(EMPTY_DATE_TEXT)) return null;

        Pattern dayMonthYearPattern = Pattern.compile(DAY_MONTH_YEAR_PATTERN_TEXT);
        Pattern yearMonthDayPattern = Pattern.compile(YEAR_MONTH_DAY_PATTERN_TEXT);
        Matcher matcher;
        matcher = dayMonthYearPattern.matcher(date);

        if (!matcher.matches()) {
            matcher = yearMonthDayPattern.matcher(date);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(UNSUPPORTED_DATE_EXCEPTION_TEXT + date);
            }
        }

        int day = Integer.parseInt(matcher.group(DATE_DAY_TEXT));
        int month = Integer.parseInt(matcher.group(DATE_MONTH_TEXT));
        int year = Integer.parseInt(matcher.group(DATE_YEAR_TEXT));

        Date parsedDate = new GregorianCalendar(year, month - 1, day).getTime();
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

        if (parsedDate.after(currentDate)) {
            throw new IllegalArgumentException(UNSUPPORTED_DATE_EXCEPTION_TEXT + df.format(parsedDate));
        }

        return parsedDate;
    }

    private ArrayList<String> getLines(String filePath) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while ((strLine = reader.readLine()) != null) {
                lines.add(strLine);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}