package drawer;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class DrawingApiFx implements DrawingApi {
    private static final AtomicReference<Canvas> CANVAS = new AtomicReference<>(new Canvas(500, 500));
    private final List<Consumer<GraphicsContext>> drawingTasks = new ArrayList<>();

    public DrawingApiFx(int screenSize) {
        CANVAS.set(new Canvas(screenSize, screenSize));
    }

    @Override
    public long getDrawingAreaSize() {
        return (long) CANVAS.get().getWidth();
    }

    @Override
    public void drawPoint(Coord coord, double radius) {
        drawingTasks.add(graphicsContext -> graphicsContext.fillOval(coord.getX() - radius, coord.getY() - radius, 2 * radius, 2 * radius));
    }

    @Override
    public void drawLine(Coord coord1, Coord coord2) {
        drawingTasks.add(graphicsContext -> graphicsContext.strokeLine(
                coord1.getX(), coord1.getY(),
                coord2.getX(), coord2.getY())
        );
    }

    @Override
    public void drawCircle(Coord coord, double radius) {
        drawingTasks.add(graphicsContext -> graphicsContext.strokeOval(coord.getX() - radius, coord.getY() - radius, 2 * radius, 2 * radius));
    }

    @Override
    public void paint() {
        final GraphicsContext graphicsContext = CANVAS.get().getGraphicsContext2D();
        drawingTasks.forEach(drawingTask -> drawingTask.accept(graphicsContext));
        FxDrawer.launch(FxDrawer.class);
    }

    public static class FxDrawer extends Application {
        @Override
        public void start(Stage stage) {
            final Group group = new Group();
            group.getChildren().add(CANVAS.get());

            final Scene scene = new Scene(group, Color.WHITE);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
        }
    }
}
