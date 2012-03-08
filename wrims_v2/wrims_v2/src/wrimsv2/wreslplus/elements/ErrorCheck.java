package wrimsv2.wreslplus.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;



import wrimsv2.commondata.wresldata.Dvar;
import wrimsv2.commondata.wresldata.ModelDataSet;
import wrimsv2.commondata.wresldata.Param;
import wrimsv2.commondata.wresldata.StudyDataSet;
import wrimsv2.commondata.wresldata.Timeseries;
import wrimsv2.wreslparser.elements.LogUtils;

public class ErrorCheck {
	
	
	private ErrorCheck(){}

	
	public static int checkStudy (StudyTemp s){
		
		// check modelList itself
		
		for (String k: s.modelList){
			
			ModelTemp m = s.modelMap.get(k);
			
			checkVarRedefined(m);
			
		}
		
		return 0;

	}	

	public static int checkVarRedefined (ModelTemp m){
		
		// check dvar list duplicates
		ArrayList<String> dvDup = findDuplicates(m.dvList);
		
		if (dvDup.size()>0) {
			m.dvList = removeDuplicates(m.dvList);
		
			for (String s: dvDup){
				LogUtils.errMsg("Dvar redefined: "+s+" in file: unknown");
			}
		}
		
	
		// check svar list duplicates
		ArrayList<String> svDup = findDuplicates(m.svList);
		
		if (svDup.size()>0) {
			m.svList = removeDuplicates(m.svList);
		
			for (String s: svDup){
				LogUtils.errMsg("Svar redefined: "+s+" in file: unknown");
			}
		}
		
	
		
		return 0;
	
	}


	public static ArrayList<String> findDuplicates(ArrayList<String> a){
		
		ArrayList<String> duplicates = new ArrayList<String>(a);
		Set<String> varSet = new LinkedHashSet<String>();
		
		varSet.addAll(a);
		
		for (String s: varSet) {
			duplicates.remove(s);
		}
		
		return duplicates;
	}
	
	public static ArrayList<String> removeDuplicates(ArrayList<String> a){

		Set<String> varSet = new LinkedHashSet<String>();
		
		varSet.addAll(a);
		
		return new ArrayList<String>(varSet);

	}

}
	