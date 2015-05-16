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

package fr.fanaen.eclat.model.sparkle;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Fanaen
 */

public class SparkleRoundSimple extends SparkleRound {
    
    // -- Fields --
    
    protected float alpha;
    protected float beta;    
    
    public SparkleRoundSimple() {
        size = new Dimension(20, 20);
        speed = 400; // Pixel per second
        alpha = speed / 1000;
        beta = 10;
    }

    @Override
    public void draw(Graphics g) {
        
        int distance = (int) (alpha * age + beta);        
        Graphics2D g2 = (Graphics2D) g;        
        
        // Primary Round --
        g2.setColor(colorPrimary);
        g2.setStroke(new BasicStroke(5));
        g2.drawOval(origin.x - distance, origin.y - distance, distance*2, distance*2);
        
    }
    
    @Override
    public boolean isDead() {
        int distance = (int) (alpha * age + beta);
        
        return distance > screenSize.width + screenSize.height * 2;
    }
}
