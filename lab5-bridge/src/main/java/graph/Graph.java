package graph;

import drawer.Coord;
import drawer.DrawingApi;

public abstract class Graph {
    protected final DrawingApi drawingApi;

    protected final int vertexCount;

    public Graph(DrawingApi drawingApi, int vertexCount) {
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("vertexCount must be grater 0");
        }
        this.drawingApi = drawingApi;
        this.vertexCount = vertexCount;
    }

    public abstract void drawGraph();

    protected void drawPoints() {
        double pointRadius = drawingApi.getDrawingAreaSize() / 100.0;
        for (int i = 0; i < vertexCount; i++) {
            var coord = vertexToCoord(i);
            drawingApi.drawPoint(coord, pointRadius);
        }
    }

    protected void drawEdge(int v, int u) {
        var coordV = vertexToCoord(v);
        var coordU = vertexToCoord(u);
        if (v == u) {
            double circleRadius = drawingApi.getDrawingAreaSize() / 30.0;
            double angle = 2.0 * Math.PI * v / vertexCount;
            drawingApi.drawCircle(
                    new Coord(
                            coordV.getX() + circleRadius * Math.cos(angle),
                            coordV.getY() + circleRadius * Math.sin(angle)
                    ),
                    circleRadius
            );
            return;
        }
        drawingApi.drawLine(coordU, coordV);
    }

    private Coord vertexToCoord(int v) {
        double center = drawingApi.getDrawingAreaSize() / 2.0;
        double radius = drawingApi.getDrawingAreaSize() / 2.0 * 0.8;
        double angle = 2.0 * Math.PI * v / vertexCount;
        return new Coord(
                center + radius * Math.cos(angle),
                center + radius * Math.sin(angle)
        );
    }

    public static class Edge {
        public final int v;
        public final int u;

        public Edge(int v, int u) {
            this.v = v;
            this.u = u;
        }
    }
}
