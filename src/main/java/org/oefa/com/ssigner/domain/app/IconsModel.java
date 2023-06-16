package org.oefa.com.ssigner.domain.app;

import io.github.palexdev.mfxresources.fonts.IconDescriptor;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeBrands;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeRegular;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeSolid;

public class IconsModel {
    public static final IconDescriptor[] notificationsIcons;

    static {
        notificationsIcons = new IconDescriptor[]{
                FontAwesomeSolid.BELL, FontAwesomeRegular.BELL,
                FontAwesomeSolid.CALENDAR, FontAwesomeSolid.CALENDAR_DAYS,
                FontAwesomeSolid.CHART_PIE, FontAwesomeSolid.CIRCLE, FontAwesomeRegular.CIRCLE,
                FontAwesomeSolid.CIRCLE_EXCLAMATION, FontAwesomeSolid.TRIANGLE_EXCLAMATION,
                FontAwesomeSolid.GEAR, FontAwesomeBrands.GOOGLE_DRIVE, FontAwesomeSolid.HOUSE,
                FontAwesomeSolid.CIRCLE_INFO, FontAwesomeSolid.MUSIC,
                FontAwesomeSolid.USER, FontAwesomeSolid.USERS, FontAwesomeSolid.VIDEO,
                FontAwesomeSolid.CIRCLE_XMARK
        };
    }
}
