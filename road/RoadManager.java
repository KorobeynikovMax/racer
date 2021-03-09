package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.GameObject;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private static final int PLAYER_CAR_DISTANCE = 12;
    private List<RoadObject> items = new ArrayList<>();

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        if (type == RoadObjectType.THORN) {
            return new Thorn(x, y);
        } else {
            return new Car(type, x, y);
        }
    }

    private void addRoadObject(RoadObjectType type, Game game) {
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (roadObject != null && isRoadSpaceFree(roadObject)) {
            items.add(roadObject);
        }
    }

    public void draw(Game game) {
        for (RoadObject roadObject : items) {
            roadObject.draw(game);
        }
    }

    public void move(int boost) {
        for (RoadObject roadObject : items) {
            roadObject.move(boost + roadObject.speed);
        }
        deletePassedItems();
    }

    private boolean isThornExists() {
        boolean result = false;
        for (RoadObject roadObject : items) {
            if (roadObject.type == RoadObjectType.THORN) {
                result = true;
            }
        }
        return result;
    }

    private void generateThorn(Game game) {
        if (game.getRandomNumber(100) < 10 && !isThornExists()) {
            addRoadObject(RoadObjectType.THORN, game);
        }
    }

    public void generateNewRoadObjects(Game game) {
        generateThorn(game);
        generateRegularCar(game);
    }

    private void deletePassedItems() {
        List<RoadObject> itemsCopy = new ArrayList<>();
        itemsCopy.addAll(items);
        for (RoadObject roadObject : itemsCopy) {
            if (roadObject.y >= RacerGame.HEIGHT) {
                items.remove(roadObject);
            }
        }
    }

    public boolean checkCrush(PlayerCar playerCar) {
        boolean result = false;
        for (RoadObject roadObject : items) {
            result = result || roadObject.isCollision(playerCar);
        }
        return result;
    }

    private void generateRegularCar(Game game) {
        int carTypeNumber = game.getRandomNumber(4);
        if (game.getRandomNumber(100) < 30) {
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
        }
    }

    private boolean isRoadSpaceFree(RoadObject object) {
        for (RoadObject roadObject : items) {
            if (roadObject.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)) {
                return false;
            }
        }
        return true;
    }
}
