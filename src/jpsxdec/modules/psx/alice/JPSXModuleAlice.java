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

package jpsxdec.modules.psx.alice;

import java.io.IOException;
import jpsxdec.cdreaders.CdSector;
import jpsxdec.modules.DiscItemSerialization;
import jpsxdec.modules.IdentifiedSector;
import jpsxdec.modules.IndexingDemuxerIS;
import jpsxdec.modules.JPSXModule;
import jpsxdec.modules.psx.video.bitstreams.BitStreamUncompressor;
import jpsxdec.modules.xa.JPSXModuleXAAudio;
import jpsxdec.util.NotThisTypeException;


public class JPSXModuleAlice extends JPSXModule {

    private static JPSXModuleAlice SINGLETON;

    public static JPSXModuleAlice getModule() {
        if (SINGLETON == null)
            SINGLETON = new JPSXModuleAlice();
        return SINGLETON;
    }

    private JPSXModuleAlice() {
    }



    @Override
    public void indexing_sectorRead(IdentifiedSector sector) {
        if (sector.getClass() == SectorAliceFrameChunk.class) {
            SectorAliceFrameChunk alice = (SectorAliceFrameChunk) sector;
            if (alice.getFrameNumber() == 1 && alice.getChunkNumber() == 0)
                JPSXModuleXAAudio.getModule().indexing_endAllBeforeCurrent();
        } 
    }

    @Override
    public void indexing_endOfDisc() {
    }

    @Override
    public void indexing_static(IndexingDemuxerIS inStream) throws IOException {
    }

    @Override
    public void deserialize_lineRead(DiscItemSerialization serial) {
    }

    @Override
    public IdentifiedSector identifySector(CdSector sector) {
        try {
            SectorAliceFrameChunkNull nullAlice = new SectorAliceFrameChunkNull(sector);
            try {
                return new SectorAliceFrameChunk(sector);
            } catch (NotThisTypeException ex) {
                return nullAlice;
            }
        } catch (NotThisTypeException ex) {}
        return null;
    }

    @Override
    public BitStreamUncompressor identifyVideoFrame(byte[] abHeaderBytes, long lngFrameNum) {
        return null;
    }

    @Override
    public String getModuleDescription() {
        return "Alice In Cyberland handling by Michael Sabin";
    }

}