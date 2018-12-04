package moco.qiesfrontend.session;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ValidServicesList
 */
public class ValidServicesList {

    private Set<String> validServices;

    public ValidServicesList(File validServicesFile) {
        validServices = new HashSet<String>();
        readServices(validServicesFile);
    }

    public void readServices(File validServicesFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(validServicesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("00000"))
                    validServices.add(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isInList(String service) {
        if (validServices.contains(service)) {
            return true;
        }
        return false;
    }

    public void deleteService(String service) {
        validServices.remove(service);
    }
}