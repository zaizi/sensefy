package com.zaizi.sensefy.sensefyui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class) 
@SuiteClasses({ 
	aLoginTest.class,
	bSearchTest.class,
	cSortTest.class,
	dResultsPerPage.class,
	eLanguageChangeTest.class,
	fFacetsTest.class,
	gSearchPageTest.class,
	lEntitySearch.class,
	mVideoSearch.class,
	nResultsperpage.class,
	oAudioSearch.class
})
public class testSuite 
{
	
}
