package com.boot.util;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

public class AsianFontProvider extends XMLWorkerFontProvider {
    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        String fntname = fontname;
        if (fntname == null) {
            fntname = "/static/fonts/simsun.ttc";
        }
        if (size == 0) {
            size = 2;
        }
        return super.getFont(fntname, encoding, size, style);
    }


}
