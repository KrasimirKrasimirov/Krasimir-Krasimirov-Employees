package Models;

import java.util.Date;

public class TimeWorked {
    /**
     * Exception text constants
     */
    private static final String UNSUPPORTED_DATE_EXCEPTION_TEXT = ("Unsupported date format or invalid date, record skipped.");
    private static final String DATES_VALIDATION_EXCEPTION_TEXT = ("Starting date cannot be later than end date, record skipped.");
    private Date DateFrom;
    private Date DateTo;

    public TimeWorked(Date dateFrom, Date dateTo) {
        if (dateFrom == null) {
            throw new IllegalArgumentException(UNSUPPORTED_DATE_EXCEPTION_TEXT);
        }
        if (dateTo != null && dateTo.getTime() < dateFrom.getTime()) {
            throw new IllegalArgumentException(DATES_VALIDATION_EXCEPTION_TEXT);
        }

        this.DateFrom = dateFrom;
        this.DateTo = dateTo;
    }

    public Date getDateFrom() {
        return this.DateFrom;
    }

    public Date getDateTo() {
        if (this.DateTo == null)
            return new Date();

        return this.DateTo;
    }
}
