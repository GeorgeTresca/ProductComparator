package config;

import com.accesa.config.AppDateProvider;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AppDateProviderTest {

    @Test
    void shouldSetAndGetTodayCorrectly() {
        AppDateProvider provider = new AppDateProvider();
        LocalDate testDate = LocalDate.of(2025, 5, 8);

        provider.setToday(testDate);
        assertEquals(testDate, provider.getToday());
    }
}

