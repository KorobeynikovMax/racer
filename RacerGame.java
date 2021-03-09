package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class RacerGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;

    private RoadMarking roadMarking;
    private PlayerCar player;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        showGrid(false);
        createGame();
    }

    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        setTurnTimer(40);
        drawScene();
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x >=0 && x < WIDTH && y >= 0 && y < HEIGHT)  {
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onTurn(int step) {
        moveAll();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key.equals(Key.RIGHT)) {
            player.setDirection(Direction.RIGHT);
        }
        if (key.equals(Key.LEFT)) {
            player.setDirection(Direction.LEFT);
        }
    }

    private void drawField() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                setCellColor(j, i, Color.GREEN);
                if (j >= ROADSIDE_WIDTH && j < WIDTH - ROADSIDE_WIDTH) {
                    setCellColor(j, i, Color.DARKGRAY);
                }
                if (j == CENTER_X) {
                    setCellColor(CENTER_X, i, Color.WHITE);
                }
            }
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        player.move();
    }
}
