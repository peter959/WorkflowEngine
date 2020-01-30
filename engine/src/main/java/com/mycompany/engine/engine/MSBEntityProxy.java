package com.mycompany.engine.engine;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="MSB")
@RibbonClient(name="MSB")
public interface MSBEntityProxy extends EntityProxy {
	@GetMapping("/MSB")
	public MSResult run();
}