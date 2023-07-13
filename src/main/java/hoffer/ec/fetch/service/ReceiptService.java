package hoffer.ec.fetch.service;

import hoffer.ec.fetch.persistence.IReceiptDAO;
import hoffer.ec.fetch.model.Receipt;
import hoffer.ec.fetch.model.exception.ReceiptNotFoundException;
import hoffer.ec.fetch.model.exception.ReceiptProcessingException;
import hoffer.ec.fetch.service.helper.IdGenerator;
import hoffer.ec.fetch.service.helper.PointCalculator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Holds the business logic for api processing.
 */
@Service
@RequiredArgsConstructor
public class ReceiptService {

    @Autowired
    final private IReceiptDAO receiptDAO;

    @Autowired
    final private IdGenerator idGenerator;

    @Autowired
    final private PointCalculator pointCalculator;

    /**
     * Given an ID from a previous receipt upload, queries our db for the points value and returns it.
     *
     * @param id | the id of the receipt
     * @return the point value of that receipt
     */
    public double getPoints(
            @NonNull final String id
    ) {
        double points;
        try {
            points = receiptDAO.get(id);
        } catch (NullPointerException npe) {
            throw new ReceiptNotFoundException("Unable to find receipt with Id " + id);
        }
        return points;
    }

    /**
     * Given a receipt upload from the client, tallies up the point value, assigns it a unique ID, and stores it in
     * the db.
     *
     * @param receipt | the receipt uploaded by the client
     * @return the id which the receipt's point value is stored under
     */
    public String process(
            @NonNull final Receipt receipt
    ) {
        final String id = idGenerator.generateId();
        try {
            final double points = pointCalculator.tallyPoints(receipt);
            receiptDAO.put(id, points);
        } catch (Exception e) {
            throw new ReceiptProcessingException("Unable to process receipt", e);
        }
        return id;
    }
}
