package main;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import object.OBJ_POKEBALL;

public class UI {
    GamePanel gp;
    Font arial_20;
    BufferedImage pokeballImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_POKEBALL pokeball = new OBJ_POKEBALL();
        pokeballImage = pokeball.image;
    }

    public void showMassage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawImage(pokeballImage, gp.titleSize / 2, gp.titleSize / 2, gp.titleSize, gp.titleSize, null);
        g2.drawString("Key = " + gp.player.hasKey, 30, 30);

        if (messageOn == true) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.titleSize / 2, gp.titleSize * 5);

            messageCounter++;

            if(messageCounter > 120){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
