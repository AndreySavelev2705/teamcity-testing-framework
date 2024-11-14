package com.example.teamcity.ui.pages.setup;

import com.example.teamcity.ui.BaseUiTest;
import org.testng.annotations.Test;

public class SetupServerTest extends BaseUiTest {
    @Test(groups = {"Setup"})
    public void setupTeamCityServerTest(){
        FirstStartPage.open().setupFirstStart();
    }
}
