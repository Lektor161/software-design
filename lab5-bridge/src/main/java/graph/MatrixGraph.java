package graph;

import drawer.DrawingApi;

import java.util.List;

public class MatrixGraph extends Graph {
    List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi, List<List<Boolean>> matrix) {
        super(drawingApi, matrix.size());
        this.matrix = matrix;
    }

    @Override
    public void drawGraph() {
        drawPoints();
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (matrix.get(i).get(j)) {
                    drawEdge(i, j);
                }
            }
        }
        drawingApi.paint();
    }
}
