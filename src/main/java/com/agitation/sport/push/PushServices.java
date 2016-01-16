package com.agitation.sport.push;

import com.agitation.sport.push.android.AndroidCustomizedcast;
import com.agitation.sport.push.android.AndroidUnicast;
import com.agitation.sport.push.ios.IOSCustomizedcast;
import com.agitation.sport.push.ios.IOSUnicast;
import com.agitation.sport.util.Constants;

/**
 * 推送服务
 * @author Fanxl
 *
 */
public class PushServices {
	
	private static PushClient client = new PushClient();
	
	/**
	 * 发送单个消息到指定用户通过DeviceToken
	 * @param deviceToken AohJMGGcWVfiIlTe36AwotjIBI74rIMTCDXvd7g8jKXG
	 * @param title
	 * @param text
	 * @param ticker
	 * @throws Exception
	 */
	public static void sendMsgToAndroidUnicast(String deviceToken, String title, String text, String ticker) throws Exception{
		
		AndroidUnicast unicast = new AndroidUnicast(Constants.PUSH_ANDROID_APP_KEY, Constants.PUSH_ANDROID_APP_MASTER_SECRET);
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(text);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
//		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
	/**
	 * IOS发送单个消息到指定用户通过DeviceToken
	 * @param deviceToken AohJMGGcWVfiIlTe36AwotjIBI74rIMTCDXvd7g8jKXG
	 * @param title
	 * @param text
	 * @param ticker
	 * @throws Exception
	 */
	public static void sendMsgToIOSUnicast(String deviceToken, String alert) throws Exception{
		IOSUnicast unicast = new IOSUnicast(Constants.PUSH_IOS_APP_KEY, Constants.PUSH_IOS_APP_MASTER_SECRET);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(alert);
		unicast.setBadge(0);
		unicast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setProductionMode();
		// Set customized fields
//		unicast.setCustomizedField("h", "highyundong://com.highyundong.sport?pageid=1&value");
		client.send(unicast);
	}
	
	/**
	 * 发送单个消息到指定用户通过alias
	 * @param alias
	 * @param title
	 * @param text
	 * @param ticker
	 * @throws Exception
	 */
	public static void sendMsgToAndroidCustomCast(String alias, String title, String text, 
			String ticker, boolean toUser) throws Exception{
		
		AndroidCustomizedcast customizedcast;
		if(toUser){
			customizedcast = new AndroidCustomizedcast(Constants.PUSH_ANDROID_APP_KEY, Constants.PUSH_ANDROID_APP_MASTER_SECRET);
		}else{
			customizedcast = new AndroidCustomizedcast(Constants.PUSH_SELLER_ANDROID_APP_KEY, Constants.PUSH_SELLER_ANDROID_MASTER_SECRET);
		}
				
		customizedcast.setAlias(alias, Constants.PUSH_ALIAS_TYPE);
		customizedcast.setTicker(ticker);
		customizedcast.setTitle(title);
		customizedcast.setText(text);
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	/**
	 * 发送单个消息到指定用户通过alias
	 * @param alias
	 * @param title
	 * @param text
	 * @param ticker
	 * @throws Exception
	 */
	public static void sendMsgToIOSCustomCast(String alias, String alert) throws Exception{
		
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(Constants.PUSH_IOS_APP_KEY, Constants.PUSH_IOS_APP_MASTER_SECRET);
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias(alias, Constants.PUSH_ALIAS_TYPE);
		customizedcast.setAlert(alert);
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setProductionMode();;
		client.send(customizedcast);
	}

}
