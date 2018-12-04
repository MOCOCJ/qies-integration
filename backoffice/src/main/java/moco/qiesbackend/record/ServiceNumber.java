package moco.qiesbackend.record;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import moco.qiesfrontend.session.SessionManager;

/**
 * ServiceNumber
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ServiceNumber extends RecordElement {
    // Vars
    private String number;
    private final static String DEFAULT = "00000";
    private final static String NUMBERS = "0123456789";

    /*
     * This constructor is for the Agent/Planner to validate the String with the
     * valid-service-list.txt public ServiceNumber(String number, SessionManager
     * manager) { if (isContained(number, manager)) { this.number = number;
     * this.isSet = true; } else { throw new IllegalArgumentException(); } }
     */

    // This constructor is to check tempService in the isContained method as well as
    // ValidServiceList constructor set up
    public ServiceNumber(String number, boolean defaultStr) {
        if (defaultStr) {
                this.number = number;
                this.isSet = true;
        } else {
            if (isValid(number)) {
                this.number = number;
                this.isSet = true;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /*
     * Checks is the ServiceName is contained in the valid_services_list.txt
     * public static boolean isContained(String value, SessionManager manager) { if
     * (isValid(value)) { if (manager.getServicesList().isInList(value)) { return
     * true; } } return false; }
     */

    // Checks if value fits the rules for ServiceNumbers
    public static boolean isValid(String value) {
        if (value.length() == 5) {
            if (value.charAt(0) != '0') {
                for (int i = 0; i < 5; i++) {
                    if (!NUMBERS.contains("" + value.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (isSet) {
            return number;
        }
        return DEFAULT;
    }
}