package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.truffle.js.runtime.JSContextOptions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

@SpringBootApplication
public class DemoApplication {

	@Value("${application.script.pathToScript}")
	private String pathToScript;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Context context() {
		return Context.newBuilder("js")
				.allowHostAccess(HostAccess.ALL)
				.allowIO(true)
				.allowHostClassLookup(s -> true)
				.option(JSContextOptions.ECMASCRIPT_VERSION_NAME, "2022")
				.build();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runScript() throws Exception {
		var context = context();
		context.eval("js", Files.readString(Paths.get(pathToScript), StandardCharsets.UTF_8));
		var scriptInstance = context.eval("js", "new Script()");
		var processMethod = scriptInstance.getMember("process");
		var payload = new ObjectMapper().writeValueAsString(new TestEvent("hello"));
		processMethod.execute(payload);
		System.exit(0);
	}

	class TestEvent {
		String name;

		public TestEvent(String name) {
			this.name = name;
		}

		public TestEvent() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
