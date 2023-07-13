package hoffer.ec.fetch.service;

import hoffer.ec.fetch.persistence.IReceiptDAO;
import hoffer.ec.fetch.model.Receipt;
import hoffer.ec.fetch.model.exception.ReceiptNotFoundException;
import hoffer.ec.fetch.model.exception.ReceiptProcessingException;
import hoffer.ec.fetch.service.helper.IdGenerator;
import hoffer.ec.fetch.service.helper.PointCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTests {

    private static final String ID = UUID.randomUUID().toString();
    private static final Receipt RECEIPT = Receipt.builder().build();

    @Mock
    private IReceiptDAO receiptDAO;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private PointCalculator pointCalculator;

    @InjectMocks
    private ReceiptService receiptService;

    @AfterEach
    public void close() {
        verifyNoMoreInteractions(idGenerator, receiptDAO, pointCalculator);
    }

    // PROCESS RECEIPT TESTS

    @Test
    public void when_processReceipt_then_returnsId() {
        final double expectedPoints = 1.337;
        when(idGenerator.generateId()).thenReturn(ID);
        when(pointCalculator.tallyPoints(RECEIPT)).thenReturn(expectedPoints);

        final String id = receiptService.process(RECEIPT);

        assertEquals(id, ID);

        verify(idGenerator, times(1)).generateId();
        verify(pointCalculator, times(1)).tallyPoints(RECEIPT);
        verify(receiptDAO, times(1)).put(ID, expectedPoints);
    }

    @Test
    public void when_processReceiptThrowsException_then_wrapWithProcessingException() {
        when(pointCalculator.tallyPoints(RECEIPT)).thenThrow(new NullPointerException("Oops"));

        assertThrows(ReceiptProcessingException.class, () -> receiptService.process(RECEIPT));

        verify(idGenerator, times(1)).generateId();
        verify(pointCalculator, times(1)).tallyPoints(RECEIPT);
    }

    // GET POINTS TESTS

    @Test
    public void when_getPointsHasGoodID_then_returnsPointValue() {
        final double expectedPoints = 867.5309;
        when(receiptDAO.get(ID)).thenReturn(expectedPoints);

        final double points = receiptService.getPoints(ID);

        assertEquals(expectedPoints, points);
        verify(receiptDAO, times(1)).get(ID);
    }

    @Test
    public void when_getPointsDaoCallThrowsException_then_wrapWithIDException() {
        when(receiptDAO.get(ID)).thenThrow(new NullPointerException("Oops"));

        assertThrows(ReceiptNotFoundException.class, () -> receiptService.getPoints(ID));

        verify(receiptDAO, times(1)).get(ID);
    }
}
