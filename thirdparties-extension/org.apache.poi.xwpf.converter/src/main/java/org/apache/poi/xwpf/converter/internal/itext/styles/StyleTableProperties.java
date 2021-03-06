/**
 * Copyright (C) 2011 The XDocReport Team <xdocreport@googlegroups.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.apache.poi.xwpf.converter.internal.itext.styles;

import java.awt.Color;

public class StyleTableProperties
{

    private StyleBorder borderTop;

    private StyleBorder borderBottom;

    private StyleBorder borderLeft;

    private StyleBorder borderRight;

    private Color backgroundColor;

    private Float width;

    public StyleTableProperties()
    {

    }

    public StyleTableProperties( StyleTableProperties tableProperties )
    {
        this.backgroundColor = tableProperties.backgroundColor;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor( Color backgroundColor )
    {
        this.backgroundColor = backgroundColor;
    }

    public Float getWidth()
    {
        return width;
    }

    public void setWidth( Float width )
    {
        this.width = width;
    }

    public void merge( StyleTableProperties tableProperties )
    {
        if ( tableProperties.getBackgroundColor() != null )
        {
            backgroundColor = tableProperties.getBackgroundColor();
        }
    }

    public void setBorderBottom( StyleBorder borderBottom )
    {
        this.borderBottom = borderBottom;
    }

    public void setBorderLeft( StyleBorder borderLeft )
    {
        this.borderLeft = borderLeft;
    }

    public void setBorderRight( StyleBorder borderRight )
    {
        this.borderRight = borderRight;

    }

    public void setBorderTop( StyleBorder borderTop )
    {
        this.borderTop = borderTop;

    }

    public StyleBorder getBorderTop()
    {
        return borderTop;
    }

    public StyleBorder getBorderBottom()
    {
        return borderBottom;
    }

    public StyleBorder getBorderLeft()
    {
        return borderLeft;
    }

    public StyleBorder getBorderRight()
    {
        return borderRight;
    }

}
