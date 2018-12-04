package moco.qiesfrontend.session;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.Setter;
import moco.qiesfrontend.transaction.record.ServiceNumber;

/**
 * SessionManager
 */
@Getter
@Setter
public class SessionManager {

    private Session session;
    private ValidServicesList servicesList;
    private TransactionQueue transactionQueue;
    private File summaryFile;
    private Input input;

    public SessionManager(String validServicesFilePath, String summaryFilePath) {

        this.session = new NoSession();
        this.summaryFile = new File(summaryFilePath);
        this.transactionQueue = new TransactionQueue();
        File validServicesFile = new File(validServicesFilePath);
        this.servicesList = new ValidServicesList(validServicesFile);
        this.input = new Input(" ----- ");
    }

    public void operate() {
        session.process(this, transactionQueue);
    }

    public void setSession(Session session) {
        if (session instanceof AgentSession) {
            this.input.setPrompt(" AGENT ");
        }
        if (session instanceof PlannerSession) {
            this.input.setPrompt("PLANNER");
        }
        if (session instanceof NoSession) {
            this.input.setPrompt(" ----- ");
            printTransactionSummary();
        }

        this.session = session;
        operate();
    }

    public void printTransactionSummary() {
        Path summaryPath = summaryFile.toPath();
        byte[] recordBytes;
        try {
            while (!transactionQueue.isEmpty()) {
                recordBytes = transactionQueue.pop().toString().getBytes();

                Files.write(summaryPath, recordBytes, CREATE, WRITE, APPEND);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isInValidServices(ServiceNumber service) {
        if (servicesList.isInList(service.getNumber())) {
            return true;
        } else {
            return false;
        }
    }
}