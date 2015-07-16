package com.zaizi.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Controller
public class WhitelabelErrorController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error(Model model) {
		model.addAttribute("timestamp", "12345678");
		model.addAttribute("error", "error type");
		model.addAttribute("status", "status of the error");
		model.addAttribute("message", "description of the error");
		return "error/error";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
