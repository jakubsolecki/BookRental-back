package pl.jsol.bookrental.unit;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jsol.bookrental.model.Copy;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.LoanHist;
import pl.jsol.bookrental.model.Member;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class LoanHistTests {
    private Loan loan;

    @BeforeEach
    protected void setUp() {
        loan = Mockito.mock(Loan.class);
        when(loan.getMember()).thenReturn(Mockito.mock(Member.class));
        when(loan.getCopy()).thenReturn(Mockito.mock(Copy.class));
    }

    @Test
    public void newLoanHist_MetDeadline_NoPenalty() {
        when(loan.getDueDate()).thenReturn(LocalDate.now().plusDays(3));
        LoanHist loanHist = new LoanHist(loan);

        assertEquals(new BigDecimal(0), loanHist.getPenalty());
    }

    @Test
    public void newLoanHist_ExceededDeadline_GivenPenalty() {
        when(loan.getDueDate()).thenReturn(LocalDate.now().minusDays(3));
        LoanHist loanHist = new LoanHist(loan);

        assertEquals(new BigDecimal(3), loanHist.getPenalty());
    }
}
