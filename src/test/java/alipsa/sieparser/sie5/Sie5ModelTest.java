package alipsa.sieparser.sie5;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

class Sie5ModelTest {

    @Test
    void accountTypeValueMapping() {
        assertEquals(AccountTypeValue.ASSET, AccountTypeValue.valueOf("ASSET"));
        assertEquals(AccountTypeValue.LIABILITY, AccountTypeValue.valueOf("LIABILITY"));
        assertEquals(AccountTypeValue.EQUITY, AccountTypeValue.valueOf("EQUITY"));
        assertEquals(AccountTypeValue.COST, AccountTypeValue.valueOf("COST"));
        assertEquals(AccountTypeValue.INCOME, AccountTypeValue.valueOf("INCOME"));
        assertEquals(AccountTypeValue.STATISTICS, AccountTypeValue.valueOf("STATISTICS"));
        assertEquals(6, AccountTypeValue.values().length);
    }

    @Test
    void yearMonthAdapter() throws Exception {
        YearMonthAdapter adapter = new YearMonthAdapter();
        YearMonth ym = adapter.unmarshal("2024-06");
        assertEquals(YearMonth.of(2024, 6), ym);
        assertEquals("2024-06", adapter.marshal(ym));
        assertNull(adapter.unmarshal(null));
        assertNull(adapter.marshal(null));
    }

    @Test
    void localDateAdapter() throws Exception {
        LocalDateAdapter adapter = new LocalDateAdapter();
        LocalDate ld = adapter.unmarshal("2024-01-15");
        assertEquals(LocalDate.of(2024, 1, 15), ld);
        assertEquals("2024-01-15", adapter.marshal(ld));
        assertNull(adapter.unmarshal(null));
        assertNull(adapter.marshal(null));
    }

    @Test
    void baseBalanceProperties() {
        BaseBalance bb = new BaseBalance();
        bb.setMonth(YearMonth.of(2024, 1));
        bb.setAmount(new BigDecimal("1234.56"));
        bb.setQuantity(new BigDecimal("10"));

        assertEquals(YearMonth.of(2024, 1), bb.getMonth());
        assertEquals(new BigDecimal("1234.56"), bb.getAmount());
        assertEquals(new BigDecimal("10"), bb.getQuantity());
    }

    @Test
    void accountWithBalancesFilter() {
        Account acc = new Account();
        acc.setId("1234");
        acc.setName("Test");
        acc.setType(AccountTypeValue.ASSET);

        AccountBalance.Opening ob = new AccountBalance.Opening();
        ob.setMonth(YearMonth.of(2024, 1));
        ob.setAmount(new BigDecimal("100"));

        AccountBalance.Closing cb = new AccountBalance.Closing();
        cb.setMonth(YearMonth.of(2024, 12));
        cb.setAmount(new BigDecimal("200"));

        Budget b = new Budget();
        b.setAmount(new BigDecimal("150"));

        acc.getBalancesAndBudgets().add(ob);
        acc.getBalancesAndBudgets().add(cb);
        acc.getBalancesAndBudgets().add(b);

        assertEquals(1, acc.getOpeningBalances().size());
        assertEquals(1, acc.getClosingBalances().size());
        assertEquals(1, acc.getBudgets().size());
        assertEquals(new BigDecimal("100"), acc.getOpeningBalances().get(0).getAmount());
        assertEquals(new BigDecimal("200"), acc.getClosingBalances().get(0).getAmount());
    }
}
