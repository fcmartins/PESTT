package domain.events;

import adt.graph.AbstractPath;
import adt.graph.Path;

public class TestRequirementChangedEvent {
	
	public final Iterable<AbstractPath<Integer>> testRequirementSet;
	public final Iterable<Path<Integer>> manuallyAdded;
	public final Iterable<AbstractPath<Integer>> infeasigles;
	public final boolean hasInfinitePath;
	
	public TestRequirementChangedEvent(Iterable<AbstractPath<Integer>> iterable, Iterable<AbstractPath<Integer>> iterable2, Iterable<Path<Integer>> manuallyAdded, boolean hasInfinitePath) {
		this.testRequirementSet = iterable;
		this.manuallyAdded = manuallyAdded;
		this.infeasigles = iterable2;
		this.hasInfinitePath = hasInfinitePath;
	}
}
