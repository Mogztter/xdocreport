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
package org.odftoolkit.odfdom.converter.internal.itext.stylable;

import org.odftoolkit.odfdom.converter.internal.itext.styles.Style;
import org.odftoolkit.odfdom.converter.internal.itext.styles.StyleTextProperties;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;

import fr.opensagres.xdocreport.itext.extension.IITextContainer;

public class StylablePhrase
    extends Phrase
    implements IStylableContainer
{
    private static final long serialVersionUID = 664309269352903329L;

    private IStylableContainer parent;

    private Style lastStyleApplied = null;

    public StylablePhrase( IStylableFactory ownerDocument, IStylableContainer parent )
    {
        this.parent = parent;
    }

    public void addElement( Element element )
    {
        super.add( element );
    }

    public void applyStyles( Style style )
    {
        this.lastStyleApplied = style;

        StyleTextProperties textProperties = style.getTextProperties();
        if ( textProperties != null )
        {
            // Font
            Font font = textProperties.getFont();
            if ( font != null )
            {
                super.setFont( font );
            }
        }
    }

    public Style getLastStyleApplied()
    {
        return lastStyleApplied;
    }

    public IStylableContainer getParent()
    {
        return parent;
    }

    public Element getElement()
    {
        return this;
    }

    public IITextContainer getITextContainer()
    {
        return parent;
    }

    public void setITextContainer( IITextContainer container )
    {
    }
}
