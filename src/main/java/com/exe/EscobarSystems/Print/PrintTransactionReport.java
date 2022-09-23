package com.exe.EscobarSystems.Print;


import com.exe.EscobarSystems.Transaction.TransactionDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PrintTransactionReport {

    public void print(List<TransactionDto> transactions, String accountName){
        Document doc = new Document();
        PdfWriter docWriter = null;
        String currentDate = java.time.LocalDate.now().toString();

        try{
            Font textBilledTo = new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD, new BaseColor(0, 0, 0));
            Font textBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font text12 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(0, 0, 0));
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream("Report.pdf"));
            doc.open();
            int pdfWidth = 9;
            float perWidth = 100/pdfWidth;

            float[] columnWidths = new float[pdfWidth];
            for (int x = 0; x < pdfWidth; x++){
                columnWidths[x] = (float)perWidth;
            }

            PdfPTable texts = new PdfPTable(columnWidths);

            Paragraph paragraph = new Paragraph();

            createcell(texts, "Date Issued: " + currentDate, Element.ALIGN_RIGHT, pdfWidth, text12, 0, 255, 255, 255);
            createcell(texts, "REPORT", Element.ALIGN_CENTER, pdfWidth, textBilledTo, 0, 255, 255, 255);

            createcell(texts, "___________________________________________________________________________________________________________________", Element.ALIGN_CENTER, pdfWidth, text12, 0, 255, 255, 255);
            createcell(texts, String.format("PRINTED BY %s", accountName), Element.ALIGN_LEFT, pdfWidth, textBold12, 0, 255, 255, 255);
            createSpace(texts, pdfWidth);
            createcell(texts, "Id", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Date", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Supply", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Supplier", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Quantity", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Measurement", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Price", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "User", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createcell(texts, "Type", Element.ALIGN_CENTER, 1, text12, 0, 179, 179, 179);
            createSpace(texts, pdfWidth);
            transactions
                    .stream()
                    .forEach((transaction)-> {
                        createcell(texts, "___________________________________________________________________________________________________________________", Element.ALIGN_CENTER, pdfWidth, text12, 0, 255, 255, 255);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        createcell(texts, transaction.getTransactionId().toString(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getTransactionDate().format(formatter), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getSupplyName(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getSupplierName(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getSupplyQuantity().toString(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getUnitOfMeasurementAbbreviation(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getPricePerUnit().toString(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getTransactByName(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);
                        createcell(texts, transaction.getTransactionType().toString(), Element.ALIGN_CENTER, 1, text12, 0, 255, 255, 255);

                    });

            createcell(texts, "___________________________________________________________________________________________________________________", Element.ALIGN_CENTER, pdfWidth, text12, 0, 255, 255, 255);

            texts.setWidthPercentage(100f);

            paragraph.add(texts);
            doc.add(paragraph);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        if (doc != null){
            doc.close();
        }

        if (docWriter != null){
            docWriter.close();
        }

        try{
            Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "Report.pdf");
        }
        catch(Exception ex){
        }
    }

    private void createcell(PdfPTable table, String text, int align, int columnSpan, Font font, int border, int r, int g, int b){
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setHorizontalAlignment(align);
        cell.setColspan(columnSpan);
        if (border == 0){
            cell.setBorder(Rectangle.NO_BORDER);
        }
        else{
            cell.setBorder(Rectangle.BOX);
        }
        cell.setBackgroundColor(new BaseColor(r, g, b));
        table.addCell(cell);
    }

    public void createSpace(PdfPTable table, int pdfWidth){
        PdfPCell cell = new PdfPCell();
        cell.setColspan(pdfWidth);
        cell.setMinimumHeight(15f);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}
