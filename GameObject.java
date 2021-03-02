package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public int x;
    public int y;
    public int[][] matrix;

    public int width;
    public int height;

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.width = matrix[0].length;
        this.height = matrix.length;
        this.matrix = matrix;
    }

    public void draw(Game game) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                game.setCellColor(j + this.x, i + this.y,
                        Color.values()[matrix[i][j]]);
            }
        }
    }
}
