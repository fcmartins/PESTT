package domain.events;

import domain.constants.TourType;

public class TestPathSelectedTourEvent {

	public TourType selectedTourType;
	
	public TestPathSelectedTourEvent(TourType selectedTourType) {
		this.selectedTourType = TourType.TOUR;
	}

}
