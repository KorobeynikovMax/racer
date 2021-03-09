package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;

public class RacerGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;

    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        showGrid(false);
        createGame();
    }

    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        setTurnTimer(40);
        drawScene();
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
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
        roadManager.generateNewRoadObjects(this);
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key.RIGHT.equals(key)) {
            player.setDirection(Direction.RIGHT);
        }
        if (key.LEFT.equals(key)) {
            player.setDirection(Direction.LEFT);
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (Key.LEFT.equals(key) && Direction.LEFT.equals(player.getDirection())) {
            player.setDirection(Direction.NONE);
        }
        if (Key.RIGHT.equals(key) && Direction.RIGHT.equals(player.getDirection())) {
            player.setDirection(Direction.NONE);
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
        roadManager.move(player.speed);
        player.move();
    }
}
