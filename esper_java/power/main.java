package power_esper;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.adapter.InputAdapter;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.csv.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.espertech.esperio.csv.CSVInputAdapterSpec;

import power_esper.IRListener;

public class main {
	
public static void main(String [] args){
		
		Map<String, Object> eventProperties = new HashMap<String, Object>();
		eventProperties.put("IDHOME", Integer.class);
		eventProperties.put("IDSTREET", Integer.class);
		eventProperties.put("IDDISTRICT", Integer.class);
		eventProperties.put("IDDEVICE", Integer.class);
		eventProperties.put("TYPEDEVICE", String.class);
		eventProperties.put("POWERCONSUM", Integer.class);
		eventProperties.put("STATUSDEVICE", String.class);
		eventProperties.put("TIMESTAMPEPOCH", Integer.class);
		eventProperties.put("DATEVARCHAR", String.class);
		eventProperties.put("TIMESTP", Integer.class);
		
		
		Configuration configuration = new Configuration();
		configuration.addEventType("PowerEvent", eventProperties);

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);


		EPStatement stmt = epService.getEPAdministrator().createEPL(
				   "select IRSTREAM * from PowerEvent.win:length(10)");
		
		IRListener listener = new IRListener();
		stmt.addListener(listener);
		
		CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource("power_esper.csv"), "PowerEvent");
		spec.setTimestampColumn("TIMESTP");
		InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
		inputAdapter.start();
		
	
	}

}
