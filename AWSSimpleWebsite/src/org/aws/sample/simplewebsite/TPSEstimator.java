package org.aws.sample.simplewebsite;

public class TPSEstimator extends Thread {
	private static final long tpsRefreshInterval = 10000;
	private double estimatedTPS = -1;
	private int countIntervalRequests = 0;
	private int countTotalRequests = 0;

	@Override
	public void run() {
		while (true) {
			long startTime = System.currentTimeMillis();
			try {
				sleep(tpsRefreshInterval);
			} catch (InterruptedException e) {
			}
			calculateTPS(startTime);
		}
	}

	private synchronized void calculateTPS(long startTime) {
		double interval = ((System.currentTimeMillis() - startTime) / 1000.0);
		estimatedTPS = countIntervalRequests / interval;
		countIntervalRequests = 0;
	}

	public synchronized boolean isReady() {
		return estimatedTPS != -1;
	}
	
	public synchronized void newRequest() {
		countIntervalRequests++;
		countTotalRequests++;

	}

	public synchronized double getTPS() {
		return estimatedTPS;
	}

	public synchronized int getTotalRequests() {
		return countTotalRequests;
	}
}
