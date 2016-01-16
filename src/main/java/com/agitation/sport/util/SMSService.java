package com.agitation.sport.util;

import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 短信发送服务类
 * @author Fanxl
 *
 */
public class SMSService {
	
	/**
	 * 课程确认成功的短信
	 * @param phoneNumber 收信人的电话号码
	 * @param name 购买人的名字
	 * @param courseName 购买的课程名字
	 * @param time 课程的上课时间
	 * @return 是否成功
	 */
	public static boolean confirmCourseSMS(String phoneNumber, String name, String courseName, String time){
		boolean success = false;
		String smsParam = "{'name':'"+ name +"','courseName':'"+courseName+"','time':'"+time+"'}";
		TaobaoClient client = new DefaultTaobaoClient(Constants.SMS_APP_URL, Constants.SMS_APP_KEY, Constants.SMS_APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("High运动");
		req.setSmsTemplateCode("SMS_3605201");
		req.setSmsParam(smsParam);
		req.setRecNum(phoneNumber);
		
		try {
			AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
			Map<String, Object> smsResult = JsonConvertTool.str2Map(response.getBody());
			if(smsResult!=null){
				
				@SuppressWarnings("unchecked")
				Map<String, Object> responseResult = (Map<String, Object>) smsResult.get("alibaba_aliqin_fc_sms_num_send_response");
				@SuppressWarnings("unchecked")
				Map<String, Object> sendResult = (Map<String, Object>) responseResult.get("result");
				if(Boolean.parseBoolean(sendResult.get("success")+"")){
					success = true;
				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	@SuppressWarnings("unchecked")
	public static void sendCode(String action, String phoneNumber, Map<String, Object> result){
		
		String code = Math.round(Math.random()*899999+100000)+"";
		String smsParam = "{'code':'"+ code +"','product':'High运动'}";
		
		TaobaoClient client = new DefaultTaobaoClient(Constants.SMS_APP_URL, Constants.SMS_APP_KEY, Constants.SMS_APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("High运动");
		
		if("register".equals(action)){
			req.setSmsTemplateCode("SMS_3095594");
		}else if("forgetPw".equals(action)){
			req.setSmsTemplateCode("SMS_3095592");
		}
		req.setSmsParam(smsParam);
		req.setRecNum(phoneNumber);
		
		try {
			AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
			Map<String, Object> smsResult = JsonConvertTool.str2Map(response.getBody());
			if(smsResult!=null){
				result.put("result", true);
				Map<String, Object> responseResult = (Map<String, Object>) smsResult.get("alibaba_aliqin_fc_sms_num_send_response");
				Map<String, Object> sendResult = (Map<String, Object>) responseResult.get("result");
				if(Boolean.parseBoolean(sendResult.get("success")+"")){
					result.put("result", true);
					result.put("code", code);
				}else{
					result.put("result", false);
					result.put("error", "发送失败");
				}
			}else{
				result.put("result", false);
				result.put("error", "发送失败");
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	

}
