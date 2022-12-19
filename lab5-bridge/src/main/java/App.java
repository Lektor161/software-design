import drawer.DrawingApi;
import drawer.DrawingApiAwt;
import drawer.DrawingApiFx;
import graph.Graph;
import graph.LineGraph;
import graph.MatrixGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        DrawingApi drawingApi = switch (args[0]) {
            case "fx" -> new DrawingApiFx(1000);
            case "awt" -> new DrawingApiAwt(1000);
            default -> throw new IllegalArgumentException("excepted fx or awt in first argument");
        };

        App app = new App();
        Scanner scanner = new Scanner(new File(app.getClass().getClassLoader().getResource(args[2]).getFile()));

        Graph graph = switch (args[1]) {
            case "line" -> parseLineGraph(drawingApi, scanner);
            case "matrix" -> parseMatrixGraph(drawingApi, scanner);
            default -> throw new IllegalArgumentException("excepted line or matrix in second argument");
        };
        graph.drawGraph();
    }

    private static Graph parseLineGraph(DrawingApi drawingApi, Scanner scanner) {
        List<Graph.Edge> edges = new ArrayList<>();
        int vertexCount = scanner.nextInt();
        while (scanner.hasNext()) {
            edges.add(new Graph.Edge(scanner.nextInt(), scanner.nextInt()));
        }
        return new LineGraph(drawingApi, vertexCount, edges);
    }

    private static Graph parseMatrixGraph(DrawingApi drawingApi, Scanner scanner) {
        List<List<Boolean>> matrix = new ArrayList<>();
        while (scanner.hasNext()) {
            matrix.add(Arrays.stream(scanner.nextLine().split(" ")).map(it -> Integer.parseInt(it) > 0).toList());
        }
        return new MatrixGraph(drawingApi, matrix);
    }
}
