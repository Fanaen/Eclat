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

/**
 *
 * @author Fanaen
 */

public class SparklePlusSimple extends SparklePlus {
    
    // -- Fields --
    
    protected float alpha;
    protected float beta;    
    
    public SparklePlusSimple() {
        size = new Dimension(50, 50);
        speed = 150; // Pixel per second
        alpha = speed / 1000;
    }

    @Override
    public void draw(Graphics g) {
        
        int distance = (int) (alpha * age + beta);
        
        // Primary rectangles --
        g.setColor(colorPrimary);
        
        g.fillRect(origin.x - distance, origin.y, size.width, size.height); // Left
        g.fillRect(origin.x + distance, origin.y, size.width, size.height); // Right
        g.fillRect(origin.x, origin.y - distance, size.width, size.height); // Bottom
        g.fillRect(origin.x, origin.y + distance, size.width, size.height); // Top
        
        // Secondary rectangles --
        g.setColor(colorSecondary);
        
        if(distance > size.width) {
            int distanceTail = distance - size.width;
            g.fillRect(origin.x - distanceTail, origin.y, size.width, size.height); // Left
            g.fillRect(origin.x + distanceTail, origin.y, size.width, size.height); // Right
        }
        
        if(distance > size.height) {
            int distanceTail = distance - size.height;
            g.fillRect(origin.x, origin.y - distanceTail, size.width, size.height); // Bottom
            g.fillRect(origin.x, origin.y + distanceTail, size.width, size.height); // Top
        }
        
    }

    @Override
    public boolean isDead() {
        int distance = (int) (alpha * age + beta);
        
        return distance > screenSize.width + screenSize.height * 2;
    }
    
    
}
