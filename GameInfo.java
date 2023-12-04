package com.object;

import java.io.Serializable;

/**
 * object of game information, is used to save user's game
 */

public class GameInfo implements Serializable {
    private int[][] data;
    private int x = 0;
    private int y = 0;
    private String path;
    private int moves;


    public GameInfo() {
    }

    public GameInfo(int[][] data, int x, int y, String path, int moves) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.path = path;
        this.moves = moves;
    }

    /**
     * 获取
     * @return data
     */
    public int[][] getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(int[][] data) {
        this.data = data;
    }

    /**
     * 获取
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * 设置
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 获取
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * 设置
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 获取
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取
     * @return moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * 设置
     * @param moves
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    public String toString() {
        return "GameInfo{data = " + data + ", x = " + x + ", y = " + y + ", path = " + path + ", moves = " + moves + "}";
    }
}
