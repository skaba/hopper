package nl.serkan.hopper;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import static nl.serkan.hopper.Coordinate.coordinate;
import static nl.serkan.hopper.Node.node;
import static nl.serkan.hopper.Speed.ZERO;

@RequiredArgsConstructor
public class Grid {
    private final int[][] nodes;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public int shortestPath() {
        // key node, value parent
        Map<Coordinate, Node> parents = new HashMap<>();
        Node start = null;
        Node end = null;

        // find the start node
        for (int row = 0; row < nodes.length; row++) {
            for (int column = 0; column < nodes[row].length; column++) {
                if (nodes[row][column] == 8) {
                    var startCoordinate = coordinate(row, column);
                    start = node(startCoordinate, ZERO, value(startCoordinate));
                    System.out.println("start " + start);
                    break;
                }
            }
            if (start != null) {
                break;
            }
        }

        if (start == null) {
            throw new RuntimeException("can't find start node");
        }

        // traverse every node using breadth first search until reaching the destination
        List<Node> temp = new ArrayList<Node>();
        temp.add(start);
        parents.put(start.getCoordinate(), null);

        boolean reachDestination = false;
        while (temp.size() > 0 && !reachDestination) {
            Node currentNode = temp.remove(0);
            List<Node> children = getChildren(currentNode);
            children.remove(start);
            for (Node child : children) {
                // Node can only be visted once
                if (!parents.containsKey(child.getCoordinate())) {
                    parents.put(child.getCoordinate(), currentNode);

                    int value = child.getValue();
                    if (value == 1) {
                        temp.add(child);
                    } else if (value == 9) {
                        temp.add(child);
                        reachDestination = true;
                        end = child;
                        break;
                    }
                }
            }
        }

        if (end == null) {
            return -1;
        }

        // get the shortest path
        Node node = end;
        List<Node> path = new ArrayList<Node>();
        while (node != null) {
            path.add(0, node);
            node = parents.get(node.getCoordinate());
        }
        printPath(path);
        return path.size();
    }

    public Optional<Node> search(Node start) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);

        Node currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            if (currentNode.getValue() == 9) {
                return Optional.of(currentNode);
            } else {
                queue.addAll(getChildren(currentNode));
            }
        }

        return Optional.empty();
    }

    private void printPath(List<Node> path) {
        for (int row = 0; row < nodes.length; row++) {
            for (int column = 0; column < nodes[row].length; column++) {
                String value = nodes[row][column] + "";

                // mark path with red X
                for (int i = 1; i < path.size() - 1; i++) {
                    Node node = path.get(i);
                    if (node.getCoordinate().getX() == row && node.getCoordinate().getY() == column) {
                        value = ANSI_RED + "X" + ANSI_RESET;
                        break;
                    }
                }
                System.out.print(value);

            }
            System.out.println();

        }
        System.out.println();
        System.out.println("Path: " + path);
        System.out.println("--------------");
    }

    private List<Node> getChildren(Node parent) {
        List<Node> children = new ArrayList<>();

        var accels = Accel.accelerations().collect(Collectors.toList());

        for (Accel accel : accels) {
            // System.out.println(accels);
            var newSpeed = accel.apply(parent.getSpeed());
            if (newSpeed.isValid()) {
                var newCoordinate = newSpeed.apply(parent.getCoordinate());
                if (isValid(newCoordinate)) {
                    children.add(node(newCoordinate, newSpeed, value(newCoordinate)));
                }
            }

        }

        // System.out.println("children (" + children.size() + ") " + children);

        return children;
    }

    private boolean isValid(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getY() >= 0 && coordinate.getX() < nodes.length && coordinate.getY() < nodes[0].length && value(coordinate) != 1;
    }

    private int value(Coordinate coordinate) {
        return nodes[coordinate.getX()][coordinate.getY()];
    }

}
