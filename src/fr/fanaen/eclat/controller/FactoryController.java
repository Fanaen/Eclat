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

import fr.fanaen.eclat.helper.RandomHelper;
import fr.fanaen.eclat.model.sparkle.Sparkle;
import fr.fanaen.eclat.model.sparkle.SparklePlusSimple;
import fr.fanaen.eclat.model.sparkle.SparkleRoundSimple;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fanaen
 */

public class FactoryController implements TimeController.Listener, KeyboardController.Listener {
    
    // -- Listener --
    
    protected List<Listener> listListener = new ArrayList<>();

    public interface Listener {
        public void onNewSparkle(Sparkle sparkle);
    }
    
    public void addListener(Listener listener) {
        listListener.add(listener);
    }
    
    // -- Waiting mode (optional) --
    
    protected boolean waitingForInputMode;
    protected boolean waiting;
    
    // -- Fields --
    
    protected long timeSinceOrigin;
    protected long timeNextSparkle;
    
    protected float hueBase;
    
    protected RandomHelper random;
    protected KeyboardController keyboardController;
    
    protected int screenWidth = 1300;
    protected int screenHeight = 600;
    protected int offsetX = -10;
    protected int offsetY = -10;
    
    
    
    // -- Constructors --
    
    public FactoryController()
    {
        timeSinceOrigin = 0;
        timeNextSparkle = 1000;
        waiting = false;
        
        random = RandomHelper.getSingleton();
        
    }
    
    public FactoryController(boolean waitingForInput) 
    {
        this();
        waitingForInputMode = waitingForInput;
        
        if(waitingForInputMode) {
            timeNextSparkle = 0;
        }
        
    }
    
    // -- Methods --

    @Override
    public void onTimeDiffChanged(long timeDiff) {
        timeSinceOrigin += timeDiff;
        
        if(waitingForInputMode) { // Waiting mode --
            if(keyboardController.getCount() > 1 && timeSinceOrigin > timeNextSparkle) {
                // Compute time before next sparkle : 1000-4000 ms. 
                int count = keyboardController.getCount() + 1;
                long nextSparkle = (long) (1000 * (1 / (count * count)));

                timeNextSparkle = timeSinceOrigin + 200 + nextSparkle;
                newSparkle(new SparklePlusSimple());
            }
        }
        else { // Standard mode --
            if(timeSinceOrigin > timeNextSparkle) {
                // Compute time before next sparkle : 1000-4000 ms. 
                int count = keyboardController.getCount() + 1;
                long nextSparkle = (long) (3000 * (1 / (count * count)));

                timeNextSparkle = timeSinceOrigin + 1000 + nextSparkle;
                newSparkle(new SparklePlusSimple());
            }
        }
    }

    public void newSparkle(Sparkle sparkle) {
        int diversity = keyboardController.getDiversity();
        float hue = 0;
        
        // Color --
        if(diversity < 5) {
            hue = hueBase + random.getRandomVariation(20);
        }
        else /* if (diversity < 20) */ {
            
            if(random.getRandomInt() % 2 == 1)
                hue = hueBase + random.getRandomVariation(20);
            else
                hue = 1 - hueBase + random.getRandomVariation(20);
        }
        
        hueBase = hue;
        
        float saturation = 1.0f; 
        float brightnessPrimary = 0.95f + random.getRandomVariation(10);
        float brightnessSecondary = brightnessPrimary - 0.15f;
        
        sparkle.setColorPrimary(Color.getHSBColor(hue, saturation, brightnessPrimary));
        sparkle.setColorSecondary(Color.getHSBColor(hue, saturation, brightnessSecondary));
        
        // Origin point --
        Point origin = new Point();
        
        origin.x = (random.getRandomInt() % screenWidth ) + offsetX;
        origin.y = (random.getRandomInt() % screenHeight) + offsetY;
        
        sparkle.setOrigin(origin);
        
        // Screen size --
        sparkle.setScreenSize(new Dimension(screenWidth, screenHeight));
        
        // Callback for listeners -
        for(Listener listener : listListener) {
            listener.onNewSparkle(sparkle);
        }
        
        //System.out.println("New Sparkle (" + origin.x +"," + origin.y + ")");
    }
    
    @Override
    public void onExtraSparkle() {
        newSparkle(new SparkleRoundSimple());
    }
    
    @Override
    public void onBinding(KeyboardController keyboardSource) {
        keyboardController = keyboardSource;
    }
    
    
    // -- Getters and setters --
    
    public void setFormSize(Dimension size) {
        screenWidth = size.width;
        screenHeight = size.height;
    }
    
    
}
