package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.truffle.js.runtime.JSContextOptions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

public class DemoApplication {

	public static void main(String... args) throws Exception {
		var context = Context.newBuilder("js")
				.allowHostAccess(HostAccess.ALL)
				.allowIO(true)
				.allowHostClassLookup(s -> true)
				.option(JSContextOptions.ECMASCRIPT_VERSION_NAME, "2022")
				.build();
		context.eval("js", Files.readString(Paths.get(System.getenv("PATH_TO_SCRIPT")), StandardCharsets.UTF_8));
		var scriptInstance = context.eval("js", "new Script()");
		var processMethod = scriptInstance.getMember("process");
		var payload = new ObjectMapper().writeValueAsString(new TestEvent("hello"));
		processMethod.execute(payload);
		System.exit(0);
	}

	static class TestEvent {
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
