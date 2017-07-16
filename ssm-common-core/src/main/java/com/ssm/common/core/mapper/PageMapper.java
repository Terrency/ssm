package com.ssm.common.core.mapper;

import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.Pageable;

public interface PageMapper {

    <T> Page<T> selectPage(Pageable pageable);

}
