package com.rekoe.quartz.job;

import org.nutz.integration.quartz.annotation.Scheduled;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rekoe.service.ServerCacheService;

@IocBean
@Scheduled(cron = "0 */10 * * * ?")
public class ReloadServerJob implements Job {

	@Inject
	protected ServerCacheService serverCacheService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			//serverCacheService.loadWeb();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
		try {
			//serverCacheService.loadMobileChannel();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
		try {
			//serverCacheService.loadMobileServers();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}
}
