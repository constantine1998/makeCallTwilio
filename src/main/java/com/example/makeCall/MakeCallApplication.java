package com.example.makeCall;

import com.twilio.http.TwilioRestClient;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.twilio.twiml.voice.Say.Language.EN_US;

@SpringBootApplication
@RestController
public class MakeCallApplication {
	// Find your Account Sid and Token at twilio.com/console
	// and set the environment variables. See http://twil.io/secure

	public static void main(String[] args) {
		SpringApplication.run(MakeCallApplication.class, args);
	}
	private final static String ACCOUNT_SID = "Use AccountSID";
	private final static String AUTH_ID = "Use AuthID";

	@RequestMapping(value="/voice-note", method= RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> getVoiceNote() throws TwiMLException {
		String otp = "12345";
		Say say = new Say.Builder("Your OTP is: "+otp).voice(Say.Voice.WOMAN).language(EN_US).build();
		VoiceResponse response = new VoiceResponse.Builder().say(say).build();
		return new ResponseEntity<>(response.toXml(), HttpStatus.OK);
	}

	@RequestMapping(value="/calls", method=RequestMethod.POST)
	public ResponseEntity<Object> makeCall() {

		TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_ID).build();
		PhoneNumber to_number = new PhoneNumber("+918919288297");
		PhoneNumber from_number = new PhoneNumber("+12513224608");
		URI uri = URI.create("http://demo.twilio.com/docs/classic.mp3");
		Call call = Call.creator(to_number, from_number, uri).create(client);
		return new ResponseEntity<Object>("Call has initiated successfully and call SID is:"+call.getSid(), HttpStatus.OK);
	}

}
