package com.ssm.common.core.mapper;

import com.ssm.common.page.Page;
import com.ssm.common.page.Pageable;

public interface PageMapper {

    <T> Page<T> selectPage(Pageable pageable);

}
