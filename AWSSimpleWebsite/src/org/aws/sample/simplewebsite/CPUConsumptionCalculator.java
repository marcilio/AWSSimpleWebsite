package org.aws.sample.simplewebsite;

public interface CPUConsumptionCalculator {

	public long getCPUTime(double currentTPS);

	public long getSleepTime(double currentTPS);
}
