package com.toughguy.binheSportSystem.controller.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.toughguy.binheSportSystem.model.content.Zhengcefagui;
import com.toughguy.binheSportSystem.pagination.PagerModel;
import com.toughguy.binheSportSystem.service.content.prototype.IZhengcefaguiService;

@Controller
@RequestMapping(value = "/zhengcefagui")
public class ZhengcefaguiController {
	@Autowired
	private IZhengcefaguiService zhengcefaguiService;
	
	@ResponseBody	
	@RequestMapping(value = "/save")
	@RequiresPermissions("zhengcefagui:save")
	public String saveZhengcefagui(Zhengcefagui zhengcefagui) {
		try {
			zhengcefaguiService.save(zhengcefagui);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false, \"msg\" : \"操作失败\" }";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit")
	@RequiresPermissions("zhengcefagui:edit")
	public String editZhengcefagui(Zhengcefagui zhengcefagui) {
		try {
			zhengcefaguiService.update(zhengcefagui);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "{ \"success\" : false }";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete")
	@RequiresPermissions("zhengcefagui:delete")
	public String deleteZhengcefagui(int id) {
		try {
			zhengcefaguiService.delete(id);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false, \"msg\" : \"操作失败\" }";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/data")
	//@RequiresPermissions("zhengcefagui:data")
	public String data(String params,HttpSession session) {
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(params)) {
				// 参数处理
				map = om.readValue(params, new TypeReference<Map<String, Object>>() {});
			}
			PagerModel<Zhengcefagui> pg = zhengcefaguiService.findPaginated(map);
			
			// 序列化查询结果为JSON
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", pg.getTotal());
			result.put("rows", pg.getData());
			return om.writeValueAsString(result);
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"total\" : 0, \"rows\" : [] }";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getById")
//	@RequiresPermissions("zhengcefagui:getById")
	public Zhengcefagui getById(int id) {
		return  zhengcefaguiService.find(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/findAll")
	//@RequiresPermissions("zhengcefagui:findAll")
	public List<Zhengcefagui> findAll() {
		return zhengcefaguiService.findAll();
	}
	
	@ResponseBody
	@RequestMapping(value = "/findByTitle")
	//@RequiresPermissions("zhengcefagui:findByTitle")
	public List<Zhengcefagui> findByTitle(String title){
		return zhengcefaguiService.findByTitle(title);
	}
	
}
