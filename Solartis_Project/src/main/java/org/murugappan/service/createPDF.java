package org.murugappan.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.murugappan.DAO.CartDAO;
import org.murugappan.DAO.CartImpl;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class createPDF {
    CartDAO ci=new CartImpl();

    void createPDF(int userid) {
        ResultSet rs=ci.createPDF(userid);
        String billTo = "John Doe";
        String UserAddress = "Demo abcdefghijklmnop";
        LocalDate currentDate = LocalDate.now();
        String invoiceNo = "INV-2024-001";
        String paymentMode = "Credit Card";
        String path = "invoid.pdf";
        PdfWriter writer = null;
        Document document = null;
        try {
            writer = new PdfWriter(path);

            PdfDocument pdfDoc = new PdfDocument(writer);
            document = new Document(pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            Paragraph paragraph = new Paragraph("GSTIN:32AAHCR7467A1ZI              TaxInvoice                   OriginalRecepient");
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setBorder(new SolidBorder(1f));
            paragraph.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            document.add(paragraph);
            Paragraph paragraph2 = new Paragraph("Hello World Plaza\nNo 6 Main Street Kanchipuram Tamil Nadu India\nMobile No:9090897490,9089456787\nEmail:heloworld@admin.com");
            paragraph2.setTextAlignment(TextAlignment.CENTER);
            paragraph2.setBorder(new SolidBorder(1f));
            paragraph2.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            document.add(paragraph2);
            Table table = new Table(new float[]{1, 1});
            table.setWidth(UnitValue.createPercentValue(100));
            Paragraph billToParagraph = new Paragraph().add("Bill To:" + UserAddress);
            billToParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(billToParagraph));
            Paragraph detailsParagraph = new Paragraph()
                    .add("Date: " + currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n")
                    .add("Invoice No: " + invoiceNo + "\n")
                    .add("Payment Mode: " + paymentMode);
            detailsParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(detailsParagraph));
            document.add(table);
            Paragraph notes = new Paragraph().add("Notes:");
            notes.setTextAlignment(TextAlignment.LEFT);
            notes.setBorder(new SolidBorder(1f));
            notes.setWidth(UnitValue.createPercentValue(pageSize.getWidth()));
            document.add(notes);
            Table table1 = new Table(new float[]{3, 1, 1, 1, 2, 1, 2}); // Adjust column widths as needed
            table1.setWidth(UnitValue.createPercentValue(100));
            table1.addCell(new Cell().add("Product Name").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Quantity").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Unit Price").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Tax Percent").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Total Price Before Tax").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Tax Price").setBorder(new SolidBorder(1f)));
            table1.addCell(new Cell().add("Price Inclusive of Tax").setBorder(new SolidBorder(1f)));
            try{

            while (rs.next()){
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double priceBeforeTax = rs.getDouble("unit_price");
                int taxPercent = rs.getInt("tax_percent");
                double totalPriceBeforeTax = rs.getDouble("total_price_before_tax");
                double taxAmount = rs.getDouble("tax_amount");
                double priceInclusiveOfTax = rs.getDouble("price_inclusive_of_tax");
                table1.addCell(new Cell().add(productName));
                table1.addCell(new Cell().add(String.valueOf(quantity)));
                table1.addCell(new Cell().add(String.valueOf(priceBeforeTax)));
                table1.addCell(new Cell().add(String.valueOf(taxPercent)));
                table1.addCell(new Cell().add(String.valueOf(totalPriceBeforeTax)));
                table1.addCell(new Cell().add(String.valueOf(taxAmount)));
                table1.addCell(new Cell().add(String.valueOf(priceInclusiveOfTax)));
            }}
            catch (SQLException e){
                e.printStackTrace();
            }
            document.add(table1);
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        } finally {
            document.close();
        }

    }
}
