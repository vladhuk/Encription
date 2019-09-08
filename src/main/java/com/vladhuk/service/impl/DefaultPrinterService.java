package com.vladhuk.service.impl;

import com.vladhuk.service.PrinterService;

import java.awt.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class DefaultPrinterService implements PrinterService {

    @Override
    public void print(String text) {
        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            graphics.drawString(text, 100, 100);

            return Printable.PAGE_EXISTS;
        });

        boolean isConfirmed = job.printDialog();
        if (isConfirmed) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
