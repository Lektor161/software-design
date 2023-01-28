package stat;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class EventStatisticTest {
    @Test
    public void test1() {
        Clock clock = mock(Clock.class);
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        LocalDateTime now = LocalDateTime.now();

        when(clock.now())
                .thenReturn(now)
                .thenReturn(now.plusMinutes(55))
                .thenReturn(now.plusMinutes(55))
                .thenReturn(now.plusHours(2));

        eventsStatistic.incEvent("event");
        assertThat(eventsStatistic.getEventStatisticByName("event"), is(1.0 / 60));
        assertThat(eventsStatistic.getAllEventStatistic(), is(Map.of("event", 1.0 / 60)));
        assertThat(eventsStatistic.getEventStatisticByName("event"), is(0.0));
    }
}
