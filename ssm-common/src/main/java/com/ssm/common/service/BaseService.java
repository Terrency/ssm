package com.ssm.common.service;

import com.ssm.common.model.Model;
import com.ssm.common.validation.Groups;
import com.ssm.common.validation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

public interface BaseService<T extends Model> {

    int add(@NotNull @Validated({Groups.Add.class}) T model);

    int add(@Size(min = 1) @Validated({Groups.Add.class}) Collection<T> collection);

    int update(@NotNull @Validated({Groups.Update.class}) T model);

    int delete(@NotNull Long id);

    int delete(@Size(min = 1) Long[] ids);

    T getById(@NotNull Long id);

}
