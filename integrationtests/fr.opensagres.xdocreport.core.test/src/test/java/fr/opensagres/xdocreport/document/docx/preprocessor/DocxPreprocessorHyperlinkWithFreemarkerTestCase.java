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
package fr.opensagres.xdocreport.document.docx.preprocessor;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import fr.opensagres.xdocreport.core.io.IOUtils;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.DocxPreprocessor;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.hyperlinks.HyperlinkInfo;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.hyperlinks.HyperlinkUtils;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.hyperlinks.InitialHyperlinkMap;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.IDocumentFormatter;
import fr.opensagres.xdocreport.template.freemarker.FreemarkerDocumentFormatter;

public class DocxPreprocessorHyperlinkWithFreemarkerTestCase
    extends TestCase
{

    private static final String HYPERLINK_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<w:document " + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
        + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
        + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
        + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
        + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
        + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
        + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
        + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
        + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">" + "<w:tbl>" + "<w:tblPr>"
        + "<w:tblStyle w:val=\"Grilledutableau\"/>" + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
        + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>" + "<w:tblGrid>" + "<w:gridCol w:w=\"3070\"/>"
        + "<w:gridCol w:w=\"3071\"/>" + "<w:gridCol w:w=\"3071\"/>" + "</w:tblGrid>"
        + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">" + "<w:tc>" + "<w:tcPr>"
        + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>" + "<w:shd w:val=\"pct50\" w:color=\"auto\" w:fill=\"auto\"/>"
        + "</w:tcPr>" + "<w:p w:rsidR=\"005D6D71\" w:rsidRPr=\"005D6D71\" w:rsidRDefault=\"005D6D71\">" + "<w:pPr>"
        + "<w:rPr>" + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>" + "</w:rPr>" + "</w:pPr>" + "<w:r>"
        + "<w:rPr>" + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>" + "</w:rPr>" + "<w:t>Mail</w:t>"
        + "</w:r>" + "</w:p>" + "</w:tc>" + "</w:tr>" + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">" + "<w:tc>"
        + "<w:tcPr>" + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>" + "</w:tcPr>"
        + "<w:p w:rsidR=\"005D6D71\" w:rsidRDefault=\"000F2653\" w:rsidP=\"000F2653\">" + "<w:pPr>" + "<w:rPr>"
        + "<w:color w:val=\"FF0000\"/>" + "</w:rPr>" + "</w:pPr>" + "<w:hyperlink w:history=\"1\" r:id=\"rId5\">"
        + "<w:r w:rsidRPr=\"000F2653\">" + "<w:rPr>" + "<w:rStyle w:val=\"Lienhypertexte\"/>" + "</w:rPr>"
        + "<w:t>${</w:t>" + "</w:r>" + "<w:proofErr w:type=\"spellStart\"/>" + "<w:r w:rsidRPr=\"000F2653\">"
        + "<w:rPr>" + "<w:rStyle w:val=\"Lienhypertexte\"/>" + "</w:rPr>" + "<w:t>developers.mail}</w:t>" + "</w:r>"
        + "<w:proofErr w:type=\"spellEnd\"/>" + "</w:hyperlink>" + "</w:p>" + "</w:tc>" + "</w:tr>" + "</w:tbl>"
        + "</w:document>";

    public void testImageWithListFieldInTableNoFieldsMetadata()
        throws Exception
    {
        DocxPreprocessor preprocessor = new DocxPreprocessor();
        InputStream stream =
                        IOUtils.toInputStream( HYPERLINK_XML, "UTF-8" );

        StringWriter writer = new StringWriter();

        FieldsMetadata metadata = new FieldsMetadata();
        // metadata.addFieldAsList("developers.mail");
        IDocumentFormatter formatter = new FreemarkerDocumentFormatter();

        preprocessor.preprocess( "test", stream, writer, metadata, formatter, new HashMap<String, Object>() );

        assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<w:document "
            + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
            + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
            + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">" + "<w:tbl>" + "<w:tblPr>"
            + "<w:tblStyle w:val=\"Grilledutableau\"/>" + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
            + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>" + "<w:tblGrid>" + "<w:gridCol w:w=\"3070\"/>"
            + "<w:gridCol w:w=\"3071\"/>" + "<w:gridCol w:w=\"3071\"/>" + "</w:tblGrid>"
            + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">" + "<w:tc>" + "<w:tcPr>"
            + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>" + "<w:shd w:val=\"pct50\" w:color=\"auto\" w:fill=\"auto\"/>"
            + "</w:tcPr>" + "<w:p w:rsidR=\"005D6D71\" w:rsidRPr=\"005D6D71\" w:rsidRDefault=\"005D6D71\">" + "<w:pPr>"
            + "<w:rPr>" + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>" + "</w:rPr>" + "</w:pPr>"
            + "<w:r>" + "<w:rPr>" + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>" + "</w:rPr>"
            + "<w:t>Mail</w:t>" + "</w:r>" + "</w:p>" + "</w:tc>" + "</w:tr>"
            + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">" + "<w:tc>" + "<w:tcPr>"
            + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>" + "</w:tcPr>"
            + "<w:p w:rsidR=\"005D6D71\" w:rsidRDefault=\"000F2653\" w:rsidP=\"000F2653\">" + "<w:pPr>" + "<w:rPr>"
            + "<w:color w:val=\"FF0000\"/>" + "</w:rPr>" + "</w:pPr>" + "<w:hyperlink w:history=\"1\" r:id=\"rId5\">"
            + "<w:r w:rsidRPr=\"000F2653\">" + "<w:rPr>" + "<w:rStyle w:val=\"Lienhypertexte\"/>"
            + "</w:rPr>"
            // + "<w:t>${</w:t>"
            + "<w:t></w:t>" + "</w:r>" + "<w:proofErr w:type=\"spellStart\"/>" + "<w:r w:rsidRPr=\"000F2653\">"
            + "<w:rPr>" + "<w:rStyle w:val=\"Lienhypertexte\"/>" + "</w:rPr>"
            // + "<w:t>developers.mail}</w:t>"
            + "<w:t>${developers.mail}</w:t>" + "</w:r>" + "<w:proofErr w:type=\"spellEnd\"/>" + "</w:hyperlink>"
            + "</w:p>" + "</w:tc>" + "</w:tr>" + "</w:tbl>" + "</w:document>", writer.toString() );
    }

    public void testImageWithListFieldInTable()
        throws Exception
    {
        DocxPreprocessor preprocessor = new DocxPreprocessor();
        InputStream stream =
                        IOUtils.toInputStream(HYPERLINK_XML, "UTF-8" );

        StringWriter writer = new StringWriter();

        FieldsMetadata metadata = new FieldsMetadata();
        metadata.addFieldAsList( "developers.mail" );
        IDocumentFormatter formatter = new FreemarkerDocumentFormatter();

        String entryName = "word/document.xml";
        Map<String, Object> sharedContext = new HashMap<String, Object>();
        // Emulate document.rels.xml with hyperlink
        InitialHyperlinkMap hyperlinkMap = new InitialHyperlinkMap();
        hyperlinkMap.put( "rId5", new HyperlinkInfo( "rId5", "${x}", "External" ) );
        sharedContext.put( HyperlinkUtils.getHyperlinkRegistryKey( entryName ), hyperlinkMap );

        preprocessor.preprocess( entryName, stream, writer, metadata, formatter, sharedContext );

        assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                          + "<w:document "
                          + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
                          + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
                          + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
                          + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
                          + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
                          + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                          + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
                          + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
                          + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
                          + "<w:tbl>"
                          + "<w:tblPr>"
                          + "<w:tblStyle w:val=\"Grilledutableau\"/>"
                          + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
                          + "<w:tblLook w:val=\"04A0\"/>"
                          + "</w:tblPr>"
                          + "<w:tblGrid>"
                          + "<w:gridCol w:w=\"3070\"/>"
                          + "<w:gridCol w:w=\"3071\"/>"
                          + "<w:gridCol w:w=\"3071\"/>"
                          + "</w:tblGrid>"
                          + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">"
                          + "<w:tc>"
                          + "<w:tcPr>"
                          + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>"
                          + "<w:shd w:val=\"pct50\" w:color=\"auto\" w:fill=\"auto\"/>"
                          + "</w:tcPr>"
                          + "<w:p w:rsidR=\"005D6D71\" w:rsidRPr=\"005D6D71\" w:rsidRDefault=\"005D6D71\">"
                          + "<w:pPr>"
                          + "<w:rPr>"
                          + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>"
                          + "</w:rPr>"
                          + "</w:pPr>"
                          + "<w:r>"
                          + "<w:rPr>"
                          + "<w:color w:val=\"FFFFFF\" w:themeColor=\"background1\"/>"
                          + "</w:rPr>"
                          + "<w:t>Mail</w:t>"
                          + "</w:r>"
                          + "</w:p>"
                          + "</w:tc>"
                          + "</w:tr>"

                          + "[#list developers as item_developers]"

                          + "<w:tr w:rsidR=\"005D6D71\" w:rsidTr=\"005D6D71\">"
                          + "<w:tc>"
                          + "<w:tcPr>"
                          + "<w:tcW w:w=\"3071\" w:type=\"dxa\"/>"
                          + "</w:tcPr>"
                          + "<w:p w:rsidR=\"005D6D71\" w:rsidRDefault=\"000F2653\" w:rsidP=\"000F2653\">"
                          + "<w:pPr>"
                          + "<w:rPr>"
                          + "<w:color w:val=\"FF0000\"/>"
                          + "</w:rPr>"
                          + "</w:pPr>"
                          // + "<w:hyperlink w:history=\"1\" r:id=\"rId5\">"
                          + "<w:hyperlink w:history=\"1\" r:id=\"${___HyperlinkRegistryword_document_xml.registerHyperlink(\"${x}\",\"External\")}\">"
                          + "<w:r w:rsidRPr=\"000F2653\">" + "<w:rPr>"
                          + "<w:rStyle w:val=\"Lienhypertexte\"/>"
                          + "</w:rPr>"

                          // + "<w:t>${</w:t>"
                          + "<w:t></w:t>"

                          + "</w:r>" + "<w:proofErr w:type=\"spellStart\"/>" + "<w:r w:rsidRPr=\"000F2653\">"
                          + "<w:rPr>" + "<w:rStyle w:val=\"Lienhypertexte\"/>" + "</w:rPr>"

                          // + "<w:t>developers.mail}</w:t>"
                          + "<w:t>${item_developers.mail}</w:t>"

                          + "</w:r>" + "<w:proofErr w:type=\"spellEnd\"/>" + "</w:hyperlink>" + "</w:p>" + "</w:tc>"
                          + "</w:tr>"

                          + "[/#list]"

                          + "</w:tbl>" + "</w:document>", writer.toString() );
    }
}
