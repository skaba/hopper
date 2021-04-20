package nl.serkan.hopper;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Value
@EqualsAndHashCode(exclude = { "value", "speed" })
@RequiredArgsConstructor(staticName = "node")
public class Node {
    private final Coordinate coordinate;
    private final Speed speed;
    private final int value;

}
