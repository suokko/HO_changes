package de.hattrickorganizer.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import ho.core.model.match.IMatchDetails;
import ho.module.lineup.LoddarStatsCalculator;

import org.junit.Test;


public class LoddarStatsCalculatorTest {

	@Test
	public void shouldCalculateLoddarStatsForReportedBug() throws Exception {
		LoddarStatsCalculator calculator = new LoddarStatsCalculator();
		calculator.setRatings(9.89, 7.69, 7.01, 7.93, 8.48, 4.58, 8.13);
		calculator.setTactics(IMatchDetails.TAKTIK_WINGS, 14.0f, 0.0f);
		assertThat((double) calculator.calculate(), is(closeTo(25.6185353011d,
				0.001d)));
	}

	@Test
	public void shouldCalculateLoddarStatsForNormalTactic() throws Exception {
		LoddarStatsCalculator calculator = new LoddarStatsCalculator();
		calculator.setRatings(9.89, 7.69, 7.01, 7.93, 8.48, 4.58, 8.13);
		calculator.setTactics(IMatchDetails.TAKTIK_NORMAL, 14.0f, 0.0f);
		assertThat((double) calculator.calculate(), is(closeTo(23.6417583662d,
				0.001d)));
	}

	@Test
	public void shouldCalculateLoddarStatsForAiMTactic() throws Exception {
		LoddarStatsCalculator calculator = new LoddarStatsCalculator();
		calculator.setRatings(9.89, 7.69, 7.01, 7.93, 8.48, 4.58, 8.13);
		calculator.setTactics(IMatchDetails.TAKTIK_MIDDLE, 14.0f, 0.0f);
		assertThat((double) calculator.calculate(), is(closeTo(21.6649814313d,
				0.001d)));
	}

	@Test
	public void shouldCalculateLoddarStatsForCATactic() throws Exception {
		LoddarStatsCalculator calculator = new LoddarStatsCalculator();
		calculator.setRatings(9.89, 7.69, 7.01, 7.93, 8.48, 4.58, 8.13);
		calculator.setTactics(IMatchDetails.TAKTIK_KONTER, 14.0f, 10.0f);
		assertThat((double) calculator.calculate(), is(closeTo(27.4178270502d,
				0.001d)));
	}
}
