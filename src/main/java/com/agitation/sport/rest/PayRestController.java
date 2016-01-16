package com.agitation.sport.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;

@Controller
@RequestMapping(value = "/api/v1/drp/pay")
public class PayRestController {
	
//	private static Logger logger = LoggerFactory.getLogger(PayRestController.class);
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static String appId = "app_z5uvrT4KuXL4aTmn";
	/**
	 * pingpp 管理平台对应的 API key
	 */
	public static String apiKey = "sk_live_r5qD88yz5qH0KWnDCGOmzPqP";
	
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> aliPay(@RequestParam(required=false) Map<String, Object> params) {
		
		Pingpp.apiKey = apiKey;
		Map<String, Object> result = new HashMap<String, Object>();
		if(params!=null){
			if (params.get("phoneType")==null) {
				try {
					params.put("subject", URLEncoder.encode(params.get("subject")+"", "UTF-8"));
					params.put("body", URLEncoder.encode(params.get("body")+"", "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				params.remove("phoneType");
			}
			
			Charge charge = charge(params);
			
			Map<String, Object> retData = new HashMap<String, Object>();
			
			if(charge==null){
				result.put("result", false);
				result.put("error", "支付出现问题，请稍后重试！");
			}else{
				retData.put("charge", charge.toString());
				result.put("result", true);
				result.put("retData", retData);
			}
		}else{
			result.put("result", false);
			result.put("error", "支付参数为空!");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	/**
     * 创建 Charge
     * 
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return
     */
    public Charge charge(Map<String, Object> chargeMap) {
    	
        Charge charge = null;
//        //支付金额 单位为分
//        chargeMap.put("amount", 1); 
        //三位 ISO 货币代码，人民币为 cny。
        chargeMap.put("currency", "cny"); 
//        //商品的标题，该参数最长为 32 个 Unicode 字符，银联全渠道（upacp/upacp_wap）限制在 32 个字节。
//        chargeMap.put("subject", "Your Subject");
//        //商品的描述信息，该参数最长为 128 个 Unicode 字符，yeepay_wap 对于该参数长度限制为 100 个 Unicode 字符。
//        chargeMap.put("body", "Your Body");
//        //商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。推荐使用 8-20 位，要求数字或字母，不允许特殊字符
//        chargeMap.put("order_no", "123456789");
//        //支付渠道
//        chargeMap.put("channel", "alipay");
//        //发起支付请求终端的 ip 地址
//        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);
        
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
        } catch (PingppException e) {
            e.printStackTrace();
        } 
        return charge;
    }
    
    @RequestMapping(value = "checkPayStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> checkPayStatus(@RequestParam(required=false) Map<String, Object> params) {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	if (params.get("chargeId")==null) {
    		result.put("result", false);
    		result.put("info", "chargeId不能为空！");
    		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		}
    	
    	String chargeId = params.get("chargeId")+"";
    	
    	Pingpp.apiKey = apiKey;
    	Charge checkCharge = null;
    	try {
    		checkCharge = Charge.retrieve(chargeId);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		} 
    	
    	if(checkCharge==null){
    		result.put("result", false);
    		result.put("info", "查询出现问题，请稍后重试！");
		}else{
			result.put("result", true);
			result.put("charge", checkCharge.toString());
		}
    	return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    
}
