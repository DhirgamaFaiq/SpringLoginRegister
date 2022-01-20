package com.example.demo.Login;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.example.demo.model.LoginModel;
import com.example.demo.model.UserDb;
import com.example.demo.model.users;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AppLogUtil;

@RestController
public class Login {
	@Autowired
	LoginRepository logrep;

	@Autowired
	UserRepository userrep;

	Gson gson = new Gson();

	@PostMapping(value = { "#{${login.path}}" })
	public ResponseEntity<String> login(@RequestHeader HttpHeaders headers, @RequestBody String reqbody) {
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString();
		String token = UUID.randomUUID().toString();
		users userlog = new users();

		try {
			String token_auth = headers.get("Authorization").get(0);
			userlog = gson.fromJson(reqbody, users.class);
			UserDb dataUser = userrep.findByUsername(userlog.getUsername());
			if (null != token_auth) {
				if (null != dataUser) {
					LoginModel logindata = new LoginModel();
					if (dataUser.getPassword().equals(userlog)) {
						logindata = logrep.findByUsername(userlog.getUsername());
						if (null != logindata) {
							token = logindata.getToken();
						} else {
							return new ResponseEntity<String>(token, HttpStatus.BAD_REQUEST);
						}
					} else {
						return new ResponseEntity<String>("User Not Valid", HttpStatus.BAD_REQUEST);
					}
				}else {
					return new ResponseEntity<String>("User Found", HttpStatus.BAD_REQUEST);					
				}
			}else {
				return new ResponseEntity<String>("Missing Header", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			AppLogUtil.WriteErrorLog(guid, "error for user ", e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(token, HttpStatus.OK);

	}

	@PostMapping(value = { "#{${register.path}}" })
	public ResponseEntity<String> register(@RequestHeader HttpHeaders headers, @RequestBody String reqbody) {
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString();
		String token = UUID.randomUUID().toString();
		users userlog = new users();
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calenderToday = Calendar.getInstance();
		String created = mDateFormat.format(calenderToday.getTime());
		try {
			userlog = gson.fromJson(reqbody, users.class);
			UserDb dataUser = userrep.findByUsername(userlog.getUsername());
				if(null == dataUser) {
					dataUser.setUsername(userlog.getUsername());
					dataUser.setPassword(userlog.getPassword());
					dataUser.setCreated(created);
					dataUser.setModified(created);
					userrep.save(dataUser);
				}else {
					return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);
				}
				
		} catch (Exception e) {
			AppLogUtil.WriteErrorLog(guid, "error for user ", e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Successful", HttpStatus.OK);

	}
}
