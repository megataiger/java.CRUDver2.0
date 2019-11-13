package workWithBase.connectWithBase;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryForDAO {
    static final protected EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("com.jvc_sgu.intern");

    public void close() {
        factory.close();
    }
}
