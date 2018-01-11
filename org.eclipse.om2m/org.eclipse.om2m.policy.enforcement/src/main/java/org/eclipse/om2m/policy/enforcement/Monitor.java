package org.eclipse.om2m.policy.enforcement;

import org.eclipse.om2m.commons.resource.*;
import org.eclipse.om2m.commons.rest.*;
import org.eclipse.om2m.core.service.SclService;

public class Monitor {
	static SclService core;
	static String sclId = System.getProperty("org.eclipse.om2m.sclBaseId", "");
	static String reqEntity = System.getProperty("org.eclipse.om2m.adminRequestingEntity", "");
	static String ipuId = "sample";
	static String RESPId = "POLICY_ENFORCEMENT_RESP";
	static String REQId = "POLICY_ENFORCEMENT_REQ";
	static boolean actuatorValue = false;
	static int sensorValue = 0;

	public Monitor(SclService sclService) {
		core = sclService;
	}

	public void start() {
               // Create required resources for the Sensor
		createREQResources();
               // Listen for the Sensor data
		//listenToREQ();

               // Create required resources for the Actuator
		createRESPResources();
               // Listen for the Actuator data
		//listenToRESP();
	}

	public void createREQResources() {
		String targetId, content;

               // Create the MY_SENSOR application
		targetId = sclId + "/applications";
		ResponseConfirm response = core
				.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
						new Application(REQId, ipuId)));

		if (response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
                       // Create the "DESCRIPTOR" container
			targetId = sclId + "/applications/" + REQId + "/containers";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DESCRIPTOR")));

                        // Create the "DATA" container
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DATA")));

                        // Create the description contentInstance
			content = Mapper.getSensorDescriptorRep(sclId, REQId, ipuId);
			targetId = sclId + "/applications/" + REQId
					+ "/containers/DESCRIPTOR/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new ContentInstance(content.getBytes())));

                        // Create the data contentInstance
			content = Mapper.getSensorDataRep(sensorValue);
			targetId = sclId + "/applications/" + REQId
					+ "/containers/DATA/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new ContentInstance(content.getBytes())));
		}
	}

	public void createRESPResources() {
		String targetId, content;

               // Create the "MY_ACTUATOR" application
		targetId = sclId + "/applications";
		ResponseConfirm response = core.doRequest(new RequestIndication(
				"CREATE", targetId, reqEntity, new Application(RESPId,ipuId)));

		if (response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
                       // Create the "DESCRIPTOR" container
			targetId = sclId + "/applications/" + RESPId + "/containers";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DESCRIPTOR")));

                        // Create the "DATA" container
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DATA")));

                       // Create the description contentInstance
			content = Mapper.getActutaorDescriptorRep(sclId, RESPId, ipuId);
			targetId = sclId + "/applications/" + RESPId
					+ "/containers/DESCRIPTOR/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity, 
					content));

                       // Create the data contentInstance
			content = Mapper.getActuatorDataRep(actuatorValue);
			targetId = sclId + "/applications/" + RESPId
					+ "/containers/DATA/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,	
					content));
		}
	}

	public void listenToREQ() {
		new Thread() {
			public void run() {
                // Simualte a random measurement of the sensor
				sensorValue = 10 + (int) (Math.random() * 100);

                 // Create a data contentInstance
				String content = Mapper.getSensorDataRep(sensorValue);
				String targetID = sclId + "/applications/" + REQId
						+ "/containers/DATA/contentInstances";
				core.doRequest(new RequestIndication("CREATE", targetID,reqEntity,content));
			}
		}.start();
	}

	public void listenToRESP() {
		new Thread() {
			public void run() {

				boolean memorizedActuatorValue = false;
				while (true) {
                                       // If the Actuator state has changed
					if (memorizedActuatorValue != actuatorValue) {
                                               // Memorize the new Actuator state
						memorizedActuatorValue = actuatorValue;

                                               // Create a data contentInstance
						String content = Mapper.getActuatorDataRep(actuatorValue);
						String targetID = sclId + "/applications/" + RESPId
								+ "/containers/DATA/contentInstances";
						core.doRequest(new RequestIndication("CREATE",targetID, reqEntity, 
								content));
					}

                                       // Wait for 2 seconds then loop
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}