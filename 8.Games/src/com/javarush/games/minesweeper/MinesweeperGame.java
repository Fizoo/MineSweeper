package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private int countMinesOnField = 0;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE * SIDE;
    private int score;

    //public boolean isMine;
    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private GameObject[][] gameField = new GameObject[SIDE][SIDE];

    private void createGame() {
        //isGameStopped=false;
        //int count=0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                boolean mine = false;
                if (getRandomNumber(10) < 1) {
                    mine = true;
                    countMinesOnField++;
                }
                gameField[j][i] = new GameObject(i, j, mine);
                setCellColor(j, i, Color.ORANGE);
                setCellValue(j, i, "");
            }
        }
        //countMinesOnField=count;
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List list = new ArrayList<GameObject>();
        int x = gameObject.x;
        int y = gameObject.y;
        for (int i = y - 1; i <= y + 1; i++)
            for (int j = x - 1; j <= x + 1; j++) {
                if ((j != x || i != y) && (j >= 0 && i >= 0 && j < SIDE && i < SIDE))
                    list.add(gameField[i][j]);
            }
        return list;
    }

    private void countMineNeighbors() {
        List<GameObject> list2 = new ArrayList<>();
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                if (!gameField[y][x].isMine) {
                    list2 = getNeighbors(gameField[y][x]);
                    for (int i = 0; i < list2.size(); i++) {
                        if (list2.get(i).isMine)
                            gameField[y][x].countMineNeighbors++;
                    }
                }
            }
        }
    }

    private void openTile(int x, int y) {
        if (gameField[y][x].isOpen || gameField[y][x].isFlag || isGameStopped) return;
        gameField[y][x].isOpen = true;
        countClosedTiles--;
        if (gameField[y][x].isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
            return;
        }
        if (gameField[y][x].countMineNeighbors == 0) {
            score = score + 5;
            setScore(score);
            setCellValue(x, y, "");
            for (GameObject go : getNeighbors(gameField[y][x])) openTile(go.x, go.y);
        } else {
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
            score = score + 5;
            setScore(score);
        }
        setCellColor(x, y, Color.GREEN);

        if (countClosedTiles == countMinesOnField) {
            win();
            return;
        }
    }

    private void markTile(int x, int y) {
        if (gameField[y][x].isOpen) return;
        if (countFlags == 0 && !gameField[y][x].isFlag) return;
        else if (!gameField[y][x].isFlag) {

            gameField[y][x].isFlag = true;
            countFlags--;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.YELLOW);

        } else if (gameField[y][x].isFlag) {
            gameField[y][x].isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        }


    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.SILVER, "You lose", Color.BLACK, 23);

    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You Win!!!", Color.WHITE, 100);
    }

    private void restart() {
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        createGame();

    }


    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped) {
            restart();
        } else {
            openTile(x, y);

        }
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }
}
