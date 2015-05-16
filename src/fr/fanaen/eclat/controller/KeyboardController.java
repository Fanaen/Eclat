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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Fanaen
 */

public class KeyboardController implements TimeController.Listener {
 
    // -- Listener --
    
    protected List<Listener> listListener = new ArrayList<>();

    public interface Listener {
        public void onExtraSparkle();
        public void onBinding(KeyboardController keyboardSource);
    }
    
    public void addListener(Listener listener) {
        listListener.add(listener);
        listener.onBinding(this);
    }
    
    // -- Fields --
    
    protected long timeSinceOrigin;
    protected long timeCursor;
    
    protected RandomHelper random;

    protected HashMap<Integer, Integer> mapKey;
    protected int countNumber = 0;
    protected int countDiversity = 0;
    
    protected boolean first = true;
    
    // -- Constructor --
    
    public KeyboardController() {
        timeSinceOrigin = 0;
        timeCursor = 0;
        mapKey = new HashMap<>();
        
        random = RandomHelper.getSingleton();
    }
    
    // -- Methods --
    
    
    public void onKeyPressed(KeyEvent evt) {
        // Ignore --
    }

    public void onKeyReleased(KeyEvent evt) {
        int code = evt.getExtendedKeyCode();
        
        if(mapKey.containsKey(code))
            mapKey.put(code, mapKey.get(code)+1);
        else
            mapKey.put(code, 1);
        
        countNumber++;
        
        // Event for first touch --
        if(first) {
            first = false;
            for(Listener listener : listListener) listener.onExtraSparkle();
        }
        else if(random.getRandomInt() % 50 == 1) { // 2 % chances of generating extra sparkle --
            for(Listener listener : listListener) listener.onExtraSparkle();
        }
    }

    public void onKeyTyped(KeyEvent evt) {
        //System.out.println("Test " + evt.getKeyChar() + " " + evt.getKeyCode() + " " + evt.getKeyLocation() + " " + evt.getExtendedKeyCode() + " " + evt.isActionKey());
    }
    
    public int getCount() {
        return countNumber;
    }
    
    public int getDiversity() {
        return countDiversity;
    }
    
    // -- Events --
    
    @Override
    public void onTimeDiffChanged(long timeDiff) {
        timeSinceOrigin += timeDiff; 
        long current = timeSinceOrigin / 1000;
        
        if(current > timeCursor) {
            timeCursor = current;
            
            // Reset count and count key's diversity -- 
            int diversity = 0;
            for(int key : mapKey.keySet()) {
                if(mapKey.get(key) > 0) diversity++;
                mapKey.put(key, 0);
            }
            
            countDiversity = diversity;
            
            // Update key count --
            countNumber = countNumber > 2 ? (int)(countNumber * 0.5) : 0;
        }
    }
    
}
