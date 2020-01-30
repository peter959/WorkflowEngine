package com.mycompany.engine.engine;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="MSC")
@RibbonClient(name="MSC")
public interface MSCEntityProxy extends EntityProxy {
	@GetMapping("/MSC")
	public Boolean run();
}