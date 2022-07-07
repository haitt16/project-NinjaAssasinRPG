package main;

import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Hut;
import Object.OBJ_Key;
import Object.OBJ_POKEBALL;
import Object.Recuperate;
import entity.NPC_OldMan;
import entity.MON_GreenSlime;

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
	private final String OBJ_LIST[] = { "Key", "Door", "Chest", "Recuperate", "pokeball" };

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		objectMapNum = new int[20][3];
		monsterMapNum = new int[20][2];
		npcMapNum = new int[10][2];

		readAssetMap("/maps/asset_map.txt");
		System.out.println("objectCount : " + objectCount);
	}

	// public void setObject() {// Vật phẩm và các đối tượng đặc biệt như Door,
	// Recuperate
	// gp.obj[0] = new OBJ_Key();
	// gp.obj[0].x = 3 * gp.tileSize;
	// gp.obj[0].y = 9 * gp.tileSize;

	// gp.obj[1] = new OBJ_Key();
	// gp.obj[1].x = 4 * gp.tileSize;
	// gp.obj[1].y = 9 * gp.tileSize;

	// gp.obj[2] = new OBJ_Key();
	// gp.obj[2].x = 5 * gp.tileSize;
	// gp.obj[2].y = 9 * gp.tileSize;

	// gp.obj[3] = new OBJ_Door();
	// gp.obj[3].x = 13 * gp.tileSize;
	// gp.obj[3].y = 3 * gp.tileSize;

	// gp.obj[4] = new OBJ_Door();
	// gp.obj[4].x = 9 * gp.tileSize;
	// gp.obj[4].y = 7 * gp.tileSize;

	// gp.obj[5] = new OBJ_Door();
	// gp.obj[5].x = 13 * gp.tileSize;
	// gp.obj[5].y = 7 * gp.tileSize;

	// gp.obj[6] = new OBJ_Chest();
	// gp.obj[6].x = 13 * gp.tileSize;
	// gp.obj[6].y = 5 * gp.tileSize;

	// gp.obj[7] = new OBJ_Chest();
	// gp.obj[7].x = 9 * gp.tileSize;
	// gp.obj[7].y = 9 * gp.tileSize;

	// gp.obj[8] = new OBJ_Chest();
	// gp.obj[8].x = 13 * gp.tileSize;
	// gp.obj[8].y = 9 * gp.tileSize;

	// gp.obj[9] = new Recuperate();
	// gp.obj[9].x = 6 * gp.tileSize;
	// gp.obj[9].y = 5 * gp.tileSize;

	// gp.obj[10] = new OBJ_POKEBALL();
	// gp.obj[10].x = 10 * gp.tileSize;
	// gp.obj[10].y = 5 * gp.tileSize;
	// }

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
				case "pokeball":
					gp.obj[i] = new OBJ_POKEBALL();
					break;
				case "Recuperate":
					gp.obj[i] = new Recuperate();
					break;
				case "Hut":
					gp.obj[i] = new OBJ_Hut();
					break;
			}
			gp.obj[i].x = objectMapNum[i][1] * gp.tileSize;
			gp.obj[i].y = objectMapNum[i][2] * gp.tileSize;
		}
	}

	public void setMonster() {
		// gp.monster[0] = new MON_GreenSlime(gp);
		// gp.monster[0].x = gp.tileSize * 9;
		// gp.monster[0].y = gp.tileSize * 5;

		// gp.monster[1] = new MON_GreenSlime(gp);
		// gp.monster[1].x = gp.tileSize * 12;
		// gp.monster[1].y = gp.tileSize * 9;
		for (int i = 0; i < monsterCount; i++) {
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].x = monsterMapNum[i][0] * gp.tileSize;
			gp.monster[i].y = monsterMapNum[i][1] * gp.tileSize;
		}
	}

	public void setNPC() {
		// gp.npc[0] = new NPC_OldMan(gp);
		// gp.npc[0].x = 3 * gp.tileSize;
		// gp.npc[0].y = 1 * gp.tileSize;
		for (int i = 0; i < npcCount; i++) {
			gp.npc[i] = new NPC_OldMan(gp);
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
