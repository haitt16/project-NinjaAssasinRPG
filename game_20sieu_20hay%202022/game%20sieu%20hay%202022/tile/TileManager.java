package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	private final String MAP_FILES[] = { "/maps/map_01.txt", "/maps/map_02.txt", "/maps/map_03.txt" };

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

		getTileImage();
		loadMap(MAP_FILES[gp.mapLevel]);
	}

	public void getTileImage() {// Bugging
		try {
			setup(0, "big_rock", true);
			setup(1, "big_rock_brown", true);
			setup(2, "big_rock_snow", true);
			setup(3, "flag1", false);
			setup(4, "flag2", false);
			setup(5, "flag3", false);
			setup(6, "grass", false);
			setup(7, "ground", false);
			setup(8, "ground_pink", false);
			setup(9, "non_pit", false);
			setup(10, "pit", false);
			setup(11, "sand", false);
			setup(12, "shield_blue", false);
			setup(13, "snow", false);
			setup(14, "tree", true);
			setup(15, "tree_cuted", true);
			setup(21, "tree_half_snow", true);
			setup(16, "tree_pink", true);
			setup(17, "tree_snow", true);
			setup(18, "water", true);
			setup(19, "water_around", false);
			setup(20, "water_snow", true);
			setup(22, "snow_around", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setup(int index, String tileName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/" + tileName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				while (col < gp.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e) {
		}
	}

	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

			int tileNum = mapTileNum[col][row];

			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
	}
}