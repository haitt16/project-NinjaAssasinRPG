package entity;//Nhân vật trong game(player and monster)

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;//Hướng đi
    
    public int spriteCounter = 0;//sprite: Hình ảnh hai chiểu tích hợp vào một khung cảnh lớn hơn
    public int spriteNum = 1;
    
    public Rectangle solidArea;//Không gian giảm sự phụ thuộc
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;//va chạm
    
    public int maxLife;
    public int life;
}
