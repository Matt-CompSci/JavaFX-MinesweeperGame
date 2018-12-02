package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
    private static double stageWidth, stageHeight, tileSizeX, tileSizeY;
    private static int boardTiles, mines;
    private static Tile[][] tileMap;
    private static Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        tileSizeX = stageWidth / boardTiles;
        tileSizeY = stageHeight / boardTiles;

        Pane root = new Pane();

        tileMap = new Tile[boardTiles][boardTiles];

        for (int x = 0; x < boardTiles; x++)
        {
            for(int y = 0; y < boardTiles; y++)
            {
                tileMap[x][y] = new Tile(this, tileMap, stage, root, x, y, tileSizeX, tileSizeY, false);
            }
        }

        primaryStage.setTitle("Minesweeper");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setWidth(stageWidth);
        primaryStage.setHeight(stageHeight);
        primaryStage.setScene(new Scene(root, stageWidth, stageHeight));
        primaryStage.show();

        for(int i = 0; i < mines; i++)
        {
            boolean placedMine = false;

            do
            {
                int x = (int)(Math.random() * (mines - 1));
                int y = (int)(Math.random() * (mines - 1));

                if(!tileMap[x][y].getIsMine())
                {
                    tileMap[x][y].setIsMine(true);
                    placedMine = true;
                }
            }while(!placedMine);


        }
    }

    public static void checkWin()
    {
        boolean won = true;
        int x = 0;
        int y = 0;

        while(x < boardTiles && won)
        {
            while(y < boardTiles && won)
            {
                Tile currentTile = tileMap[x][y];
                if(!currentTile.getIsPressed() && !currentTile.getIsMine())
                {
                    won = false;
                }
                y++;
            }
            x++;
        }

        if(won)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You won");
            alert.setHeaderText("You searched all tiles that aren't mines");
            alert.setContentText("Thanks for playing!");
            alert.showAndWait();
            stage.close();
        }
    }

    public static void gameOver()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Game Over");
        alert.setHeaderText("You set off a mine!");
        alert.setContentText("Check adjacent tiles to avoid this!");
        alert.showAndWait();
        stage.close();
    }

    public static void main(String[] args)
    {
        stageWidth = 500;
        stageHeight = 500;
        boardTiles = 10;
        mines = 10;
        launch(args);
    }
}
