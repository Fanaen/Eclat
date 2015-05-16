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

package fr.fanaen.eclat.helper;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Fanaen
 */

public class RandomHelper {
    
    // -- Singleton --
    
    private static RandomHelper singleton;
    
    public static RandomHelper getSingleton() {
        if(singleton == null) singleton = new RandomHelper();
        return singleton;
    }
    
    // -- Fields --
    
    protected Random randomGenerator;
    
    // -- Constructors --
    
    public RandomHelper() {
        randomGenerator = new SecureRandom();
    }
    
    // -- Methods --
    
    public int getRandomInt() {
        byte[] aesKey = new byte[4]; // 16 bytes = 128 bits
        randomGenerator.nextBytes(aesKey);
        int i = (aesKey[0]<<24)&0xff000000|
                (aesKey[1]<<16)&0x00ff0000|
                (aesKey[2]<< 8)&0x0000ff00|
                (aesKey[3]    )&0x000000ff;
        
        return Math.abs(i);
    }
    
    public float getRandomVariation(int range) {
        return (((float) getRandomInt() % range - 10) / 100);
    }
    
}
