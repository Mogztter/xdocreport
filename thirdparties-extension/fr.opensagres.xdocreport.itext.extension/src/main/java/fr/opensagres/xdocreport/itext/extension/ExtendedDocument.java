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
package fr.opensagres.xdocreport.itext.extension;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

public class ExtendedDocument
    extends Document
    implements IITextContainer
{
    private Map<String, MasterPage> masterPagesCache = new HashMap<String, MasterPage>();

    private MasterPage defaultMasterPage;

    private final ExtendedHeaderFooter headerFooter;

    protected final PdfWriter writer;

    protected float originMarginTop;

    protected float originMarginBottom;

    protected float originMarginRight;

    protected float originMarginLeft;

    private PageOrientation orientation = PageOrientation.Portrait;

    public ExtendedDocument( OutputStream out, Rectangle rectangle )
        throws DocumentException
    {
        super( rectangle );
        this.writer = ExtendedPdfWriter.getInstance( this, out );
        headerFooter = new ExtendedHeaderFooter( this );

        writer.setPageEvent( headerFooter );
        this.originMarginTop = marginTop;
        this.originMarginBottom = marginBottom;
        this.originMarginRight = marginRight;
        this.originMarginLeft = marginLeft;
    }

    public ExtendedDocument( OutputStream out, Rectangle rectangle, float marginLeft, float marginRight,
                             float marginTop, float marginBottom )
        throws DocumentException
    {
        super( rectangle, marginLeft, marginRight, marginTop, marginBottom );
        this.writer = ExtendedPdfWriter.getInstance( this, out );
        headerFooter = new ExtendedHeaderFooter( this );
        writer.setPageEvent( headerFooter );
        this.originMarginTop = marginTop;
        this.originMarginBottom = marginBottom;
        this.originMarginRight = marginRight;
        this.originMarginLeft = marginLeft;
    }

    public ExtendedDocument( OutputStream out )
        throws DocumentException
    {
        this.writer = ExtendedPdfWriter.getInstance( this, out );
        headerFooter = new ExtendedHeaderFooter( this );
        writer.setPageEvent( headerFooter );
        this.originMarginTop = marginTop;
        this.originMarginBottom = marginBottom;
        this.originMarginRight = marginRight;
        this.originMarginLeft = marginLeft;
    }

    public boolean setOriginalMargins( float marginLeft, float marginRight, float marginTop, float marginBottom )
    {
        this.originMarginTop = marginTop;
        this.originMarginBottom = marginBottom;
        this.originMarginRight = marginRight;
        this.originMarginLeft = marginLeft;
        return super.setMargins( marginLeft, marginRight, marginTop, marginBottom );
    }

    public void addElement( Element element )
    {
        try
        {
            add( element );
        }
        catch ( DocumentException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add( Element element )
        throws DocumentException
    {
        if ( !isOpen() )
        {
            open();
        }
        return super.add( element );
    }

    public MasterPage getDefaultMasterPage()
    {
        return defaultMasterPage;
    }

    public float getOriginMarginBottom()
    {
        return originMarginBottom;
    }

    public float getOriginMarginLeft()
    {
        return originMarginLeft;
    }

    public float getOriginMarginRight()
    {
        return originMarginRight;
    }

    public float getOriginMarginTop()
    {
        return originMarginTop;
    }

    public void setActiveMasterPage( MasterPage masterPage )
    {
        headerFooter.setMasterPage( masterPage );
    }

    public void addMasterPage( MasterPage currentMasterPage )
    {
        if ( defaultMasterPage == null )
        {
            defaultMasterPage = currentMasterPage;
        }
        masterPagesCache.put( currentMasterPage.getName(), currentMasterPage );
    }

    public void setActiveMasterPage( String masterPageName )
    {
        MasterPage masterPage = getMasterPage( masterPageName );
        if ( masterPage != null )
        {
            setActiveMasterPage( masterPage );
        }
    }

    public MasterPage getMasterPage( String masterPageName )
    {
        if ( masterPageName == null )
        {
            return null;
        }
        return masterPagesCache.get( masterPageName );
    }

    public IITextContainer getITextContainer()
    {
        return null;
    }

    public void setITextContainer( IITextContainer container )
    {

    }

    public PageOrientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation( PageOrientation orientation )
    {
        if ( !this.orientation.equals( orientation ) )
        {
            super.getPageSize().rotate();
        }
        this.orientation = orientation;
    }
}
