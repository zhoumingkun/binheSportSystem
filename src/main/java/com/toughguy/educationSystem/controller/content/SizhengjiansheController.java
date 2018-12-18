package com.toughguy.educationSystem.controller.content;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toughguy.educationSystem.model.content.Guizhangzhidu;
import com.toughguy.educationSystem.model.content.Sizhengjianshe;
import com.toughguy.educationSystem.pagination.PagerModel;
import com.toughguy.educationSystem.service.content.prototype.ISizhengjiansheService;
import com.toughguy.educationSystem.util.BackupUtil;
import com.toughguy.educationSystem.util.UploadUtil;

@Controller
@RequestMapping(value = "/sizhengjianshe")
public class SizhengjiansheController {
	@Autowired
	private ISizhengjiansheService sizhengjiansheService;
	
	@ResponseBody	
	@RequestMapping(value = "/save")
	//@RequiresPermissions("sizhengjianshe:save")
	public String saveSizhengjianshe1(Sizhengjianshe sizhengjianshe) {
		try {
			sizhengjiansheService.save(sizhengjianshe);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false, \"msg\" : \"操作失败\" }";
		}
	}
	
	@ResponseBody	
	@RequestMapping(value = "/uploadImage")
	//@RequiresPermissions("sizhengjianshe:uploadImage")
	public String uploadImage(MultipartFile pictureFile) {
		if(UploadUtil.isPicture(pictureFile.getOriginalFilename())){
			try {
			 String path = UploadUtil.uploadPicture(pictureFile);
			 return "{ \"success\" : true ,\"msg\" :\""+ path +"\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{ \"success\" : false ,\"msg\" : \"上传失败\"}";
			}
		}else{
			return "{ \"success\" : false , \"msg\" : \"请上传正确图片格式的图片\"}";
		}	
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit")
	//@RequiresPermissions("sizhengjianshe:edit")
	public String editSizhengjianshe1(Sizhengjianshe sizhengjianshe) {
		try {
			 sizhengjiansheService.update(sizhengjianshe);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "{ \"success\" : false }";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete")
	//@RequiresPermissions("sizhengjianshe:detele")
	public String deleteSizhengjianshe(int id) {
		try {
			sizhengjiansheService.delete(id);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false, \"msg\" : \"操作失败\" }";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/data")
	//@RequiresPermissions("content:data")
	public String data(String params,HttpSession session) {
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(params)) {
				// 参数处理
				map = om.readValue(params, new TypeReference<Map<String, Object>>() {});
			}
			PagerModel<Sizhengjianshe> pg = sizhengjiansheService.findPaginated(map);
			
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
	/**
	 * 这个只用于小程序文档访问使用（统计浏览量）
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById")
//	@RequiresPermissions("sizhengjianshe:getById")
	public Sizhengjianshe getById(int id) {
		Sizhengjianshe szjs =  sizhengjiansheService.find(id);
		szjs.setHits(szjs.getHits() + 1);
		return szjs;
	}
	
	@ResponseBody
	@RequestMapping(value = "/findAll")
	//@RequiresPermissions("sizhengjianshe:findAll")
	public List<Sizhengjianshe> findAll() {
		return sizhengjiansheService.findAll();
	}
	
	/**
	 * 图片上传
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Value("${uploadDir}")   
	private String uploadDir;
	@ResponseBody
	@RequestMapping(value = "/uploadImage" ,method = { RequestMethod.POST,RequestMethod.GET})
    public String uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入上传图片方法！");
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        //服务器路径需要换
//	        String realPath = "C:/Users/Administrator/git/reportingSystem/upload/barcode";
        String realPath = uploadDir;
        String path = BackupUtil.rename("jpg");
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file  =  new File(realPath,path);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return path;
    }
	
	@ResponseBody
	@RequestMapping(value = "/findByTitle")
	//@RequiresPermissions("activity:findByTitle")
	public List<Sizhengjianshe> findByTitle(String title){
		return sizhengjiansheService.findByTitle(title);
	}
	
	@ResponseBody
	@RequestMapping(value = "/findBySource")
	//@RequiresPermissions("activity:findByTitle")
	public List<Sizhengjianshe> findBySource(String articleSource){
		return sizhengjiansheService.findBySource(articleSource);
	}
}
