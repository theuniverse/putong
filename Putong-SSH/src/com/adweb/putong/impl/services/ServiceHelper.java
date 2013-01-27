package com.adweb.putong.impl.services;

import java.util.Collections;
import java.util.List;

import com.iiiss.template.ssh.common.core.IBean;

public class ServiceHelper {
	public static <T extends IBean> List<T> getRange(List<T> list,
			Integer sindex, Integer eindex, boolean sort, boolean reverse) {
		if (sort)
			Collections.sort(list);
		if (reverse)
			Collections.reverse(list);

		if (sindex == null || sindex < 0)
			sindex = 0;
		if (eindex == null || eindex > list.size())
			eindex = list.size();

		if (sindex > eindex)
			return Collections.emptyList();
		else
			return list.subList(sindex, eindex);
	}
}
