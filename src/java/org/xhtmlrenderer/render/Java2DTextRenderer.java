/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci, Torbj�rn Gannholm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package org.xhtmlrenderer.render;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.xhtmlrenderer.extend.FontContext;
import org.xhtmlrenderer.extend.OutputDevice;
import org.xhtmlrenderer.extend.TextRenderer;
import org.xhtmlrenderer.swing.Java2DOutputDevice;

/**
 * @author   Joshua Marinacci
 * @author   Torbj�rn Gannholm
 */
public class Java2DTextRenderer implements TextRenderer {

    protected float scale = 1.0f;

    protected float threshold = 25;

    protected int level = HIGH;

    public void drawString(OutputDevice outputDevice, String string, float x, float y ) {
        Graphics2D graphics = ((Java2DOutputDevice)outputDevice).getGraphics();
        if ( graphics.getFont().getSize() > threshold && level > NONE ) {
            graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        }
        graphics.drawString( string, (int)x, (int)y );
        if ( graphics.getFont().getSize() > threshold && level > NONE ) {
            graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );
        }
    }

    public void setup(FontContext fontContext) {
        //Uu.p("setup graphics called");
        ((Java2DFontContext)fontContext).getGraphics().setRenderingHint( 
                RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF );
    }

    public void setFontScale( float scale ) {
        this.scale = scale;
    }

    public void setSmoothingThreshold( float fontsize ) {
        threshold = fontsize;
    }

    public void setSmoothingLevel( int level ) {
        this.level = level;
    }

    public FSFontMetrics getFSFontMetrics(FontContext fc, FSFont font, String string ) {
        Graphics2D graphics = ((Java2DFontContext)fc).getGraphics();
        return new LineMetricsAdapter(
                ((AWTFSFont)font).getAWTFont().getLineMetrics(
                        string, graphics.getFontRenderContext()));
    }
    
    public int getWidth(FontContext fc, FSFont font, String string) {
        Graphics2D graphics = ((Java2DFontContext)fc).getGraphics();
        Font awtFont = ((AWTFSFont)font).getAWTFont();
        return (int)Math.ceil(
                graphics.getFontMetrics(awtFont).getStringBounds(string, graphics).getWidth());
    }

    public float getFontScale() {
        return this.scale;
    }

    public int getSmoothingLevel() {
        return level;
    }
}

