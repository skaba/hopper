package nl.serkan.hopper;

import org.junit.jupiter.api.Test;

import static nl.serkan.hopper.Coordinate.coordinate;
import static nl.serkan.hopper.Node.node;
import static org.assertj.core.api.Assertions.assertThat;

public class GridTest {
    @Test
    void testShortestPathInUnweightedGrid1() {
        Grid grid = constructGrid1();
        assertThat(grid.search(node(coordinate(4, 0), Speed.ZERO, 8)))
                .isPresent();

    }

    private static Grid constructGrid1() {
        int grid[][] = new int[5][5];
        grid[4][0] = 8;
        grid[4][4] = 9;

        for (int x = 1; x <= 4; x++) {
            for (int y = 2; y <= 3; y++) {
                // obstacle;
                grid[x][y] = 1;
            }
        }

        return new Grid(grid);
    }

}
