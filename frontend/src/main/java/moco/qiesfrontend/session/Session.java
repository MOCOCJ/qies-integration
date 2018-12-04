package moco.qiesfrontend.session;

/**
 * Session
 */
public interface Session {

    public void process(SessionManager manager, TransactionQueue queue);
}