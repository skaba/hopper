package nl.serkan.hopper;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.function.Function;

import static nl.serkan.hopper.Coordinate.coordinate;

@Value
@RequiredArgsConstructor(staticName = "of")
public class Speed implements Function<Coordinate, Coordinate> {

    private int x;
    private int y;

    public static final Speed ZERO = of(0, 0);

    @Override
    public Coordinate apply(Coordinate coordinate) {
        return coordinate(coordinate.getX() + x, coordinate.getY() + y);
    }

    public boolean isValid() {
        return x < 4 && y < 4;
    }

}
