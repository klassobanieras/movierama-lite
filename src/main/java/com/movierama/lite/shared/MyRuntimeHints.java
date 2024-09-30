package com.movierama.lite.shared;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MyRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		Method method = ReflectionUtils.findMethod(HashMap.class, "get", Object.class);
		hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
	}

}