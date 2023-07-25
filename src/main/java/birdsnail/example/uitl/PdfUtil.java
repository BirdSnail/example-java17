package birdsnail.example.uitl;


import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfcleanup.PdfCleaner;
import com.itextpdf.pdfcleanup.autosweep.CompositeCleanupStrategy;
import com.itextpdf.pdfcleanup.autosweep.RegexBasedCleanupStrategy;

import java.io.IOException;
import java.util.Collection;

public class PdfUtil {
    public static void main(String[] args) throws IOException {
        com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader("src/main/resources/authority_template.pdf");
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter("src/main/resources/authority_template2.pdf");
        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        replaceText(pdfDocument);
        pdfDocument.close();
    }

    /**
     * 替换字符，先进行删除，再插入
     */
    private static void replaceText(PdfDocument pdfDocument) throws IOException {
        CompositeCleanupStrategy strategy = new CompositeCleanupStrategy();
        strategy.add(new RegexBasedCleanupStrategy("代理人身份证号").setRedactionColor(ColorConstants.WHITE));
        PdfCleaner.autoSweepCleanUp(pdfDocument, strategy);
        /*使用宋体，必须以",0"结尾 */
        PdfFont font = PdfFontFactory.createFont("C:\\Windows\\Fonts\\simsun.ttc,0", "Identity-H");
        Collection<IPdfTextLocation> locations = strategy.getResultantLocations();
        for (IPdfTextLocation location : locations) {
            com.itextpdf.kernel.pdf.PdfPage page = pdfDocument.getPage(location.getPageNumber() + 1);
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), page.getDocument());
            Color fillColor = new DeviceRgb(63, 63, 63); // 灰色
            Canvas canvas = new Canvas(pdfCanvas, location.getRectangle());
            canvas.add(new Paragraph("代理人证件号").setFontSize(10.4f).setMarginTop(0).setFont(font).setFontColor(fillColor));
        }
    }


}
