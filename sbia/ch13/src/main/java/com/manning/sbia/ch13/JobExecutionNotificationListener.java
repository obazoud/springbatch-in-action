package com.manning.sbia.ch13;

import javax.management.Notification;
import javax.management.NotificationListener;

public class JobExecutionNotificationListener implements NotificationListener {

	public void handleNotification(Notification notification, Object handback) {
		System.out.println("notified");
	}

}
