package stat;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EventsStatisticImpl implements EventsStatistic {
    private final Clock clock;
    private final Map<String, List<LocalDateTime>> events = new HashMap<>();

    @Override
    public void incEvent(String name) {
        events.putIfAbsent(name, new ArrayList<>());
        events.get(name).add(clock.now());
    }

    @Override
    public double getEventStatisticByName(String name) {
        var now = clock.now();
        return events.getOrDefault(name, new ArrayList<>()).stream()
                .filter(it -> it.isAfter(now.minusHours(1)))
                .toList().size() / 60.0;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        return events.keySet().stream()
                .collect(Collectors.toMap(it -> it, this::getEventStatisticByName));
    }

    @Override
    public void printStatistic() {
        getAllEventStatistic().forEach((key, value) -> System.out.printf("Event %s has rpm of %s%n", key, value));
    }
}
