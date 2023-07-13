package hoffer.ec.fetch.persistence;

import org.springframework.stereotype.Repository;

public interface IReceiptDAO {
    public double get(String id);
    public void put(String id, double points);
}
