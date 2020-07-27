package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class HazelcastCacheService {

	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");
	private static Logger LOG = Logger.getLogger(HazelcastCacheService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String cachename = ConfigProvider.getConfig().getValue("hazelcast.cache.name", String.class);

	public MsgBean setMsg(HazelcastInstance client) {
		MsgBean msgbean = errormsg;
		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Set");
			Map<Integer, String> map = client.getMap(cachename);
			map.put(msgbean.getId(), msgbean.getMessage());
		} catch (IllegalStateException | NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "Set Error.", e);
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(HazelcastInstance client) {
		List<MsgBean> msglist = getMsgList(client);
		return msglist.get(msglist.size() - 1);

	}

	public List<MsgBean> getMsgList(HazelcastInstance client) {
		List<MsgBean> msglist = new ArrayList<>();

		try {
			Map<Integer, String> map = client.getMap(cachename);

			for (Entry<Integer, String> entry : map.entrySet()) {
				MsgBean msgbean = new MsgBean(entry.getKey(), entry.getValue(), "Get");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean);
			}
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			e.printStackTrace();
		} finally {
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Get"));
			}
			if (client != null) {
				client.shutdown();
			}
		}
		return msglist;
	}

	public boolean isActive() {
		boolean status = false;
		HazelcastInstance client = null;
		try {
			client = createHazelcastInstance();
			status = client.getLifecycleService().isRunning();
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}

	public HazelcastInstance createHazelcastInstance() {
		return HazelcastService.getInstance();
	}
}
