package org.eclipse.om2m.sample.ipu;

import org.eclipse.om2m.commons.resource.*;
import org.eclipse.om2m.commons.rest.*;
import org.eclipse.om2m.core.service.SclService;
 
public class Monitor {
	static SclService core;
	static String sclId = System.getProperty("org.eclipse.om2m.sclBaseId", "");
	static String reqEntity = System.getProperty("org.eclipse.om2m.adminRequestingEntity", "");
	static String ipuId = "sample";
	static String actuatorId = "MY_ACTUATOR";
	static String sensorId = "MY_SENSOR";
	static boolean actuatorValue = false;
	static int sensorValue = 0;
 
	public Monitor(SclService sclService) {
		core = sclService;
	}
 
	public void start() {
                // Create required resources for the Sensor
		createSensorResources();
                // Listen for the Sensor data
		listenToSensor();
 
                // Create required resources for the Actuator
		createActuatorResources();
                // Listen for the Actuator data
		listenToActuator();
	}
 
	public void createSensorResources() {
		String targetId, content;
 
                // Create the MY_SENSOR application
		targetId = sclId + "/applications";
		ResponseConfirm response = core
				.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
						new Application(sensorId, ipuId)));
 
		if (response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
                        // Create the "DESCRIPTOR" container
			targetId = sclId + "/applications/" + sensorId + "/containers";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DESCRIPTOR")));
 
                         // Create the "DATA" container
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DATA")));
 
                         // Create the description contentInstance
			content = Mapper.getSensorDescriptorRep(sclId, sensorId, ipuId);
			targetId = sclId + "/applications/" + sensorId
					+ "/containers/DESCRIPTOR/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new ContentInstance(content.getBytes())));
 
                         // Create the data contentInstance
			content = Mapper.getSensorDataRep(sensorValue);
			targetId = sclId + "/applications/" + sensorId
					+ "/containers/DATA/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new ContentInstance(content.getBytes())));
		}
	}
 
	public void createActuatorResources() {
		String targetId, content;
 
                // Create the "MY_ACTUATOR" application
		targetId = sclId + "/applications";
		ResponseConfirm response = core.doRequest(new RequestIndication(
				"CREATE", targetId, reqEntity, new Application(actuatorId,ipuId)));
 
		if (response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
                        // Create the "DESCRIPTOR" container
			targetId = sclId + "/applications/" + actuatorId + "/containers";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DESCRIPTOR")));
 
                         // Create the "DATA" container
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,
					new Container("DATA")));
 
                        // Create the description contentInstance
			content = Mapper.getActutaorDescriptorRep(sclId, actuatorId, ipuId);
			targetId = sclId + "/applications/" + actuatorId
					+ "/containers/DESCRIPTOR/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity, 
					content));
 
                        // Create the data contentInstance
			content = Mapper.getActuatorDataRep(actuatorValue);
			targetId = sclId + "/applications/" + actuatorId
					+ "/containers/DATA/contentInstances";
			core.doRequest(new RequestIndication("CREATE", targetId, reqEntity,	
					content));
		}
	}
 
	public void listenToSensor() {
		new Thread() {
			public void run() {
				while (true) {
                                        // Simualte a random measurement of the sensor
					sensorValue = 10 + (int) (Math.random() * 100);
 
                                        // Create a data contentInstance
					String content = Mapper.getSensorDataRep(sensorValue);
					String targetID = sclId + "/applications/" + sensorId
							+ "/containers/DATA/contentInstances";
					core.doRequest(new RequestIndication("CREATE", targetID,reqEntity, 
							content));
 
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
 
	public void listenToActuator() {
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
						String targetID = sclId + "/applications/" + actuatorId
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
