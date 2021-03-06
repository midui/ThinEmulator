package com.thinemulator.adapter;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.thinemulator.beans.AndroidEmulator;
import com.thinemulator.beans.EmulatorUser;
import com.thinemulator.beans.SpringMongoConfig;
import com.thinemulator.beans.UserBean;
import com.thinemulator.controller.DeviceConfig;

/**
 * MongoDB operation adapter class
 *
 */
public class MongoDBAdapter {
	private ApplicationContext ctx;
	private MongoOperations mongoOperation;
	 
	public MongoDBAdapter() {
		this.ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		this.mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	}
	
	public UserBean getEmulatorUser(String userId) {
		System.out.println("*** in getEmulatorUser...");
		Query searchUserQuery = new Query(Criteria.where("_id").is(userId));
		UserBean searchedUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
		return searchedUser;
	}
	
	public AndroidEmulator getDevice(String emulatorType){
		System.out.println("*** in getDevice...");
		Query searchDeviceQuery = new Query(Criteria.where("_id").is(emulatorType));
		AndroidEmulator searchedDevice = mongoOperation.findOne(searchDeviceQuery, AndroidEmulator.class);
		return searchedDevice;
	}
	
	public String addEmulator(String userId, DeviceConfig deviceConfig) {
		String opStatus;
		Query searchUserQuery = new Query(Criteria.where("_id").is(userId));
		UserBean searchedUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
		if (searchedUser.getEmulatorList().contains(deviceConfig.getDeviceName())) {
			opStatus = "device '".concat(deviceConfig.getDeviceName()).concat(" already configured.");
			System.out.println(opStatus);
		} else {
			Query updateEmulatorListQuery = new Query(Criteria.where("_id").is(userId));
			Update update = new Update();
			update.addToSet("emulatorList", deviceConfig);
			mongoOperation.updateFirst(updateEmulatorListQuery, update, UserBean.class);
			//TODO test status of db operation
			opStatus = "success";
			//System.out.println("addToSet status: "+writeResult.isUpdateOfExisting());
		}
		return opStatus;
	}
	
	public String removeEmulator(String userId, String emulatorName) {
		String opStatus = "There are no emulators to delete";
		Query searchUserQuery = new Query(Criteria.where("_id").is(userId));
		UserBean searchedUser = mongoOperation.findOne(searchUserQuery, UserBean.class);
		System.out.println(searchedUser.toString());
		System.out.println(userId);
		System.out.println(emulatorName);
		System.out.println(searchedUser.getEmulatorList());
		List<DeviceConfig> emulatorList = searchedUser.getEmulatorList();
		for(DeviceConfig deviceConfig : emulatorList) {
			System.out.println("AccessTest : "+deviceConfig.getDeviceName());
			if (deviceConfig.getDeviceName().equalsIgnoreCase(emulatorName)) {
				Update update = new Update();
				update.pull("emulatorList", deviceConfig);
				mongoOperation.updateFirst(searchUserQuery, update, UserBean.class);
				//TODO test status of db operation
				opStatus = "success";
				break;
			} else {
				opStatus = "Emulator isnot created";
				System.out.println(opStatus);
			}
		}
		
		return opStatus;
	}

/*	public void removeEmulator(String userId, String emulatorName) {
		Query updateEmulatorListQuery = new Query(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.pull("emulatorList", emulatorName);
		mongoOperation.updateFirst(updateEmulatorListQuery, update, EmulatorUser.class);
		//TODO test status of db operation
		//return true;
	}*/
}