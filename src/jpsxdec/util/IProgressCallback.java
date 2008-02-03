/*
 * jPSXdec: Playstation 1 Media Decoder/Converter in Java
 * Copyright (C) 2007  Michael Sabin
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,   
 * Boston, MA  02110-1301, USA.
 *
 */

/*
 * IProgressCallback.java
 */

package jpsxdec.util;

/** A general purpose listener interface. */
public interface IProgressCallback {
    /** Return true to stop, false to continue */
    boolean ProgressCallback(String sWhatDoing, double dblPercentComplete);
    
    public static interface IProgressCallbackEvent extends IProgressCallback {
        /** Return true to stop, false to continue */
        boolean ProgressCallback(String sEvent);
    }
    
    public static interface IProgressCallbackError extends IProgressCallback {
        /** Return true to stop, false to continue */
        void ProgressCallback(Exception e);
    }
    
    public static interface IProgressCallbackEventError extends IProgressCallbackEvent, IProgressCallbackError {
    }
    
}