package org.aws.sample.simplewebsite;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This Servlet will increase the CPU consumption of an EC2 instance as the TPS increases.
 * Hence, the service can be used to demonstrate AWS's ElasticLoadBalancing and AutoScalingGroup
 * features in practice.  
 * 
 * In addition, the AWSSimpleWebsiteLoadTest tool (check my GitHut account for details) can be used 
 * to produce TPS on the AWSSimpleWebsite.
 * 
 * Details:
 * - On start-up the Servlet will randomly select a latency value from 100ms to 5000ms. This is the 
 *   time the Servlet will take to respond to a client request. During this time the Servlet will sleep
 *   for some time and consume CPU for another chunk of time. The larger the TPS the more the latency
 *   time is used towards CPU consumption. Ultimate, on high TPS values CPU consumption will reach 100%.
 *  
 */
@WebServlet("/LoadGeneratorServlet")
public class LoadGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final TPSEstimator tpsEstimator = new TPSEstimator();
	private static final int instanceId = new Random().nextInt(10000);
	private static final long latency = 100 * (new Random().nextInt(50) + 1);

	static {
		tpsEstimator.start();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		tpsEstimator.newRequest();

		CPUConsumer cpuConsumer = new CPUConsumer(new SimpleCPUConsumptionCalculator());
		int totalRequests = 0;
		double currentTPS = 0.0;
		if (tpsEstimator.isReady()) {
			currentTPS = tpsEstimator.getTPS();
			totalRequests = tpsEstimator.getTotalRequests();
			cpuConsumer.consume(latency, currentTPS);
		}

		produceResponse(totalRequests, currentTPS, response);

	}

	private void produceResponse(int totalRequests, double currentTPS, HttpServletResponse response)
			throws IOException {
		StringBuffer responseContent = new StringBuffer();
		responseContent.append("<h2>AWSSimpleWebsite - Instance Load Details:</h2>");
		responseContent.append("generated instance-id (not same as AWS instanceId): ").append(instanceId).append("<p>");
		responseContent.append("latency (ms): ").append(latency).append("<p>");
		responseContent.append("#requests processed: ").append(totalRequests).append("<p>");
		responseContent.append("#estimated TPS: ").append(currentTPS).append("<p>");
		responseContent.append("#thread-id: ").append(Thread.currentThread().getId()).append("<p>");
		response.setContentType("text/html");
		response.getWriter().println(responseContent);
		response.flushBuffer();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
