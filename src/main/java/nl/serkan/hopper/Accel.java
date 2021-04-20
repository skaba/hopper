package nl.serkan.hopper;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Value
@RequiredArgsConstructor(staticName = "accel")
public class Accel implements Function<Speed, Speed> {

    private static List<Integer> OPTIONS = List.of(-1, 0, 1);

    private int x;
    private int y;

    @Override
    public Speed apply(Speed speed) {
        return Speed.of(speed.getX() + x, speed.getY() + y);
    }

    public static Stream<Accel> accelerations() {
        return OPTIONS
                .stream()
                .flatMap(x -> OPTIONS
                        .stream()
                        .flatMap(y -> Stream.of(accel(x, y))));
    }

}
