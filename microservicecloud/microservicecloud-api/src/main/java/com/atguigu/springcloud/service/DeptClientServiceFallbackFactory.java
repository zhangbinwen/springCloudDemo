package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.DeptModel;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // 不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>
{
	@Override
	public DeptClientService create(Throwable throwable)
	{
		return new DeptClientService() {
			@Override
			public DeptModel get(long id)
			{
				System.out.println("---------降级-------------");
				return new DeptModel().setDeptno(id).setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
						.setDbSource("no this database in MySQL");
			}

			@Override
			public List<DeptModel> list()
			{
				return null;
			}

			@Override
			public boolean add(DeptModel dept)
			{
				return false;
			}
		};
	}
}
