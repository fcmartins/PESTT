package coverage;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;

import constants.Colors_ID;
import constants.Description_ID;

public class CoverageData implements ICoverageData{

	private IClassCoverage classCoverage;
	private HashMap<Integer, String> lineStatus;
	
	public CoverageData(IClassCoverage classCoverage) {
		this.classCoverage = classCoverage;
		lineStatus = new LinkedHashMap<Integer, String>();
		setLineStatus();
	}
	
	public HashMap<Integer, String> getLineStatus() {
		return lineStatus;
	}
	
	public String getLineStatus(int line) {
		return lineStatus.get(line);
	}
	
	private void setLineStatus() {
		for(int i = classCoverage.getFirstLine(); i <= classCoverage.getLastLine(); i++)
			lineStatus.put(Integer.valueOf(i), getColor(classCoverage.getLine(i).getStatus()));		
	}
	
	private String getColor(final int status) {
		switch (status) {
		case ICounter.NOT_COVERED:
			return Colors_ID.RED_ID;
		case ICounter.PARTLY_COVERED:
			return Colors_ID.GRENN_ID;
		case ICounter.FULLY_COVERED:
			return Colors_ID.GRENN_ID;
		}
		return Description_ID.EMPTY;
	}
}
