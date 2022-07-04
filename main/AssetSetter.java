package main;

import object.OBJ_POKEBALL;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_POKEBALL();
        gp.obj[0].worldX = 10 * gp.titleSize;
        gp.obj[0].worldY = 2 * gp.titleSize;

    }

}
