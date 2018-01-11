package org.eclipse.om2m.sample.ipu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.SclService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
 
public class Activator implements BundleActivator {
	private static Log logger = LogFactory.getLog(Activator.class);
	private ServiceTracker<Object, Object> sclServiceTracker;
 
        // Activate the plugin
	public void start(BundleContext context) throws Exception {
		logger.info("IPU started");
 
                // Register the IPU Controller service
		logger.info("Register IpuService..");
		context.registerService(IpuService.class.getName(), new Controller(), null);
		logger.info("IpuService is registered.");
 
                // Track the CORE SCL service
		sclServiceTracker = new ServiceTracker<Object, Object>(context,
				SclService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("SclService removed");
			}
 
			public Object addingService(ServiceReference<Object> reference) {
				logger.info("SclService discovered");
				SclService sclService = (SclService) this.context.getService(reference);
				final Monitor IpuMonitor = new Monitor(sclService);
				new Thread() {
					public void run() {
						try {
							IpuMonitor.start();
						} catch (Exception e) {
							logger.error("IpuMonitor error", e);
						}
					}
				}.start();
				return sclService;
			}
		};
		sclServiceTracker.open();
	}
 
        // Deactivate the plugin
	public void stop(BundleContext context) throws Exception {
		logger.info("IPU stopped");
	}
}
