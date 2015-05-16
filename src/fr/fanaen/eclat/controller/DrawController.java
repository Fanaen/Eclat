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

package fr.fanaen.eclat.controller;

import fr.fanaen.eclat.model.sparkle.Sparkle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fanaen
 */

public class DrawController implements TimeController.Listener, FactoryController.Listener{
    
    // -- Fields --
    
    protected List<Sparkle> listSparkle;
    protected List<Sparkle> listSparkleDead;
    
    protected long timeDiff;
        
    // -- Constructors --

    public DrawController() {
        listSparkle = new LinkedList<>();
        listSparkleDead = new LinkedList<>();
    }
    
    // -- Methods --
    
    public void draw(Graphics g) {
        
        g.setColor(Color.BLACK);
        
        // Update articles --
        for(Sparkle sparkle : listSparkle) {
            sparkle.addTime(timeDiff);
            sparkle.draw(g);
            
            // Tag dead particle --
            if(sparkle.isDead()) {
                listSparkleDead.add(sparkle);
            }
        }
        
        // Remove dead particles --
        for(Sparkle sparkle : listSparkleDead) {
            listSparkle.remove(sparkle);
        }
        listSparkleDead.clear();
    }
    
    private void addSparkle(Sparkle sparkle)
    {
        listSparkle.add(sparkle);
    }

    // -- Events --
    
    @Override
    public void onTimeDiffChanged(long timeDiff) {
        this.timeDiff = timeDiff;
    }
    
    @Override
    public void onNewSparkle(Sparkle sparkle) {
        addSparkle(sparkle);
    }

    public void setFormSize(Dimension size) {
        for(Sparkle sparkle : listSparkleDead) {
            sparkle.setScreenSize(size);
        }
    }
}
