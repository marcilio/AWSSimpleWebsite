package org.aws.sample.simplewebsite;

public class CPUConsumer {

	private CPUConsumptionCalculator consumptionCalculator;

	public CPUConsumer(CPUConsumptionCalculator consumptionCalculator) {
		this.consumptionCalculator = consumptionCalculator;
	}

	public void consume(long totalTime, double tps) {

		long cpuTime = consumptionCalculator.getCPUTime(tps);
		long sleepTime = consumptionCalculator.getSleepTime(tps);

		long endTime = System.currentTimeMillis() + totalTime;
		while (System.currentTimeMillis() <= endTime) {
			consume(cpuTime);
			sleep(sleepTime);
		}

	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	private void consume(long time) {
		long endTime = System.currentTimeMillis() + time;
		while (System.currentTimeMillis() <= endTime)
			;
	}

}
