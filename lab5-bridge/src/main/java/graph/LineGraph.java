package graph;

import drawer.DrawingApi;

import java.util.List;

public class LineGraph extends Graph {
    private final List<Edge> edges;

    public LineGraph(DrawingApi drawingApi, int vertexCount, List<Edge> edges) {
        super(drawingApi, vertexCount);
        this.edges = edges;
    }

    @Override
    public void drawGraph() {
        drawPoints();
        edges.forEach(edge -> drawEdge(edge.v, edge.u));
        drawingApi.paint();
    }
}
