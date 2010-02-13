/*
 * jPSXdec: PlayStation 1 Media Decoder/Converter in Java
 * Copyright (C) 2007-2010  Michael Sabin
 * All rights reserved.
 *
 * Redistribution and use of the jPSXdec code or any derivative works are
 * permitted provided that the following conditions are met:
 *
 *  * Redistributions may not be sold, nor may they be used in commercial
 *    or revenue-generating business activities.
 *
 *  * Redistributions that are modified from the original source must
 *    include the complete source code, including the source code for all
 *    components used by a binary built from the modified sources. However, as
 *    a special exception, the source code distributed need not include
 *    anything that is normally distributed (in either source or binary form)
 *    with the major components (compiler, kernel, and so on) of the operating
 *    system on which the executable runs, unless that component itself
 *    accompanies the executable.
 *
 *  * Redistributions must reproduce the above copyright notice, this list
 *    of conditions and the following disclaimer in the documentation and/or
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jpsxdec.plugins.xa;

import javax.sound.sampled.AudioFormat;
import jpsxdec.plugins.DiscItemSerialization;
import jpsxdec.plugins.DiscItemStreaming;
import jpsxdec.util.NotThisTypeException;

/** Interface for all DiscItems that represent an audio stream.
 * This is necessary for the video plugin to utilize any audio stream that
 * runs parallel to the video. */
public abstract class DiscItemAudioStream extends DiscItemStreaming {

    public DiscItemAudioStream(DiscItemSerialization fields) throws NotThisTypeException {
        super(fields);
    }

    public DiscItemAudioStream(int iStartSector, int iEndSector) {
        super(iStartSector, iEndSector);
    }

    public boolean overlaps(DiscItemAudioStream other) {
        //  [ this ]  < [ other ]
        if (getEndSector() + getSectorsPastEnd() < other.getStartSector())
            return false;
        //  [ other ] < [ this ]
        if (other.getEndSector() + other.getSectorsPastEnd() < getStartSector())
            return false;
        return true;
    }

    abstract public int getSampleRate();

    abstract public boolean isStereo();

    /** Creates a decoder capable of converting IdentifiedSectors into audio
     *  data which will then be fed into an AudioOutputStream.
     * @see IAudioSectorDecoder#open(jpsxdec.util.AudioOutputStream) */
    abstract public IAudioSectorDecoder makeDecoder(boolean blnBigEndian, double dblVolume);

    abstract public AudioFormat getAudioFormat(boolean blnBigEndian);

    abstract public int getSectorsPastEnd();

}