package route_esper;


import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.adapter.InputAdapter;
import com.espertech.esper.client.*;
import com.espertech.esperio.csv.*;

public class main_esper {

	public static void main(String [] args){
		
		Map<String, Object> eventProperties = new HashMap<String, Object>();
		eventProperties.put("IDROUTE", Integer.class);
		eventProperties.put("IDPLACE", Integer.class);
		eventProperties.put("TYPEPERSON", String.class);
		eventProperties.put("TYPEPLACE", String.class);
		eventProperties.put("ACTIVITY", String.class);
		eventProperties.put("NUMMEASURE", Integer.class);
		eventProperties.put("UNIT", String.class);
		eventProperties.put("TIMESTAMPEPOCH", Integer.class);
		eventProperties.put("DATEVARCHAR", String.class);
		eventProperties.put("TIMESTP", Integer.class);
		
		
		Configuration configuration = new Configuration();
		configuration.addEventType("RouteEvent", eventProperties);

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);


		EPStatement stmt = epService.getEPAdministrator().createEPL(
				   "select IRSTREAM * from RouteEvent.win:length(10)");
		
		IRListener listener = new IRListener();
		stmt.addListener(listener);
		
		CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource("route_esper2.csv"), "RouteEvent");
		spec.setTimestampColumn("TIMESTP");
		InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
		inputAdapter.start();
		
	
	}
}

