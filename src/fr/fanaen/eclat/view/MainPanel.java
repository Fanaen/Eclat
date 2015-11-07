/* 
 * Copyright (C) 2015 Fanaen <contact@fanaen.fr>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package fr.fanaen.eclat.view;

import fr.fanaen.eclat.controller.DrawController;
import fr.fanaen.eclat.controller.FactoryController;
import fr.fanaen.eclat.controller.KeyboardController;
import fr.fanaen.eclat.controller.TimeController;
import fr.fanaen.eclat.model.sparkle.SparkleRoundRainbow;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fanaen
 */

public class MainPanel extends javax.swing.JPanel implements Runnable {

    // -- Listener --
    
    protected List<Listener> listListener = new ArrayList<>();

    public interface Listener {
        public void onSwitchFullscreen(boolean state);
    }
    
    public void addListener(Listener listener) {
        listListener.add(listener);
    }
    // -- Fields --
    
    protected TimeController timeController;
    protected DrawController drawController;
    protected FactoryController factoryController;
    protected KeyboardController keyboardController;
    protected boolean pursue;    
    
    protected long timeClicked = 0;
    protected boolean fullscreen = false;
    
    protected SparkleRoundRainbow cursorSparkle = null;
    
    // -- Constructors --
    
    public MainPanel() {
        pursue = true;
        
        timeController = new TimeController();
        drawController = new DrawController();
        factoryController = new FactoryController(true);
        keyboardController = new KeyboardController();
        
        timeController.addListener(factoryController);
        timeController.addListener(drawController);
        timeController.addListener(keyboardController);
        
        factoryController.addListener(drawController);
        
        keyboardController.addListener(factoryController);
        
        initComponents();
        
        // Cursor system (optional) --
        boolean cursorMode = true;
        if(cursorMode) {
            cursorSparkle = new SparkleRoundRainbow();
            factoryController.newSparkle(cursorSparkle);
            
            // Transparent 16 x 16 pixel cursor image.
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

            // Create a new blank cursor.
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

            // Set the blank cursor to the JFrame.
            this.setCursor(blankCursor);
        }        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        long now = System.currentTimeMillis();
        
        if(timeClicked == 0 || now - timeClicked >  200) {
            timeClicked = now;
        }
        else {
            fullscreen = !fullscreen;
            for(Listener listener : listListener) listener.onSwitchFullscreen(fullscreen);
        }
            
    }//GEN-LAST:event_formMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        keyboardController.onKeyPressed(evt);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        keyboardController.onKeyReleased(evt);
    }//GEN-LAST:event_formKeyReleased

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        keyboardController.onKeyTyped(evt);
    }//GEN-LAST:event_formKeyTyped

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        onResized();
    }//GEN-LAST:event_formComponentResized

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        //System.out.println("Moved");
        cursorSparkle.setOrigin(evt.getPoint());
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    // -- Methods --
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawController.draw(g);
        
    }
    
    @Override
    public void run() {
        
        while (pursue) {

            timeController.updateTimeCursor();
            repaint();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }
    
    private void onResized() {
        factoryController.setFormSize(this.getSize());
        drawController.setFormSize(this.getSize());
    }
}
