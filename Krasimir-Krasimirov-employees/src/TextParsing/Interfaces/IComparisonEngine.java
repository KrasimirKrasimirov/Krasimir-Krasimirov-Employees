package TextParsing.Interfaces;

import Models.ComparisonResultInfo;

public interface IComparisonEngine {
    ComparisonResultInfo run(String filePath);
}
