package moco.qiesbackend;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import moco.qiesbackend.record.ServiceNumber;

/**
 * CentralServicesList
 */
@Getter
public class CentralServicesList {

    Map<ServiceNumber, Service> services;

    public CentralServicesList() {
        services = new HashMap<>();
    }

    public void add(Service service) {
        services.put(service.getServiceNumber(), service);
    }

    public void delete(ServiceNumber number) {
        services.remove(number);
    }

    public Service get(ServiceNumber number) {
        return services.get(number);
    }

    public boolean contains(ServiceNumber number) {
        return services.keySet().contains(number);
    }

    private ArrayList<String> centralServicesFileContents() {
        ArrayList<String> lines = new ArrayList<>();

        services.forEach((serviceNumber, service) -> lines.add(service.toString()));
        Collections.sort(lines);

        return lines;
    }

    public void writeCentralServicesFile(Path centralServicesOutputPath) {
        try {
            Files.deleteIfExists(centralServicesOutputPath);
            Files.write(centralServicesOutputPath, centralServicesFileContents(), CREATE, WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> validServicesFileContents() {
        ArrayList<String> lines = new ArrayList<>();

        services.forEach((serviceNumber, service) -> lines.add(serviceNumber.toString()));
        Collections.sort(lines);
        lines.add("00000");

        return lines;
    }

    public void writeValidServicesFile(Path validServicesOutputPath) {
        try {
            Files.deleteIfExists(validServicesOutputPath);
            Files.write(validServicesOutputPath, validServicesFileContents(), CREATE, WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}