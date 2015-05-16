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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fanaen
 */

public class TimeController {
    
    // -- Listener --
    
    protected List<Listener> listListener = new ArrayList<>();

    public interface Listener {
        public void onTimeDiffChanged(long timeDiff);
    }
    
    public void addListener(Listener listener) {
        listListener.add(listener);
    }
    
    // -- Fields --
    
    protected long timeOrigin;
    protected long timeReference;
    protected long timeCursor;
    protected long timeDiff;
    protected long timeOffset;
    protected long timePaused;
    
    protected boolean paused;
    
    // -- Constructors --

    public TimeController() {
        timeOrigin = timeReference = timeCursor = timePaused = System.currentTimeMillis();
        timeOffset = 0;
        paused = false;
    }
        
    // -- Time Methods --
    
    public void pause() {
        if(paused == false) {
            paused = true;
            timePaused = System.currentTimeMillis();
        }
    }
    
    public void resume() {
        if(paused == true) {
            paused = false;
            timeOffset += (System.currentTimeMillis() - timePaused);
        }
    }
    
    public void updateTimeCursor() {
        timeCursor = System.currentTimeMillis();
        
        // Compute time difference according to the game state --
        if(paused) {
            timeDiff = timePaused - timeReference - timeOffset;
            timeOffset = 0;
            timePaused = timeCursor;
        }
        else {
            timeDiff = timeCursor - timeReference - timeOffset;
            timeOffset = 0;
        }
        
        timeReference = timeCursor;
        
        // Callback for listeners -
        for(Listener listener : listListener) {
            listener.onTimeDiffChanged(timeDiff);
        }
    }

    long getTimeDiff() {
        return timeDiff;
    }
    
    // -- Getters and setters --
    
}
