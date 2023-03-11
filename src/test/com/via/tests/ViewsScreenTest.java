package com.via.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.via.ui.Elements.*;

public class ViewsScreenTest extends BaseTest {

    @Test
    public void controls() throws InterruptedException {
        goToViews();
        click(CONTROLS);
        click(LIGHT_THEME);
        type(EDIT_TEXT,"Meirav Mor");
        click(CHECK_BOX_1);
        click(RADIO_BUTTON_2);
        scrollDownAndClick(DROPDOWN);
        click(JUPITER);
        scrollUpAndClick(SAVE);
    }

    @Test()
    public void dateWidgets() throws InterruptedException {
        goToViews();
        click(DATE_WIDGETS);
        click(DIALOG);
        click(CHANGE_DATE);
        click(PREV_MONTH);
        click(FIRST_DAY);
        click(OK);
        click(CHANGE_TIME);
        click(HOURS);
        click(TIME_12);
        click(MINUTES);
        click(TIME_00);
        click(OK);
    }

    @Test
    public void grid() throws InterruptedException {
        goToViews();
        click(GRID);
        click(ICON_GRID);
        countIcons();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}