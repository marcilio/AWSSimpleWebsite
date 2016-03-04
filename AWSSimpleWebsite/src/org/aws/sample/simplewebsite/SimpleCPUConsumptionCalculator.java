package org.aws.sample.simplewebsite;
/**
 * 1 TPS = 10% vcpu consumption, 90% sleeping
 * 2 TPS = 20% vcpu consumption, 80% sleeping
 * 3 TPS = 30% vcpu consumption, 70% sleeping
 * ...
 */
public class SimpleCPUConsumptionCalculator implements CPUConsumptionCalculator {

	private static final long TIME_BLOCK = 100;
	private static final long TIME_INCREMENT = 10;
	
	@Override
	public long getCPUTime(double currentTPS) {
		double tps = currentTPS <= 10 ? currentTPS : 10;
		return Math.round(tps * TIME_INCREMENT);
	}

	@Override
	public long getSleepTime(double currentTPS) {
		return TIME_BLOCK - getCPUTime(currentTPS);
	}

}
