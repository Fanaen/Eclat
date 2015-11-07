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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Fanaen
 */

public class SparkleRoundRainbow extends SparkleRound {
    
    // -- Fields --
    
    public SparkleRoundRainbow() {
        size = new Dimension(50, 50);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;    
        
        // Anti-alias --
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Primary Round --
        g2.setColor(colorPrimary);
        g2.fillOval(origin.x - (size.width/2), origin.y - (size.height/2), size.width, size.height);
    }
    
    @Override
    public boolean isDead() {
        return false;
    }
}
