package ho.core.module;

import gui.UserParameter;
import ho.core.module.config.ModuleConfig;
import ho.module.evilcard.EvilCardModule;
import ho.module.matches.MatchesModule;
import ho.module.misc.MiscModule;
import ho.module.nthrf.NthrfModule;
import ho.module.playerOverview.PlayerOverviewModule;
import ho.module.playeranalysis.PlayerAnalysisModule;
import ho.module.series.SeriesModule;
import ho.module.specialEvents.SpecialEventsModule;
import ho.module.statistics.StatisticsModule;
import ho.module.teamAnalyzer.TeamAnalyzerModule;
import ho.module.teamOfTheWeek.TeamOfTheWeekModule;
import ho.module.training.TrainingModule;
import ho.module.transfer.TransfersModule;
import ho.module.tsforecast.TSForecastModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import de.hattrickorganizer.gui.lineup.LineupModule;

public final class ModuleManager {

	private HashMap<Integer,IModule> all_modules = new HashMap<Integer,IModule>();
	private HashMap<Integer,IModule> tmpModules;

	private static ModuleManager moduleManager; 
	
	public static final ModuleManager instance(){
		if(moduleManager == null)
			moduleManager = new ModuleManager();
		return moduleManager;
	}
	
	private ModuleManager(){
		initialize();
	}
	
	private void initMap(HashMap<Integer,IModule> map){
		map.put(Integer.valueOf(IModule.PLAYEROVERVIEW), new PlayerOverviewModule());
		map.put(Integer.valueOf(IModule.LINEUP), new LineupModule());
		map.put(Integer.valueOf(IModule.SERIES), new SeriesModule());
		map.put(Integer.valueOf(IModule.MATCHES), new MatchesModule());
		map.put(Integer.valueOf(IModule.PLAYERANALYSIS), new PlayerAnalysisModule());
		map.put(Integer.valueOf(IModule.STATISTICS), new StatisticsModule());
		map.put(Integer.valueOf(IModule.TRANSFERS ), new TransfersModule());
		map.put(Integer.valueOf(IModule.TRAINING), new TrainingModule());
		map.put(Integer.valueOf(IModule.MISC), new MiscModule());
		map.put(Integer.valueOf(IModule.TEAMANALYZER), new TeamAnalyzerModule());
		map.put(Integer.valueOf(IModule.TSFORECAST), new TSForecastModule());
		map.put(Integer.valueOf(IModule.SPECIALEVENTS), new SpecialEventsModule());
		map.put(Integer.valueOf(IModule.TEAM_OF_THE_WEEK), new TeamOfTheWeekModule());
		map.put(Integer.valueOf(IModule.NTHRF), new NthrfModule());
		map.put(Integer.valueOf(IModule.EVIL_CARD), new EvilCardModule());
	}
	
	private void initialize(){
		initMap(all_modules);
		loadModuleInfos();
	}
	
	private void copy(HashMap<Integer,IModule> from, HashMap<Integer,IModule> to){
		for (Integer key : to.keySet()) {
			to.get(key).setActive(from.get(key).isActive());
			to.get(key).setStartup(from.get(key).isStartup());
		}
	}
	
	public IModule getModule(int moduleId){
		return all_modules.get(Integer.valueOf(moduleId));
	}
	
	public IModule[] getAllModules(){
		return all_modules.values().toArray(new IModule[all_modules.size()]);
	}
	
	public IModule[] getTempModules(boolean isActive){
		if(tmpModules == null){
			tmpModules = new HashMap<Integer,IModule>();
			initMap(tmpModules);
			copy(all_modules,tmpModules);
		}
		return getModules(tmpModules, isActive);
	}
	
	public IModule[] getModules(boolean isActive){
		return getModules(all_modules, isActive);
	}
	
	IModule[] getModules(HashMap<Integer,IModule> map,boolean isActive){
		ArrayList<IModule> tmp = new ArrayList<IModule>();
		Collection<IModule> c = map.values();
		for (Iterator<IModule> iterator = c.iterator(); iterator.hasNext();) {
			IModule iModule =  iterator.next();
			if(iModule.isActive() == isActive)
				tmp.add(iModule);
		}
		return tmp.toArray(new IModule[tmp.size()]);
	}
	
	public void saveActivatedModules(){
		IModule[] modules = getModules(true);
		int[] activeIds = new int[modules.length];
		for (int i = 0; i < activeIds.length; i++) {
			activeIds[i] = modules[i].getModuleId();
			if( !modules[i].isStartup())
				activeIds[i] = activeIds[i] * -1;
		}
		ModuleConfig.instance().setIntArray("MM_activeModuleIds",activeIds);
	}
	
	private void loadModuleInfos(){
		int[] activModuleIds =ModuleConfig.instance().getIntArray("MM_activeModuleIds");
		if(activModuleIds.length == 0){
			firstStart();
			return;
		}
		for (int i = 0; i < activModuleIds.length; i++) {
			IModule module = all_modules.get(Integer.valueOf(Math.abs(activModuleIds[i])));
			module.setActive(true);
			module.setStartup(activModuleIds[i] >= 0);
		}
	}
	
	private void firstStart(){
		UserParameter p = UserParameter.instance();
		
		if(!p.tempTabAufstellung)
			getModule(IModule.LINEUP).setStartup(true);
		
		if(!p.tempTabInformation)
			getModule(IModule.MISC).setStartup(true);
		
		if(!p.tempTabLigatabelle)
			getModule(IModule.SERIES).setStartup(true);
		
		if(!p.tempTabSpiele)
			getModule(IModule.MATCHES).setStartup(true);
		
		if(!p.tempTabSpieleranalyse)
			getModule(IModule.PLAYERANALYSIS).setStartup(true);

		if(!p.tempTabSpieleruebersicht)
			getModule(IModule.PLAYEROVERVIEW).setStartup(true);
		
		if(!p.tempTabStatistik)
			getModule(IModule.STATISTICS).setStartup(true);

		getModule(IModule.TRANSFERS).setStartup(true);
		getModule(IModule.TRAINING).setStartup(true);
		getModule(IModule.TEAMANALYZER).setStartup(true);
	}
	
	public void saveTemp(){
		if(tmpModules != null)
		copy(tmpModules,all_modules);
		saveActivatedModules();
		ModuleConfig.instance().save();
	}
	
	public void clearTemp(){
		tmpModules = null;
	}

}
