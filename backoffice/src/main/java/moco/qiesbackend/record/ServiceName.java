package moco.qiesbackend.record;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ServiceName
 */
@Getter
@Setter
@NoArgsConstructor
public class ServiceName extends RecordElement {
    // Vars
    private String name;
    private final static String DEFAULT = "****";
    // ALPHA is the list of allowed characters for the service name
    private final static String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\' ";

    public ServiceName(String name) {
        if (isValid(name)) {
            this.name = name;
            isSet = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isValid(String value) {
        if (value.length() >= 3 && value.length() <= 39) {
            if (value.charAt(0) != ' ' && value.charAt(value.length() - 1) != ' ') {
                for (int i = 0; i < value.length(); i++) {
                    if (!ALPHA.contains("" + value.charAt(i))) {
                        if (DEFAULT.equals(value)) {
                            return true;
                        }
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
            return name;
        }
        return DEFAULT;
    }

}