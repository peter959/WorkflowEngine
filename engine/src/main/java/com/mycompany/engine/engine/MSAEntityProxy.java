package com.mycompany.engine.engine;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="MSA")
@RibbonClient(name="MSA")
public interface MSAEntityProxy extends EntityProxy {
	@GetMapping("/MSA")
	public MSResult run();
}