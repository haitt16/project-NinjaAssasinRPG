package background;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import javax.imageio.ImageIO;

public class TilesManage {
    GamePanel gp;
    public Tiles[] tile;
    public int mapTiles[][];

    public TilesManage(GamePanel gp) {
        this.gp = gp;
        mapTiles = new int[gp.maxWorldCol][gp.maxWorldRow];
        tile = new Tiles[10];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tiles();
            tile[0].image = ImageIO.read(getClass().getResource("/envi/grass.png"));

            tile[1] = new Tiles();
            tile[1].image = ImageIO.read(getClass().getResource("/envi/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tiles();
            tile[2].image = ImageIO.read(getClass().getResource("/envi/water.png"));
            tile[2].collision = true;

            tile[3] = new Tiles();
            tile[3].image = ImageIO.read(getClass().getResource("/envi/sand.png"));
            tile[3].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream a = getClass().getResourceAsStream("/background/mapPreset.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(a));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTiles[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int titleNum = mapTiles[worldCol][worldRow];
            int worldX = worldCol * gp.titleSize;
            int worldY = worldRow * gp.titleSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (worldX + gp.titleSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.titleSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.titleSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.titleSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[titleNum].image, screenX, screenY, gp.titleSize, gp.titleSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
