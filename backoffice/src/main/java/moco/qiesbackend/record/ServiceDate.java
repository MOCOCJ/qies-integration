package moco.qiesbackend.record;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ServiceDate
 */
@Getter
@Setter
@NoArgsConstructor
public class ServiceDate extends RecordElement {
    // Vars
    private String year;
    private String month;
    private String day;
    private final static String DEFAULT = "0";
    private final static String NUMBERS = "0123456789";

    public ServiceDate(String date, boolean defaultStr) {
        if (!defaultStr) {
            if (isValid(date)) {
                year = date.substring(0, 4);
                month = date.substring(4, 6);
                day = date.substring(6);
                isSet = true;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static boolean isValid(String value) {
        if (value.length() == 8) {
            for (int i = 0; i < value.length(); i++) {
                if (!NUMBERS.contains("" + value.charAt(i))) {
                    return false;
                }
            }

            int y = Integer.parseInt(value.substring(0, 4));
            int m = Integer.parseInt(value.substring(4, 6));
            int d = Integer.parseInt(value.substring(6));

            if (y >= 1980 && y <= 2999) {
                if (m >= 1 && m <= 12) {
                    if (d >= 1 && d <= 31) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public String toString() {
        if (isSet) {
            return year + month + day;
        }
        return DEFAULT;
    }

}