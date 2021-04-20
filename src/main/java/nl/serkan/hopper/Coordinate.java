package nl.serkan.hopper;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "coordinate")
public class Coordinate {
    int x;
    int y;
}
