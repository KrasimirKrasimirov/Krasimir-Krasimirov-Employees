package TextParsing.Interfaces;

import Models.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public interface IProjectsMapParser {
    HashMap<Integer, ArrayList<Employee>> getProjectMapFromFile(String filePath);
}
