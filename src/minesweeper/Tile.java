package minesweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;


public class Tile
{
    private Pane parent;
    private Button button;
    private Stage stage;
    private double x, y, sizeX, sizeY;
    private boolean isMine, pressed;
    private Tile[][] tileMap;
    private Main application;

    static Color[] textColors = {
            Color.BLACK,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW,
            Color.PURPLE,
            Color.PINK,
            Color.GRAY,
            Color.WHITE
    };

    ArrayList<Vector2> directions = new ArrayList<Vector2>();

    public Tile(Main newApplication, Tile[][] newTileMap, Stage newStage, Pane newParent, double newX, double newY, double newSizeX, double newSizeY, boolean newIsMine)
    {
        application = newApplication;
        stage = newStage;
        x = newX;
        y = newY;
        parent = newParent;
        sizeX = newSizeX;
        sizeY = newSizeY;
        isMine = newIsMine;
        tileMap = newTileMap;

        button = new Button();

        button.setMinWidth(sizeX);
        button.setMinHeight(sizeY);
        button.setMaxWidth(sizeX);
        button.setMaxHeight(sizeY);
        button.setStyle("-fx-font-size: 12px;");

        button.setLayoutX(x * sizeX);
        button.setLayoutY(y * sizeY);

        directions.add(new Vector2(1, 0));
        directions.add(new Vector2(-1, 0));
        directions.add(new Vector2(0, 1));
        directions.add(new Vector2(0, -1));
        directions.add(new Vector2(1, 1));
        directions.add(new Vector2(1, -1));
        directions.add(new Vector2(-1, 1));
        directions.add(new Vector2(-1, -1));

        button.setOnAction((event) -> {
            pressButton();
        });

        parent.getChildren().add(button);
    }

    public boolean getIsPressed()
    {
        return pressed;
    }

    public Button getButton()
    {
        return button;
    }

    public ArrayList<Tile> getAdjacentTiles()
    {
        ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
        int tileMapSizeX = tileMap.length;
        int tileMapSizeY = tileMap[0].length;

        for(Vector2 direction : directions)
        {
            int posX = (int)x + direction.getX();
            int  posY = (int)y + direction.getY();

            if(posX < tileMapSizeX && posY < tileMapSizeY && posX >= 0 && posY >= 0)
            {
                adjacentTiles.add(tileMap[posX][posY]);
            }
        }

        return adjacentTiles;
    }
    public int getAdjacentMines()
    {
        ArrayList<Tile> adjacentTiles = getAdjacentTiles();
        int adjacentMines = 0;

        for(Tile tile : adjacentTiles)
        {
            if(tile.getIsMine())
            {
                adjacentMines++;
            }
        }

        return adjacentMines;
    }

    public void pressButton()
    {
        if(!pressed)
        {
            pressed = true;

            if (isMine)
            {
                application.gameOver();
            }

            else
            {
                int adjacentMines = getAdjacentMines();
                ArrayList<Tile> adjacentTiles = getAdjacentTiles();
                button.setTextFill(textColors[adjacentMines]);
                button.setText("" + adjacentMines);

                if (adjacentMines == 0)
                {
                    for (Tile tile : adjacentTiles)
                    {
                        if (tile.getAdjacentMines() == 0 && !tile.getIsMine())
                        {
                            tile.pressButton();
                        }
                    }
                }

                application.checkWin();
            }
        }
    }


    public boolean getIsMine()
    {
        return isMine;
    }

    public void setIsMine(boolean newIsMine)
    {
        isMine = newIsMine;
    }
}