package main;

import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;
import Object.Recuperate;
import Object.OBJ_Pit;
import entity.NPC_OldMan;
import entity.EntityGraphic;
import entity.MON_Tanuki;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetSetter {
	GamePanel gp;
	public int objectMapNum[][];
	public int npcMapNum[][];
	public int monsterMapNum[][];
	public int objectCount = 0;
	public int monsterCount = 0;
	public int npcCount = 0;
	private final String OBJ_LIST[] = { "Key", "Door", "Chest", "Recuperate", "pokeball", "Pit" };
	private final String ASSET_FILES[] = { "/maps/asset_map.txt", "/maps/asset_map_02.txt", "/maps/asset_map_03.txt" };

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		objectMapNum = new int[100][3];
		monsterMapNum = new int[20][2];
		npcMapNum = new int[10][2];

		readAssetMap(ASSET_FILES[gp.mapLevel]);
		System.out.println("objectCount : " + objectCount);
		System.out.println("mapLevel : " + gp.mapLevel);
	}

	public void setObject() {
		for (int i = 0; i < objectCount; i++) {
			int objectTypeIndex = objectMapNum[i][0];
			switch (OBJ_LIST[objectTypeIndex]) {
				case "Key":
					gp.obj[i] = new OBJ_Key();
					break;
				case "Door":
					gp.obj[i] = new OBJ_Door();
					break;
				case "Chest":
					gp.obj[i] = new OBJ_Chest();
					break;
				case "Recuperate":
					gp.obj[i] = new Recuperate();
					break;
				case "Pit":
					gp.obj[i] = new OBJ_Pit(gp);
			}
			gp.obj[i].x = objectMapNum[i][1] * gp.tileSize;
			gp.obj[i].y = objectMapNum[i][2] * gp.tileSize;
		}
	}

	public void setMonster() {
		for (int i = 0; i < monsterCount; i++) {
			gp.monsterGraphics[i] = new EntityGraphic(gp);
			gp.monster[i] = new MON_Tanuki(gp.monsterGraphics[i]);
			gp.monster[i].x = monsterMapNum[i][0] * gp.tileSize;
			gp.monster[i].y = monsterMapNum[i][1] * gp.tileSize;
		}
	}

	public void setNPC() {
		for (int i = 0; i < npcCount; i++) {
			gp.npcGraphics[i] = new EntityGraphic(gp);
			gp.npc[i] = new NPC_OldMan(gp.npcGraphics[i]);
			gp.npc[i].x = npcMapNum[i][0] * gp.tileSize;
			gp.npc[i].y = npcMapNum[i][1] * gp.tileSize;
		}
	}

	public void readAssetMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			System.out.println(filePath);

			for (int row = 0; row < gp.maxScreenRow; row++) {
				String line[] = br.readLine().split(" ");
				for (int col = 0; col < gp.maxScreenCol; col++) {
					String infor[] = line[col].split(":");
					switch (infor[0]) {
						case "o":
							int oType = Integer.parseInt(infor[1]);
							objectMapNum[objectCount++] = new int[] { oType, col, row };
							break;
						case "m":
							monsterMapNum[monsterCount++] = new int[] { col, row };
							break;
						case "n":
							npcMapNum[npcCount++] = new int[] { col, row };
							break;
						default:
							break;
					}
				}
			}

			br.close();
		} catch (Exception e) {
		}
	}
}
