package main;

/**
 * KeyHandler
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == "play") {
            keyGameStatePlay(code);
        } else if (gp.gameState == "die") {
            keyGameStateDie(code);
        } else if (gp.gameState == "title") {
            keyGameStateTitle(code);
        } else if (gp.gameState == "finish") {
            keyGameStateFinish(code);
        } else if (gp.gameState == "character") {
            keyGameStateCharacter(code);
        } else if (gp.gameState == "pause") {
            keyGameStatePause(code);
        }
    }

    public void keyGameStatePause(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        } else if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        } else if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.retry();
            } else if (gp.ui.commandNum == 1) {
                gp.startNewGame();
            } else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }

        } else if (code == KeyEvent.VK_P) {
            gp.gameState = "play";
        }
    }

    public void keyGameStatePlay(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = "character";
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            gp.player.attacking = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = "pause";
        }
    }

    public void keyGameStateDie(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        } else if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        } else if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.retry();
            } else if (gp.ui.commandNum == 1) {
                gp.startNewGame();
            } else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }

        }
    }

    public void keyGameStateFinish(int code) {
        if (code == KeyEvent.VK_N) {
            if (gp.mapLevel < 2) {
                gp.changeMap();
            } else {
                gp.gameState = "title";
            }
        }
    }

    public void keyGameStateTitle(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        } else if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        } else if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.startNewGame();
            } else if (gp.ui.commandNum == 1) {
                // add later
            } else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }

        }
    }

    public void keyGameStateCharacter(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = "play";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}