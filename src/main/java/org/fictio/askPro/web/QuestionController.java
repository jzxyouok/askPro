package org.fictio.askPro.web;

import org.fictio.askPro.aop.annotation.UserAccess;
import org.fictio.askPro.pojo.Question;
import org.fictio.askPro.pojo.RequestData;
import org.fictio.askPro.pojo.ResponseData;
import org.fictio.askPro.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping(value="/quest",method=RequestMethod.POST)
public class QuestionController {
	
	private static Logger log = LoggerFactory.getLogger(QuestionController.class);
	private static Gson gson = new Gson();
	@Autowired
	private QuestionService questionService;
	@RequestMapping("/create")
	@UserAccess
	public String createQuestion(@RequestBody RequestData<Question> req){
		ResponseData<String> result = new ResponseData<>();
		String userName = req.getUserName();
		if(req.getObj() != null){
			result = questionService.createQuestion(req.getObj(),userName);
		}else{
			result.setMessage("参数不能为空");
			log.info("req为空");
		}
		return gson.toJson(result);
	}
	@RequestMapping("/update")
	@UserAccess
	public String updateQuestion(@RequestBody RequestData<Question> req){
		ResponseData<String> result = new ResponseData<>();
		if(req.getObj() != null){
			result = questionService.updateQuestion(req.getObj());
		}else{
			log.error(result.getMessage());
			result.setMessage("参数不能为空");
		}
		return gson.toJson(result);
	}
	
	@RequestMapping("/get")
	@UserAccess
	public String getQuestionById(@RequestBody RequestData<Integer> req){
		ResponseData<Question> result = new ResponseData<>();
		result = questionService.getQuestionById(req.getObj(),req.getUserName());
		return gson.toJson(result);
	}

}
