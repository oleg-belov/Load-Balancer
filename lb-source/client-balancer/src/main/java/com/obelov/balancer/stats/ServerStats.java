package com.obelov.balancer.stats;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerStats {

	private double cpuUtilization;

	private double jvmMemoryMax;

	private double jvmMemoryUsed;

	private int tomcatSessions;

	private int tomcatSessionsCurrent;

	public ServerStats(double cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
}
